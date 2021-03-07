package com.anya.dogipedia.ui.dogs

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.anya.dogipedia.R
import com.anya.dogipedia.databinding.DogsFragmentBinding
import com.anya.dogipedia.utils.Status
import com.anya.dogipedia.utils.hide
import com.anya.dogipedia.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_error.view.*

@AndroidEntryPoint
class DogsFragment : Fragment(R.layout.dogs_fragment) {

    companion object {
        fun newInstance() = DogsFragment()
    }

    private lateinit var viewModel: DogsViewModel
    private lateinit var breedsListAdapter: BreedsImagesAdapter
    private var mIsGrid: Boolean = true
    private lateinit var dogBreed: String
    private var subBreed: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        breedsListAdapter = BreedsImagesAdapter(requireContext())

        requireArguments().let {
            DogsFragmentArgs.fromBundle(it).also { args ->
                // selected row from dogs list
                if(args.subBreed != null){
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

        // bind dog list frag
        val binding = DogsFragmentBinding.bind(view)

        binding.dogListRecyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.dogListRecyclerView.adapter = breedsListAdapter

        // Small nice to have that allows different view grid and list view
        binding.gridOrListButton.setOnClickListener {
            if (mIsGrid) {
                it.setBackgroundResource(R.drawable.ic_list_view_icon)
                (binding.dogListRecyclerView.layoutManager as GridLayoutManager).spanCount = 1
            } else {
                it.setBackgroundResource(R.drawable.ic_grid_view_icon)
                (binding.dogListRecyclerView.layoutManager as GridLayoutManager).spanCount = 3
            }
            mIsGrid = !mIsGrid
        }

        try {
            viewModel = ViewModelProvider(this).get(DogsViewModel::class.java)

            viewModel.getDogsImages(dogBreed, subBreed).observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            var data = resource.data as ArrayList<String>
                            // if success with no data - show empty view with error
                            if (data == null || data.isEmpty()) {
                                binding.layoutError.root.show()
                            } else {
                                // otherwise create list adapter for the recycler
                                breedsListAdapter.setDogsImageList(data)
                                binding.layoutError.root.hide()
                                binding.progressBar.hide()
                            }
                        }
                        Status.ERROR -> {
                            binding.layoutError.root.error.text =
                                resource.message //result.httpException?.developerMessage
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

        } catch (e: Exception) {
            println("TAGTAG" + e.localizedMessage)
        }
    }

}