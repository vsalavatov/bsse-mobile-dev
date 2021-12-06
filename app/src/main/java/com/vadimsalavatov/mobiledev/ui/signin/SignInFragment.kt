package com.vadimsalavatov.mobiledev.ui.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vadimsalavatov.mobiledev.ui.base.BaseFragment
import com.vadimsalavatov.mobiledev.R
import com.vadimsalavatov.mobiledev.databinding.FragmentSignInBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    private val viewBinding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.signInButton.setOnClickListener {
            viewModel.signIn()
        }
    }
}
