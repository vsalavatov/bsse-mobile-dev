package com.vadimsalavatov.mobiledev.ui.emailconfirmation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vadimsalavatov.mobiledev.R
import com.vadimsalavatov.mobiledev.databinding.FragmentEmailConfirmationBinding
import com.vadimsalavatov.mobiledev.ui.base.BaseFragment
import com.vadimsalavatov.mobiledev.ui.signup.SignUpViewModel
import com.vadimsalavatov.mobiledev.util.getSpannedString
import com.vadimsalavatov.mobiledev.util.showAsToast
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmailConfirmationFragment : BaseFragment(R.layout.fragment_email_confirmation) {

    private val viewBinding by viewBinding(FragmentEmailConfirmationBinding::bind)

    private val viewModel: EmailConfirmationViewModel by viewModels()
    private val signUpViewModel: SignUpViewModel by hiltNavGraphViewModels(R.id.signUpFragment)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val signUpForm = signUpViewModel.formData ?: error("sign up form is not filled")
        viewBinding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.backButton.applyInsetter {
            type(statusBars = true) { margin() }
        }
        viewBinding.confirmButton.setOnClickListener {
            viewModel.signUpWithCode(
                signUpForm.firstname,
                signUpForm.lastname,
                signUpForm.nickname,
                signUpForm.email,
                signUpForm.password,
                viewBinding.codeTextEdit.getCode()
            )
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
        viewBinding.openMailAppButton.applyInsetter {
            type(navigationBars = true) { margin() }
        }
        subscribeToFormFields()
        subscribeToEvents()
        subscribeToSendAgainTimer()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.sendVerificationCode(signUpForm.email)
        }
    }

    private fun subscribeToFormFields() {
        viewBinding.confirmButton.isEnabled = false
        viewBinding.codeTextEdit.onVerificationCodeFilledChangeListener = { filled ->
            viewBinding.confirmButton.isEnabled = filled
        }
    }

    private fun subscribeToEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.emailConfirmationActionStateFlow().collect { event ->
                    when (event) {
                        EmailConfirmationViewModel.EmailConfirmationActionState.Loading -> {
                            // TODO: show progress bar?
                        }
                        EmailConfirmationViewModel.EmailConfirmationActionState.Pending -> {
                            // nothing
                        }
                        is EmailConfirmationViewModel.EmailConfirmationActionState.NetworkError -> event.e.showAsToast(requireContext())
                        is EmailConfirmationViewModel.EmailConfirmationActionState.ServerError -> event.e.showAsToast(requireContext())
                        is EmailConfirmationViewModel.EmailConfirmationActionState.EmailVerificationError -> event.e.showAsToast(requireContext())
                        is EmailConfirmationViewModel.EmailConfirmationActionState.UnknownError -> event.e.showAsToast(requireContext())
                    }
                }
            }
        }
    }

    private fun subscribeToSendAgainTimer() {
        viewModel.viewModelScope.launch {
            viewModel.timerStateFlow.collect { remainingMs ->
                if (remainingMs == 0L) {
                    viewBinding.sendCodeAgainHintText.visibility = TextView.INVISIBLE
                    viewBinding.sendCodeAgainButton.isEnabled = true
                } else {
                    viewBinding.sendCodeAgainHintText.visibility = TextView.VISIBLE
                    viewBinding.sendCodeAgainButton.isEnabled = false
                    viewBinding.sendCodeAgainHintText.setSendCodeTimerHint(remainingMs / 1000)
                }
            }
        }
    }

    private fun TextView.setSendCodeTimerHint(seconds: Long) {
        text = resources.getSpannedString(
            R.string.email_confirmation_send_code_again_hint_template,
            buildSpannedString {
                inSpans {
                    append("%02d:%02d".format(seconds / 60, seconds % 60))
                }
            }
        )
    }
}

