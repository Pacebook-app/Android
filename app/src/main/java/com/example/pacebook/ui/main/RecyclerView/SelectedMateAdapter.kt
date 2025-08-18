package com.example.pacebook.ui.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pacebook.databinding.SelectedMateBinding
import com.example.pacebook.ui.model.User
import com.example.pacebook.R

class SelectedMateAdapter(
    private val selectedList: List<User>
) : RecyclerView.Adapter<SelectedMateAdapter.SelectedMateViewHolder>() {

    inner class SelectedMateViewHolder(private val binding: SelectedMateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.selectedMateImageIv.setImageResource(user.imageResId.ifNullUseDefault())
            binding.selectedMateLocationTv.text = user.place
            binding.selectedMateCountTv.text = "${user.participantCount}명"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedMateViewHolder {
        val binding = SelectedMateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectedMateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectedMateViewHolder, position: Int) {
        holder.bind(selectedList[position])
    }

    override fun getItemCount(): Int = selectedList.size

    // 기본 이미지로 대체 (널 방지)
    private fun Int?.ifNullUseDefault(): Int {
        return this ?: R.drawable.ic_launcher_background
    }
}
