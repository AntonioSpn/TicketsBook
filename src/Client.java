import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.Scanner;

public class Client {
    // private UserHash userList; //creazione userList
    private final Scanner user_scanner = new Scanner(System.in);
    private String address;
    private int port;
    private ObjectOutputStream pw;
    private ObjectInputStream serverStream;
    private Socket socket;
 

    public Client (String address, int port) throws ClassNotFoundException, IOException{
        this.address = address;
        this.port = port;
    }    
    
    public static void main(String[] args) {
        Client user = null;

        try {
            
            user = new Client("192.168.1.178", 8888);
            user.connect();
            user.Menu(); 
        } catch (Exception e) {            
            e.printStackTrace();
            System.exit(-1);
        }               
    }

    private void Menu() throws IOException, ClassNotFoundException {
        while(true){
            System.out.println("Welcome on TicketsBook platform!\nInsert 1 to Sign in or 2 to Sign up or 0 to leave the platform\nInsert choice: ");
            int choice = user_scanner.nextInt();
            user_scanner.nextLine();
            while(choice!=1 && choice!=2 && choice!=0){
                System.out.println("Choose 1 or 2 or 0!\n1: Sign In\n2: Sign Up\n0: Leave \nInsert choice: ");
                choice = user_scanner.nextInt();
                user_scanner.nextLine();
            }

            switch (choice){
                case 1:
                    // try {
                        User tmp = SignIn();
                        if(tmp == null) {
                            System.out.println("SIGN IN ERROR\n");
                            break;
                        }
                        switch (tmp.getClass().getName()){
                            case "Owner":
                                ownerClient((Owner) tmp);
                                break;
                            case "Customer":
                                customerClient((Customer) tmp);
                                break;
                        }

                    // }
                    // catch (UnsupportedEncodingException | NoSuchAlgorithmException e ) {e.printStackTrace();}
                    // catch (IOException e) {e.printStackTrace();}
                    // catch (ClassNotFoundException e) {e.printStackTrace();}
                    break;
                case 2:
                    SignUp();
                    break;
                case 0:
                    
                    quit(1);   
            }

            // userList.saveOnFile();
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
            System.out.println("EMAIL ERROR");
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
            try {
                pw.writeObject("SIGNUP");
                pw.writeObject((tmp.getClass().getName()));
                pw.writeObject(tmp);
                
                String answer = (String) serverStream.readObject();
                if(!answer.equals("USERNAME ACCEPTED")) quit(-1);
                System.out.println("Welcome " + tmp.getClass().getName() + " " + tmp.getName() + " " + tmp.getSurname());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            
            
    }

    private void customerClient(Customer tmp) throws ClassNotFoundException, IOException {
        while(true){
            System.out.println("Hello Customer " + tmp.getName() + "\n");
            System.out.println("Choose your option: \n1: Search film and buy ticket\n2: View all your tickets \n0: Leave");
            int choice = user_scanner.nextInt();
            user_scanner.nextLine();
            //Ticket t = new Ticket(1, 9.00, "CIN1000001");
            //ArrayList <Ticket> tickets = new ArrayList<>();
            switch (choice){            
                case 1:
                    //tmp.buyTicket(t); // t non è inizializzato, sistemare
                    System.out.println ("Search for title or cinema");
                    System.out.println ("1 Search by film \n2 Search by cinema");
                    int ch = user_scanner.nextInt();
                    user_scanner.nextLine();
                    System.out.println("Insert title or name of the cinema");
                    String search = user_scanner.nextLine();

                    Ticket t = searchTicket(search, ch);
                    if(t==null){ //non trova il cinema o il film
                        System.out.println("Impossible buy ticket");
                        break;
                    }

                    tmp.buyTicket(t); // t non è inizializzato, sistemare

                    break;
                case 2:
                    tmp.getTickets().forEach((ticket) -> System.out.println(ticket));
                    break;
                case 0:
                    try {
                        pw.writeObject("UPDATE");
                        pw.writeObject(tmp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                default:
                    System.out.println("Press 1, 2 or 0");
            }
        }
     }

    private void ownerClient(Owner tmp) {
        while (true){
            System.out.println("Hello Mr." + tmp.getName() + "\n");
            System.out.println("Choose your option:");
            if(!tmp.hasCinema()){
                System.out.println("1: Add your cinema");
                
            } else System.out.println("1: Add a film to the cinema \n2 Remove a film from your cinema \n3 Set ticket cost for a film \n4 Set number of tickets available for a film \n5 View all films \n6 View today");
            System.out.println("0: Leave");
            int choice = user_scanner.nextInt();
            user_scanner.nextLine();
            switch (choice){
                case 1: 
                    if (!tmp.hasCinema()) {
                        this.addCinema(tmp);
                        System.out.println("Your cinema is "+tmp.getCinema().getName()+", Placed in: "+tmp.getCinema().getCity());
                    } else {
                        this.addFilm(tmp.getCinema());
                        tmp.getCinema().getListFilm().forEach((film) -> System.out.println(film));    
                    } 
                    break;
                case 2: 
                    if(tmp.hasCinema()){
                        this.removeFilm(tmp.getCinema());
                        tmp.getCinema().getListFilm().forEach((film) -> System.out.println(film));
                    }
                    break;
                case 3:
                    if(tmp.hasCinema()){
                        this.changeCost(tmp.getCinema()); 
                    }   
                    break;
                
                case 4:
                    if(tmp.hasCinema()){
                        this.setNumberTicketsMax(tmp.getCinema());
                    }
                                        
                    break;
                case 5:
                    if(tmp.hasCinema()){
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        tmp.getCinema().getListFilm().forEach((film) -> 
                            System.out.println(
                                film.getTitle() + 
                                " ( " + formatter.format(film.getStartDate()) + " - " + formatter.format(film.getEndDate()) + " ) " +
                                "room " + film.getRoomView() + ", " +
                                "tickets sold " + film.getNumberTicketSold()
                            )
                        );
                    }
                    break;
                case 6:
                    if(tmp.hasCinema()){
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        tmp.getCinema().viewToday(new Date()).forEach((film) -> 
                            System.out.println(
                                film.getTitle() + 
                                " ( " + formatter.format(film.getStartDate()) + " - " + formatter.format(film.getEndDate()) + " ) " +
                                "room " + film.getRoomView() + ", " +
                                "tickets sold " + film.getNumberTicketSold()
                            )
                        );
                    }
                    break;        
                case 0:
                    try {
                        pw.writeObject("UPDATE");
                        pw.writeObject(tmp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    
                    return;
                default: 
                    System.out.println("Press 1, 2, 3, 4, 5, 6 or 0");
            }
        }
    }

    private User SignIn() { // accesso
        System.out.println("Insert username: ");
        String username=user_scanner.nextLine();
        System.out.println("Insert password: ");
        String password = user_scanner.nextLine();

        try {
            pw.writeObject("SIGNIN");
            pw.writeObject(username);
            pw.writeObject(password);
            String answer = (String) serverStream.readObject();
            if(answer.equals("SIGNIN OK")){
                return (User) serverStream.readObject();
            }
        } catch (IOException e) {
            
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return null;
    };

    private void addFilm(Cinema cinema){
        System.out.println("Insert title of the film ");
        String title = user_scanner.nextLine();
        System.out.println("Insert director ");
        String director = user_scanner.nextLine();
        System.out.println("Insert genere of the film "); // tradurre genere
        String gen = user_scanner.nextLine();
        System.out.println("Insert full cost of the ticket ");
        double costAdult= user_scanner.nextDouble();
        user_scanner.nextLine();
        System.out.println("Insert reduced cost of the ticket ");
        double costKid= user_scanner.nextDouble();
        user_scanner.nextLine();   
        System.out.println("Which room is the film played in?");
        int roomView = -1;
        while ( roomView < 1 || roomView > cinema.getRoom().length){
            for (int i=0; i<cinema.getRoom().length; i++){
                System.out.println("Room number "+(i+1) +", seats: " + cinema.getRoom()[i]);
                
            }
            roomView = user_scanner.nextInt();
            user_scanner.nextLine();
        }
        int numberTicketSold = 0; //numero di biglietti venduti
        int numberTicketMax = cinema.getRoom()[roomView-1];
        Date startDate = null;
        Date endDate = null;
        while(startDate == null || endDate == null){
            try {
                System.out.println("Insert start date");
                System.out.println("Insert day: DD");
                String day = user_scanner.nextLine();
                System.out.println("Insert month: MM");
                String month = user_scanner.nextLine();
                System.out.println("Insert year: YYYY");
                String year = user_scanner.nextLine();
                String date_string = year+month+day;
            //Instantiating the SimpleDateFormat class
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");      
            //Parsing the given String to Date object
                startDate = formatter.parse(date_string); 
                System.out.println("Date value: "+startDate);


                System.out.println("Insert end date");
                System.out.println("Insert day: DD");
                day = user_scanner.nextLine();
                System.out.println("Insert month: MM");
                month = user_scanner.nextLine();
                System.out.println("Insert year: YYYY");
                year = user_scanner.nextLine();
                date_string = year+month+day;
            //Instantiating the SimpleDateFormat class
                //SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");      
            //Parsing the given String to Date object
                endDate = formatter.parse(date_string);
                System.out.println("Date value: "+endDate); 
            } catch (ParseException e) {
                System.out.println("Error date");
            }      
        }
        System.out.println ("Film time: ");
        String timeBeginning = user_scanner.nextLine();
        cinema.addFilm(new Film(title, director, startDate, endDate, gen, costAdult, costKid, roomView, numberTicketSold, numberTicketMax, timeBeginning));
    };

    private void addCinema(Owner tmp){
        System.out.println("Insert name of the cinema ");
        String namecinema = user_scanner.nextLine();
        System.out.println("Insert city");
        String citycinema = user_scanner.nextLine();
        System.out.println("Insert number of rooms");
        int numberrooms= user_scanner.nextInt();
        user_scanner.nextLine();
        int [] arraynumber = new int[numberrooms];
        for (int i=0; i<numberrooms; i++){
            System.out.println("How many seats has room number "+(i+1) +"?");
            int num= user_scanner.nextInt();
            user_scanner.nextLine();
            arraynumber[i]=num;
        }
        Cinema cinema = new Cinema (namecinema, citycinema, arraynumber);
        tmp.setCinema(cinema);
    }

    
    private int selectFilm(Cinema cinema){
        int film = -1;
        while ( film < 1 || film > cinema.getListFilm().size()){
            for(int i = 0; i< cinema.getListFilm().size(); i++){
                System.out.println((i+1) + ": "+cinema.getListFilm().get(i).getTitle());
            }
            System.out.println ("Choice index:");
            film = user_scanner.nextInt();
            user_scanner.nextLine();
        };
        return film - 1;

    }
    
    private void removeFilm (Cinema cinema){
        System.out.println("Choose the index at which remove the film");
        cinema.removeFilm(this.selectFilm(cinema));
    }



    private void changeCost (Cinema cinema){
        System.out.println("Choose the index at which edit the cost of the film");
        int film = this.selectFilm(cinema);
        while (true){
            System.out.println("Do you want to edit full price or reduce price?");
            System.out.println("1: Full price \n2 Reduced price");
            int choice = user_scanner.nextInt();
            user_scanner.nextLine();
            switch(choice){
                case 1: 
                    System.out.println("New price: ");
                    double newprice = user_scanner.nextDouble();
                    user_scanner.nextLine();
                    cinema.getListFilm().get(film).setCostAdult(newprice);
                    return;
                case 2:
                    System.out.println("New price: ");
                    double newpricekid = user_scanner.nextDouble();
                    user_scanner.nextLine();
                    cinema.getListFilm().get(film).setCostKid(newpricekid);
                    return; 
                default:
            }

        }
        

    }

    private void setNumberTicketsMax(Cinema cinema){
        System.out.println("Choose the index at which edit the number of tickets available for a film");
        int film = this.selectFilm(cinema);
        int max = cinema.getRoom()[cinema.getListFilm().get(film).getRoomView()-1];
        int min = cinema.getListFilm().get(film).getNumberTicketSold();
       
        int newseat = -1;
        while (newseat < min || newseat > max){
            System.out.println("Setta il nuovo numero di posti");
            newseat = user_scanner.nextInt();
            user_scanner.nextLine();
        }
        cinema.getListFilm().get(film).setNumberTicketMax(newseat);


    }

    private Ticket searchTicket (String searchString, int type) throws ClassNotFoundException, IOException{
        pw.writeObject("SEARCH");
        pw.writeObject(type);
        pw.writeObject(searchString);
        ArrayList <SearchCinemaFilm> tmp = (ArrayList <SearchCinemaFilm>) serverStream.readObject();       
        if (tmp.size() == 0) return null;

        int choice = -1;
        while ( choice < 1 || choice > tmp.size()){
            for(int i = 0; i< tmp.size(); i++){                             
                System.out.println((i+1) + ": "+tmp.get(i));
            }
            System.out.println("Choose the cinema");
            choice = user_scanner.nextInt();
            user_scanner.nextLine();
        }
        choice--;
                
        int typeticket =-1;
        while(typeticket != 1 && typeticket !=2){
        
            System.out.println("Adult or kids? \n1 Adult \n2 Kids");
            typeticket = user_scanner.nextInt();
            user_scanner.nextLine();
        }   
        double cost =0;
        if (typeticket == 1) cost = tmp.get(choice).getFilm().getCostAdult();
        if (typeticket == 2) cost = tmp.get(choice).getFilm().getCostKid();
        Date dayTicket = null;
        while(dayTicket == null){
            System.out.println("Choose date");
            System.out.println("Insert day: DD");
            String day = user_scanner.nextLine();
            System.out.println("Insert month: MM");
            String month = user_scanner.nextLine();
            System.out.println("Insert year: YYYY");
            String year = user_scanner.nextLine();
            String date_string = year+month+day;
        //Instantiating the SimpleDateFormat class
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");      
        //Parsing the given String to Date object
            try {
                dayTicket = formatter.parse(date_string);
            } catch (ParseException e) {
                System.out.println("Error date");
                // e.printStackTrace();
            }
            if(tmp.get(choice).getFilm().getStartDate().compareTo(dayTicket) >0 || tmp.get(choice).getFilm().getEndDate().compareTo(dayTicket) < 0 ){
                // se data scelta è maggiore della data finale o minore di quella iniziale, il film non è disponibile
                dayTicket=null;
                System.out.println("Film not available in that date");
            }                    
        }

        System.out.println ("Do you want to buy a ticket?\n1 yes \n Another number no");
        int buy = user_scanner.nextInt();
        user_scanner.nextLine();
        // sellticket da passare su server TODO
        if(buy == 1 && tmp.get(choice).getFilm().sellTicket()) return new Ticket(typeticket, cost, tmp.get(choice).getBarcode(), tmp.get(choice).getCinema().getName(), tmp.get(choice).getFilm().getRoomView(), tmp.get(choice).getFilm().getTitle(), dayTicket);
        else return null;
    }

    private void connect(){
        System.out.println("Starting Client connection to "+address+":"+port);
        try {
            socket = new Socket(address,port);
            System.out.println("Started Client connection to "+address+":"+port);
            this.pw = new ObjectOutputStream(socket.getOutputStream());
            this.serverStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void quit(int i){
        try {
            pw.writeObject("EXIT");
        } catch (IOException e) {
        
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            
            e.printStackTrace();
        }

        System.exit(i);
    }


   

 
}// chiusura classe