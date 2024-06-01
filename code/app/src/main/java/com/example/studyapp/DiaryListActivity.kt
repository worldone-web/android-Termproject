// DiaryListActivity.kt
package com.example.studyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studyapp.databinding.ActivityDiaryListBinding

class DiaryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiaryListBinding
    private lateinit var diaryListAdapter: DiaryListEntryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // DatabaseHelper를 사용하여 모든 일기 항목을 가져옵니다.
        val dbHelper = DatabaseHelper(this)
        val entries = dbHelper.getAllDiaryEntries()
        dbHelper.close()

        // DiaryListEntryAdapter를 초기화하고 RecyclerView에 설정합니다.
        diaryListAdapter = DiaryListEntryAdapter(
            this,
            entries,
            onDeleteClickListener = { entry -> onDeleteClicked(entry) },
            onItemClickListener = { entry -> onItemClicked(entry) }
        )

        binding.diaryRecyclerView.adapter = diaryListAdapter
        binding.diaryRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    // 일기장 항목을 클릭했을 때 호출되는 함수
    private fun onItemClicked(entry: DiaryEntry) {
        // 각각의 일기장을 클릭하면 상세 화면으로 이동합니다. DiaryDetailActivity로 전환
        val intent = Intent(this, DiaryDetailActivity::class.java)
        intent.putExtra("entryId", entry.id) // id값을 넘겨줘서 그 값을 통해 title, content를 가져옴
        startActivity(intent)
    }

    // 일기 항목 삭제 버튼을 클릭했을 때 호출되는 함수
    private fun onDeleteClicked(entry: DiaryEntry) {
        // 삭제 버튼 클릭 시, 확인 다이얼로그를 표시하고 사용자가 확인하면 항목을 삭제합니다.
        // 확인 없이 직접 항목을 삭제할 수도 있습니다.
        // 업데이트: 데이터베이스에서 항목을 삭제하는 코드 추가
        val dbHelper = DatabaseHelper(this)
        dbHelper.deleteDiaryEntry(entry.id)
        dbHelper.close()

        // UI를 갱신하여 목록을 새로 고침합니다.
        refreshDiaryList()

        // 사용자에게 삭제 메시지를 표시합니다.
        Toast.makeText(this, "일기장 삭제되었습니다.", Toast.LENGTH_SHORT).show()
    }

    // 목록을 갱신하는 함수
    private fun refreshDiaryList() {
        // DatabaseHelper를 사용하여 모든 일기 항목을 가져옵니다.
        val dbHelper = DatabaseHelper(this)
        val entries = dbHelper.getAllDiaryEntries()
        dbHelper.close()

        // 어댑터를 새로운 항목 목록으로 갱신합니다.
        diaryListAdapter.updateEntries(entries)
    }
}
