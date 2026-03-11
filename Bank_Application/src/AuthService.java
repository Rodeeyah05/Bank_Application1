import java.sql.SQLException;
import java.sql.SQLOutput;

public class AuthService {

    public UserDAO userDAO;
    public AccountsDAO accountsDAO;

    public AuthService(UserDAO userDAO, AccountsDAO accountsDAO) {
        this.userDAO = userDAO;
        this.accountsDAO = accountsDAO;
    }

    public AuthService() {
    }

    public String register (String Name, String username, String password, String email, String phoneNumber,String createdAt) throws SQLException {
        //Validating the User
        if (Name == null || Name.isEmpty()) {
            return "Name is required!";
        }

        if (username == null || username.isEmpty()) {
            return "Username is required!";
        }

        if (email == null || email.isEmpty()) {
            return "Email is required!";
        }

//        if (email.contains("@")) {
//            return "Invalid email format!";
//        }

        if (password == null || password.isEmpty()) {
            return "Password is required!";
        }

        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return "Phone number is required!";
        }

        if (username.length() < 3) {
            return "Username must be at least 3 characters!";
        }

        if (password.length() < 6) {
            return "Password must be at least 6 characters!";
        }

        try{
            Users existUser = userDAO.getUserByUsername(username);
            if (existUser != null) {
                return "Username already exists!";
            }
        } catch (SQLException e) {
            return "Error!"+ e.getMessage();
        }

        // Creating a new user
        Users user = new Users(Name,username,password,email,phoneNumber,createdAt);
        try{
            boolean newUser = userDAO.registerUser(user);
            System.out.println("Register method started...");
            if (!newUser) {
                return "Failed to register user!";
            }
        }catch (SQLException e) {
            return "Error!"+ e.getMessage();
        }

        // Checking Created User
        userDAO.getUserByUsername(username);
        Users registeredUser = userDAO.getUserByUsername(username);
        System.out.println("Checking if the user is created.....");
        if (registeredUser == null) {
            return "Failed to register user!";
        }

        // Generate AccountNUmber for Users
        String AccountNumber = "ACC" + String.format("%05d", registeredUser.getId());
        System.out.println("Creating Account number.....");

        // Create BankAccount
        boolean accountCreated = accountsDAO.createAccount(registeredUser.getId(), AccountNumber);
        System.out.println("Account created successfully!, Your account Number is: " + AccountNumber);
        if (!accountCreated) {
            return "Failed to create account!";
        }
        return "Account created successfully!, Your account Number is: " + AccountNumber;
    }
    public String Login (String username, String password) throws SQLException {
        if(username == null || username.isEmpty()){
            return "Username is required!";
        }
        if(password == null || password.isEmpty()){
            return "Password is required!";
        }
        if (userDAO.getUserByUsername(username) == null){
            return "Username not found!";
        }else{
            System.out.println("Login method started...");
        }
        if(!password.equals(userDAO.getUserByUsername(username).getPassword())) {
            return "Passwords do not match!";
        }else{
            System.out.println("Login Successful!");
        }
        return "Success" ;
    }
}


