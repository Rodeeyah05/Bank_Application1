import java.sql.SQLException;

public class BankingService {
    UserDAO userDAO = new UserDAO();
    private AccountsDAO accountsDAO;
    private TransactionsDAO transactionsDAO;

    public BankingService(AccountsDAO accountsDAO, TransactionsDAO transactionsDAO) {
        this.accountsDAO = new AccountsDAO();
        this.transactionsDAO = new TransactionsDAO();
    }

    public String Balance(int accountID) throws SQLException {
        Double Balance = accountsDAO.getBalance(accountID);
        Users existaccount = userDAO.getUserByID(accountID);
        if (existaccount == null) {
            return "Account does not exist";
        }
        return Balance.toString();
    }

    public String Deposit (int accountID, double amount) {
        //Validating the amount
        if (amount <= 0) {
            return "Amount must be greater than 0.00";
        }
        if (amount >= 1000000000) {
            return "You cannot deposit more than $10k at once";
        }

        Double NewBalance;
        try {
            Double CurrentBalance = accountsDAO.getBalance(accountID);
            NewBalance = CurrentBalance + amount;
            boolean UpdateBalance = accountsDAO.updateBalance(accountID, NewBalance);
            if (!UpdateBalance) {
                return "Failed to update balance";
            }
            Transactions newtransactions = new Transactions(accountID,"DEPOSIT",amount,NewBalance,0,"Deposit","19:00","Success");
            boolean Record = transactionsDAO.recordTransactions(newtransactions);
            if (!Record) {
                return "Failed to record transactions";
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "Deposited Successfully,your new balance is : " + NewBalance;
    }

    public String Withdraw (int accountID, double amount) {
        if (amount <= 0) {
            return "Amount must be greater than 0.00";
        }
        if (amount >= 1000000000) {
            return "You cannot withdraw more than $10k at once";
        }

        try{
            Double CurrentBalance = accountsDAO.getBalance(accountID);
            if (amount >= CurrentBalance) {
                return "You do not have sufficient funds";
            }
            Double NewBalance = CurrentBalance - amount;

            Boolean UpdateBalance = accountsDAO.updateBalance(accountID,NewBalance);

            if (!UpdateBalance) {
                return "Failed to update balance";
            }
            Transactions newTransactions = new Transactions(accountID,"Withdrawal",amount,NewBalance,0,"Withdrawal","19:00","Success");
            boolean Record = transactionsDAO.recordTransactions(newTransactions);
            if (!Record) {
                return "Failed to record transactions";
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Withdrawal Successfully: " + amount;
    }
public String Transfer (int senderId,int reciepientID,String accountNumber, double amount){
        if (amount <= 0) {
        return "Amount must be greater than 0.00";
        }
        if(amount >= 1000000000){
            return "You cannot withdraw more than $10k at once";
        }

        try {
            Double CurrentBalance = accountsDAO.getBalance(senderId);

            if(amount > CurrentBalance){
                return "Insufficient Balance";
            }
             Accounts account = accountsDAO.getAccountByAccountNumber(accountNumber);
            if(account == null){
                return "Account Not Found";
            }

            if(reciepientID == 0){
                return "Reciever not Found";
            }

            if(reciepientID == senderId){
                return "You cannot transfer money to YOURSELF";

            }
            Double NewBalance = CurrentBalance - amount;
            boolean UpdateBalance = accountsDAO.updateBalance(senderId,NewBalance);
            if (!UpdateBalance) {
                return "Failed to update balance";
            }

            System.out.println("RecieverID"+ reciepientID);

            Double RecieverBalance = accountsDAO.getBalance(reciepientID);
            Double reciepientBalance = RecieverBalance + amount;
            boolean UpdateRecieverBalance = accountsDAO.updateBalance(reciepientID,reciepientBalance);
            if (!UpdateRecieverBalance) {
                return "Failed to update Receiver balance";
            }
            Transactions Sendertransactions = new Transactions(senderId,"Transfer",amount,NewBalance,reciepientID,"Transfer","19:00","Success");
            boolean Record = transactionsDAO.recordTransactions(Sendertransactions);
            if (!Record) {
                return "Failed to record transactions";
            }

            String SenderAccountNumber = account.getAccountNumber();
            System.out.println("Sender Acc" + SenderAccountNumber);
            Transactions RecieverTransactions = new Transactions(reciepientID,"Transfer-In",amount,reciepientBalance,senderId,"Transfer from:"+SenderAccountNumber,"19:00","Success");
            boolean RecieverRecord = transactionsDAO.recordTransactions(RecieverTransactions);
            if (!RecieverRecord) {
                return "Failed to record transactions";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    return "Transfer Successful: "+ amount;
}
}
