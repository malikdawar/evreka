package com.dawar.evreka.activities

import android.os.Bundle
import com.dawar.evreka.base.BaseActivity
import com.dawar.evreka.extensions.replaceFragmentSafely
import com.dawar.evreka.fragments.OperationFragment

/**
 * @author Malik Dawar, malikdawar332@gmail.com
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        replaceFragmentSafely(OperationFragment())
    }
}
