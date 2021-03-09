package com.anya.dogipedia.ui.breeds

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.anya.dogipedia.R
import com.anya.dogipedia.databinding.BreedsFragmentBinding
import com.anya.dogipedia.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_error.view.*


@AndroidEntryPoint
class BreedsFragment : Fragment(R.layout.breeds_fragment), BreedsListAdapter.OnRowClickListener {

    companion object {
        fun newInstance() = BreedsFragment()
    }

    private var parentBreed: String? = null
    private lateinit var viewModel: BreedsViewModel
    private lateinit var breedsListAdapter: BreedsListAdapter
    private lateinit var subBreedsList : Map<String, List<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        breedsListAdapter = BreedsListAdapter(requireContext(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.parentBreed = null
        activity?.title = "Breeds";

        // bind dog list frag
        val binding = BreedsFragmentBinding.bind(view)

        binding.dogListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.dogListRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        binding.dogListRecyclerView.adapter = breedsListAdapter

            viewModel = ViewModelProvider(this).get(BreedsViewModel::class.java)

            viewModel.getDogs().observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            var data = resource.data as Map<String, List<String>>?
                            // if success with no data - show empty view with error
                            if (data == null || data.isEmpty()) {
                                binding.layoutError.root.show()
                            } else {
                                // otherwise create list adapter for the recycler
                                breedsListAdapter.setDogsList(data)
                                binding.layoutError.root.hide()
                                binding.progressBar.hide()
                                subBreedsList = data
                            }
                        }
                        Status.ERROR -> {
                            binding.layoutError.root.error.text = resource.message
                            binding.layoutError.root.show()
                            binding.progressBar.hide()
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                           // Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                            binding.progressBar.show()
                        }
                    }
                }
            })
    }

    // any row clicked - navigate to dogs breed image fragment & pass selected row
    override fun onRowClick(breed: String, position: Int) {
        val subBreedsList = subBreedsList.get(breed)
        if(parentBreed == null && subBreedsList != null && subBreedsList.isNotEmpty()){
            breedsListAdapter.updateDogsList(subBreedsList)
            parentBreed = breed
            getActivity()?.setTitle(breed.capitalize());
        } else {
            if(parentBreed != null){
                getActivity()?.setTitle("${parentBreed!!} ${breed}".capitalize())
            } else {
                getActivity()?.setTitle(breed.capitalize());
            }
            findNavController().navigate(
                BreedsFragmentDirections.actionBreedsFragmentToDogsFragment(
                    parentBreed,
                    breed
                )
            )
        }
    }
}