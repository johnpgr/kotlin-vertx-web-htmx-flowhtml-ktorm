package experiment.datasources

import org.postgresql.ds.PGSimpleDataSource

class PostgresDataSource : PGSimpleDataSource(){
    init {
        user = "postgres"
        password = "postgres"
        serverName = "localhost"
        portNumber = 5432
        databaseName = "experiment"
    }
}
