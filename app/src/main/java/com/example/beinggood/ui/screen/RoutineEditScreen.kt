package com.example.beinggood.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beinggood.ui.model.ExercisePlan
import com.example.beinggood.ui.model.ExerciseSet

@Composable
fun RoutineEditScreen(
    selectedBodyParts: List<String>,
    onStartWorkout: (List<ExercisePlan>) -> Unit
) {
    var exercises by remember { mutableStateOf(listOf<ExercisePlan>()) }
    var showDialog by remember { mutableStateOf(false) }
    var availableExercises by remember { mutableStateOf(listOf<String>()) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                availableExercises = getDummyExercisesFor(selectedBodyParts)
                showDialog = true
            }) {
                Text("+")
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("운동 계획을 설정하세요", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(exercises) { exercise ->
                    ExercisePlanItem(
                        plan = exercise,
                        onUpdate = { updated ->
                            exercises = exercises.map {
                                if (it.id == updated.id) updated else it
                            }
                        }
                    )
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    // TODO: PoseCameraScreen으로 이동
                    onStartWorkout(exercises)
                }
            ) {
                Text("운동 시작")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("운동을 선택하세요") },
            text = {
                if (availableExercises.isNotEmpty()) {
                    Column {
                        availableExercises.forEach { name ->
                            Text(
                                text = name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        exercises = exercises + ExercisePlan(name = name)
                                        showDialog = false
                                    }
                                    .padding(12.dp)
                            )
                        }
                    }
                } else {
                    Text("선택된 부위에 해당하는 운동이 없습니다.")
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("닫기")
                }
            }
        )
    }
}

fun getDummyExercisesFor(parts: List<String>): List<String> {
    val result = mutableListOf<String>()
    if ("하체" in parts) result += listOf("스쿼트")
    return result
}

@Composable
fun ExercisePlanItem(plan: ExercisePlan, onUpdate: (ExercisePlan) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFF0F0F0))
            .padding(12.dp)
    ) {
        Text(plan.name, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(8.dp))
        plan.sets.forEachIndexed { i, set ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("세트 ${i + 1}: ", modifier = Modifier.width(60.dp))

                OutlinedTextField(
                    value = set.weight.toString(),
                    onValueChange = {
                        val newWeight = it.toIntOrNull() ?: set.weight
                        onUpdate(plan.copy(sets = plan.sets.toMutableList().apply {
                            set(i, set.copy(weight = newWeight))
                        }))
                    },
                    label = { Text("무게(kg)") },
                    modifier = Modifier.width(120.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                OutlinedTextField(
                    value = set.reps.toString(),
                    onValueChange = {
                        val newReps = it.toIntOrNull() ?: set.reps
                        onUpdate(plan.copy(sets = plan.sets.toMutableList().apply {
                            set(i, set.copy(reps = newReps))
                        }))
                    },
                    label = { Text("횟수") },
                    modifier = Modifier.width(100.dp)
                )
            }
            Spacer(Modifier.height(8.dp))
        }

        Button(onClick = {
            onUpdate(plan.copy(sets = plan.sets + ExerciseSet()))
        }) {
            Text("+ 세트 추가")
        }
    }
}