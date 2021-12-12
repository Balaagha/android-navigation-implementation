package com.example.androidnavigationimplementation.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.androidnavigationimplementation.R
import com.example.androidnavigationimplementation.databinding.FragmentEditProfileBinding
import com.example.androidnavigationimplementation.ui.viewmodel.LettersViewModel
import kotlinx.android.synthetic.main.fragment_edit_profile.*

class EditProfileFragment : DialogFragment() {

    private val lettersViewModel: LettersViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding: FragmentEditProfileBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_profile, container, false
        )
        binding.viewModel = lettersViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener {
            dialog?.setTitle(getString(R.string.edit_profile))
            dialog?.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        btnSave.setOnClickListener {
            lettersViewModel?.saveProfile()
            dismiss()
        }

    }
}
