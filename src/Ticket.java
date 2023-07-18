import java.io.Serializable;
import java.util.Date;

public class Ticket implements Serializable{
   private int type; //1 adulti, 2 bambini
   private double cost;
   private String barcode;
   private String nameCinema;
   private int room; // numero della sala dove viene riprodotto il film
   private String nameFilm;
   private Date date; // data del film   

    public Ticket(int type, double cost, String barcode, String nameCinema, int room, String nameFilm, Date date) {
        this.type = type;
        this.cost = cost;
        this.barcode = barcode;
        this.nameCinema = nameCinema;
        this.room = room;
        this.nameFilm = nameFilm;
        this.date = date;
    }    

    public String getBarcode() {
        return this.barcode;
    }

    public String getNameCinema() {
        return this.nameCinema;
    }    

    public int getRoom() {
        return this.room;
    }   

    public String getNameFilm() {
        return this.nameFilm;
    }   

    public Date getDate() {
        return this.date;
    } 

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        if(type!=1 && type !=2){
            System.out.println("Type must be 1 or 2, 1 for adults 2 for kids");
            return;
        };
        this.type = type;
    }

    public double getCost() {
        return this.cost;
    }

    public void setCost(double cost) {
        if (cost <0){
            System.out.println("Cost can't be negative");
            return;
        };
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", cost='" + getCost() + "'" +
            ", barcode='" + getBarcode() + "'" +
            ", nameCinema='" + getNameCinema() + "'" +
            ", room='" + getRoom() + "'" +
            ", nameFilm='" + getNameFilm() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }    
}