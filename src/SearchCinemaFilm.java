import java.text.SimpleDateFormat;

public class SearchCinemaFilm {

    private Cinema cinema;
    private Film film;


    public SearchCinemaFilm(Cinema cinema, Film film) {
        this.cinema = cinema;
        this.film = film;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return this.cinema.getName() + " " + this.cinema.getCity() + " " + film.getTitle() + 
        " ( " + formatter.format(film.getStartDate()) + " - " + formatter.format(film.getEndDate()) + " ) " +
        "room " + film.getRoomView() + ", " +
        "tickets available " +( film.getNumberTicketMax() - film.getNumberTicketSold());
    }



    public Cinema getCinema() {
        return this.cinema;
    }

    

    public Film getFilm() {
        return this.film;
    }

    public String getBarcode(){
        return "barcode";
    }

   
    
}