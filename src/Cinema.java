public class Cinema{
    private String city;
    private int room; //numero sale del cinema
    private Owner owner;



    public Cinema(String city, int room, Owner owner) {
        this.city = city;
        this.room = room;
        this.owner = owner;
    }



    public String getCity() {
        return this.city;
    }

    
    public int getRoom() {
        return this.room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }



}