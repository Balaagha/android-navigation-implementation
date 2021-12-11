package com.example.androidnavigationimplementation.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidnavigationimplementation.R
import com.example.androidnavigationimplementation.databinding.FragmentWelcomeBinding


/**
 * A simple [Fragment] subclass.
 * Use the [WelcomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    private var param1: String? = null
    private var param2: String? = null
    private var param3: UserInfoModel? = null

    companion object {
        @JvmStatic
        fun newInstance() = WelcomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { arguments ->
            val safeArgs = WelcomeFragmentArgs.fromBundle(arguments)
            param1 = safeArgs.param1
            param2 = safeArgs.param2
            param3 = safeArgs.param3
        }
        binding.txtWelcome.text =
            String.format(resources.getString(R.string.welcome_x_y), param1, param2)
        param3?.let {
            Toast.makeText(requireContext(),it.userName,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}