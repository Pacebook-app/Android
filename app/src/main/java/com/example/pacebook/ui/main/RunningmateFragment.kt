package com.example.pacebook.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pacebook.R
import com.example.pacebook.databinding.FragmentRunningmateBinding
import com.example.pacebook.ui.RecyclerView.ChooseMateAdapter
import com.example.pacebook.ui.RecyclerView.SelectedMateAdapter
import com.example.pacebook.ui.model.User

class RunningmateFragment : Fragment() {

    private var _binding: FragmentRunningmateBinding? = null
    private val binding get() = _binding!!

    private lateinit var chooseMateAdapter: ChooseMateAdapter
    private lateinit var selectedMateAdapter: SelectedMateAdapter

    private val selectedUsers = mutableListOf<User>()
    private val allUsers = mutableListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRunningmateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // [수정] 다이얼로그에서 '탈퇴하기'를 눌렀을 때 결과를 받습니다.
        parentFragmentManager.setFragmentResultListener("leaveMate", this) { _, bundle ->
            val position = bundle.getInt("position")
            if (position != -1 && position < selectedUsers.size) {
                selectedUsers.removeAt(position)
                selectedMateAdapter.notifyItemRemoved(position)
                updateMateTitle()
                updateEmptyViewVisibility()
            }
        }

        // [수정] 상단 RecyclerView 어댑터 생성 시, 클릭 리스너를 함께 전달합니다.
        selectedMateAdapter = SelectedMateAdapter(selectedUsers) { clickedUser ->
            // 클릭된 아이템의 User 정보를 받아 다이얼로그를 생성하고 보여줍니다.
            val position = selectedUsers.indexOf(clickedUser)
            val dialog = MateDetailsDialogFragment.newInstance(position, clickedUser)
            dialog.show(parentFragmentManager, "MateDetailsDialog")
        }
        binding.runningmateSelectedlistRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.runningmateSelectedlistRv.adapter = selectedMateAdapter

        // 하단 RecyclerView (기존과 동일)
        chooseMateAdapter = ChooseMateAdapter(allUsers) { user ->
            if (!selectedUsers.contains(user)) {
                selectedUsers.add(user)
                selectedMateAdapter.notifyItemInserted(selectedUsers.size - 1)
                updateMateTitle()
                updateEmptyViewVisibility()
            }
        }
        binding.runninmateChooselistRv.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.runninmateChooselistRv.adapter = chooseMateAdapter

        // '+' 버튼 클릭 리스너 (기존과 동일)
        binding.runningmateAddbutton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.main, Recruitment())
                addToBackStack(null)
                commit()
            }
        }

        // UI 업데이트
        populateDummyData()
        updateMateTitle()
        updateEmptyViewVisibility()
    }

    private fun updateEmptyViewVisibility() {
        if (selectedUsers.isEmpty()) {
            binding.runningmateSelectedlistRv.visibility = View.GONE
            binding.runningmateEmptyTv.visibility = View.VISIBLE
        } else {
            binding.runningmateSelectedlistRv.visibility = View.VISIBLE
            binding.runningmateEmptyTv.visibility = View.GONE
        }
    }

    private fun populateDummyData() {
        if (allUsers.isEmpty()) {
            // [수정] 완성된 User 클래스에 맞게 더미 데이터를 채웁니다.
            allUsers.addAll(
                listOf(
                    User("한양대 정문", 5, R.drawable.example_picture, "오후 7시", "6'00\"", "정문에서 만나요!"),
                    User("한대앞역", 3, R.drawable.example_picture, "오후 8시", "5'30\"", "역 앞에서 바로 출발합니다."),
                    User("파리바게트 앞", 4, R.drawable.example_picture, "오전 6시", "6'30\"", "아침 운동하실 분!"),
                    User("후문 놀이터", 2, R.drawable.example_picture, "오후 9시", "7'00\"", "천천히 뛰어요."),
                    User("안산천", 6, R.drawable.example_picture, "주말 오후", "5'00\"", "페이스 좋으신 분 환영"),
                    User("호수공원", 7, R.drawable.example_picture, "아무때나", "자유", "같이 뛰어요~"),
                    User("상록수 근처", 8, R.drawable.example_picture, "오후 7시", "6'15\"", "퇴근 후 러닝!")
                )
            )
        }
        chooseMateAdapter.notifyDataSetChanged()
    }

    private fun updateMateTitle() {
        binding.runningmateTitleTv.text = "나의 메이트 (${selectedUsers.size})"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}