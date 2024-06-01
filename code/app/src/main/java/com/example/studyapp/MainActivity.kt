package com.example.studyapp


import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studyapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.create.setOnClickListener {
            // 일기장 생성 버튼을 누르면 DiaryEntryActivity 시작
            val intent = Intent(this, DiaryEntryActivity::class.java)
            startActivity(intent)
        }

        binding.list.setOnClickListener {
            // 일기장 목록 버튼을 누르면 DiaryListActivity 시작
            val intent = Intent(this, DiaryListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}