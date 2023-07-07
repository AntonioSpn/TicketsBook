public class Ticket {
   private int type; //1 adulti, 2 bambini
   private double cost;
   private String barcode;


    public Ticket(int type, double cost, String barcode) {
        this.setType(type);
        this.setCost(cost);
        this.setBarcode(barcode);
    }


    public int getType() {
        return this.type;
    }

    public void setType(int type) {

        if(type!=1 && type !=2){
            System.out.println("Type must be 1 or 2, 1 for adults 2 for kids");
            return;
        };

        this.type = type;
    }

    public double getCost() {
        return this.cost;
    }

    public void setCost(double cost) {

        if (cost <0){
            System.out.println("Cost can't be negative");
            return;
        };

        this.cost = cost;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }




}