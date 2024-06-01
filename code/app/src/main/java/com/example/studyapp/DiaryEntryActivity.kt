// DiaryEntryActivity.kt

package com.example.studyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studyapp.databinding.ActivityDiaryEntryBinding

// DiaryEntryActivity 클래스는 다이어리를 생성합니다.
class DiaryEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiaryEntryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiaryEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.saveButton.setOnClickListener {
            // 다이어리 항목을 저장하는 메서드 호출
            saveDiaryEntry()
        }
    }

    // 다이어리 항목을 저장하는 메서드
    private fun saveDiaryEntry() {
        // 입력된 제목과 내용을 가져옴
        val title = binding.titleEditText.text.toString()
        val text = binding.textEditText.text.toString()

        // SQLite 데이터베이스에 저장
        val dbHelper = DatabaseHelper(this)
        dbHelper.insertDiaryEntry(title, text)
        dbHelper.close()

        // 호출한 액티비티에 결과를 알리기 위해 Intent 설정
        val resultIntent = Intent()
        setResult(RESULT_OK, resultIntent)

        // 현재 액티비티 종료
        finish()
    }
}
