import java.sql.*;

public class PostgresqlExample {

    public static Integer res;

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://msk1-cbox215.unix.nspk.ru:5432/iakovlevmo", "iakovlevmo", "password")) {

            System.out.println("Java JDBC PostgreSQL Example");
            System.out.println("Connected to PostgreSQL database!");
            Statement statement = connection.createStatement();

            //System.out.printf("%-30.30s  %-30.30s%n", "FirstName", "LastName", "OSId");
            /*ResultSet resultSet = statement.executeQuery("SELECT * FROM public.users");
            while (resultSet.next()) {
                System.out.printf("%-30.30s  %-30.30s%n", resultSet.getString("firstname"), resultSet.getString("lastname"), resultSet.getInt("osid"));
            }*/

            ResultSet resultSet = statement.executeQuery(/*"SELECT * FROM public.users;"*/ "SELECT * FROM public.users WHERE id = (SELECT MAX(id) FROM public.users)");
            while (resultSet.next()) {
                res = resultSet.getInt("id");
            }
            System.out.println(res+1);

            /*statement.executeUpdate("INSERT INTO public.users " + "VALUES (?, 'Test', 'Test', '4')") {
                preparedStatement.setInt(1, res);
            };*/

            PreparedStatement statement1 = connection.prepareStatement(("INSERT INTO public.users " + "VALUES (?, 'Test', 'Test', '4')")); {
                statement1.setInt(1, res+1);
            }
            statement1.executeUpdate();
        }  catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }
}
