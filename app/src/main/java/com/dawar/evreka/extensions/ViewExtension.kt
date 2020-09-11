package com.dawar.evreka.extensions

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dawar.evreka.App
import com.dawar.evreka.R
/**
 * @author Malik Dawar, malikdawar332@gmail.com
 */


/**
 * Extension function to show custom toast message
 * @return void
 * @author Dawar Malik.
 */
fun Activity.showToastMsg(
    message: String,
    drawable: Int = R.drawable.ic_caution,
    toastDuration: Int = Toast.LENGTH_LONG
) {
    val layout: View = layoutInflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container))

    val tvCaution: TextView = layout.findViewById(R.id.tvCaution)
    tvCaution.text = message

    val imgCaution: ImageView = layout.findViewById(R.id.imgCaution)
    imgCaution.setImageResource(drawable)

    with (Toast(App.instance)) {
        setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        duration = toastDuration
        view = layout
        setGravity(Gravity.BOTTOM, 0, 100)
        show()
    }
}

/**
 * An Extension to make view Visible
 * @return void
 * @author Dawar Malik.
 */
fun View.visible() {
    visibility = View.VISIBLE
}

/**
 * An Extension to make view InVisible
 * @return void
 * @author Dawar Malik.
 */
fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * An Extension to make view Gone
 * @return void
 * @author Dawar Malik.
 */
fun View.gone() {
    visibility = View.GONE
}

/**
 * An Extension to getColorCompat of view via context
 * @return void
 * @author Dawar Malik.
 */
fun Context.getColorCompat(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)

/**
 * An Extension to getColorCompat via fragment
 * @return void
 * @author Dawar Malik.
 */
fun Fragment.getColor(@ColorRes colorRes: Int) = ContextCompat.getColor(App.getAppContext(), colorRes)

