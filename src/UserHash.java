import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserHash {
    private HashMap<String,User> userHashMap;
    private File file;
    
    public UserHash(String path) throws IOException, ClassNotFoundException {
        file = new File(path);
        if(file.createNewFile())userHashMap = new HashMap<>();
        else userHashMap = loadFromFile();
        saveOnFile();
    }

    public UserHash() throws IOException, ClassNotFoundException {
        file=new File("myfile.dat");
        if(file.createNewFile())userHashMap = new HashMap<>();
        else userHashMap = loadFromFile();
        saveOnFile();
    }

    public synchronized void saveOnFile() throws IOException {
        FileOutputStream fileStream = new FileOutputStream(file);
        ObjectOutputStream os = new ObjectOutputStream(fileStream);
        os.writeObject(userHashMap);
        os.close();
    }

    @SuppressWarnings("unchecked")
    private synchronized HashMap<String,User> loadFromFile() throws IOException, ClassNotFoundException {
        HashMap<String,User> userHM = new HashMap<>();
        FileInputStream fileStream = new FileInputStream(file);
        ObjectInputStream os = new ObjectInputStream(fileStream);        
        userHM = (HashMap<String, User>) os.readObject();
        os.close();
        return userHM;
    }

    public synchronized String put (User user){
        String resultAccepted="USERNAME ACCEPTED";
        String resultUsed="THIS USERNAME IS USED";        
        if(userHashMap.containsKey(user.getUsername()))return resultUsed;
        else{
        userHashMap.put(user.getUsername(),user);
        return resultAccepted;
        }
    }

    public User get(String key){
        return userHashMap.get(key);
    }    
    
    public Set<Map.Entry<String, User>> entrySet(){
        return userHashMap.entrySet();
    }

    public synchronized void update(User tmp){
        userHashMap.remove(tmp.getUsername());
        userHashMap.put(tmp.getUsername(),tmp);
    }

    public synchronized boolean containsKey(String key){
        return userHashMap.containsKey(key);
    }
}