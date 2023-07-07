import java.util.Date;

public class Film {
    private String title;
    private String director; // regista
    private Date date; // data di uscita del film al cinema
    //controllare tipo data

    public Film(String title, String director, Date date) {
        this.title = title;
        this.director = director;
        this.date = date;
    }


    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return this.director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
