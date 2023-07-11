import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
                        hash.saveOnFile();
                        System.out.println(hash.toString());
                        break;

                    case "SIGNIN":
                        String username = (String) clientScanner.readObject();
                        String password = (String) clientScanner.readObject();
                        if(hash.containsKey(username)){
                            User tmp = hash.get(username);
                            if(tmp.signIn(username,password)){
                                pw.writeObject("SIGNIN OK"); //mando che il signin è ok
                                pw.writeObject(tmp); //mando l'ìutente
                            }
                            
                        } 
                        pw.writeObject("SIGNIN NOT OK");

                        break;    
                    case "EXIT":
                    default: 
                        log("Closing connection to "+clientSocket.getRemoteSocketAddress());
                        clientSocket.close();
                        hash.saveOnFile();
                        exit = true;
                }
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

