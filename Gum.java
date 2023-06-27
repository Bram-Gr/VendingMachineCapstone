
public class Gum extends Product {
    public Gum(String productName, String slotKey, int productPrice, String productType) {
        super(productName, slotKey, productPrice, productType);
    }

    public Gum() {
    }

    ;

    @Override
    public void use() {

    }

    @Override
    public String message() {
        return "Chew Chew, Yum!";
    }
}
