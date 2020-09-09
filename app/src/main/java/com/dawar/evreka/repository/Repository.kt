package com.dawar.evreka.repository

import com.dawar.evreka.base.BaseRepository
import com.dawar.evreka.models.FireBasePayload


/**
 *  Created by malik dawar
 */
class Repository private constructor() : BaseRepository() {

    fun getUserFirebaseDetails(id: String, onUser: ((FireBasePayload?) -> Unit)) {
        //appDatabase.getUser(id, onUser)
    }

    fun saveUserFirebaseDetails(payload: FireBasePayload, onSuccess: ((Boolean) -> Unit)? = null) {
        //appDatabase.saveUser(payload, onSuccess)
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