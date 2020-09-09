package com.dawar.evreka.base

import androidx.lifecycle.ViewModel
import com.dawar.evreka.preference.PrefManager
import org.koin.java.KoinJavaComponent

/**
 * An abstract Base View model to share common behavior and additionally clear navigation reference upon invalidation.
 */
abstract class BaseViewModel<View> : ViewModel() {
    private var view: View? = null

    protected val prefManager: PrefManager by KoinJavaComponent.inject(
        PrefManager::class.java
    )

    /**
     * This method must be called by the UI to attach navigation to be monitored by the substituted view model to respond to UI specific event changes.
     * @param navigator reference to navigation
     */
    open fun attachView(view: View) {
        this.view = view
    }

    /**
     * @returns current navigator if attached
     * @throws NullPointerException if not attached.
     */
    protected fun getView(): View {
        if (view == null)
            throw NullPointerException("View is null please call attach method 1st")

        return view!!
    }

    override fun onCleared() {
        view = null
    }
}