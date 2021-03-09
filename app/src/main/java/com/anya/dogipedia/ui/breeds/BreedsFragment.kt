package com.anya.dogipedia.ui.breeds

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anya.dogipedia.R
import com.anya.dogipedia.databinding.BreedsFragmentBinding
import com.anya.dogipedia.utils.Status
import com.anya.dogipedia.utils.hide
import com.anya.dogipedia.utils.show
import com.novoda.merlin.Merlin
import com.novoda.merlin.MerlinsBeard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_error.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BreedsFragment constructor(
    private val merlin: Merlin
) : Fragment(R.layout.breeds_fragment), BreedsListAdapter.OnRowClickListener {

    private var parentBreed: String? = null
    private lateinit var viewModel: BreedsViewModel
    private lateinit var breedsListAdapter: BreedsListAdapter
    private lateinit var subBreedsList: Map<String, List<String>>
    private lateinit var binding: BreedsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        breedsListAdapter = BreedsListAdapter(requireContext(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        merlin.bind()

        this.parentBreed = null
        activity?.title = "Breeds";

        // bind dog list frag
        binding = BreedsFragmentBinding.bind(view)

        binding.dogListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.dogListRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        binding.dogListRecyclerView.adapter = breedsListAdapter
        viewModel = ViewModelProvider(this).get(BreedsViewModel::class.java)

        // Check upon loading and show state according to network state
        if (MerlinsBeard.Builder().build(context).isConnected) {
            loadBreedsList()
        } else {
            onBreedsListGetError()
        }

        // When internet connection is back
        merlin.registerConnectable {
            GlobalScope.launch(Dispatchers.Main) {
                loadBreedsList()
            }
        }

        // When internet connection is lost
        merlin.registerDisconnectable {
            GlobalScope.launch(Dispatchers.Main) {
                onBreedsListGetError()
            }
        }
    }

    override fun onStop() {
        merlin.unbind()
        super.onStop()
    }

    fun loadBreedsList() {
        viewModel.getDogs().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        var data = resource.data as Map<String, List<String>>?
                        // if success with no data - show empty view with error
                        if (data == null || data.isEmpty()) {
                            binding.dogListRecyclerView.hide()
                            binding.layoutError.root.show()
                        } else {
                            // otherwise create list adapter for the recycler
                            breedsListAdapter.setDogsList(data)
                            binding.layoutError.root.hide()
                            binding.dogListRecyclerView.show()
                            binding.progressBar.hide()
                            subBreedsList = data
                        }
                    }
                    Status.ERROR -> {
                        onBreedsListGetError()
                    }
                    Status.LOADING -> {
                        binding.layoutError.root.hide()
                        binding.progressBar.show()
                    }
                }
            }
        })
    }

    fun onBreedsListGetError() {
        binding.layoutError.root.error.text = getString(R.string.api_error_network)
        binding.dogListRecyclerView.hide()
        binding.layoutError.root.show()
        binding.progressBar.hide()
    }

    // any row clicked - navigate to dogs breed image fragment & pass selected row
    override fun onRowClick(breed: String, position: Int) {
        val subBreedsList = subBreedsList.get(breed)
        if (parentBreed == null && subBreedsList != null && subBreedsList.isNotEmpty()) {
            breedsListAdapter.updateDogsList(subBreedsList)
            parentBreed = breed
            getActivity()?.setTitle(breed.capitalize());
        } else {
            if (parentBreed != null) {
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