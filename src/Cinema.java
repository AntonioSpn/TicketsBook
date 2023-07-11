import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Cinema implements Serializable{
    private String name;
    private String city;
    private int[] room; //numero sale del cinema
    private ArrayList <Film> listFilm;



    public Cinema(String name, String city, int[] room, ArrayList<Film> listFilm) {
        this.name = name;
        this.city = city;
        this.room = room;
        this.listFilm = listFilm;
    }

    
    public Cinema(String name, String city, int[] room) {
        this.name = name;
        this.city = city;
        this.room = room;
        this.listFilm = new ArrayList<>();
    }




    public String getName() {
        return this.name;
    }

    

    public String getCity() {
        return this.city;
    }


    public int[] getRoom() {
        return this.room;
    }

    
    public ArrayList<Film> getListFilm() {
        return this.listFilm;
    }

    public void addFilm(Film film){
        listFilm.add(film);
    }

    public void removeFilm(int i){
        listFilm.remove(i);
    }

    public ArrayList <Film> viewToday (Date date){
        ArrayList<Film> tmp = new ArrayList<>();

        for (Film film : this.listFilm) {
            if(film.getStartDate().compareTo(date) <1 && film.getEndDate().compareTo(date) > 0 ){ //
                tmp.add(film);
            }
        }

        return tmp;
    }

    








}