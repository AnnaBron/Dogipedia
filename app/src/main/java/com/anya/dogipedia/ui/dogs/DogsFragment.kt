package com.anya.dogipedia.ui.dogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.anya.dogipedia.R
import com.anya.dogipedia.databinding.DogsFragmentBinding
import com.anya.dogipedia.utils.Status
import com.anya.dogipedia.utils.hide
import com.anya.dogipedia.utils.show
import com.novoda.merlin.Merlin
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_error.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DogsFragment constructor(
    private val merlin: Merlin
) : Fragment(R.layout.dogs_fragment) {

    private lateinit var viewModel: DogsViewModel
    private lateinit var breedsListAdapter: BreedsImagesAdapter
    private var mIsGrid: Boolean = true
    private lateinit var dogBreed: String
    private var subBreed: String? = null
    private lateinit var binding: DogsFragmentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        breedsListAdapter = BreedsImagesAdapter(requireContext())

        requireArguments().let {
            DogsFragmentArgs.fromBundle(it).also { args ->
                // selected row from dogs list
                if (args.subBreed != null) {
                    dogBreed = args.subBreed
                    subBreed = args.breed
                } else {
                    dogBreed = args.breed
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        merlin.bind()
        // bind dog list frag
        binding = DogsFragmentBinding.bind(view)

        binding.dogListRecyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.dogListRecyclerView.adapter = breedsListAdapter

        // Small nice to have that allows different view grid and list view
        binding.gridOrListButton.setOnClickListener {
            if (mIsGrid) {
                it.setBackgroundResource(R.drawable.ic_list_view)
                (binding.dogListRecyclerView.layoutManager as GridLayoutManager).spanCount = 1
            } else {
                it.setBackgroundResource(R.drawable.ic_grid_view)
                (binding.dogListRecyclerView.layoutManager as GridLayoutManager).spanCount = 3
            }
            mIsGrid = !mIsGrid
        }


        viewModel = ViewModelProvider(this).get(DogsViewModel::class.java)

        // Fetch list on fragment load
        loadDogsList()

        // When internet connection is back
        merlin.registerConnectable {
            GlobalScope.launch(Dispatchers.Main) {
                loadDogsList()
            }

        }

        // When internet connection is lost
        merlin.registerDisconnectable {
            GlobalScope.launch(Dispatchers.Main) {
                onDogListGetError()
            }
        }
    }

    override fun onStop() {
        merlin.unbind()
        super.onStop()
    }

    fun loadDogsList() {
        viewModel.getDogsImages(dogBreed, subBreed).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        var data = resource.data as ArrayList<String>
                        // if success with no data - show empty view with error
                        if (data == null || data.isEmpty()) {
                            binding.layoutError.root.show()
                            binding.dogListRecyclerView.hide()
                        } else {
                            // otherwise create list adapter for the recycler
                            breedsListAdapter.setDogsImageList(data)
                            binding.dogListRecyclerView.show()
                            binding.layoutError.root.hide()
                        }
                    }
                    Status.ERROR -> {
                        onDogListGetError()
                    }
                }
            }
        })
    }

    fun onDogListGetError() {
        binding.layoutError.root.error.text = getString(R.string.api_error_network)
        binding.dogListRecyclerView.hide()
        binding.layoutError.root.show()
    }
}