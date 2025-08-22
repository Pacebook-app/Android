package com.example.pacebook.ui.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pacebook.R
import com.example.pacebook.databinding.ChooseMateBinding
import com.example.pacebook.ui.model.User

class ChooseMateAdapter(
    private val userList: List<User>,
    private val onMateSelected: (User) -> Unit
) : RecyclerView.Adapter<ChooseMateAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ChooseMateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            // 1. 표준 User 데이터 클래스의 변수 이름을 사용합니다.
            binding.chooseMateLocationTv.text = user.name
            binding.chooseMateCountTv.text = "${user.participantCount}명"

            // 2. 이미지를 설정하고, 0일 경우 기본 이미지를 보여줍니다.
            if (user.profileImage != 0) {
                binding.chooseMateImageIv.setImageResource(user.profileImage)
            } else {
                binding.chooseMateImageIv.setImageResource(R.drawable.ic_launcher_background) // 원하는 기본 이미지로 변경 가능
            }

            // 3. 아이템 뷰에 클릭 리스너를 설정합니다. (작성하신 코드 그대로, 아주 좋습니다!)
            itemView.setOnClickListener {
                onMateSelected(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChooseMateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    // 'bind' 함수 안에서 직접 처리하므로 이 함수는 더 이상 필요 없습니다.
    // private fun Int?.ifNullUseDefault(): Int { ... }
}