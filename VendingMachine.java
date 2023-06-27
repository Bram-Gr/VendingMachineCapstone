import java.util.Scanner;


public class VendingMachine{
    private static final String BANNER = "\n===============================================\n";
    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase Process Menu";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};
    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_OPTION_SELECT_ITEM = "Select Item";
    private static final String PURCHASE_MENU_OPTION_FINISH_SALE = "End Transaction";
    private static final String[] PURCHASE_PROCESS_MENU = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_ITEM, PURCHASE_MENU_OPTION_FINISH_SALE};
    //TODO: make log class and instatiate here
    private Scanner userInput;
    private int balance = 0;
    private double depositAmount = 0;
    private double changeAmount = 0;
    private final String TRANSACTION_FORMATTER =  "%1$-20s %2$-10s $%3$-10s $%4$3.2f";
    private final String LOG_FORMATTER = "%1$-31s $%2$-10s $%3$3.2f";


    Product product;

    public static void main(String[] args) {
        VendingMachine cli = new VendingMachine();
        cli.userInput = new Scanner(System.in);
        cli.run();

    }

    public VendingMachine() {

    }


    public void run() {

        Inventory inv = new Inventory();
        inv.restock();
        LogSummary ls = new LogSummary();

        boolean runMenu = true;
        String[] activeMenu = MAIN_MENU_OPTIONS;

        while (runMenu) {
            displayMenuOptions(activeMenu);
            System.out.println("\nPlease Make a selection");
            String userSelection = userInput.nextLine();
            try {
                int userSelectionLocation = Integer.parseInt(userSelection) - 1;
                String menuSelection = activeMenu[userSelectionLocation];
                System.out.println("You have selected: " + menuSelection);

                switch (menuSelection) {
                    case MAIN_MENU_OPTION_DISPLAY_ITEMS:

                        for (Product invProduct : inv.getProductList()) {
                            System.out.println(invProduct.toString());
                        }
                        break;
                    case MAIN_MENU_OPTION_PURCHASE:
                        activeMenu = PURCHASE_PROCESS_MENU;
                        System.out.println(getBalance());
                        break;
                    case MAIN_MENU_OPTION_EXIT:
                        runMenu = false;
                        break;
                    case PURCHASE_MENU_OPTION_FEED_MONEY:
                        System.out.println(feedMoney(userInput));
                        String firstTransactionEvent = String.format(LOG_FORMATTER, "FEED MONEY " , depositAmount, balance/100.);
                        ls.writeAppLog(ls.formattedEvent + firstTransactionEvent);


                        break;
                    case PURCHASE_MENU_OPTION_SELECT_ITEM:
                        System.out.println("Please enter a slotkey: ");
                        String slotKeySelection = userInput.nextLine().toUpperCase();

                        if (inv.currentSupply.containsKey(slotKeySelection)) {

                            if (inv.currentSupply.get(slotKeySelection).size() <= 0) {
                                System.out.println("SOLD OUT");
                                activeMenu = PURCHASE_PROCESS_MENU;
                            }
                            int productPrice = inv.currentSupply.get(slotKeySelection).get(0).getProductPrice();
                            String productName = inv.currentSupply.get(slotKeySelection).get(0).getProductName();
                            String productSlotKeySelection = inv.currentSupply.get(slotKeySelection).get(0).getSlotKey();
                            String productMessage = inv.getMessage(slotKeySelection);
                            if (balance < productPrice) {
                                System.out.println("Insufficient balance, please add money");
                                activeMenu = PURCHASE_PROCESS_MENU;

                            } else {
                                inv.buyProduct(slotKeySelection);
                                balance -= productPrice;
                                System.out.println("You have selected " + productName + " " + "\n" + productMessage + "\n" + getBalance());
                                String transactionEvent = String.format(TRANSACTION_FORMATTER, productName, "["+productSlotKeySelection+"]", productPrice/100., balance/100.);
                                ls.writeAppLog(ls.formattedEvent + transactionEvent);
                            }

                        } else {
                            System.out.println("Please enter valid slot-key");
                            activeMenu = PURCHASE_PROCESS_MENU;

                        }
                        break;
                    case PURCHASE_MENU_OPTION_FINISH_SALE:
                        System.out.println(makeChange(balance));
                        balance = 0;
                        activeMenu = MAIN_MENU_OPTIONS;
                        String transactionEvent = String.format(LOG_FORMATTER, "GIVE CHANGE " , changeAmount, balance/100.);
                        ls.writeAppLog(ls.formattedEvent + transactionEvent);
                        break;
                    default:
                        System.out.println("Feature not implemented");
                        break;
                }

            } catch (NumberFormatException nfe) {
                System.out.println("Please only enter a number\t");
            } catch (Exception e) {
                System.out.println("Please enter a valid selection\t");
            }
        }
    }


    public void displayMenuOptions(String[] menu) {
        System.out.println(BANNER);
        for (int i = 0; i < menu.length; i++) {
            System.out.println("(" + (i + 1) + ") " + menu[i]);
        }
        System.out.println(BANNER);
    }


    public String getBalance() {
        return String.format("Your current balance is: $%1$3.2f", balance / 100d);
    }

    public String feedMoney(Scanner userInput) {
        System.out.println("Please enter the dollar amount you would like to deposit: ");
        depositAmount = Double.parseDouble(userInput.nextLine());
        balance += depositAmount * 100;
        return String.format("Your amount provided was: $%1$3.2f \nYour updated balance is: $%2$3.2f", depositAmount, (balance / 100.));

    }


    public String makeChange(int balance) {
        String quartersFormat = "";
        String dimesFormat = "";
        String nickelsFormat = "";
        changeAmount = balance/100.;
        if (balance != 0) {
            int quarters = balance / 25;
            quartersFormat = String.format("(25) --> %1d", quarters);
            balance -= (quarters * 25);
            int dimes = balance / 10;
            dimesFormat = String.format("\n(10) --> %1d", dimes);
            balance -= (dimes * 10);
            int nickels = balance / 5;
            nickelsFormat = String.format("\n(5) --> %1d", nickels);

            String change = "Your change is: \n" + quartersFormat + dimesFormat + nickelsFormat;

            return change;
        }
        return "No change needed";
    }

}



/*          -------------------------------FORMAT PLAYGROUND---------------------------------------------
						String.format("Your current balance is: $%.2f", balance/100.0);
				       	 System.out.println("Your current balance is: $" + balance);
                	return String.format("Your updated balance: $%1$3.2f", updatedBalance);
	         String updatedBalance = String.format("Your updated balance is: $%1$3.2f", updateBalance
           -----------------------------------------------------------------------------------------------
*/


