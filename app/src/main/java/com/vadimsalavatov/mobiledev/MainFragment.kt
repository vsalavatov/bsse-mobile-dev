package com.vadimsalavatov.mobiledev

import androidx.fragment.app.viewModels
import com.vadimsalavatov.mobiledev.databinding.FragmentMainBinding

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val viewBinding by viewBinding(FragmentMainBinding::bind)

    private val viewModel: MainFragmentViewModel by viewModels()
}
