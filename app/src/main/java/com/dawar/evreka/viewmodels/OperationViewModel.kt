package com.dawar.evreka.viewmodels

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.dawar.evreka.R
import com.dawar.evreka.base.BaseViewModel
import com.dawar.evreka.extensions.getString
import com.dawar.evreka.models.ContainerDao
import com.dawar.evreka.repository.Repository
import org.koin.java.KoinJavaComponent
import kotlin.random.Random

class OperationViewModel : BaseViewModel<OperationViewModel.View>() {

    private var lifecycleOwner: LifecycleOwner? = null
    private val TAG = OperationViewModel::class.java.simpleName

    private val repository: Repository by KoinJavaComponent.inject(
        Repository::class.java
    )

    fun addObserver(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    fun getContainers() {
        repository.getContainersFromFireBase {
            it?.run {
                getView().onContainers(this)
            } ?: run {
                getView().onDetailsUpdateError(getString(R.string.someThingWentWrong))
            }
        }
    }

    fun saveInFireBase(iteration: Int) {
        //used this dynamic method to populate FireBase, called in a repeat method
        val containerDao = ContainerDao(
            1000 + iteration,
            Random.nextInt(0, 100),
            "${Random.nextInt(0, 30)}.12.2020(T1)",
            39 + Random.nextDouble(.12223, .8955),
            32 + Random.nextDouble(.12223, .8955)
        )
        repository.saveContainerInFireBase(containerDao) {
            Log.d(TAG, "DataSaved: $it")
        }
    }

    interface View {
        fun onDetailsUpdateError(error: String)
        fun onContainers(containers: MutableList<ContainerDao>)
        fun onProgress()
        fun onProgressDismiss()
    }
}