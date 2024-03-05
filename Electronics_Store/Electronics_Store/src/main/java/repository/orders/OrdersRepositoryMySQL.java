package repository.orders;

import model.Electro;
import model.Orders;
import model.User;
import model.builder.ElectroBuilder;
import model.builder.OrderBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdersRepositoryMySQL implements OrdersRepository {

    private final Connection connection;

    public OrdersRepositoryMySQL(Connection connection) {this.connection = connection;}

    @Override
    public List<Orders> findAll() {
        String sql = "SELECT * FROM orders;";

        List<Orders> orders = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(sql);

            while(resultSet.next())
            {
                orders.add(getOrdersFromResultSet(resultSet));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public Optional<Orders> findById(Long id) {
        String sql = "SELECT * FROM orders where id=?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(getOrdersFromResultSet(resultSet));
            }

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean save(Orders order) {
        String sql = "INSERT INTO orders VALUES(null, ? , ? , ?, ?, ?);";

        //String newSql = "INSERT INTO electro VALUES(null, \'" + electro.getAuthor() +"\', \'"+ electro.getTitle()+"\', null );";


        try{

            /*Statement statement = connection.createStatement();
            statement.executeUpdate(newSql);
            return true;*/

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,order.getUser_id());
            preparedStatement.setLong(2, order.getElectro_id());
            preparedStatement.setInt(3, order.getQuantity());
            preparedStatement.setInt(4, order.getPrice());
            preparedStatement.setDate(5, java.sql.Date.valueOf(order.getDate_created()));


            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted == 1;

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Orders findByUser_id(Long id) {
        String sql = "SELECT * FROM orders where user_id=?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return getOrdersFromResultSet(resultSet);
            }

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addElectroById(Electro electro, int quantity, User user) {
        return;
    }

    @Override
    public boolean updateQuantityById(Long id,int quantity) {
        String sql = "UPDATE orders SET quantity = ? WHERE electro_id = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setLong(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void removeAll() {
        String sql = "DELETE FROM orders;";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean removeById(Long id) {
        String sql = "DELETE FROM orders WHERE id = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private Orders getOrdersFromResultSet(ResultSet resultSet) throws SQLException {
        return  new OrderBuilder()
                .setId(resultSet.getLong("id"))
                .setUser_id(resultSet.getLong("user_id"))
                .setElectro_id(resultSet.getLong("electro_id"))
                .setQuantity(resultSet.getInt("quantity"))
                .setPrice(resultSet.getInt("price"))
                .setDate(new java.sql.Date(resultSet.getDate("date_created").getTime()).toLocalDate())

                .build();
    }
}
