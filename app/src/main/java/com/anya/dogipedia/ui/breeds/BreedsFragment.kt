package com.anya.dogipedia.ui.breeds

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anya.dogipedia.R

class BreedsFragment : Fragment() {

    companion object {
        fun newInstance() = BreedsFragment()
    }

    private lateinit var viewModel: BreedsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.breeds_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BreedsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}