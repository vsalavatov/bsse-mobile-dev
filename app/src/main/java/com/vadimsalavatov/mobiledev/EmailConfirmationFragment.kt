package com.vadimsalavatov.mobiledev

import androidx.fragment.app.viewModels
import com.vadimsalavatov.mobiledev.databinding.FragmentEmailConfirmationBinding
import com.vadimsalavatov.mobiledev.databinding.FragmentSignInBinding

class EmailConfirmationFragment : BaseFragment(R.layout.fragment_email_confirmation) {

    private val viewBinding by viewBinding(FragmentEmailConfirmationBinding::bind)

    private val viewModel: EmailConfirmationViewModel by viewModels()
}
