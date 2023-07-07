public class Owner extends User{
    private String partitaIva;
    private Cinema cinema;

    public Owner(String name, String surname, String username, String email, String password, String partitaIva, Cinema cinema) {
        super(name, surname, username, email, password);
        this.partitaIva = partitaIva;
        this.cinema = cinema;
    };

    public String getPartitaIva() {
        return this.partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }


    public Cinema getCinema() {
        return this.cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }


}