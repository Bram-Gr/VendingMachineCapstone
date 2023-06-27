
public class Chips extends Product {

    public Chips(String productName, String slotKey, int productPrice, String productType) {
        super(productName, slotKey, productPrice, productType);
    }

    public Chips() {
    }

    ;

    @Override
    public void use() {

    }

    @Override
    public String message() {
        return "Crunch Crunch, Yum!";
    }
}
