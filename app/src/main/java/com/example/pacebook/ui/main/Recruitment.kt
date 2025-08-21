package com.example.pacebook.ui.main

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.pacebook.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar
import android.util.Log

class Recruitment : Fragment(R.layout.fragment_recruitment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("RecruitmentFragment", "onViewCreated 시작!")


        val toolbar = view.findViewById<MaterialToolbar>(R.id.recruitment_appbar_MT)
        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val startTimeLayout = view.findViewById<TextInputLayout>(R.id.recruitment_starttimebox_IL)
        val endTimeLayout = view.findViewById<TextInputLayout>(R.id.recruitment_endtimebox_IL)


        val startTimeEditText = view.findViewById<TextInputEditText>(R.id.recruitment_starttime_TI)
        val endTimeEditText = view.findViewById<TextInputEditText>(R.id.recruitment_endtime_TI)

        Log.d("RecruitmentFragment", "startTimeLayout is null: ${startTimeLayout == null}")
        Log.d("RecruitmentFragment", "endTimeLayout is null: ${endTimeLayout == null}")

        startTimeLayout.setOnClickListener {
            showTimePickerDialog(startTimeEditText) // 함수 호출은 EditText를 넘겨줍니다.
            Log.d("RecruitmentFragment", "Start Time Layout 클릭됨!")
        }
        endTimeLayout.setOnClickListener {
            showTimePickerDialog(endTimeEditText)
            Log.d("RecruitmentFragment", "End Time Layout 클릭됨!")
        }
        Log.d("RecruitmentFragment", "Click Listener 설정 완료")
    }

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
}