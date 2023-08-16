import java.util.ArrayList;

public class Account {
    private String name;
    private double balance;
    private String uuid;
    private User holder;

    private ArrayList<Transaction>transactions;

    public Account(String name,User holder,Bank theBank){
        this.name=name;
        this.holder=holder;
        this.uuid=theBank.getNewAccountUuid();
        this.transactions=new ArrayList<>();
        holder.addAccount(this);
        theBank.addAccount(this);
    }
    public String getUuid(){
        return this.uuid;
    }
    public String getSummaryLine(){
        //get the account balance 
        double balance=this.getBalance();

        //format the summary ling,depending on the whether the balance is negative

        if(balance>=0){
            return String.format("%s : $%.02f : %s",this.uuid, balance,this.name);
        }
        else{
            return String.format("%s : $(%.02f) : %s",this.uuid, balance,this.name);

      }

    }
    /**
     * get the balance of this account by adding the amounts of the transactions
     * 
     * @return the balance value
     */
    public double getBalance(){
        double balance=0;
        for(Transaction t:this.transactions){
            balance +=t.getAmount();
        }
        return balance;
    }

    //print the transaction history of the account
    public void  printTransHistory(){
        System.out.printf("\nTransaction history for account %s\n",this.uuid);
        for(int t=this.transactions.size()-1; t>=0; t--){
            System.out.printf(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }
    /**
     * Add a new transaction in this account
     * @param amount
     * @param memo
     */
    public void addTransaction(double amount,String memo){
        //create new transaction object and add it to out list
        Transaction newTrans=new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
}
