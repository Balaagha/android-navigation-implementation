package com.example.androidnavigationimplementation.ui.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidnavigationimplementation.R
import com.example.androidnavigationimplementation.adapter.LetterAdapter
import com.example.androidnavigationimplementation.data.model.FragmentType
import com.example.androidnavigationimplementation.data.model.Letter
import com.example.androidnavigationimplementation.ui.viewmodel.LettersViewModel
import kotlinx.android.synthetic.main.fragment_inbox.*

class InboxFragment : Fragment(R.layout.fragment_inbox) {

    private val lettersViewModel: LettersViewModel? = null

    private val adapter by lazy { LetterAdapter(FragmentType.INBOX) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setItemClickListener {
            // TODO: navigate to presentation fragment
        }
        adapter.setItemDeleteListener {
            lettersViewModel?.deleteLetter(it, FragmentType.INBOX)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        lettersViewModel?.inboxLettersLiveData?.observe(viewLifecycleOwner,
            Observer { listItems: List<Letter> ->
                adapter.update(listItems)
            })
        lettersViewModel?.loadInboxLetters()
    }
}
