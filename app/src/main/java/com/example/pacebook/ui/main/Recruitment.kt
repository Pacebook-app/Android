package com.example.pacebook.ui.main

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pacebook.databinding.FragmentRecruitmentBinding // ★★★ import 변경 ★★★
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar
import android.widget.Toast
import androidx.core.os.bundleOf

class Recruitment : Fragment() {

    // 1. View Binding을 위한 변수 선언 (RunningmateFragment와 동일한 방식)
    private var _binding: FragmentRecruitmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 2. View Binding으로 레이아웃 인플레이트
        _binding = FragmentRecruitmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.setFragmentResultListener("imagePicker", this) { requestKey, bundle ->
            val result = bundle.getString("action")
            when (result) {
                "default" -> {
                    Toast.makeText(requireContext(), "기본 이미지 선택", Toast.LENGTH_SHORT).show()
                    // TODO: 기본 이미지를 보여주는 로직 구현
                }
                "album" -> {
                    Toast.makeText(requireContext(), "앨범에서 선택", Toast.LENGTH_SHORT).show()
                    // TODO: 갤러리(앨범)를 여는 로직 구현
                }
            }
        }

        binding.recruitmentMakebuttonMB.setOnClickListener {
            // 1. 입력된 정보들을 가져옵니다.
            val place = binding.recruitmentPlacetextTI.text.toString()
            val intro = binding.recruitmentIntrotextTI.text.toString()
            val startTime = binding.recruitmentStarttimeTI.text.toString()
            val endTime = binding.recruitmentEndtimeTI.text.toString() // 예시

            // 2. Bundle에 데이터를 담습니다.
            val result = bundleOf(
                "place" to place,
                "intro" to intro,
                "time" to "$startTime ~ $endTime"
            )
            parentFragmentManager.setFragmentResult("newMatePost", result)

            // 4. 현재 프래그먼트를 닫고 이전 화면으로 돌아갑니다.
            parentFragmentManager.popBackStack()
        }

        binding.recruitmentPicturebuttonAB.setOnClickListener {
            val bottomSheet = ImagePickerBottomSheetDialog()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }

        // 3. 모든 뷰 접근을 'binding.ID' 방식으로 변경
        // --- 툴바 뒤로가기 기능 ---
        binding.recruitmentAppbarMT.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // --- 시간 선택 기능 ---
        binding.recruitmentStarttimeclickareaV.setOnClickListener {
            showTimePickerDialog(binding.recruitmentStarttimeTI)
        }
        binding.recruitmentEndtimeclickareaV.setOnClickListener {
            showTimePickerDialog(binding.recruitmentEndtimeTI)
        }

    }

    // 시간 선택 다이얼로그를 보여주는 함수 (수정 없음)
    private fun showTimePickerDialog(targetEditText: TextInputEditText) {
        val cal = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                val ampm = if (hourOfDay < 12) "오전" else "오후"
                val formattedHour = if (hourOfDay == 0 || hourOfDay == 12) 12 else hourOfDay % 12
                val formattedMinute = String.format("%02d", minute)
                val selectedTime = "$ampm $formattedHour:$formattedMinute"

                targetEditText.setText(selectedTime)
            },
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            false
        )
        timePickerDialog.show()
    }

    // 4. Fragment가 파괴될 때 binding을 정리 (메모리 누수 방지)
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}