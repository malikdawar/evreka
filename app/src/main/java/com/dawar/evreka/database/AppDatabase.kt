package com.dawar.evreka.database


class AppDatabase private constructor() {

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