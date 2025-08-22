package com.example.pacebook.ui.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.pacebook.databinding.MateinformationBinding
import com.example.pacebook.ui.model.User // User 클래스를 import 해야 합니다.

class MateDetailsDialogFragment : DialogFragment() {

    private var _binding: MateinformationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MateinformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 전달받은 데이터로 UI 채우기
        val place = arguments?.getString("place")
        val time = arguments?.getString("time")
        val pace = arguments?.getString("pace")
        val intro = arguments?.getString("intro")
        val imageResId = arguments?.getInt("imageResId", 0) ?: 0
        val position = arguments?.getInt("position", -1)

        binding.mateinformationInputplaceTv.text = place
        binding.mateinformationInputtimeTv.text = time
        binding.mateinformationInputpaceTv.text = pace
        binding.mateinformationInputintroTv.text = intro

        if (imageResId != 0) {
            binding.mateinformationImageIv.setImageResource(imageResId)
        }

        // 취소 버튼
        binding.mateinformationCancelMb.setOnClickListener {
            dismiss() // 다이얼로그 닫기
        }

        // 탈퇴하기 버튼
        binding.mateinformationSecessionMb.setOnClickListener {
            // 어느 아이템을 삭제할지 position 정보와 함께 결과 전달
            setFragmentResult("leaveMate", bundleOf("position" to position))
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        // 다이얼로그의 배경을 투명하게 만듦
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 다이얼로그의 너비를 화면 너비의 90%로 설정
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)

        // 배경의 어두운 정도(Dim)를 조절 (0.0f ~ 1.0f)
        dialog?.window?.setDimAmount(0.6f) // 60% 어둡게
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        // 데이터를 전달받아 다이얼로그를 생성하는 함수
        fun newInstance(position: Int, user: User): MateDetailsDialogFragment {
            val fragment = MateDetailsDialogFragment()
            val args = Bundle()

            // [수정] User 객체의 모든 정보를 arguments에 담아 전달합니다.
            args.putString("place", user.name)
            args.putString("time", user.time)
            args.putString("pace", user.pace)
            args.putString("intro", user.intro)
            args.putInt("imageResId", user.profileImage)
            args.putInt("position", position)

            fragment.arguments = args
            return fragment
        }
    }
}