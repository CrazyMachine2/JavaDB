import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user","root");
        properties.setProperty("password","1234");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/soft_uni?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",properties);

        PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM employees WHERE first_name = ? AND last_name = ?");

        Scanner scanner = new Scanner(System.in);

        String first_name = scanner.nextLine();
        String last_name = scanner.nextLine();

        statement.setString(1,first_name);
        statement.setString(2,last_name);

        ResultSet rs = statement.executeQuery();

        while (rs.next()){
            System.out.printf("%s %s %s %s %s%n",
                    rs.getString("employee_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("job_title"),
                    rs.getString("salary"));
        }
    }
}
