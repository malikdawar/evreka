package com.dawar.evreka.repository

import com.dawar.evreka.base.BaseRepository
import com.dawar.evreka.models.ContainerDao

/**
 *  Created by malik dawar
 *  this class will be responsible to talk to the DB
 */
class Repository private constructor() : BaseRepository() {

    fun getContainersFromFireBase(onContainers: ((MutableList<ContainerDao>?) -> Unit)) {
        appDatabase.getContainers(onContainers)
    }

    fun saveContainerInFireBase(
        containerDao: ContainerDao,
        onSuccess: ((Boolean) -> Unit)? = null
    ) {
        appDatabase.saveContainer(containerDao, onSuccess)
    }

    companion object {
        private var instance: Repository? = null
        fun getInstance(): Repository {
            if (instance == null)
                instance =
                    Repository()
            return instance!!
        }
    }
}