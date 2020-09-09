package com.dawar.evreka.viewmodels

import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import com.dawar.evreka.R
import com.dawar.evreka.base.BaseViewModel
import com.dawar.evreka.extensions.getString
import com.dawar.evreka.extensions.isValidPassword
import com.dawar.evreka.extensions.isValidUser

class LoginViewModel : BaseViewModel<LoginViewModel.View>() {

    private var lifecycleOwner: LifecycleOwner? = null

    fun addObserver(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    fun authenticateUser(userName: String, password: String) {
        if (userName.isBlank()) {
            getView().onDetailsUpdateError(getString(R.string.user_required))
            return
        }
        if (!password.isValidPassword()) {
            getView().onDetailsUpdateError(getString(R.string.wrong_password))
            return
        }

        if (userName.isValidUser()) {
            prefManager.saveSessionDetails(userName, password)
            getView().onLoginSuccess()
        } else {
            getView().onDetailsUpdateError(getString(R.string.login_fail))
            getView().onLoginFailed()
        }
    }

    private var pwdVisibility: Boolean = true
    fun passwordTransformation(editText: EditText, imageView: ImageView) {
        editText.run {
            if (pwdVisibility) {
                transformationMethod = null
                imageView.setImageResource(R.drawable.ic_visibility_gone)
                pwdVisibility = false
            } else {
                transformationMethod = PasswordTransformationMethod()
                imageView.setImageResource(R.drawable.ic_visibility)
                pwdVisibility = true
            }
        }
    }

    interface View {
        fun onDetailsUpdateError(error: String)
        fun onLoginSuccess()
        fun onLoginFailed()
    }
}