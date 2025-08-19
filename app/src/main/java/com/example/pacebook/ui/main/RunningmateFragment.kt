package com.example.pacebook.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pacebook.databinding.FragmentRunningmateBinding
import com.example.pacebook.ui.RecyclerView.ChooseMateAdapter
import com.example.pacebook.ui.RecyclerView.SelectedMateAdapter
import com.example.pacebook.ui.model.User
import com.example.pacebook.R

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

        // 상단 RecyclerView (선택된 메이트)
        selectedMateAdapter = SelectedMateAdapter(selectedUsers)
        binding.runningmateSelectedlistRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.runningmateSelectedlistRv.adapter = selectedMateAdapter

        // 하단 RecyclerView (전체 메이트 목록)
        chooseMateAdapter = ChooseMateAdapter(allUsers) { user ->
            if (!selectedUsers.contains(user)) {
                selectedUsers.add(user)
                selectedMateAdapter.notifyItemInserted(selectedUsers.size - 1)
                updateMateTitle()
            }
        }

        binding.runninmateChooselistRv.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.runninmateChooselistRv.adapter = chooseMateAdapter

        // 테스트용 더미 데이터
        populateDummyData()
    }

    private fun populateDummyData() {
        allUsers.addAll(
            listOf(
                User("한양대 정문", 5, R.drawable.example_picture),
                User("한대앞역", 3, R.drawable.example_picture),
                User("파리바게트 앞", 4, R.drawable.example_picture),
                User("후문 놀이터", 2, R.drawable.example_picture),
                User( "안산천" , 6, R.drawable.example_picture),
                User( "호수공원" , 7, R.drawable.example_picture),
                User( "상록수 근처" , 8, R.drawable.example_picture)
            )
        )
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