package fr.dappli.photocloud.common.db

class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    fun clearCache() {
        dbQuery.transaction {
            dbQuery.deleteAllCache()
        }
    }

    fun clearCache(key: String) {
        dbQuery.transaction {
            dbQuery.deleteFromCacheByKey(key)
        }
    }

    fun insertToCache(key: String, value: String) {
        dbQuery.transaction {
            dbQuery.insertToCache(key, value)
        }
    }

    fun getFromCache(key: String): String? {
        return dbQuery.selectFromCacheByKey(key).executeAsOneOrNull()?.value_
    }

    fun updateCache(key: String, value: String) {
        dbQuery.transaction {
            dbQuery.update(value, key)
        }
    }
}
