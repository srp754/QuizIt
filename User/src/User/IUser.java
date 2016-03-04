package User;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by Alex on 3/3/2016.
 */
public interface IUser
{
    boolean createNewUser(String username, String password, Boolean isAdmin);

    void setUsername(String username);

    String getUsername();

    boolean isCorrectLogin(String username, String password);

    void promoteToAdmin(String userToPromote);

    boolean isAdmin();

    void addFriend(String friendUsername);

    void removesFriend(String friendUsername);

    void addQuizScore(String quizId, Double grade);

    void removeQuiz(String username, String quizId);

    Double getQuizScore(String quizId);

    Integer getNumberOfQuizzesTaken();

    void addCreatedQuiz(String quizId);

    Integer getNumberOfQuizzesCreated();

    List<String> getRecentlyCreatedQuizzes();

    void addAchievement(UserAchievements.Achievements achievement);

    List<String> getAchievements();

    Integer getNumberOfUsers() throws SQLException;

    Set<String> getAllUsers();
}
