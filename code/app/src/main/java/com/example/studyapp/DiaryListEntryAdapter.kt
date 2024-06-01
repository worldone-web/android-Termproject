// DiaryListEntryAdapter.kt
package com.example.studyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapp.databinding.DiaryEntryListItemBinding

class DiaryListEntryAdapter(
    private val context: Context,
    private var entries: List<DiaryEntry>,
    private val onDeleteClickListener: (DiaryEntry) -> Unit,
    private val onItemClickListener: (DiaryEntry) -> Unit
) : RecyclerView.Adapter<DiaryListEntryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: DiaryEntryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // 기존의 클릭 이벤트 유지
            itemView.setOnClickListener {
                val entry = entries[bindingAdapterPosition]
                onItemClickListener.invoke(entry)
            }
            // 추가된 "X" 버튼 클릭 이벤트 처리
            binding.deleteButton.setOnClickListener {
                val entry = entries[bindingAdapterPosition]
                onDeleteClickListener.invoke(entry)
            }
        }
        // 데이터 바인딩을 통해 뷰에 데이터를 연결
        fun bind(entry: DiaryEntry) {
            binding.entry = entry
            binding.executePendingBindings()
        }
    }

    // 외부에서 어댑터에 새로운 항목 목록을 전달하고 UI를 갱신하는 함수
    fun updateEntries(newEntries: List<DiaryEntry>) {
        entries = newEntries
        notifyDataSetChanged()
    }

    // 뷰홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 레이아웃 인플레이션을 통해 뷰홀더를 생성하고 반환
        val binding =
            DiaryEntryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // 뷰홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = entries[position]
        holder.bind(entry)
    }

    // 목록의 크기 반환
    override fun getItemCount(): Int = entries.size
}
