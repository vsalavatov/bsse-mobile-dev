package com.vadimsalavatov.mobiledev

import androidx.fragment.app.viewModels
import com.vadimsalavatov.mobiledev.databinding.FragmentSignInBinding

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    private val viewBinding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel: SignInViewModel by viewModels()
}
