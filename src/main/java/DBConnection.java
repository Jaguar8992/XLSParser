import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;

    private static String dbName = "XLSTable";
    private static String dbUser = "root";
    private static String dbPass = "testtest";
    private static String tableName = "nalog1nom_Kolchanov";


    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + dbName + "?createDatabaseIfNotExist=true" +
                                "&user=" + dbUser + "&password=" + dbPass);
                connection.createStatement().execute("DROP TABLE IF EXISTS " + tableName);
                connection.createStatement().execute("CREATE TABLE " + tableName + "(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "fielda TEXT NULL, " +
                        "fieldb TEXT NULL, " +
                        "fieldv INT NULL, " +
                        "field1 INT NULL, " +
                        "field2 INT NULL, " +
                        "field3 INT NULL, " +
                        "field4 INT NULL, " +
                        "field5 INT NULL, " +
                        "field6 INT NULL, " +
                        "field7 INT NULL, " +
                        "field8 INT NULL, " +
                        "field9 INT NULL, " +
                        "field10 INT NULL, " +
                        "field11 INT NULL, " +
                        "field12 INT NULL, " +
                        "field13 INT NULL, " +
                        "field14 INT NULL, " +
                        "field15 INT NULL, " +
                        "field16 INT NULL, " +
                        "field17 INT NULL, " +
                        "field18 INT NULL, " +
                        "field19 INT NULL, " +
                        "field20 INT NULL, " +
                        "field21 INT NULL, " +
                        "field22 INT NULL, " +
                        "field23 INT NULL, " +
                        "field24 INT NULL, " +
                        "field25 INT NULL, " +
                        "ter TEXT NULL, " +
                        "dat TEXT NULL, " +
                        "PRIMARY KEY(id)) "
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void executeInsert(String query) throws SQLException {
        String sql = "INSERT INTO " + tableName + " (fielda, fieldb, fieldv, field1, field2, field3, field4, field5, " +
                "field6, field7, field8, field9, field10, field11, field12,field13, field14, field15, field16, field17, " +
                "field18, field19, field20, field21, field22, field23, field24, field25, ter, dat) " +
                "VALUES" + query;
        DBConnection.getConnection().createStatement().execute(sql);
    }
}
