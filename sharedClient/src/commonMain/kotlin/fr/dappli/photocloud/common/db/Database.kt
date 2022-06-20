package fr.dappli.photocloud.common.db

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearCache() {
        dbQuery.transaction {
            dbQuery.deleteAllCache()
        }
    }

    internal fun insertToCache(key: String, value: String) {
        dbQuery.transaction {
            dbQuery.insertToCache(key, value)
        }
    }

    internal fun getFromCache(key: String): String? {
        return dbQuery.selectFromCacheByKey(key).executeAsOneOrNull()?.value_
    }
}
