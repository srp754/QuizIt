package user;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by scottparsons on 3/6/16.
 */
@WebServlet("/RemoveAccountServlet")
public class RemoveAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IUserRepository userRepo = (UserRepository) request.getSession().getAttribute("user");
        String username = request.getParameter("inputUserName");

        if(userRepo.userExists(username)) {
            DatabaseTasks.DeleteUserDetail(username);
        }

        response.sendRedirect("user/remove_user.jsp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
