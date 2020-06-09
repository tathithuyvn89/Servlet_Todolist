package dao;

import com.mysql.cj.jdbc.JdbcConnection;
import model.Login;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {
    public boolean validate(Login login){
        boolean status=false;
        Connection connection= JDBCUtils.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select * from users where username=? " +
                    "and password=?");
            preparedStatement.setString(1,login.getUsername());
            preparedStatement.setString(2,login.getPassword());
            ResultSet resultSet= preparedStatement.executeQuery();
            status=resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
       return status;
    }
}
