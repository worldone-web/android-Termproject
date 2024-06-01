package com.example.studyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapp.databinding.DiaryDetailItemBinding

class DiaryDetailAdapter(private val context: Context, private val details: List<String>) :
    RecyclerView.Adapter<DiaryDetailAdapter.ViewHolder>() {

    // ViewHolder 클래스 정의
    inner class ViewHolder(private val binding: DiaryDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // bind 메서드 정의: 데이터를 ViewHolder의 레이아웃에 바인딩
        fun bind(detail: String) {
            binding.detailTextView.text = detail
        }
    }

    // onCreateViewHolder 메서드 정의: ViewHolder를 생성하고 바인딩
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DiaryDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // onBindViewHolder 메서드 정의: RecyclerView의 아이템에 데이터를 바인딩
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detail = details[position]
        holder.bind(detail)
    }

    // getItemCount 메서드 정의: 데이터의 총 개수를 반환
    override fun getItemCount(): Int = details.size
}
