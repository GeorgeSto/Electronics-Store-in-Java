package repository.customer;

import model.User;
import model.builder.UserBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.USER;

public class CustomerRepositoryMySQL implements CustomerRepository{

    private final Connection connection;

    public CustomerRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean buyElectroByTitle(String title,User user)
    {
        try {
            Statement statement = connection.createStatement();

            String fetchElectroIdSql =
                    "SELECT id, stock FROM electro WHERE title = '" + title + "'";
            ResultSet resultSet  = statement.executeQuery(fetchElectroIdSql);

            int electroId = -1;
            int stock = 0;
            if(resultSet .next())
            {
                electroId = resultSet.getInt("id");
                stock = resultSet.getInt("stock");
            }

            if(electroId != -1 && stock >0)
            {
                PreparedStatement insertStatement = connection
                        .prepareStatement("INSERT INTO `user_electro` values (null, ?, ?, null)");
                insertStatement.setLong(1, user.getId());
                insertStatement.setLong(2, electroId);
                insertStatement.executeUpdate();

                PreparedStatement updateStatement = connection.prepareStatement("UPDATE electro SET stock = stock - 1 WHERE id = ?;");
                updateStatement.setInt(1, electroId);
                updateStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean sellElectroByTitle(String title, User user) {
        try {
            String fetchUserElectroSql = "SELECT COUNT(*) AS totalElectrosSold FROM user_electro WHERE user_id = ? AND electro_id IN (SELECT id FROM electro WHERE title = ?)";
            try (PreparedStatement fetchStatement = connection.prepareStatement(fetchUserElectroSql)) {
                fetchStatement.setLong(1, user.getId());
                fetchStatement.setString(2, title);
                ResultSet resultSet = fetchStatement.executeQuery();

                int totalElectrosSold = resultSet.next() ? resultSet.getInt("totalElectrosSold") : 0;

                String deleteUserElectroSql = "DELETE FROM user_electro WHERE user_id = ? AND electro_id IN (SELECT id FROM electro WHERE title = ?)";
                try (PreparedStatement deleteStatement = connection.prepareStatement(deleteUserElectroSql)) {
                    deleteStatement.setLong(1, user.getId());
                    deleteStatement.setString(2, title);
                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        String updateStockSql = "UPDATE electro SET stock = stock + ? WHERE title = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateStockSql)) {
                            updateStatement.setInt(1, totalElectrosSold);
                            updateStatement.setString(2, title);
                            updateStatement.executeUpdate();
                        }
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return false;
    }

    @Override
    public List<User> findAllCustomers()
    {
        String sql = "SELECT * FROM user where `isCustomer` = 1;";

        List<User> users = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(sql);

            while(resultSet.next())
            {
                users.add(getUserFromResultSet(resultSet));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return users;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new UserBuilder()
                .setId(resultSet.getLong("id"))
                .setUsername(resultSet.getString("username"))
                .setPassword(resultSet.getString("password"))
                .build();
    }


}
