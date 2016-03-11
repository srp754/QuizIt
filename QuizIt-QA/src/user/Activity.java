package user;

/**
 * Created by scottparsons on 3/9/16.
 */
public class Activity {
    public String type;
    public String date;
    public int linkId;

    public Activity(String type, String date, int linkId) {
        this.type = type;
        this.date = date;
        this.linkId = linkId;
    }
}
