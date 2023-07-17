import java.io.Serializable;

public abstract class User implements Serializable {
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;

    public User(String name, String surname, String username, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
    }


    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean signIn (String username, String password){
        if(this.password.equals(password) && this.username.equals(username)) return true;
        else return false;
    }
}