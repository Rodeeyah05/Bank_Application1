import java.sql.SQLException;

public static void main(String[] args) throws SQLException {
    UserDAO userDAO = new UserDAO();
    AccountsDAO accountsDAO = new AccountsDAO();
    RequestHandler requestHandler = new RequestHandler();
    TransactionsDAO transactionsDAO = new TransactionsDAO();
    AuthService auth = new AuthService(userDAO,accountsDAO);

// auth.register("Rodeeyah","Rodeeyah_m20","Rodeeyah@06","musarodiyah04@gmail.com","07043861174","19.00");
//  auth.register("Abidemi","Oluranti22","Oluranti2025","abidemimusa22@gmail.com","08053456428","18:30");
//  auth.register("Rahma","Rahmaa08","RahmaMusa","rahmamusa@gmail.com","08023544160","22:00");
//  auth.register("Abdullah","Adex","Rodeeyah1234","adexadebayo@gmail.com","08096771956","11:00");
//auth.Login()
//  auth.register("Bayo","bayo_apart","Ayo001","adebayo@gmail.com","090890000000","8:57");

// BankingService bankingService = new BankingService(accountsDAO,transactionsDAO);
// String Result = bankingService.Deposit(1,950000);
// System.out.println(Result);
// bankingService.Deposit(2,30000);
// bankingService.Deposit(3,50000);
//bankingService.Deposit(5,1000000);
// bankingService.Withdraw(1,40000);
//String Result = bankingService.Transfer(1,4,"ACC00004",60000);
// System.out.println(Result);
}

