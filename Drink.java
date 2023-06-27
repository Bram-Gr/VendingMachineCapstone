
public class Drink extends Product {


    public Drink(String productName, String slotKey, int productPrice, String productType) {
        super(productName, slotKey, productPrice, productType);
    }

    public Drink() {
    }

    ;

    @Override
    public void use() {

    }

    @Override
    public String message() {
        return "Glug Glug, Yum!";
    }
}