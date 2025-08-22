package com.example.pacebook.ui.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pacebook.R
import com.example.pacebook.databinding.SelectedMateBinding
import com.example.pacebook.ui.model.User

// 생성자에 '아이템이 클릭되었을 때 실행할 함수'를 전달받도록 추가합니다.
class SelectedMateAdapter(
    private val userList: List<User>,
    private val onItemClicked: (User) -> Unit // 클릭된 User 객체를 전달
) : RecyclerView.Adapter<SelectedMateAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: SelectedMateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            // 1. 표준화된 User 데이터 클래스의 변수 이름을 사용합니다.
            binding.selectedMateLocationTv.text = user.name
            binding.selectedMateCountTv.text = "${user.participantCount}명"

            // 2. 이미지를 설정하고, null일 경우 기본 이미지를 보여줍니다.
            if (user.profileImage != 0) {
                binding.selectedMateImageIv.setImageResource(user.profileImage)
            } else {
                binding.selectedMateImageIv.setImageResource(R.drawable.ic_launcher_background) // 원하는 기본 이미지로 변경 가능
            }

            // 3. 아이템 뷰에 클릭 리스너를 설정합니다.
            itemView.setOnClickListener {
                onItemClicked(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SelectedMateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size
}