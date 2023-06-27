
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Inventory {
    private static final String VENDING_MACHINE_SOURCE_FILE = "src/vendingmachine.csv";
    private static final int MAX_INVENTORY_AMOUNT = 5;
    private List<Product> productList = new ArrayList<>();

    public Map<String, List<Product>> currentSupply = new TreeMap<>();

    public Inventory() {
    }

    ;

    public List<Product> getProductList() {
        return this.productList;
    }

    public void restock() {
        File vendingProductsFile = new File(VENDING_MACHINE_SOURCE_FILE);
        try (Scanner restocker = new Scanner(vendingProductsFile)) {
            while (restocker.hasNext()) {
                String productLine = restocker.nextLine() + "|5";

                String[] productProperties = productLine.split("\\|");//this is for getting each element
                for (int i = 0; i < productProperties.length; i++) {
                    productProperties[i] = productProperties[i].trim(); //this is for getting each element without spaces
                }
                Product newProduct = productAssignment(productProperties);

                productList.add(newProduct);

                List<Product> productStockingList = new ArrayList<>();

                for (int i = 0; i < MAX_INVENTORY_AMOUNT; i++) {
                    productStockingList.add(newProduct);
                }

                currentSupply.put(newProduct.getSlotKey(), productStockingList);
            }

        } catch (FileNotFoundException fnf) {
            System.out.println(fnf.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public Product productAssignment(String[] productProperties) {
        Product result = null;

        String slotKey = productProperties[0];
        String productName = productProperties[1];
        int productPrice = (int) (Double.parseDouble(productProperties[2]) * 100);
        String productType = productProperties[3];


        switch (productType.toLowerCase()) {

            case "chip":
                result = new Chips(slotKey, productName, productPrice, productType);
                break;
            case "candy":
                result = new Candy(slotKey, productName, productPrice, productType);
                break;
            case "drink":
                result = new Drink(slotKey, productName, productPrice, productType);
                break;
            case "gum":
                result = new Gum(slotKey, productName, productPrice, productType);
                break;
            default:
                break;

        }
        return result;
    }

    public void buyProduct(String slotKeySelection) {
        if (currentSupply.get(slotKeySelection).size() > 0) {
            currentSupply.get(slotKeySelection).remove(0);
        }
    }

    public String getMessage(String slotKeySelection) {
        Chips chip = new Chips();
        Candy candy = new Candy();
        Drink drink = new Drink();
        Gum gum = new Gum();

        String type = currentSupply.get(slotKeySelection).get(0).getProductType().toLowerCase();
        if (type.equals("chip")) {
            return chip.message();
        }
        if (type.equals("candy")) {
            return candy.message();
        }
        if (type.equals("drink")) {
            return drink.message();
        }
        if (type.equals("gum")) {
            return gum.message();
        } else {
            return null;
        }
    }
}
