package com.vadimsalavatov.mobiledev

import androidx.fragment.app.viewModels
import com.vadimsalavatov.mobiledev.databinding.FragmentSignUpBinding

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private val viewBinding by viewBinding(FragmentSignUpBinding::bind)

    private val viewModel: SignUpViewModel by viewModels()
}
