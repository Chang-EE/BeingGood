package com.example.beinggood.ui.screen

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beinggood.ui.model.ExercisePlan

@Composable
fun WorkoutScreen() {
    var isEditingRoutine by remember { mutableStateOf(false) }  // 운동 계획을 설정할지 말지
    var isDoingWorkout by remember { mutableStateOf(false) }     // 운동을 진행할지 말지
    var selectedParts by remember { mutableStateOf<List<String>>(emptyList()) } // 선택된 부위
    var selectedPlans by remember { mutableStateOf<List<ExercisePlan>>(emptyList()) } // 운동 계획 리스트

    when {
        isDoingWorkout -> { // 운동 중일 때
            PoseCameraScreen(
                exerciseName = selectedPlans.firstOrNull()?.name ?: "",  // 운동 이름
                onFinish = {
                    // 운동 종료 후
                    isDoingWorkout = false
                    isEditingRoutine = false
                    selectedPlans = emptyList()
                }
            )
        }
        isEditingRoutine -> { // 운동 계획 설정 중일 때
            RoutineEditScreen(
                selectedBodyParts = selectedParts,
                onStartWorkout = { plans ->
                    // 운동 계획이 설정되면 운동 시작
                    selectedPlans = plans
                    isDoingWorkout = true
                }
            )
        }
        else -> { // 운동 부위 선택 화면
            BodyPartSelectionScreen(
                onStartRoutine = { selected ->
                    selectedParts = selected // 부위 선택 저장
                    isEditingRoutine = true   // 운동 계획 설정 화면으로 이동
                }
            )
        }
    }
}