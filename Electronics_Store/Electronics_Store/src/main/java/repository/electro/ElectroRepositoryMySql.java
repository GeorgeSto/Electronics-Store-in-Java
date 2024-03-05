package repository.electro;

import model.Electro;
import model.User;
import model.builder.ElectroBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ElectroRepositoryMySql implements ElectroRepository {

    private final Connection connection;

    public ElectroRepositoryMySql(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Electro> findAll() {
        String sql = "SELECT * FROM electro;";

        List<Electro> electros = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(sql);

            while(resultSet.next())
            {
                electros.add(getElectroFromResultSet(resultSet));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return electros;
    }


    @Override
    public Optional<Electro> findById(Long id) {
        String sql = "SELECT * FROM electro where id=?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(getElectroFromResultSet(resultSet));
            }

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     *
     * How to reproduce a sql injection attack on insert statement
     *
     *
     * 1) Uncomment the lines below and comment out the PreparedStatement part
     * 2) For the Insert Statement DROP TABLE SQL Injection attack to succeed we will need multi query support to be added to our connection
     * Add to JDBConnectionWrapper the following flag after the DB_URL + schema concatenation: + "?allowMultiQueries=true"
     * 3) electro.setAuthor("', '', null); DROP TABLE electro; -- "); // this will delete the table electro
     * 3*) electro.setAuthor("', '', null); SET FOREIGN_KEY_CHECKS = 0; SET GROUP_CONCAT_MAX_LEN=32768; SET @tables = NULL; SELECT GROUP_CONCAT('`', table_name, '`') INTO @tables FROM information_schema.tables WHERE table_schema = (SELECT DATABASE()); SELECT IFNULL(@tables,'dummy') INTO @tables; SET @tables = CONCAT('DROP TABLE IF EXISTS ', @tables); PREPARE stmt FROM @tables; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET FOREIGN_KEY_CHECKS = 1; --"); // this will delete all tables. You are not required to know the table name anymore.
     * 4) Run the program. You will get an exception on findAll() method because the test_library.electro table does not exist anymore
     */

    @Override
    public boolean save(Electro electro) {
        String sql = "INSERT INTO electro VALUES(null, ? , ? , ?, ?, ?, ?, ?);";

        //String newSql = "INSERT INTO electro VALUES(null, \'" + electro.getAuthor() +"\', \'"+ electro.getTitle()+"\', null );";


        try{

            /*Statement statement = connection.createStatement();
            statement.executeUpdate(newSql);
            return true;*/

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,electro.getCompany());
            preparedStatement.setString(2, electro.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(electro.getPublishedDate()));
            preparedStatement.setInt(4,electro.getStock());
            preparedStatement.setString(5, electro.getDescription());
            preparedStatement.setString(6, electro.getImagePath());
            preparedStatement.setInt(7, electro.getPrice());

            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted == 1;

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void removeAll() {
        String sql = "DELETE FROM electro;";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Electro findByTitle(String title) {
        String sql = "SELECT * FROM electro where title=?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,title);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return getElectroFromResultSet(resultSet);
            }

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteElectroByTitle(String title) {
        String sql = "DELETE FROM electro WHERE title = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);

            int rowsDeleted = preparedStatement.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateStockByTitle(String title, int newStock) {
        String sql = "UPDATE electro SET stock = ? WHERE title = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, newStock);
            preparedStatement.setString(2, title);

            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateStockById(Long id, int stock) {
        String sql = "UPDATE electro SET stock = ? WHERE id = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, stock);
            preparedStatement.setLong(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean sellElectro(String title, User customer, User employee) {
        try {
            Statement statement = connection.createStatement();

            String fetchElectroIdSql =
                    "SELECT id, stock FROM electro WHERE title = '" + title + "'";
            ResultSet resultSet  = statement.executeQuery(fetchElectroIdSql);

            int electroId = -1;
            int stock = 0;

            if(resultSet.next()) {
                electroId = resultSet.getInt("id");
                stock = resultSet.getInt("stock");
            }

            if(electroId != -1 && stock > 0) {
                PreparedStatement insertStatement = connection
                        .prepareStatement("INSERT INTO `user_electro` VALUES (null, ?, ?, ?)");
                insertStatement.setLong(1, customer.getId());
                insertStatement.setLong(2, electroId);
                insertStatement.setLong(3, employee.getId());
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
    public List<Electro> findSoldElectros() {
        String sql = "SELECT electro_id FROM user_electro;";

        List<Electro> soldElectros = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(sql);

            while (resultSet.next()) {
                int electroId = resultSet.getInt("electro_id");

                Optional<Electro> optionalElectro = findById((long) electroId);

                optionalElectro.ifPresent(soldElectros::add);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return soldElectros;
    }

    @Override
    public String getElectroTitleById(Long electroId) {
        String sql = "SELECT title FROM electro WHERE id = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, electroId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("title");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getElectroCompanyById(Long id) {
        String sql = "SELECT company FROM electro WHERE id = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("company");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Electro getElectroFromResultSet(ResultSet resultSet) throws SQLException {
        return  new ElectroBuilder()
                .setId(resultSet.getLong("id"))
                .setCompany(resultSet.getString("company"))
                .setTitle(resultSet.getString("title"))
                .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .setStock(resultSet.getInt("stock"))
                .setDescription(resultSet.getString("description"))
                .setImagePath(resultSet.getString("imagePath"))
                .setPrice((int)resultSet.getLong("price"))
                .build();
    }
}