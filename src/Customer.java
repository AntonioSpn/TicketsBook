import java.util.ArrayList;

public class Customer extends User {
    private ArrayList<Ticket> tickets;

    public Customer(String name, String surname, String username, String email, String password) {
        super(name, surname, username, email, password);

        tickets = new ArrayList<Ticket>();
    }

    public Customer(String name, String surname, String username, String email, String password, ArrayList<Ticket> tickets) {
        super(name, surname, username, email, password);

        this.tickets = tickets;
    }

    public void buyTicket (Ticket ticket){
        tickets.add(ticket);
    }



    public ArrayList<Ticket> getTickets() {
        return this.tickets;
    }

    

}