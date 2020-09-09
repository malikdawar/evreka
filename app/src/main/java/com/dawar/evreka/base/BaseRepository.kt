package com.dawar.evreka.base

import com.dawar.evreka.database.AppDatabase

abstract class BaseRepository {

    protected val appDatabase = AppDatabase.getInstance()
}
