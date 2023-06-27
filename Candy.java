
public class Candy extends Product {

    public Candy(String productName, String slotKey, int productPrice, String productType) {
        super(productName, slotKey, productPrice, productType);
    }

    public Candy() {
    }

    ;

    @Override
    public void use() {

    }

    @Override
    public String message() {
        return "Munch Munch, Yum!";
    }

}
