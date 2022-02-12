import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;

import java.sql.*;

public class FetchUsersData {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:postgresql://localhost:5432/public";
        String username = "postgres";
        String password = "12345678";
        Class.forName("org.postgresql.Driver");
        ClassicConfiguration configuration = new ClassicConfiguration();
        configuration.setDataSource(url,username,password);
        Flyway flyway = new Flyway(configuration);
        flyway.info();
        flyway.repair();
        flyway.migrate();

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        }
    }
}
