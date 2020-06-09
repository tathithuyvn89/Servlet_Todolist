package dao;

import model.Todo;

import java.util.List;

public interface ITodoDao {
    void insertTodo(Todo todo);
    Todo selectTodo(int id);
    List<Todo> selectAlltool();
    boolean deleteTodo(int id);
    boolean updateTodo(Todo todo);
}
