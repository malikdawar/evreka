package com.dawar.evreka.viewmodels

import androidx.lifecycle.LifecycleOwner
import com.dawar.evreka.R
import com.dawar.evreka.base.BaseViewModel
import com.dawar.evreka.extensions.getString
import com.dawar.evreka.models.ContainerDao
import com.dawar.evreka.repository.Repository
import org.koin.java.KoinJavaComponent

class OperationViewModel : BaseViewModel<OperationViewModel.View>() {

    private var lifecycleOwner: LifecycleOwner? = null

    private val repository: Repository by KoinJavaComponent.inject(
        Repository::class.java
    )

    fun addObserver(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    fun getContainers(){
        repository.getContainersFromFireBase {
            it?.run {
                getView().onContainers(this)
            }?: run {
                getView().onDetailsUpdateError(getString(R.string.someThingWentWrong))
            }
        }
    }


    interface View {
        fun onDetailsUpdateError(error: String)
        fun onContainers(containers : MutableList<ContainerDao>)
        fun onProgress()
        fun onProgressDismiss()
    }
}