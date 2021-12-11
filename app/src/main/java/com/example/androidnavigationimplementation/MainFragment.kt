package com.example.androidnavigationimplementation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.androidnavigationimplementation.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener { v ->
            v.findNavController().navigate(R.id.actionLogin)
        }

        binding.btnSignUp.setOnClickListener { v ->
            v.findNavController().navigate(R.id.actionSingUp)
        }

        binding.fab.setOnClickListener {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}