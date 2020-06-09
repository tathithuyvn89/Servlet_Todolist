package controller;

import dao.LoginDao;
import model.Login;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginController", urlPatterns = "/login")
public class LoginController extends HttpServlet {
    private LoginDao loginDao;

    @Override
    public void init() throws ServletException {
        loginDao=new LoginDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        authenticate(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login/login.jsp");

    }

    private void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String username=request.getParameter("username");
        String password =request.getParameter("password");
        Login login= new Login();
        login.setUsername(username);
        login.setPassword(password);
        if (loginDao.validate(login)){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("todo/todo-list.jsp");
            requestDispatcher.forward(request,response);
        } else {
            HttpSession session = request.getSession();
        }

    }
}
