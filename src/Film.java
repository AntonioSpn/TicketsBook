import java.io.Serializable;
import java.util.Date;

public class Film implements Serializable{
    private String title;
    private String director; // regista
    private Date startDate; // data di uscita del film al cinema
    private Date endDate; 
    private String gen; // genere del film
    private double costAdult;
    private double costKid;
    private int roomView; // sala in cui è proiettato il fim
    private int numberTicketSold; //numero di biglietti venduti
    private int numberTicketMax; //numero di biglietti massimi
    private String timeBeginning; // ora di inizio

    public Film(String title, String director, Date startDate, Date endDate, String gen, double costAdult, double costKid, int roomView, int numberTicketSold, int numberTicketMax, String timeBeginning) {
        this.title = title;
        this.director = director;
        this.startDate = startDate;
        this.endDate = endDate;
        this.gen = gen;
        this.costAdult = costAdult;
        this.costKid = costKid;
        this.roomView = roomView;
        this.numberTicketSold = numberTicketSold;
        this.numberTicketMax = numberTicketMax;
        this.timeBeginning = timeBeginning;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDirector() {
        return this.director;
    }    

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getGen() {
        return this.gen;
    }    

    public double getCostAdult() {
        return this.costAdult;
    }

    public void setCostAdult(double costAdult) {
        this.costAdult = costAdult;
    }

    public double getCostKid() {
        return this.costKid;
    }

    public void setCostKid(double costKid) {
        this.costKid = costKid;
    }

    public int getRoomView() {
        return this.roomView;
    }

    public void setRoomView(int roomView) {
        this.roomView = roomView;
    }

    public int getNumberTicketSold() {
        return this.numberTicketSold;
    }

    public void setNumberTicketSold(int numberTicketSold) {
        this.numberTicketSold = numberTicketSold;
    }

    public int getNumberTicketMax() {
        return this.numberTicketMax;
    }

    public void setNumberTicketMax(int numberTicketMax) {
        this.numberTicketMax = numberTicketMax;
    }

    public String getTimeBeginning() {
        return this.timeBeginning;
    }

    public boolean ticketAvailable(){
        return this.numberTicketMax > this.numberTicketSold;
    }

    @Override
    public String toString() {
        return "{" +
            " title='" + getTitle() + "'" +
            ", director='" + getDirector() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", gen='" + getGen() + "'" +
            ", costAdult='" + getCostAdult() + "'" +
            ", costKid='" + getCostKid() + "'" +
            ", roomView='" + getRoomView() + "'" +
            ", numberTicketSold='" + getNumberTicketSold() + "'" +
            ", numberTicketMax='" + getNumberTicketMax() + "'" +
            ", timeBeginning='" + getTimeBeginning() + "'" +
            "}";
    }

    public synchronized boolean sellTicket(){
        if(this.ticketAvailable()){
            this.numberTicketSold++;
            return true;
        } else return false;
    }    
}