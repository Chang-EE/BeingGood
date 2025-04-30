/*
package com.example.beinggood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.beinggood.ui.theme.BeingGoodTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BeingGoodTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BeingGoodTheme {
        Greeting("Android")
    }
}
*/

package com.example.beinggood

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val workoutBtn = findViewById<Button>(R.id.tabWorkout)
        val feedbackBtn = findViewById<Button>(R.id.tabFeedback)
        val profileBtn = findViewById<Button>(R.id.tabProfile)

        workoutBtn.setOnClickListener {
            Toast.makeText(this, "운동 탭 클릭됨", Toast.LENGTH_SHORT).show()
        }

        feedbackBtn.setOnClickListener {
            Toast.makeText(this, "피드백 탭 클릭됨", Toast.LENGTH_SHORT).show()
        }

        profileBtn.setOnClickListener {
            Toast.makeText(this, "프로필 탭 클릭됨", Toast.LENGTH_SHORT).show()
        }
    }
}
