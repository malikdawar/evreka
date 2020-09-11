package com.dawar.evreka.extensions

import com.dawar.evreka.App
/**
 * @author Malik Dawar, malikdawar332@gmail.com
 */

/**
 * This method will check a string to return a valid user.
 * @return a valid email or null.
 * @author Dawar Malik.
 */
fun String.isValidUser(): Boolean {
    if (isNotBlank() && contains("T2"))
        return true

    return false
}

/**
 * A method which test either password is valid or not.
 * @return Boolean : True, False.
 * @author Dawar Malik.
 */
fun CharSequence?.isValidPassword() =
    !isNullOrEmpty() && this!!.length > 7


/**
 * A method to return string from string.xml
 * @return String
 * @param R.string.id
 * @author Dawar Malik.
 */
fun getString(name : Int) = App.getAppContext().getString(name)
