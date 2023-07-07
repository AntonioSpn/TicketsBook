import java.util.ArrayList;

public class Customer extends User{
    private ArrayList<Ticket> Tickets;

    public Customer(String name, String surname, String username, String email, String password) {
        super(name, surname, username, email, password);

        Tickets = new ArrayList<Ticket>();
    }

    public void buyTicket (Ticket ticket){
        Tickets.add(ticket);
    }

    public void viewTickets (){
        System.out.println(Tickets.toString());
    }

}