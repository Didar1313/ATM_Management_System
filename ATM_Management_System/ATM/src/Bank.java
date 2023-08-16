import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    //create a new Bank object with empty lists of users and accounts
    public Bank(String name){
        this.name=name;
        this.users=new ArrayList<User>();
        this.accounts=new ArrayList<Account>();

    }

    public String getNewUserUuid() {
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;
        // continue loop until we get a unique ID
        do {
            // generate the number
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer) rng.nextInt(10)).toString();
            }

            // check to ensure it's unique

            nonUnique = false;
            for (User u : this.users) {
                if (uuid.compareTo(u.getUuid()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);
        return uuid;
    }

    // create a new universally id for and account
    public String getNewAccountUuid() {
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;
        // continue loop until we get a unique ID
        do {
            // generate the number
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer) rng.nextInt(10)).toString();
            }

            // check to ensure it's unique

            nonUnique = false;
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUuid()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);
        return uuid;
    }
    public void addAccount(Account onAcct) {
        this.accounts.add(onAcct);
    }

    //create a new user of the bank
    public User addUser(String firstName,String lastName,String pin){
        //create a new User object and add it to our list
        User newUser=new User(firstName, lastName, pin, this);
        this.users.add(newUser);
        
        //create a savings account for the user
        Account newAccount=new Account("Savings", newUser,this);
        this.addAccount(newAccount);
        return newUser;
    }

    public User userLog(String userID,String pin){
        //search for list of user

        for(User u:this.users){
            if(u.getUuid().compareTo(userID)==0 && u.validatePin(pin)){
                return u;
            }
        }
        return null;
    }
    public String getName(){
        return this.name;
    }
}
