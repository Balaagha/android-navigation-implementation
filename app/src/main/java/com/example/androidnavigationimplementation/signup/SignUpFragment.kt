package com.example.androidnavigationimplementation.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.androidnavigationimplementation.R
import com.example.androidnavigationimplementation.common.ui.model.AuthUserView
import com.example.androidnavigationimplementation.databinding.FragmentSignupBinding
import com.example.androidnavigationimplementation.login.LoginFragmentDirections
import com.example.androidnavigationimplementation.welcome.UserInfoModel

class SignUpFragment : Fragment() {

    private lateinit var signUpViewModel: SignUpViewModel
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private var user = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpViewModel = ViewModelProvider(this, SignUpViewModelFactory())
            .get(SignUpViewModel::class.java)

        binding.welcome.isEnabled = false

        signUpViewModel.signupFormState.observe(viewLifecycleOwner,
            Observer { signupFormState ->
                if (signupFormState == null) {
                    return@Observer
                }
                binding.signup.isEnabled = signupFormState.isDataValid
                signupFormState.fullnameError?.let {
                    binding.fullname.error = getString(it)
                }
                signupFormState.usernameError?.let {
                    binding.username.error = getString(it)
                }
                signupFormState.passwordError?.let {
                    binding.password.error = getString(it)
                }
            })

        signUpViewModel.signupResult.observe(viewLifecycleOwner,
            Observer { signupResult ->
                signupResult ?: return@Observer
                binding.loading.visibility = View.GONE
                signupResult.error?.let {
                    showSignUpFailed(it)
                }
                signupResult.success?.let {
                    signUpSuccessAction(it)
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                signUpViewModel.signupDataChanged(
                    binding.fullname.text.toString(),
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            }
        }
        binding.username.addTextChangedListener(afterTextChangedListener)
        binding.password.addTextChangedListener(afterTextChangedListener)

        binding.signup.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            initSignUp()
        }

        binding.welcome.setOnClickListener { v ->
            val action = SignUpFragmentDirections.actionSingUpToWelcome(
                user,
                getString(R.string.action_logged_in),
                UserInfoModel(user)
            )
            v.findNavController().navigate(action)
        }
    }

    private fun initSignUp() {
        signUpViewModel.signup(
            binding.fullname.text.toString(),
            binding.username.text.toString(),
            binding.password.text.toString()
        )
    }

    private fun signUpSuccessAction(model: AuthUserView) {
        binding.signup.isEnabled = false
        binding.welcome.isEnabled = true
        user = model.displayName
        val welcome = getString(R.string.welcome) + " " + model.displayName
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
    }

    private fun showSignUpFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}