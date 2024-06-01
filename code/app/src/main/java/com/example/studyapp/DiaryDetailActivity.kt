package com.example.studyapp

// DiaryDetailActivity.kt
import android.widget.Toast
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studyapp.databinding.ActivityDiaryDetailBinding

class DiaryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiaryDetailBinding
    private var entryId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        entryId = intent.getLongExtra("entryId", -1) //entryid 값 가져오기


        // Database에서 해당 id값을 가진 diary 가져오기
        val dbHelper = DatabaseHelper(this)
        val entry = dbHelper.getDiaryEntry(entryId)
        dbHelper.close()

        if (entry != null) { // null이 아닌경우 값 넘겨주기

            binding.titleTextView.text = entry.title
            binding.contentTextView.text = entry.content


        } else {
            Toast.makeText(this, "Diary entry not found", Toast.LENGTH_SHORT).show()
            finish()
        }

        Toast.makeText(this, binding.titleTextView.text, Toast.LENGTH_SHORT).show()
        Toast.makeText(this, binding.contentTextView.text, Toast.LENGTH_SHORT).show()
    }
}
