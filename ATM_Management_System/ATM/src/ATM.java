import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank theBank = new Bank("Bangladesh National Bank");

        // add a user which also create a saving account
        User aUser = theBank.addUser("Didar", "Bhuiyan", "1234");

        // add a checking account for our user
        Account newAccount = new Account("Checking", aUser, theBank);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {
            // stay in the login prompt unitl successful login
            curUser = ATM.mainMenuPrompt(theBank, sc);
            // stay in main menu until user quits
            ATM.printUserMenu(curUser, sc);
        }
    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc) {
        String userID;
        String pin;
        User authUser;
        // prompt the user ID/pin combo until a correct one is reached
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID : ");
            userID = sc.nextLine();
            System.out.print("Enter Pin : ");
            pin = sc.nextLine();
            // try to get the user object corresponding to the ID and pin combo
            authUser = theBank.userLog(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect ID/Pin,please try again");
            }
        } while (authUser == null);// continue looping until successful login
        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner sc) {
        // print a summary of the user's accounts
        theUser.printAccounstSummary();
        int choice;
        // user menu
        do {
            System.out.printf("Welcome %s,what would you like to do?", theUser.getFirstName());
            System.out.println(" 1) Show account transaction history");
            System.out.println(" 2) Withdraw");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Quit");

            System.out.print("Enter your choice : ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice, Please choose 1-5");
            }

        } while (choice < 1 || choice > 5);
        switch (choice) {
            case 1:
                ATM.showTransHistory(theUser, sc);
                break;
            case 2:
                ATM.WithdrawFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
            case 5:
            //gobble up rest of previous input
            sc.nextLine();
            break;

        }
        if (choice != 5) {
            ATM.printUserMenu(theUser, sc);
        }
    }

     public static void depositFunds(User theUser, Scanner sc) {
       int toAcct;
        double amount;
        double acctBal;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of the amount" + " to deposit in : ",theUser.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Innvalid account,Please try again");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transer (max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount is less than zero");
            }
        } while (amount < 0);
        // gobble up rest of previous input
        sc.nextLine();
        System.out.print("Enter a memo : ");
        memo = sc.nextLine();

        // do the withdraw
        theUser.addAcctTransaction(toAcct, amount, memo);
    }



    public static void showTransHistory(User theUser, Scanner sc) {
        int theAcct;
        // get account whose transaction history to look at
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + "Whose transaction you want to see ",
                    theUser.numAccounts());
            theAcct = sc.nextInt() - 1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account,please try again");
            }
        } while (theAcct < 0 || theAcct >= theUser.numAccounts());
        // Print the transaction history
        theUser.printAcctTransHistory(theAcct);
    }

    /**
     * Process transferring funds from one account to another
     * 
     * @param theUser the logged in User object
     * @param sc
     */
    public static void transferFunds(User theUser, Scanner sc) {
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        // get the amount to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the amount" + " to transfer from : ",theUser.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Innvalid account,Please try again");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        // get the account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the amount" + " to transfer to : ",theUser.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Innvalid account,Please try again");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transer (max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount is less than zero");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n" + "balance of $%.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        // finally,do the transfer
        theUser.addAcctTransaction(fromAcct, -1 * amount, String.format(
                "Transfer to account %s", theUser.getAcctUuid(toAcct)));

        theUser.addAcctTransaction(toAcct, amount, String.format(
                "Transfer to account %s", theUser.getAcctUuid(fromAcct)));
    }

    /**
     * Process a fund withdraw from an account
     * 
     * @param theUser
     * @param sc
     */
    public static void WithdrawFunds(User theUser, Scanner sc) {
        int fromAcct;
        double amount;
        double acctBal;
        String memo;
        //get the amount to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the amount" + " to withdraw from : ",theUser.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Innvalid account,Please try again");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to withdraw (max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount is less than zero");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n" + "balance of $%.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);
        // gobble up rest of previous input
        sc.nextLine();
        System.out.print("Enter a memo : ");
        memo = sc.nextLine();

        // do the withdraw
        theUser.addAcctTransaction(fromAcct, -1 * amount, memo);
    }
}
