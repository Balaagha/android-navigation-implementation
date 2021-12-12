package com.example.androidnavigationimplementation.ui.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.androidnavigationimplementation.R
import com.example.androidnavigationimplementation.databinding.FragmentCreateLetterBinding
import com.example.androidnavigationimplementation.ui.viewmodel.LettersViewModel
import com.example.androidnavigationimplementation.utils.extension.Event
import com.example.androidnavigationimplementation.utils.extension.hideKeyboard

class CreateLetterFragment : Fragment() {

    private val lettersViewModel: LettersViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        setHasOptionsMenu(true)

        val binding: FragmentCreateLetterBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_letter, container, false
        )
        binding.viewModel = lettersViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lettersViewModel?.toastLiveData?.observe(this, Observer<Event<String>> { it ->
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this@CreateLetterFragment.context, it, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_letter, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send -> handleSend { lettersViewModel?.sendLetterWithDeeplink() }

            R.id.action_push -> handleSend { lettersViewModel?.sendPushNotification() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleSend(toSend: () -> Unit) {
        if (lettersViewModel != null && lettersViewModel.hasFullProfile()) {
            toSend()
            // TODO: navigate to sent fragment

        } else {
            // TODO: navigate to edit profile fragment

        }
        hideKeyboard()
    }
}
