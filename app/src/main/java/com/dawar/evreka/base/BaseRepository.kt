package com.dawar.evreka.base

import com.dawar.evreka.database.AppDatabase
/**
 * @author Malik Dawar, malikdawar332@gmail.com
 */
abstract class BaseRepository {
    protected val appDatabase = AppDatabase.getInstance()
}
