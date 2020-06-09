package controller;

import dao.TodoDaoImpl;
import model.Todo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "TodoController",urlPatterns = "/todos")
public class TodoController extends HttpServlet {
    private TodoDaoImpl todoDao;

    @Override
    public void init() throws ServletException {
        todoDao= new TodoDaoImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action= request.getParameter("action");
        if (action==null){
            action= "";
        }
        switch (action){
            case "insert":
                insertTodo(request,response);
                break;
            default:
                break;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action= request.getParameter("action");
        if (action==null){
            action= "";
        }
        switch (action){
            case "new":
                showNewForm(request,response);
                break;
//            case "insert":
//                insertTodo(request,response);
//                break;
            case "delete":
                deleteTodo(request,response);
                break;
            case "edit":
                showEditForm(request,response);
                break;
            case "update":
                updateTodo(request,response);
                break;
            case "list":
                listTodo(request,response);
                break;
            default:
                RequestDispatcher requestDispatcher= request.getRequestDispatcher("login/login.jsp");
                requestDispatcher.forward(request,response);
                break;
        }

    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) {
//        int id = Integer.parseInt(request.getParameter("id"));

        try {
            RequestDispatcher requestDispatcher= request.getRequestDispatcher("todo/todo-form.jsp");
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertTodo(HttpServletRequest request, HttpServletResponse response) {
        String title = request.getParameter("title");
        String username= request.getParameter("username");
        String description= request.getParameter("description");
        LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"));
        boolean isDone= Boolean.valueOf(request.getParameter("isDone"));
        Todo newTodo = new Todo(title,username,description,targetDate,isDone);
        todoDao.insertTodo(newTodo);
        listTodo(request,response);

    }

    private void listTodo(HttpServletRequest request, HttpServletResponse response) {
        List<Todo> todoList= todoDao.selectAlltool();

        try {
            request.setAttribute("listTodo",todoList);
            RequestDispatcher requestDispatcher= request.getRequestDispatcher("todo/todo-list.jsp");
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void updateTodo(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String username= request.getParameter("username");
        String description= request.getParameter("description");
        LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"));
        boolean isDone= Boolean.valueOf(request.getParameter("isDone"));
        Todo updateTodo = new Todo(id,title,username,description,targetDate,isDone);
        todoDao.updateTodo(updateTodo);
        try {
            response.sendRedirect("list");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showEditForm(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        Todo existingTodo= todoDao.selectTodo(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("todo/todo-form.jsp");
        request.setAttribute("todo",existingTodo);
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void deleteTodo(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        todoDao.deleteTodo(id);
        try {
            response.sendRedirect("list");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
