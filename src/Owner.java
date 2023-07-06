public class Owner extends User{
    private String partitaIva;

    public Owner(String name, String surname, String username, String email, String password, String partitaIva) {
        super(name, surname, username, email, password);
        this.partitaIva = partitaIva;
    };

    public String getPartitaIva() {
        return this.partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

}
