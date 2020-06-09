package dao;

import model.Todo;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.chrono.JapaneseDate;
import java.util.ArrayList;
import java.util.List;

public class TodoDaoImpl  implements ITodoDao{
    private static final String INSERT_TODOS_SQL="insert into todos" +"(title,username,description,target_date,is_done)"
           + "values"+"(?,?,?,?,?);";
    private static final String SELECT_TODO_BY_ID ="SELECT id,title,username,description,target_date,is_done"+
            "from todos where id =?;";
    private static final String SELECT_ALL_TODOS= "select * form todos";
    private static final String DELETE_TODO_BY_ID= "delete from todos where id=?;";
    private static final String UPDATE_TODO ="update todos set title=?, username=?, description=?,target_date=?, is_done=? where id=?;";

    @Override
    public void insertTodo(Todo todo) {
        Connection connection= JDBCUtils.getConnection();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(INSERT_TODOS_SQL);
            preparedStatement.setString(1,todo.getTitle());
            preparedStatement.setString(2,todo.getUsername());
            preparedStatement.setString(3,todo.getDescription());
            preparedStatement.setDate(4, JDBCUtils.getSQLDate(todo.getTargetDate()));
            preparedStatement.setBoolean(5,todo.isStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            JDBCUtils.printSQLException(throwables);
        }

    }

    @Override
    public Todo selectTodo(int id) {
        Todo todo=null;
        Connection connection= JDBCUtils.getConnection();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(SELECT_TODO_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                String title= resultSet.getString("title");
                String username= resultSet.getString("username");
                String description= resultSet.getString("description");
                LocalDate targetDate= resultSet.getDate("target_date").toLocalDate();
                Boolean isDone = resultSet.getBoolean("is_done");
                todo= new Todo(id,title,username,description,targetDate,isDone);
            }
        } catch (SQLException throwables) {
            JDBCUtils.printSQLException(throwables);
        }
        return todo;
    }

    @Override
    public List<Todo> selectAlltool() {
        List<Todo> todos= new ArrayList<>();
        Connection connection= JDBCUtils.getConnection();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(SELECT_ALL_TODOS);
            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String title= resultSet.getString("title");
                String username= resultSet.getString("username");
                String description= resultSet.getString("description");
                LocalDate targetDate= resultSet.getDate("target_date").toLocalDate();
                boolean isDone = resultSet.getBoolean("is_done");
                todos.add(new Todo(id,title,username,description,targetDate,isDone));
            }
        } catch (SQLException throwables) {
            JDBCUtils.printSQLException(throwables);
        }
        return todos;
    }

    @Override
    public boolean deleteTodo(int id) {
       boolean rowDeleted=false;
       Connection connection= JDBCUtils.getConnection();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(DELETE_TODO_BY_ID);
            preparedStatement.setInt(1,id);
            rowDeleted = preparedStatement.executeUpdate()>0;
        } catch (SQLException throwables) {
            JDBCUtils.printSQLException(throwables);
        }
        return rowDeleted;

    }

    @Override
    public boolean updateTodo(Todo todo) {
       boolean rowUpdated = false;
       Connection connection = JDBCUtils.getConnection();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(UPDATE_TODO);
            preparedStatement.setString(1,todo.getTitle());
            preparedStatement.setString(2,todo.getUsername());
            preparedStatement.setString(3,todo.getDescription());
            preparedStatement.setDate(4,JDBCUtils.getSQLDate(todo.getTargetDate()));
            preparedStatement.setBoolean(5,todo.isStatus());
            preparedStatement.setInt(6,todo.getId());
            rowUpdated = preparedStatement.executeUpdate()>0;
        } catch (SQLException throwables) {
            JDBCUtils.printSQLException(throwables);
        }
        return rowUpdated;
    }
}
