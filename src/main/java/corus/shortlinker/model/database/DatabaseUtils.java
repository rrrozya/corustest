package corus.shortlinker.model.database;

//mport org.mariadb.jdbc.MariaDbDataSource;
//import org.mariadb.jdbc.MySQLDataSource;


import com.mysql.cj.jdbc.MysqlDataSource;
import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtils {
    public static DataSource getDataSource() {
        return DataSourceHolder.INSTANCE;
    }

    private static final class DataSourceHolder {
        private static final DataSource INSTANCE;
        private static final Properties PROPERTIES = new Properties();

        static {
            try {
                PROPERTIES.load(DataSourceHolder.class.getResourceAsStream("/application.properties"));
            } catch (IOException e) {
                throw new RuntimeException("Can't load application.properties.", e);
            }

            MysqlDataSource dataSource = new MysqlDataSource();
            try {
                dataSource.setUrl(PROPERTIES.getProperty("database.url"));
                dataSource.setUser(PROPERTIES.getProperty("database.user"));
                dataSource.setPassword(PROPERTIES.getProperty("database.password"));
                dataSource.setServerTimezone("UTC");
            } catch (SQLException e) {
                throw new RuntimeException();
            }

            INSTANCE = dataSource;


            try (Connection connection = INSTANCE.getConnection()) {
                if (connection == null) {
                    throw new RuntimeException("Can't get testing connection from DB.");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Can't get testing connection from DB.", e);
            }
        }
    }
}
