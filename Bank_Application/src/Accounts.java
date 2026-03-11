public class Accounts {
    private int Id;
    private int UserId;
    private String accountNumber;
    private String Transaction_Type;
    private double Balance;
    private String createdAt;

    public Accounts() {
    }
    public Accounts(int Id, int UserId, String accountNumber, String Transaction_Type, double Balance, String createdAt) {
        this.Id = Id;
        this.UserId = UserId;
        this.accountNumber = accountNumber;
        this.Transaction_Type = Transaction_Type;
        this.Balance = Balance;
        this.createdAt = createdAt;
    }
    public Accounts(int UserId, String accountNumber, String Transaction_Type, double Balance, String createdAt) {
        this.UserId = UserId;
        this.accountNumber = accountNumber;
        this.Transaction_Type = Transaction_Type;
        this.Balance = Balance;
        this.createdAt = createdAt;
    }

    public Accounts(int userID, String username, String password, String email, String phoneNumber) {
    }

    public Accounts(int userID, String accountNumber) {

    }

    public Accounts(int id, int userId, String accNumber, double balance, String accountType, String status, String createdAt) {
    }

    public int getId() {
        return Id;
    }
    public void setId(int Id) {
        this.Id = Id;
    }
    public int getUserId() {
        return UserId;
    }
    public void setUserId(int UserId) {
        this.UserId = UserId;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getTransaction_Type() {
        return Transaction_Type;
    }
    public void setTransaction_Type(String Transaction_Type) {
        this.Transaction_Type = Transaction_Type;
    }
    public double getBalance() {
        return Balance;
    }
    public void setBalance(double Balance) {
        this.Balance = Balance;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Accounts{" +
                "Id=" + Id +
                ", UserId=" + UserId +
                ", accountNumber='" + accountNumber + '\'' +
                ", Transaction_Type='" + Transaction_Type + '\'' +
                ", Balance=" + Balance +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
