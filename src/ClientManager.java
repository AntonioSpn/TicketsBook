import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map.Entry;

public class ClientManager implements Runnable {

    private Socket clientSocket;
    private UserHash hash;


    public ClientManager(Socket clientSocket, UserHash hash) {
        this.clientSocket = clientSocket;
        this.hash = hash;
    }


    @Override
    public void run() {
        String s = "Accepted connction from " + clientSocket.getRemoteSocketAddress();
        log(s);   

        try {           
            ObjectInputStream clientScanner = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream pw = new ObjectOutputStream(clientSocket.getOutputStream());
            String cmd = null;
            
            boolean exit = false;
            while (!exit){
                cmd = (String) clientScanner.readObject();
                log(cmd);
                switch (cmd){
                    case "LIST":
                        pw.writeObject("LIST OK");
                        pw.writeObject(hash);
                        break;
                    case "SIGNUP":
                        cmd = (String) clientScanner.readObject();
                        switch (cmd){
                            case "Owner":
                                Owner owner = (Owner) clientScanner.readObject();
                                pw.writeObject(hash.put(owner));
                                break;
                            case "Customer":
                                Customer customer= (Customer) clientScanner.readObject();
                                pw.writeObject(hash.put(customer));
                                break;

                                
                        }

                       
                        System.out.println(hash.toString());
                        break;
                    case "UPDATE":
                        User user = (User) clientScanner.readObject();
                        hash.update(user);                       
                        break;

                    case "SIGNIN":
                        String username = (String) clientScanner.readObject();
                        String password = (String) clientScanner.readObject();
                        if(hash.containsKey(username)){
                            User tmp = hash.get(username);
                            if(tmp.signIn(username,password)){
                                pw.writeObject("SIGNIN OK"); //mando che il signin è ok
                                pw.writeObject(tmp); //mando l'ìutente
                            } else pw.writeObject(("SIGNIN NOT OK"));                                                      
                        } else pw.writeObject("SIGNIN NOT OK");
                        break; 
                    case "SEARCH":
                        ArrayList <Cinema> cinemaList = new ArrayList<>();
                        for (Entry<String, User> entry : hash.entrySet()){ // per ogni entry presente in userList trasformato in un array di entry
                            if(entry.getValue().getClass().getName().equals("Owner") && ((Owner) entry.getValue()).hasCinema() ){ //il valore è User, mi prendo la classe e il nome della classe, se la classe è owner aggiungo
                                cinemaList.add(((Owner) entry.getValue()).getCinema()); //trasformo il valore in un owner e mi prendo il cinema, in quanto User non ha cinema ma owner si
                            }
                        };
                        ArrayList <SearchCinemaFilm> tmp = new ArrayList<>();

                        int type = (int) clientScanner.readObject();
                        String searchString = (String) clientScanner.readObject();

                        switch(type){
                            case 1: // ricerca per titolo del film    
                                for (Cinema cinema: cinemaList){
                                    for (Film f: cinema.getListFilm()){
                                        if(f.getTitle().toUpperCase().equals(searchString.toUpperCase()) && f.ticketAvailable()){
                                            tmp.add(new SearchCinemaFilm(cinema, f));
                                        }
                                    }
                                };  
                                break;             
                            case 2: // ricerca per nome del cinema
                                for (Cinema cinema: cinemaList){
                                    if(cinema.getName().toUpperCase().equals(searchString.toUpperCase())){
                                        for (Film f: cinema.getListFilm()){                    
                                            if(f.ticketAvailable()) tmp.add(new SearchCinemaFilm(cinema, f));
                                        }
                                    }
                                };
                                break;   
                            default:
                                break;    
                        }

                        pw.writeObject(tmp);
                        break;

                    case "EXIT":
                    default: 
                        log("Closing connection to "+clientSocket.getRemoteSocketAddress());
                        clientSocket.close();
                        hash.saveOnFile();
                        exit = true;
                }
                 hash.saveOnFile();
            }
            
        } catch (IOException e) {
                e.printStackTrace();
        } catch (ClassNotFoundException e) {
            
            e.printStackTrace();
        }

   
        
    }

    private void log(String msg){
        System.out.println(Thread.currentThread().getName() + ": "+ msg);
    }

    




}