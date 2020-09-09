package com.dawar.evreka.fragments

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dawar.evreka.R
import com.dawar.evreka.activities.LoginActivity
import com.dawar.evreka.activities.MainActivity
import com.dawar.evreka.extensions.getColor
import com.dawar.evreka.extensions.showToastMsg
import com.dawar.evreka.utils.LocationPermissions
import com.dawar.evreka.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), View.OnClickListener,
    LocationPermissions.LocationPermissionCallBack, LoginViewModel.View {

    private val loginViewModel: LoginViewModel by viewModels()
    private var loginActivity : LoginActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LocationPermissions.checkLocationPermissions(activity as LoginActivity, this)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginActivity = context as LoginActivity

        loginViewModel.let {
            it.attachView(this)
            it.addObserver(this)
        }

        imgBtnPassword.setOnClickListener(this)
        btnLogin.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imgBtnPassword -> loginViewModel.passwordTransformation(
                etUserPassword,
                imgBtnPassword
            )
            R.id.btnLogin -> {
                loginViewModel.authenticateUser(
                    etUserName.text.toString(),
                    etUserPassword.text.toString()
                )
            }
        }
    }

    override fun onLocationPermissionChangeListener(permission: Boolean) {

    }

    override fun onDetailsUpdateError(error: String) {
        loginActivity?.showToastMsg(error)
    }

    override fun onLoginSuccess() {
        startActivity(Intent(loginActivity, MainActivity::class.java))
        loginActivity?.finish()
    }

    override fun onLoginFailed() {
        inputLayoutUser.apply {
            setErrorTextAppearance(R.style.error_appearance)
            setHintTextAppearance(R.style.error_appearance)
        }

        etUserName.run {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_caution, 0)
            background.setColorFilter(getColor(R.color.color_red), PorterDuff.Mode.SRC_IN)
            setTextColor(getColor(R.color.color_red))
        }
    }
}
