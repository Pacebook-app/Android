package com.example.pacebook.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.pacebook.databinding.ImagePickerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ImagePickerBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: ImagePickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ImagePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // '기본 이미지 선택' 버튼 클릭
        binding.imagepickerStandardMb.setOnClickListener {
            // "requestKey"를 통해 결과를 전달하고, "bundleKey"로 어떤 버튼이 눌렸는지 알림
            setFragmentResult("imagePicker", bundleOf("action" to "default"))
            dismiss() // 바텀 시트 닫기
        }

        // '앨범에서 선택' 버튼 클릭
        binding.imagepickerChooseMb.setOnClickListener {
            setFragmentResult("imagePicker", bundleOf("action" to "album"))
            dismiss()
        }

        // '취소' 버튼 클릭
        binding.imagepickerCancelMb.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}