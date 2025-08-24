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
import android.widget.Toast

class RunningmateFragment : Fragment() {

    private var _binding: FragmentRunningmateBinding? = null
    private val binding get() = _binding!!

    private lateinit var chooseMateAdapter: ChooseMateAdapter
    private lateinit var selectedMateAdapter: SelectedMateAdapter

    private val selectedUsers = mutableListOf<User>()
    private val allUsers = mutableListOf<User>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRunningmateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFragmentResultListeners()
        setupAdapters()

        binding.runningmateAddbutton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.main, Recruitment())
                addToBackStack(null)
                commit()
            }
        }

        populateDummyData()
        updateMateTitle()
        updateEmptyViewVisibility()
    }

    private fun setupFragmentResultListeners() {
        // 모집글 생성 시 위/아래 모두 추가
        parentFragmentManager.setFragmentResultListener("newMatePost", this) { _, bundle ->
            val newUser = User(
                name = bundle.getString("place", ""),
                intro = bundle.getString("intro", ""),
                time = bundle.getString("time", ""),
                pace = "N/A",
                participantCount = 1,
                profileImage = R.drawable.example_picture,
                isCreator = true
            )
            allUsers.add(0, newUser)
            chooseMateAdapter.notifyItemInserted(0)
            selectedUsers.add(0, newUser)
            selectedMateAdapter.notifyItemInserted(0)
            updateMateTitle()
            updateEmptyViewVisibility()
        }

        // 가입하기
        parentFragmentManager.setFragmentResultListener("joinMate", this) { _, bundle ->
            val userToJoin = bundle.getParcelable<User>("user")
            if (userToJoin != null && !selectedUsers.contains(userToJoin)) {
                selectedUsers.add(userToJoin)
                selectedMateAdapter.notifyItemInserted(selectedUsers.size - 1)
                updateMateTitle()
                updateEmptyViewVisibility()
            }
        }

        // 탈퇴하기 (위에서만 삭제)
        parentFragmentManager.setFragmentResultListener("leaveMate", this) { _, bundle ->
            val userToLeave = bundle.getParcelable<User>("user")
            if (userToLeave != null) {
                selectedUsers.remove(userToLeave)
                selectedMateAdapter.notifyDataSetChanged()
                updateMateTitle()
                updateEmptyViewVisibility()
            }
        }

        // 삭제하기 (위/아래 모두 삭제)
        parentFragmentManager.setFragmentResultListener("deleteMate", this) { _, bundle ->
            val userToDelete = bundle.getParcelable<User>("user")
            if (userToDelete != null) {
                allUsers.remove(userToDelete)
                selectedUsers.remove(userToDelete)
                chooseMateAdapter.notifyDataSetChanged()
                selectedMateAdapter.notifyDataSetChanged()
                updateMateTitle()
                updateEmptyViewVisibility()
            }
        }
    }

    private fun setupAdapters() {
        // 상단 '나의 메이트' 리스트: isMember = true
        selectedMateAdapter = SelectedMateAdapter(selectedUsers) { clickedUser ->
            val dialog = MateDetailsDialogFragment.newInstance(clickedUser, true)
            dialog.show(parentFragmentManager, "MateDetailsDialog")
        }
        binding.runningmateSelectedlistRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.runningmateSelectedlistRv.adapter = selectedMateAdapter

        chooseMateAdapter = ChooseMateAdapter(allUsers) { user ->
            // 만약 클릭된 user가 selectedUsers 리스트에 이미 포함되어 있다면
            if (selectedUsers.contains(user)) {
                // 이미 가입한 경우, 아무것도 하지 않거나 간단한 메시지 표시
                Toast.makeText(requireContext(), "이미 가입한 메이트입니다.", Toast.LENGTH_SHORT).show()
            } else {
                // 가입하지 않은 경우에만 '가입하기' 다이얼로그를 띄움
                val dialog = MateDetailsDialogFragment.newInstance(user, false)
                dialog.show(parentFragmentManager, "MateDetailsDialog")
            }
        }
        binding.runninmateChooselistRv.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.runninmateChooselistRv.adapter = chooseMateAdapter
    }
    private fun populateDummyData() {
        if (allUsers.isEmpty()) {
            allUsers.addAll(
                listOf(
                    User("한양대 정문", 5, R.drawable.example_picture, "오후 7시", "6'00\"", "정문에서 만나요!"),
                    User("한대앞역", 3, R.drawable.example_picture, "오후 8시", "5'30\"", "역 앞에서 바로 출발합니다."),
                    User("파리바게트 앞", 4, R.drawable.example_picture, "오전 6시", "6'30\"", "아침 운동하실 분!"),
                    User("호수공원", 7, R.drawable.example_picture, "아무때나", "자유", "같이 뛰어요~")
                )
            )
        }
        // 어댑터가 null이 아닐 때만 notifyDataSetChanged()를 호출
        if (::chooseMateAdapter.isInitialized) {
            chooseMateAdapter.notifyDataSetChanged()
        }
    }

    private fun updateMateTitle() {
        binding.runningmateTitleTv.text = "나의 메이트 (${selectedUsers.size})"
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}