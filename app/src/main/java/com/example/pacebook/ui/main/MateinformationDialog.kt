package com.example.pacebook.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.pacebook.R
import com.example.pacebook.databinding.MateinformationBinding
import com.example.pacebook.ui.model.User

class MateDetailsDialogFragment : DialogFragment() {

    private var _binding: MateinformationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyRoundedDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MateinformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isMember = arguments?.getBoolean("isMember", false) ?: false
        val user = arguments?.getParcelable<User>("user")

        binding.mateinformationInputplaceTv.text = user?.name
        binding.mateinformationInputtimeTv.text = user?.time
        binding.mateinformationInputintroTv.text = user?.intro
        user?.profileImage?.let { binding.mateinformationImageIv.setImageResource(it) }

        binding.mateinformationNegativeBtn.setOnClickListener { dismiss() }

        if (isMember) {
            if (user?.isCreator == true) {
                binding.mateinformationPositiveBtn.text = "삭제하기"
                binding.mateinformationPositiveBtn.setOnClickListener {
                    setFragmentResult("deleteMate", bundleOf("user" to user))
                    dismiss()
                }
            } else {
                binding.mateinformationPositiveBtn.text = "탈퇴하기"
                binding.mateinformationPositiveBtn.setOnClickListener {
                    setFragmentResult("leaveMate", bundleOf("user" to user))
                    dismiss()
                }
            }
        } else {
            binding.mateinformationPositiveBtn.text = "가입하기"
            binding.mateinformationPositiveBtn.setOnClickListener {
                setFragmentResult("joinMate", bundleOf("user" to user))
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(user: User, isMember: Boolean): MateDetailsDialogFragment {
            val fragment = MateDetailsDialogFragment()
            val args = Bundle()
            args.putParcelable("user", user)
            args.putBoolean("isMember", isMember)
            fragment.arguments = args
            return fragment
        }
    }
}