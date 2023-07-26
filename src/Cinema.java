import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Cinema implements Serializable{
    private String name;
    private String city;
    private int[] room; //array di sale del cinema
    private ArrayList <Film> listFilm;
    private int progressive;

    public Cinema(String name, String city, int[] room, ArrayList<Film> listFilm) {
        this.name = name;
        this.city = city;
        this.room = room;
        this.listFilm = listFilm;
        this.progressive = 0;
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

    public synchronized String getBarcode(){
        return this.getName() + progressive++;
    }

    public Cinema clone(){
        ArrayList <Film> tmp = new ArrayList<>();
        for(Film f : this.listFilm){
            tmp.add(f.clone());
        }
        return new Cinema(this.name, this.city, this.room, tmp);
    }
}