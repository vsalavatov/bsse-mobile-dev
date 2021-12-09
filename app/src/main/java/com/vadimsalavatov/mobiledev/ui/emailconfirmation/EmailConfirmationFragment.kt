package com.vadimsalavatov.mobiledev.ui.emailconfirmation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vadimsalavatov.mobiledev.R
import com.vadimsalavatov.mobiledev.databinding.FragmentEmailConfirmationBinding
import com.vadimsalavatov.mobiledev.ui.base.BaseFragment
import com.vadimsalavatov.mobiledev.ui.signup.SignUpViewModel
import dev.chrisbanes.insetter.applyInsetter

class EmailConfirmationFragment : BaseFragment(R.layout.fragment_email_confirmation) {

    private val viewBinding by viewBinding(FragmentEmailConfirmationBinding::bind)

    private val viewModel: EmailConfirmationViewModel by viewModels()
    private val signUpViewModel: SignUpViewModel by navGraphViewModels(R.id.signUpFragment)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.backButton.applyInsetter {
            type(statusBars = true) { margin() }
        }
        viewBinding.confirmButton.setOnClickListener {
            val data = signUpViewModel.formData ?: error("sign up form is not filled")
            viewModel.confirmCode(
                data.firstname,
                data.lastname,
                data.nickname,
                data.email,
                data.password,
                viewBinding.codeTextEdit.getCode()
            )
        }
        viewBinding.confirmButton.applyInsetter {
            type(navigationBars = true) { margin() }
        }
        viewBinding.openMailAppButton.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_MAIN
                ).apply {
                    addCategory(Intent.CATEGORY_APP_EMAIL)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
        }
        subscribeToFormFields()
    }

    private fun subscribeToFormFields() {
        viewBinding.confirmButton.isEnabled = false
        viewBinding.codeTextEdit.onVerificationCodeFilledChangeListener = { filled ->
            viewBinding.confirmButton.isEnabled = filled
        }
    }
}
