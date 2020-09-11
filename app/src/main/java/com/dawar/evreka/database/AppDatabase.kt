package com.dawar.evreka.database

import com.dawar.evreka.models.ContainerDao
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AppDatabase private constructor() {

    private val database = FirebaseDatabase.getInstance()
    private val dbRootRef = database.reference
    private lateinit var onContainers: ((MutableList<ContainerDao>?) -> Unit)
    private var containerList: MutableList<ContainerDao>? = ArrayList()

    private val valueEventListener = object : ValueEventListener {
        override fun onDataChange(p0: DataSnapshot) {

            for (fireBaseDataSnapshot in p0.children) {
                val containers: ContainerDao? =
                    fireBaseDataSnapshot.getValue(ContainerDao::class.java)
                containers?.run {
                    containerList?.add(containers)
                }
            }
            onContainers(containerList)
            close()
        }

        override fun onCancelled(p0: DatabaseError) {
            onContainers(null)
            close()
        }
    }

    fun saveContainer(containerDao: ContainerDao, onSuccess: ((Boolean) -> Unit)? = null) {
        dbRootRef.child(containerDao.id.toString()).setValue(containerDao)
            .addOnCompleteListener {
                onSuccess?.invoke(it.isSuccessful)
            }
    }

    fun getContainers(
        onContainers: ((MutableList<ContainerDao>?) -> Unit)
    ) {
        this.onContainers = onContainers
        dbRootRef.addValueEventListener(valueEventListener)
    }

    private fun close() {
        dbRootRef.removeEventListener(valueEventListener)
    }

    companion object {
        private var appDatabase: AppDatabase? = null
        fun getInstance(): AppDatabase {
            if (appDatabase == null)
                appDatabase =
                    AppDatabase()
            return appDatabase!!
        }
    }
}