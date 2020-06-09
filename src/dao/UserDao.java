package dao;

import model.User;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {
    public int registerEmployee(User employee){
        String INSERT_USERS_SQL= "insert into users"+
                "(fist_name,last_name,username,password) values"+
                "(?,?,?,?);";
        int result=0;

        try {
            Connection connection= JDBCUtils.getConnection();
            PreparedStatement preparedStatement= connection.prepareStatement(INSERT_USERS_SQL);
            preparedStatement.setString(1,employee.getFistName());
            preparedStatement.setString(2,employee.getLastName());
            preparedStatement.setString(3,employee.getUsername());
            preparedStatement.setString(4,employee.getPassword());
            result= preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
