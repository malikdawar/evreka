package com.dawar.evreka.activities

import android.content.Intent
import android.os.Bundle
import com.dawar.evreka.base.BaseActivity
import com.dawar.evreka.extensions.replaceFragmentSafely
import com.dawar.evreka.fragments.LoginFragment
import com.dawar.evreka.preference.PrefManager
import org.koin.java.KoinJavaComponent

/**
 * @author Malik Dawar, malikdawar332@gmail.com
 */

class LoginActivity : BaseActivity() {

    private val prefManager: PrefManager by KoinJavaComponent.inject(
        PrefManager::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (prefManager.isUserLoggedOut())
            replaceFragmentSafely(fragment = LoginFragment())
        else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}