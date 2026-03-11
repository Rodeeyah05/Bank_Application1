public class Transactions {
    private int id;
    private int accountId;
    private String transactionType;
    private double amount;
    private double balanceAfter;
    private int Recipient_id;
    private String description;
    private String transactionDate;
    private String Status;

    public Transactions(int id, int accountId, String transactionType,double amount, double balanceAfter, int reciepient_id,String description,String transactionDate, String Status) {
        this.id = id;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.Recipient_id = reciepient_id;
        this.description = description;
        this.transactionDate = transactionDate;
        this.Status = Status;
    }
    public Transactions( int accountId, String transactionType,double amount,double balanceAfter,int reciepient_id,String description, String transactionDate, String Status ){
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.Recipient_id = reciepient_id;
        this.balanceAfter = balanceAfter;
        this.description = description;
        this.transactionDate = transactionDate;
        this.Status = Status;
    }
    public Transactions (){
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getAccountId() {
        return accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    public String getTransactionType() {

        return transactionType;
    }
    public  Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    public double getBalanceAfter() {
        return balanceAfter;
    }
    public void setBalanceAfter(double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
    
    public int getReciepient_id() {
        return Recipient_id;
    }
    public void setReciepient_id(int reciepient_id) {
        Recipient_id = reciepient_id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
    public String getStatus() {
        return Status;
    }
    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", transactionType='" + transactionType + '\'' +
                ", balanceAfter=" + balanceAfter +
                ", transactionDate='" + transactionDate + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }

}
