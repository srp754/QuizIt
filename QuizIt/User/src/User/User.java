package User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * Created by scottparsons on 2/25/16.
 */
public class User {
    // Instance variables
    private String username; // Set when the user logs in or creates a new account
    private final static int SALT_IDX = 0;
    private final static int PW_IDX = 1;
    private final static int MAX_RECENT_CREATED_QUIZZES = 5;
    private final static int MAX_RECENT_TAKEN_QUIZZES = 5;

    // TODO dummy variables until DB is ready
    private Map<String, List> dbUsersPasswords;
    private Map<String, Boolean> dbUsersAdmin = new HashMap<String, Boolean>(); // REMOVE when DB exists
    private List<String> dbFriends = new ArrayList<String>(); // REMOVE when DB exists
    private Map<String, Double> dbQuizHistory = new LinkedHashMap<String, Double>(); // REMOVE when DB exists
    private List<Achievement> dbAchievements = new ArrayList<Achievement>(); // REMOVE when DB exists
    private Map<String, List<String>> dbQuizzesCreated = new LinkedHashMap<String, List<String>>(); // REMOVE when DB exists

    /**
     * Creates a new User object. This object is instantiated whenever the session is created in a browser. A
     * user object exists for each browser session.
     */
    public User() {
        dbUsersPasswords = new HashMap<String, List>(); //REMOVE when DB exists
        List l = new ArrayList();
        byte[] salt = generateSalt();
        l.add(salt);
        l.add(hexToString(generateHashValue(hexToString(salt), "pass")));
        dbUsersPasswords.put("scott", l);
        dbUsersAdmin.put("scott", false);
    }


    /**
     * Creates a new user account and stores it in the database if credentials are valid.
     * Store in DB: String username, String salt, String hashedPassword
     * @param username New username to create
     * @param password New password to create
     * @return true if successfully created new account, false if not
     */
    public boolean createNewUser(String username, String password, Boolean isAdmin) {
        if(userExists(username)) {
            return false;
        }
        else {
            //TODO replace with adding to DB
            byte[] salt = generateSalt();
            List l = new ArrayList();
            l.add(hexToString(salt));
            l.add(hexToString(generateHashValue(hexToString(salt), password)));
            dbUsersPasswords.put(username, l);
            dbUsersAdmin.put(username, isAdmin);
            setUsername(username);
            System.out.println("User added successfully");
            return true;
        }
    }

    /**
     * Sets the User objects username
     * @param username User's username identifier
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the User's username
     * @return
     */
    public String getUsername() { return username; }

    /**
     * Checks if the login username and password are correct
     * Reads from DB: String username, String salt, String password
     * @param username Login username
     * @param password Login password
     * @return true if username exists and password matches, false if not
     */
    public boolean isCorrectLogin(String username, String password) {
        return userExists(username) && passwordMatches(username, password);
    }

    /**
     * Changes a user from a regular to admin user
     * Stores in DB: String username, boolean isAdmin
     * @param userToPromote Username to promote to admin
     */
    public void promoteToAdmin(String userToPromote) {
        //TODO replace with DB code
        dbUsersAdmin.put(userToPromote, true);
    }

    /**
     * Checks if a user has admin privileges or not
     * Reads from DB: String username, boolean isAdmin
     * @return true if admin, false otherwise
     */
    public boolean isAdmin() {
        //TODO replace with DB code
        return dbUsersAdmin.get(username);
    }

    /**
     * Adds the username to the user's friend list
     * Stores in DB: String username, String friendUsername
     * Stores in DB: Strin friendUsername, String username
     * Stores twice since they are each other's friend
     * @param friendUsername Friend's username to add to friend list
     */
    public void addFriend(String friendUsername) {
        //TODO add to DB when it's ready
        dbFriends.add(friendUsername);
    }

    /**
     * Removes a friend from the user's friend list
     * Removes from DB: String username, String friendUsername
     * Removes from DB: String friendUsername, String username
     * Removes twice since they were each other's friend
     * @param friendUsername Friend's username to remove from friend list
     */
    public void removesFriend(String friendUsername) {
        //TODO remove from DB when it's ready
        if(dbFriends.contains(friendUsername)) {
            dbFriends.remove(friendUsername);
        }
    }

    /**
     * TODO this may be better suited in the Quiz class
     * Adds a user's quiz ID and score to the database
     * Stores in DB: int quizID, String username, Double score
     * @param quizId
     * @param score
     */
    public void addQuizScore(String quizId, Double score) {
        //TODO add to DB when it's ready
        dbQuizHistory.put(quizId, score);

        // TODO If user has max score for a quiz, they earn GREATEST achievement
        if(score > 0 && !dbAchievements.contains(Achievement.GREATEST)) {
            addAchievement(Achievement.GREATEST);
        }
    }

    /**
     * Removes a particular quiz history from a user
     * Removes from DB: String username, int quizId
     * @param username
     * @param quizId
     */
    public void removeQuiz(String username, String quizId) {
        //TODO replace with DB when ready
        dbQuizHistory.remove(quizId);
    }

    /**
     * Returns the user's score for a particular quiz
     * Reads from DB: String username, int quizId, Double score
     * @param quizId
     * @return User's score on quiz
     */
    public Double getQuizScore(String quizId) {
        //TODO get from DB when it's ready
        return dbQuizHistory.get(quizId);
    }

    /**
     * Returns the number of quizzes a user has taken
     * Reads from DB: String username
     * @return number of quizzes taken
     */
    public Integer getNumberOfQuizzesTaken() {
        //TODO replace with DB when it's ready
        return dbQuizHistory.size();
    }

    /**
     * TODO may be better suited in the Quiz class
     * Adds a created quiz to the user's history and database. Checks for achievements every time a quiz is created.
     * Stores in DB: String username, int quizId
     * @param quizId Quiz identifier
     */
    public void addCreatedQuiz(String quizId) {
        //TODO replace with DB when it's ready
        // If user has created a quiz before, add new quiz to the database
        if(dbQuizzesCreated.containsKey(username)) {
            List<String> quizList = dbQuizzesCreated.get(username);
            quizList.add(quizId);

            // Check for achievements
            if(getNumberOfQuizzesCreated() == Achievement.PRODIGIOUS_AUTHOR.getThreshold()) {
                addAchievement(Achievement.PRODIGIOUS_AUTHOR);
            }
            else if(getNumberOfQuizzesCreated() == Achievement.PRODIGIOUS_AUTHOR.getThreshold()) {
                addAchievement(Achievement.PROLIFIC_AUTHOR);
            }
        }
        // Initialize created quiz list for user
        else {
            List<String> quizList = new ArrayList<String>();
            quizList.add(quizId);
            dbQuizzesCreated.put(username, quizList);
            addAchievement(Achievement.AMATEUR_AUTHOR); // First created quiz achievement
        }
    }

    /**
     * Returns the number of quizzes the user has created
     * Reads from DB: String username
     * @return
     */
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
     * Reads from DB: String username, int quizId
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
     * Returns the 5 most recently taken quizzes and scores for the user or empty list if user has not
     * taken a quiz.
     * @return
     */
    public Map<String, Double> getRecentlyTakenQuizzes() {
        //TODO pull from DB when it's ready
        int numQuizzesTaken = getNumberOfQuizzesTaken();

        if (numQuizzesTaken== 0) {
            return new LinkedHashMap<String, Double>();
        }
        else {
            Map<String, Double> quizList = new LinkedHashMap<String, Double>();
            List<String> keys = new ArrayList<String>(dbQuizHistory.keySet());
            for (int i = keys.size()-1; i >= Math.max(numQuizzesTaken - MAX_RECENT_TAKEN_QUIZZES, 0); i--) {
                quizList.put(keys.get(i), dbQuizHistory.get(keys.get(i)));
            }
            return quizList;
        }
    }

    /**
     * Adds the user's achievement to the database
     * Stores in DB: String username, String achievementName, String achievementDescription
     * @param achievement Achievement to add
     */
    public void addAchievement(Achievement achievement) {
        //TODO add to DB when it's ready
        dbAchievements.add(achievement);
    }

    /**
     * Returns all of the user's achievements
     * Reads from DB: String username
     * @return
     */
    public List<Achievement> getAchievements() {
        //TODO get from DB when ready
        return dbAchievements;
    }

    /**
     * Returns the total number of users
     * Reads from DB:
     * @return number of users
     */
    public Integer getNumberOfUsers() {
        //TODO Get from DB when ready
        return dbUsersPasswords.size();
    }

    /**
     * Returns all usernames
     * Reads from DB:
     * @return list of usernames
     */
    public Set<String> getAllUsers() {
        return dbUsersPasswords.keySet();
    }

    /**
     * Checks if a username is in use
     * Reads from DB: String username
     * @param username username to check
     * @return true if username is in use, false if not
     */
    private boolean userExists(String username) {
        // TODO replace with database code
        return dbUsersPasswords.containsKey(username);
    }

    /**
     * Returns the hashed password from the DB
     * Reads from DB: String username, String password
     * @param username username password to return
     * @return password
     */
    private String getPasswordFromDB(String username) {
        return (String) dbUsersPasswords.get(username).get(PW_IDX);
    }

    // Checks if user login password matches the hash value stored in the database
    private boolean passwordMatches(String username, String password) {
        if (userExists(username)) {
            byte[] salt = (byte[]) dbUsersPasswords.get(username).get(SALT_IDX);
            String dbPassword = (String) dbUsersPasswords.get(username).get(PW_IDX);
            String hashPassword = hexToString(generateHashValue(hexToString(salt), password));
            return hashPassword.equals(dbPassword);
        }
        return false;
    }

    // Generates the SHA hex hash value using the MessageDigest
    private static byte[] generateHashValue(String salt, String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] combined = new byte[salt.getBytes().length + password.getBytes().length];
            System.arraycopy(salt.getBytes(), 0, combined, 0, salt.getBytes().length);
            System.arraycopy(password.getBytes(), 0, combined, salt.getBytes().length, password.getBytes().length);
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
