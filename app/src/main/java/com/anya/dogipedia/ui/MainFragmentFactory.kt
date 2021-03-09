package com.anya.dogipedia.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.anya.dogipedia.ui.breeds.BreedsFragment
import com.anya.dogipedia.ui.dogs.DogsFragment
import com.novoda.merlin.Merlin
import javax.inject.Inject

// Used to perform injection to fragments with hilt. used in this case for merlin connectivity
class MainFragmentFactory @Inject constructor(
    val merlin: Merlin
): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            BreedsFragment::class.java.name ->
                BreedsFragment(merlin)
            DogsFragment::class.java.name ->
                DogsFragment(merlin)
            else -> super.instantiate(classLoader, className)
        }
    }
}