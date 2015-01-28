package app.cloud9.com.cloud9;

/**
 * Created by Chirag on 25-01-2015.
 */
public class NoticeJson {
    String id;
    String posted_by;
    String subject;
    String text;
    String target_group;
    String posted_at;
    String path;

    public void setId(String id) {
        this.id = id;
    }

    public void setPosted_by(String posted_by) {
        this.posted_by = posted_by;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPosted_at(String posted_at) {
        this.posted_at = posted_at;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTarget_group(String target_group) {
        this.target_group = target_group;
    }
}

