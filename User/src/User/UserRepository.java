package User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by scottparsons on 2/25/16.
 */
public class UserRepository implements IUser
{
    // Instance variables
    private String username;
    private final static int SALT_IDX = 0;
    private final static int PW_IDX = 1;
    private final static int MAX_RECENT_CREATED_QUIZZES = 5;

    private Map<String, List> dbUsersPasswords;
    private Map<String, Boolean> dbUsersAdmin = new HashMap<String, Boolean>(); // REMOVE when DB exists
    private List<String> dbFriends = new ArrayList<String>(); // REMOVE when DB exists
    private Map<String, Double> dbQuizHistory = new HashMap<String, Double>(); // REMOVE when DB exists
    private List<String> dbAchievements = new ArrayList<String>(); // REMOVE when DB exists
    private Map<String, List<String>> dbQuizzesCreated = new HashMap<String, List<String>>(); // REMOVE when DB exists

    public UserRepository() {
        dbUsersPasswords = new HashMap<String, List>(); //REMOVE when DB exists
        List l = new ArrayList();
        byte[] salt = generateSalt();
        l.add(salt);
        l.add(hexToString(generateHashValue(salt, "pass")));

        l.clear();
        salt = generateSalt();
        l.add(salt);
        l.add(hexToString(generateHashValue(salt, "test")));
    }

    /**
     * Creates a new user account and stores it in the database
     * @param username New username to create
     * @param password New password to create
     * @return true if successful, false if not
     */
    public boolean createNewUser(String username, String password, Boolean isAdmin) {
        if(userExists(username)) {
            System.out.println("Username already exists");
            return false;
        }
        else {
            //TODO replace with adding to database
            byte[] salt = generateSalt();
            List l = new ArrayList();
            l.add(salt);
            l.add(hexToString(generateHashValue(salt, password)));
            dbUsersPasswords.put(username, l);
            dbUsersAdmin.put(username, isAdmin);
            setUsername(username);
            System.out.println("User added successfully");
            return true;
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() { return username; }

    /**
     * Checks if the login username and password are correct
     * @param username Login username
     * @param password Login password
     * @return true if username exists and password matches, false if not
     */
    public boolean isCorrectLogin(String username, String password) {
        return userExists(username) && passwordMatches(username, password);
    }

    /**
     * Changes a user from a regular to admin user
     * @param userToPromote Username to promote to admin
     */
    public void promoteToAdmin(String userToPromote) {
        //TODO replace with DB code
        dbUsersAdmin.put(userToPromote, true);
    }

    /**
     * Checks if a user has admin privileges or not
     * @return true if admin, false otherwise
     */
    public boolean isAdmin() {
        //TODO replace with DB code
        return dbUsersAdmin.get(username);
    }

    /**
     * Adds the username to the user's friend list
     * @param friendUsername Friend's username to add to friend list
     */
    public void addFriend(String friendUsername) {
        //TODO add to DB when it's ready
        dbFriends.add(friendUsername);
    }

    /**
     * Removes a friend from the user's friend list
     * @param friendUsername Friend's username to remove from friend list
     */
    public void removesFriend(String friendUsername) {
        //TODO remove from DB when it's ready
        if(dbFriends.contains(friendUsername)) {
            dbFriends.remove(friendUsername);
        }
    }

    /**
     * Adds a user's quiz ID and score to the database
     * @param quizId
     * @param grade
     */
    public void addQuizScore(String quizId, Double grade) {
        //TODO add to DB when it's ready
        dbQuizHistory.put(quizId, grade);
    }

    /**
     * Removes a particular quiz history from a user
     * @param username
     * @param quizId
     */
    public void removeQuiz(String username, String quizId) {
        //TODO replace with DB when ready
        dbQuizHistory.remove(quizId);
    }

    /**
     * Returns the user's score for a particular quiz
     * @param quizId
     * @return
     */
    public Double getQuizScore(String quizId) {
        //TODO get from DB when it's ready
        return dbQuizHistory.get(quizId);
    }

    /**
     * Returns the number of quizzes a user has taken
     * @return number of quizzes taken
     */
    public Integer getNumberOfQuizzesTaken() {
        //TODO replace with DB when it's ready
        return dbQuizHistory.size();
    }

    /**
     * Adds a created quiz to the user's history and database. Checks for achievements every time a quiz is created.
     * @param quizId Quiz identifier
     */
    public void addCreatedQuiz(String quizId) {
        //TODO replace with DB when it's ready
        // If user has created a quiz before, add new quiz to the database
        if(dbQuizzesCreated.containsKey(username)) {
            List<String> quizList = dbQuizzesCreated.get(username);
            quizList.add(quizId);

            // Check for achievements
            if(getNumberOfQuizzesCreated() == 5) {
                addAchievement(UserAchievements.Achievements.PRODIGIOUS_AUTHOR);
            }
            else if(getNumberOfQuizzesCreated() == 10) {
                addAchievement(UserAchievements.Achievements.PROLIFIC_AUTHOR);
            }
        }
        // Initialize created quiz list for user
        else {
            List<String> quizList = new ArrayList<String>();
            quizList.add(quizId);
            dbQuizzesCreated.put(username, quizList);
            addAchievement(UserAchievements.Achievements.AMATEUR_AUTHOR); // First created quiz achievement
        }
    }


    public Integer getNumberOfQuizzesCreated() {
        //TODO replace with DB when it's ready
        if(dbQuizzesCreated.get(username) == null) {
            return 0;
        }
        else {
            return dbQuizzesCreated.get(username).size();
        }
    }

    /**
     * Returns the 5 most recently created quizzes for the user or empty list if user has not
     * created a quiz.
     * @return
     */
    public List<String> getRecentlyCreatedQuizzes() {
        //TODO pull from DB when it's ready
        int numQuizzesCreated = getNumberOfQuizzesCreated();

        if (numQuizzesCreated== 0) {
            return new ArrayList<String>();
        }
        else {
            List<String> quizList = new ArrayList<String>();
            for (int i = numQuizzesCreated-1; i >= Math.max(numQuizzesCreated - MAX_RECENT_CREATED_QUIZZES, 0); i--) {
                quizList.add(dbQuizzesCreated.get(username).get(i));
            }
            return quizList;
        }
    }

    /**
     * Adds the user's achievement to the database
     * @param achievement
     */
    public void addAchievement(UserAchievements.Achievements achievement) {
        //TODO add to DB when it's ready
        dbAchievements.add(achievement.toString());
    }

    public List<String> getAchievements() {
        //TODO get from DB when ready
        return dbAchievements;
    }

    public Integer getNumberOfUsers() throws SQLException
    {
        return DatabaseTasks.GetCountRecordsFromTable("UserDetail");
    }

    public Set<String> getAllUsers() {
        return dbUsersPasswords.keySet();
    }

    private boolean userExists(String username) {
        // TODO replace with database code
        if(dbUsersPasswords.containsKey(username)) {
            System.out.println("username exists");
        }
        return dbUsersPasswords.containsKey(username);
    }

    // Returns the hash'd password from the database.  Assumes the username exists.
    private String getPasswordFromDB(String username) {
        System.out.println(dbUsersPasswords.get(username));
        return (String) dbUsersPasswords.get(username).get(PW_IDX);
    }

    // Checks if user login password matches the hash value stored in the database
    private boolean passwordMatches(String username, String password) {
        if (userExists(username)) {
            byte[] salt = (byte[]) dbUsersPasswords.get(username).get(SALT_IDX);
            String dbPassword = (String) dbUsersPasswords.get(username).get(PW_IDX);
            String hashPassword = hexToString(generateHashValue(salt, password));
            return hashPassword.equals(getPasswordFromDB(username));
        }
        return false;
    }

    // Generates the SHA hex hash value using the MessageDigest
    private static byte[] generateHashValue(byte[] salt, String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] combined = new byte[salt.length + password.getBytes().length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(password.getBytes(), 0, combined, salt.length, password.getBytes().length);
            byte[] hash = md.digest(combined);
            return hash;
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] generateSalt() {
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        return salt;
    }

    /*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
    private static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i=0; i<bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;  // remove higher bits, sign
            if (val<16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

}
