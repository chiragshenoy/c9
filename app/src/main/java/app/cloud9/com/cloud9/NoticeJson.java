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

    public NoticeJson(){
        id=" ";
        posted_at=" ";
        posted_by=" ";
        subject= " ";
        text=" ";
        target_group=" ";
        path=" ";

    }

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


    public String getId() {
        return this.id ;
    }

    public String getPosted_by() {
        return this.posted_by ;
    }

    public String getSubject() {
        return this.subject ;
    }

    public String getText() {
        return this.text ;
    }

    public String getPosted_at() {
        return this.posted_at ;
    }

    public String getPath() {
        return this.path ;
    }

    public String getTarget_group() {
        return this.target_group ;
    }
}

