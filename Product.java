
public abstract class Product {
    private String productName;
    private String slotKey;
    private int productPrice;
    private String productType;


    VendingMachine cli;

    public Product() {
    }

    ;

    public Product(String slotKey, String productName, int productPrice, String productType) {
        this.slotKey = slotKey;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productType = productType;

    }


    public String getProductName() {
        return this.productName;
    }

    public String getSlotKey() {
        return this.slotKey;
    }

    public int getProductPrice() {
        return this.productPrice;
    }

    public String getProductType() {
        return this.productType;
    }

    public abstract void use();


    //TODO consider other methods

    public abstract String message();


    @Override
    public String toString() {
        return String.format("[%1$2s] %2$-25s $%3$6.2f %4$s ", slotKey, productName, (productPrice / 100d), productType);

    }
}
