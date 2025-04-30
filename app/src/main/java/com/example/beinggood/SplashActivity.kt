package com.example.beinggood

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

// 앱 시작 시 가장 먼저 실행되는 화면
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        //activity_splash.xml를 이 activity에 연결
        setContentView(R.layout.activity_splash)

        //handler를 통해 3초 후 다음 화면으로 이동
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent) //화면전환
            finish() //이 액티비티는 종료. 뒤로가기를 해도 못돌아옴
        }, 3000) //3000ms = 3sec
    }
}