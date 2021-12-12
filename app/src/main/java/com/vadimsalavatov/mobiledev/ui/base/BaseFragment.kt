package com.vadimsalavatov.mobiledev.ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vadimsalavatov.mobiledev.BuildConfig
import com.vadimsalavatov.mobiledev.logBackstack
import com.vadimsalavatov.mobiledev.logFragmentHierarchy
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
open class BaseFragment : Fragment {
    constructor(): super()
    constructor(@LayoutRes contentLayoutId: Int): super(contentLayoutId)

    override fun onStart() {
        super.onStart()
        if (BuildConfig.DEBUG) {
            val logTag = "NavigationInfo"
            logFragmentHierarchy(logTag)
            try {
                findNavController().logBackstack(logTag)
            } catch (error: IllegalStateException) {
                Timber.e(error)
            }
        }
    }

}