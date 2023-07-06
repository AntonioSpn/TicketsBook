// import java.io.IOException;
// import java.io.UnsupportedEncodingException;
// import java.security.NoSuchAlgorithmException;
// import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private ArrayList <User> userList; //creazione arraylist
    public Client (){
        this.userList = new ArrayList<User>();
    }
    
    private final Scanner user_scanner = new Scanner(System.in);
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
                        if(tmp == null){break;}
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
                tmp = new Owner(name, surname, user, email, pass, piva);
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
    }

    private void OwnerClient(Owner tmp) {
        System.out.println("Hello Mr." + tmp.getName() + "\n");
    }

    private User SignIn() { // accesso
        System.out.println("Insert username: ");
        String username=user_scanner.nextLine();
        System.out.println("Perfect! now choose a password: ");
        String password = user_scanner.nextLine();
        int i=0;
        boolean find=false;
        for(i=0; i<userList.size(); i++){
            if(userList.get(i).getUsername().equals(username) && userList.get(i).getPassword().equals(password)){
               find = true; // se username e password inseriti sono uguali ad una coppia username,password 
                            // trovate allo stesso indice, l'utente ha accesso
               System.out.println(" You signed in correctly");            
            }
            else {
                System.out.println("Username and password not found");
            }
        }


        return null;
    };
}
