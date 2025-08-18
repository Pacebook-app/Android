package com.example.pacebook.ui.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pacebook.databinding.ChooseMateBinding
import com.example.pacebook.ui.model.User
import com.example.pacebook.R

class ChooseMateAdapter(
    private val userList: List<User>,
    private val onMateSelected: (User) -> Unit
) : RecyclerView.Adapter<ChooseMateAdapter.ChooseMateViewHolder>() {

    inner class ChooseMateViewHolder(private val binding: ChooseMateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.chooseMateImageIv.setImageResource(user.imageResId.ifNullUseDefault())
            binding.chooseMateLocationTv.text = user.place
            binding.chooseMateCountTv.text = "${user.participantCount}명"

            // 선택 시 콜백
            binding.root.setOnClickListener {
                onMateSelected(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseMateViewHolder {
        val binding = ChooseMateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChooseMateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChooseMateViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    // 기본 이미지 처리
    private fun Int?.ifNullUseDefault(): Int {
        return this ?: R.drawable.ic_launcher_background
    }
}
