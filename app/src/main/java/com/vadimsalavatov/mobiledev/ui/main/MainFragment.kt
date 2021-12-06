package com.vadimsalavatov.mobiledev.ui.main

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vadimsalavatov.mobiledev.ui.base.BaseFragment
import com.vadimsalavatov.mobiledev.R
import com.vadimsalavatov.mobiledev.databinding.FragmentMainBinding

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val viewBinding by viewBinding(FragmentMainBinding::bind)

    private val viewModel: MainFragmentViewModel by viewModels()
}
