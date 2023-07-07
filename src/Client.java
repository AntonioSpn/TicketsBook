// import java.io.IOException;
// import java.io.UnsupportedEncodingException;
// import java.security.NoSuchAlgorithmException;
// import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private ArrayList <User> userList; //creazione arraylist
    private final Scanner user_scanner = new Scanner(System.in);

    public Client (){
        this.userList = new ArrayList<User>();
    }    
    
    public static void main(String[] args) {
        Client user= new Client();
        user.Menu();
    }

    private void Menu() {
        while(true){
            System.out.println("Welcome on TicketsBook platform!\nInsert 1 to Sign in or 2 to Sign up\nInsert choice: ");
            int choice = user_scanner.nextInt();
            user_scanner.nextLine();
            while(choice!=1 && choice!=2){
                System.out.println("Choose 1 or 2!\n1: Sign In\n2: Sign Up \nInsert choice: ");
                choice = user_scanner.nextInt();
                user_scanner.nextLine();
            }

            switch (choice){
                case 1:
                    // try {
                        User tmp = SignIn();
                        if(tmp == null) {
                            System.out.println("Autenticazione fallita! Riprova\n");
                            break;
                        }
                        switch (tmp.getClass().getName()){
                            case "Owner":
                                OwnerClient((Owner) tmp);
                                return;
                            case "Customer":
                                CustomerClient((Customer) tmp);
                                return;
                        }

                    // }
                    // catch (UnsupportedEncodingException | NoSuchAlgorithmException e ) {e.printStackTrace();}
                    // catch (IOException e) {e.printStackTrace();}
                    // catch (ClassNotFoundException e) {e.printStackTrace();}
                    break;
                case 2:
                    SignUp();
                    break;
            }
        }
    }

    private void SignUp() { //registrazione
        User tmp = null;
        System.out.println("Insert username: ");
        String user=user_scanner.nextLine();
        System.out.println("Perfect! now choose a password: ");
        String pass = user_scanner.nextLine();
        System.out.println("Insert Name: ");
        String name = user_scanner.nextLine();
        System.out.println("Insert Surname: ");
        String surname = user_scanner.nextLine();
        System.out.println("Insert Email: ");
        String email = user_scanner.nextLine();
        while(!email.contains("@")){
            System.out.println("Something wrong with your Email, try again: ");
            email = user_scanner.nextLine();
        }
        System.out.println("OK! Now choose you profile type:\n-Owner\n-Customer\n: ");
        String type = user_scanner.nextLine().toUpperCase();
        while(!type.equals("OWNER") && !type.equals("CUSTOMER")){
            System.out.println("Something wrong!Choose you profile type:\n - Owner\n-Customer\n: ");
            type = user_scanner.nextLine().toUpperCase();
        }
        switch (type){
            case "OWNER":
                System.out.println("Insert partita IVA: ");
                String piva = user_scanner.nextLine();
                tmp = new Owner(name, surname, user, email, pass, piva); // aggiungere costruttore senza cinema in owner
                break;
            case "CUSTOMER":
                tmp = new Customer(name,surname,user,email,pass);
                break;
        }
            System.out.println("Welcome " + tmp.getClass().getName() + " " + tmp.getName() + " " + tmp.getSurname());
            //inserimento in arraylist
            userList.add(tmp);
            
    }

    private void CustomerClient(Customer tmp) {
        System.out.println("Hello Customer " + tmp.getName() + "\n");
        System.out.println("Choose your option: \n1 View film \n2Buy ticket for a film \n3 View all your tickets");
        int choice = user_scanner.nextInt();
        Ticket t;
        switch (choice){
            case 1:
            //tmp.viewFilm; //aggiungere metodo
            case 2:
            tmp.buyTicket(t); // t non è inizializzato, sistemare
            case 3:
            tmp.viewTickets();
        }

    }

    private void OwnerClient(Owner tmp) {
        System.out.println("Hello Mr." + tmp.getName() + "\n");
        System.out.println("Choose your option:\n1 Add a film to your cinema \n2Remove a film from your cinema \n3 Set ticket cost for a film \n4 Set number of tickets available for a film \n5 Add a cinema");
        int choice = user_scanner.nextInt();
        switch (choice){
            case 1: 
            //tmp.addFilm(); aggiungere metodo
            case 2: 
            //tmp.removeFilm(); aggiungere metodo
            case 3: 
            //tmp.setcost(); aggiungere metodo
            case 4:
            //tmp.setnumberticketsavailable(); aggiungere metodo
            case 5:
               System.out.println("Insert city\n");
               String citycinema = user_scanner.nextLine();
               System.out.println("\nInsert number of rooms\n");
               int numberrooms= user_scanner.nextInt();
               Cinema cinema = new Cinema (citycinema, numberrooms, tmp);
               tmp.setCinema(cinema);
        }
    }

    private User SignIn() { // accesso
        System.out.println("Insert username: ");
        String username=user_scanner.nextLine();
        System.out.println("Insert password: ");
        String password = user_scanner.nextLine();
        
        for(int i=0; i<userList.size(); i++){
            User tmp = userList.get(i);
            if(tmp.signIn(username, password)){
                // se username e password inseriti sono uguali ad una coppia username,password 
                // trovate allo stesso indice, l'utente ha accesso
               System.out.println("You signed in correctly");  
               /*String type = tmp.getClass().getName().toUpperCase();
               String s1= new String("OWNER");
               String s2= new String ("CUSTOMER");
               boolean equalstringowner = type.equals(s1);
               if(equalstringowner==true){
                
                //menu owner
                System.out.println("Welcome owner. Choose your option: \n1 Add a film to a cinema \n2 other options");
               }
               boolean equalstringcustomer = type.equals(s2);
               if(equalstringcustomer==true){
                 //menu customer
                 System.out.println("Welcome customer. Choose your option: \n1 Buy a ticket \n2 View your ticketlist");
                 int choice = user_scanner.nextInt();
                 Ticket t;
                 switch (choice){
                    case 1:
                        tmp.buyTicket(t); // errore sul metodo, tmp è user, il metodo è solo per ticket
                    case 2:
                        tmp.viewTickets(); // stesso errore
                        
                 }
                 
               }*/

               return tmp;        
            }
        }

        return null;
    };


}