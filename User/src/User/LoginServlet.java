package User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by scottparsons on 2/25/16.
 */
public class LoginServlet extends HttpServlet {

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IUserRepository userRepo = (UserRepository) request.getSession().getAttribute("user");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(userRepo.isCorrectLogin(username, password))
        {
            userRepo.PopulateCurrentUser(username); //will be just userId and populate the currentUser object the repo will have
            RequestDispatcher dispatch = request.getRequestDispatcher("userHome.jsp");
            dispatch.forward(request, response);
        }
        else {
            RequestDispatcher dispatch = request.getRequestDispatcher("Incorrect.html");
            dispatch.forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
