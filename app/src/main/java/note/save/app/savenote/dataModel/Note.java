package note.save.app.savenote.dataModel;

/**
 * Created by ashrafiqubal on 21/01/18.
 */

public class Note {
    int id = 0,  is_hearted = 0, is_poem = 0, is_star = 0;
    String title = "", description = "";
    long last_updated_time = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLast_updated_time() {
        return last_updated_time;
    }

    public void setLast_updated_time(long last_updated_time) {
        this.last_updated_time = last_updated_time;
    }

    public int getIs_hearted() {
        return is_hearted;
    }

    public void setIs_hearted(int is_hearted) {
        this.is_hearted = is_hearted;
    }

    public Note() {
    }

    public int getIs_poem() {
        return is_poem;
    }

    public void setIs_poem(int is_poem) {
        this.is_poem = is_poem;
    }

    public int getIs_star() {
        return is_star;
    }

    public void setIs_star(int is_star) {
        this.is_star = is_star;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
