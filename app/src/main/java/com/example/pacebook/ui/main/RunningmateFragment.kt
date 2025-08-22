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

        // RunningmateFragment.kt 파일의 onViewCreated
        parentFragmentManager.setFragmentResultListener("newMatePost", this) { requestKey, bundle ->
            // 1. 전달받은 데이터들을 꺼냅니다.
            val place = bundle.getString("place", "알 수 없는 장소")
            val intro = bundle.getString("intro", "")
            val time = bundle.getString("time", "")
            // val pace = bundle.getString("pace", "") // 페이스는 나중에 추가

            // 2. 새 User 객체를 만듭니다.
            val newUser = User(
                name = place,
                intro = intro,
                time = time,
                // pace = "N/A", // 임시값
                participantCount = 1, // 처음 만든 사람이므로 1명
                profileImage = R.drawable.example_picture // TODO: 선택된 이미지로 교체
            )

            // 3. 전체 메이트 목록에 새로 만든 User를 추가합니다.
            allUsers.add(0, newUser) // 0번 인덱스에 추가해서 맨 앞에 보이게 함

            // 4. 어댑터에게 새 아이템이 추가되었음을 알립니다.
            chooseMateAdapter.notifyItemInserted(0)
            selectedUsers.add(0, newUser)
            selectedMateAdapter.notifyItemInserted(0)
            updateMateTitle()
            updateEmptyViewVisibility()
        }



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
                    User("한양대 정문", 5, R.drawable.example_picture, "오후 7시", "정문에서 만나요!"),
                    User("한대앞역", 3, R.drawable.example_picture, "오후 8시", "역 앞에서 바로 출발합니다."),
                    User("파리바게트 앞", 4, R.drawable.example_picture, "오전 6시", "아침 운동하실 분!"),
                    User("후문 놀이터", 2, R.drawable.example_picture, "오후 9시", "천천히 뛰어요."),
                    User("안산천", 6, R.drawable.example_picture, "주말 오후", "페이스 좋으신 분 환영"),
                    User("호수공원", 7, R.drawable.example_picture, "아무때나", "같이 뛰어요~"),
                    User("상록수 근처", 8, R.drawable.example_picture, "오후 7시",  "퇴근 후 러닝!")
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