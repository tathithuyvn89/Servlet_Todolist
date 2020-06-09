package controller;

import dao.UserDao;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserController", urlPatterns = "/register")
public class UserController extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        register(request,response);

    }

    private void register(HttpServletRequest request, HttpServletResponse response) {
        String fistName= request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String userName = request.getParameter("username");
        String passWord = request.getParameter("password");
        User employee= new User(fistName,lastName,userName,passWord);
        int result =userDao.registerEmployee(employee);
        if (result==1){
            request.setAttribute("NOTIFICATION","User Registered Successfully!");
        }

        try {
            RequestDispatcher dispatcher= request.getRequestDispatcher("register/register.jsp");
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("register/register.jsp");
    }
}
