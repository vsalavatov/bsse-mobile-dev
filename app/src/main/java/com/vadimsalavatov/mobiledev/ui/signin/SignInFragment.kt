package com.vadimsalavatov.mobiledev.ui.signin

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vadimsalavatov.mobiledev.R
import com.vadimsalavatov.mobiledev.databinding.FragmentSignInBinding
import com.vadimsalavatov.mobiledev.ui.base.BaseFragment

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    private val viewBinding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fun onBackPressed() {
            val email = viewBinding.emailEditText.text?.toString()
            val password = viewBinding.passwordEditText.text?.toString()
            if (email.isNullOrBlank() && password.isNullOrBlank()) {
                findNavController().popBackStack()
                return
            }
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.sign_in_back_alert_dialog_text)
                .setNegativeButton(R.string.sign_in_back_alert_dialog_cancel_button_text) { dialog, _ ->
                    dialog?.dismiss()
                }
                .setPositiveButton(R.string.sign_in_back_alert_dialog_ok_button_text) { _, _ ->
                    findNavController().popBackStack()
                }
                .show()
        }
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackPressed()
        }
        callback.isEnabled = true
        viewBinding.backButton.setOnClickListener {
            onBackPressed()
        }
        viewBinding.signInButton.setOnClickListener {
            viewModel.signIn(
                email = viewBinding.emailEditText.text?.toString() ?: "",
                password = viewBinding.passwordEditText.text?.toString() ?: ""
            )
        }
        subscribeToFormFields()
    }

    private fun subscribeToFormFields() {
        decideSignInButtonEnabledState(
            email = viewBinding.emailEditText.text?.toString(),
            password = viewBinding.passwordEditText.text?.toString()
        )
        viewBinding.emailEditText.doAfterTextChanged { email ->
            decideSignInButtonEnabledState(
                email = email?.toString(),
                password = viewBinding.passwordEditText.text?.toString()
            )
        }
        viewBinding.passwordEditText.doAfterTextChanged { password ->
            decideSignInButtonEnabledState(
                email = viewBinding.emailEditText.text?.toString(),
                password = password?.toString()
            )
        }
    }

    private fun decideSignInButtonEnabledState(email: String?, password: String?) {
        viewBinding.signInButton.isEnabled = !(email.isNullOrBlank() || password.isNullOrBlank())
    }
}