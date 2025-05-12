package com.example.beinggood.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WorkoutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("오늘은 어떤 운동을 할까요?", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("타겟 부위를 모두 선택해주세요", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(24.dp))

        val buttons = listOf("가슴", "팔", "어깨", "등", "하체", "코어")
        buttons.chunked(3).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { label ->
                    Button(onClick = { /* TODO: 상태 저장 */ }) {
                        Text(label)
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { /* TODO: 운동 시작 */ }) {
            Text("운동하기")
        }
    }
}
