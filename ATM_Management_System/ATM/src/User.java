import java.security.MessageDigest;
import java.util.ArrayList;

public class User {

    private String firstName;
    private String lastName;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank theBank) {
        this.firstName = firstName;
        this.lastName = lastName;
        // store the pin in hash cause for security
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (Exception e) {
            
            System.out.println("Error occured");
            System.exit(1);
        }

        // get a new unique universal id for uthe user
        this.uuid = theBank.getNewUserUuid();
        // Create a list of account for user

        this.accounts = new ArrayList<>();

        System.out.printf("Now user %s ,%s with id %s created.\n", firstName, lastName, uuid);
    }

    public void addAccount(Account onAcct) {
        this.accounts.add(onAcct);
    }

    public String getUuid() {
        return this.uuid;
    }

    // check whether the given pin matches the true user pin
    public boolean validatePin(String aPin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } catch (Exception e) {
            
            System.out.println("Erorr occured");
        }
        return false;

    }

    // return the firstname of the user
    public String getFirstName() {
        return this.firstName;
    }

    // Account summery
    public void printAccounstSummary() {
        System.out.printf("\n\n%s's accounts summery\n", this.firstName);
        for (int i = 0; i < this.accounts.size(); i++) {
            System.out.printf("%d %s\n", i + 1, this.accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }

    // Get the number of accounts of the user
    public int numAccounts() {
        return this.accounts.size();
    }

    public void printAcctTransHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }

    // Get the balance of a particular account
    // return the balance of hte account
    public double getAcctBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    //get the Uuid of particular account
    //return the Uuid of the account

    public String getAcctUuid(int acctIdx) {
        return this.accounts.get(acctIdx).getUuid();
    }

    //Add a transaction to a particular account
     
    public void addAcctTransaction(int acctIdx,double amount,String memo){
        this.accounts.get(acctIdx).addTransaction(amount,memo);
    }
}
