import java.sql.SQLException;

public class RequestHandler {
    UserDAO userDAO = new UserDAO();
    AccountsDAO accountsDAO = new AccountsDAO();
    TransactionsDAO transactionsDAO= new TransactionsDAO();
    private AuthService authService;
    private BankingService bankingService;

    public RequestHandler() {
      this.authService = new AuthService(userDAO,accountsDAO);
        this.bankingService = new BankingService(accountsDAO,transactionsDAO);
    }

    private String extractValue(String json, String key) {
        try {
            String search = "\"" + key + "\"";
            int keyIndex = json.indexOf(search);
            if (keyIndex == -1) return null;

            int colonIndex = json.indexOf(":", keyIndex);
            if (colonIndex == -1) return null;

            // Skip whitespace after colon
            int valueStart = colonIndex + 1;
            while (valueStart < json.length() && json.charAt(valueStart) == ' ') {
                valueStart++;
            }

            // Check if value is a string (has quotes)
            if (json.charAt(valueStart) == '"') {
                valueStart++; // Skip opening quote
                int valueEnd = json.indexOf('"', valueStart);
                return json.substring(valueStart, valueEnd);
            } else {
                // It's a number - read until comma, }, or newline
                int valueEnd = valueStart;
                while (valueEnd < json.length() &&
                        json.charAt(valueEnd) != ',' &&
                        json.charAt(valueEnd) != '}' &&
                        json.charAt(valueEnd) != '\n' &&
                        json.charAt(valueEnd) != '\r') {
                    valueEnd++;
                }
                return json.substring(valueStart, valueEnd).trim();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String handleRegister(HTTPRequest request) {
        try {
            String body = request.getBody();
            System.out.println("Parsing body: " + body);

            String name = extractValue(body, "name");
            String username = extractValue(body, "username");
            String email = extractValue(body, "email");
            String password = extractValue(body, "password");
            String phoneNumber = extractValue(body, "phoneNumber");
            String CreatedAt = extractValue(body, "createdAt");

            System.out.println("Extracted - name: " + name + ", username: " + username);

            if (name == null || name.isEmpty()) {
                return HTTPResponse.buildErrorResponse("Name is required!");
            }
            if (username == null || username.isEmpty()) {
                return HTTPResponse.buildErrorResponse("Username is required!");
            }
            if (email == null || email.isEmpty()) {
                return HTTPResponse.buildErrorResponse("Email is required!");
            }
            if (password == null || password.isEmpty()) {
                return HTTPResponse.buildErrorResponse("Password is required!");
            }
            if (phoneNumber == null || phoneNumber.isEmpty()) {
                return HTTPResponse.buildErrorResponse("Phone number is required!");
            }
            if (CreatedAt == null || CreatedAt.isEmpty() ){
                return HTTPResponse.buildErrorResponse("CreatedAt is required!");
            }

            String result = authService.register(name, username, email, password, phoneNumber,CreatedAt);

            if (result.contains("successful")|| result.contains("Successful")) {
                return HTTPResponse.buildSuccessResponse("{\"message\":\"" + result + "\"}");
            } else {
                return HTTPResponse.buildErrorResponse(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return HTTPResponse.buildErrorResponse("Error: " + e.getMessage());
        }
    }


    public String handleLogin(HTTPRequest request) {
       try{
           String body = request.getBody();
           System.out.println("Parsing body: " + body);

           String username = extractValue(body, "username");
           String password = extractValue(body, "password");

           if (username == null || username.isEmpty()) {
               return HTTPResponse.buildErrorResponse("Username is required!");
           }
           if (password == null || password.isEmpty()) {
               return HTTPResponse.buildErrorResponse("Password is required!");
           }
           String result = authService.Login(username,password);

           if (result.contains("successful")|| result.contains("Successful")){
               return HTTPResponse.buildSuccessResponse("{\"message\":\"" + result + "\"}");
           } else {
               return HTTPResponse.buildErrorResponse(result);
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }
    public String handleDeposit(HTTPRequest request) {
        try {
            String body = request.getBody();
            System.out.println("Body: " + body);

            int accountId = 0;
            double amount = 0.0;

            int accountIdStart = body.indexOf("\"accountId\"");
            if (accountIdStart != -1) {
                int colonIndex = body.indexOf(":", accountIdStart);
                int commaIndex = body.indexOf(",", colonIndex);
                String accountIdStr = body.substring(colonIndex + 1, commaIndex).trim();
                accountId = Integer.parseInt(accountIdStr);
            }

            int amountStart = body.indexOf("\"amount\"");
            if (amountStart != -1) {
                int colonIndex = body.indexOf(":", amountStart);
                int endIndex = body.indexOf("}", colonIndex);
                if (endIndex == -1) {
                    endIndex = body.indexOf(",", colonIndex);
                }
                String amountStr = body.substring(colonIndex + 1, endIndex).trim();
                amount = Double.parseDouble(amountStr);
            }

            System.out.println("Parsed - accountId: " + accountId + ", amount: " + amount);

            if (accountId <= 0) {
                return HTTPResponse.buildErrorResponse("Invalid account ID");
            }
            if (amount <= 0) {
                return HTTPResponse.buildErrorResponse("Invalid amount");
            }


            String result = bankingService.Deposit(accountId, amount);

            if (result.contains("successful")|| result.contains("Successful")) {
                return HTTPResponse.buildSuccessResponse("{\"message\":\"" + result + "\"}");
            } else {
                return HTTPResponse.buildErrorResponse(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return HTTPResponse.buildErrorResponse("Server error: " + e.getMessage());
        }
    }
        public String handleWithdraw(HTTPRequest request) {
            try {
                String body = request.getBody();
                System.out.println("Body: " + body);

                int accountId = 0;
                double amount = 0.0;

                int accountIdStart = body.indexOf("\"accountId\"");
                if (accountIdStart != -1) {
                    int colonIndex = body.indexOf(":", accountIdStart);
                    int commaIndex = body.indexOf(",", colonIndex);
                    String accountIdStr = body.substring(colonIndex + 1, commaIndex).trim();
                    accountId = Integer.parseInt(accountIdStr);
                }

                int amountStart = body.indexOf("\"amount\"");
                if (amountStart != -1) {
                    int colonIndex = body.indexOf(":", amountStart);
                    int endIndex = body.indexOf("}", colonIndex);
                    if (endIndex == -1) {
                        endIndex = body.indexOf(",", colonIndex);
                    }
                    String amountStr = body.substring(colonIndex + 1, endIndex).trim();
                    amount = Double.parseDouble(amountStr);
                }

                System.out.println("Parsed - accountId: " + accountId + ", amount: " + amount);

                if (accountId <= 0) {
                    return HTTPResponse.buildErrorResponse("Invalid account ID");
                }
                if (amount <= 0) {
                    return HTTPResponse.buildErrorResponse("Invalid amount");
                }


                String result = bankingService.Withdraw(accountId, amount);

                if (result.contains("successful")|| result.contains("Successful")) {
                    return HTTPResponse.buildSuccessResponse("{\"message\":\"" + result + "\"}");
                } else {
                    return HTTPResponse.buildErrorResponse(result);
                }

            } catch (Exception e) {
                e.printStackTrace();
                return HTTPResponse.buildErrorResponse("Server error: " + e.getMessage());
            }
        }

    public String handleTransfer(HTTPRequest request) {
        try {
            String body = request.getBody();
            System.out.println("Body: " + body);
            int SenderId = 0;
            int ReceiverId = 0;
            double amount = 0.0;

            int SenderIdStart = body.indexOf("\"SenderId\"");
            if (SenderIdStart != -1) {
                int colonIndex = body.indexOf(":", SenderId);
                int commaIndex = body.indexOf(",", colonIndex);
                String SenderIdStr = body.substring(colonIndex + 1, commaIndex).trim();
                SenderId = Integer.parseInt(SenderIdStr);
            }
            int ReceiverIdStart = body.indexOf("\"ReceiverId\"");
            if (ReceiverIdStart != -1) {
                int colonIndex = body.indexOf(":", ReceiverIdStart );
                int commaIndex = body.indexOf(",", colonIndex);
                String ReceiverIdStr  = body.substring(colonIndex + 1, commaIndex).trim();
                ReceiverId= Integer.parseInt(ReceiverIdStr);
            }
            String accountNumber = extractValue(body, "AccountNumber");

            int amountStart = body.indexOf("\"amount\"");
            if (amountStart != -1) {
                int colonIndex = body.indexOf(":", amountStart);
                int endIndex = body.indexOf("}", colonIndex);
                if (endIndex == -1) {
                    endIndex = body.indexOf(",", colonIndex);
                }
                String amountStr = body.substring(colonIndex + 1, endIndex).trim();
                amount = Double.parseDouble(amountStr);
            }

            if (SenderId <= 0) {
                return HTTPResponse.buildErrorResponse("Invalid account ID");
            }
            if (ReceiverId <= 0) {
                return HTTPResponse.buildErrorResponse("Invalid account ID");
            }
            if (accountNumber == null) {
                return HTTPResponse.buildErrorResponse("Invalid account");
            }
            if (amount <= 0) {
                return HTTPResponse.buildErrorResponse("Invalid amount");
            }


            String result = bankingService.Transfer(SenderId,ReceiverId,accountNumber,amount);

            if (result.contains("successful")|| result.contains("Successful")){
                return HTTPResponse.buildSuccessResponse("{\"message\":\"" + result + "\"}");
            } else {
                return HTTPResponse.buildErrorResponse(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return HTTPResponse.buildErrorResponse("Server error: " + e.getMessage());
        }
    }

    public String handleBalance(HTTPRequest request) {
        try {
            String body = request.getBody();
            System.out.println("Balance body: " + body);


            int accountId = 0;

            int accountIdStart = body.indexOf("\"accountId\"");
            if (accountIdStart != -1) {
                int colonIndex = body.indexOf(":", accountIdStart);

                int commaIndex = body.indexOf(",", colonIndex);
                int braceIndex = body.indexOf("}", colonIndex);

                // Use whichever comes first (or exists)
                int endIndex;
                if (commaIndex != -1 && commaIndex < braceIndex) {
                    endIndex = commaIndex;
                } else {
                    endIndex = braceIndex;
                }

                String accountIdStr = body.substring(colonIndex + 1, endIndex).trim();
                accountId = Integer.parseInt(accountIdStr);
            }

            System.out.println("Extracted - accountId: " + accountId);

            // === VALIDATE ===
            if (accountId <= 0) {
                return HTTPResponse.buildErrorResponse("Invalid account ID");
            }

            // === CALL SERVICE ===
            String balanceStr = bankingService.Balance(accountId);

            // === RETURN RESPONSE ===
            return HTTPResponse.buildSuccessResponse("{\"balance\":" + balanceStr + "}");

        } catch (Exception e) {
            e.printStackTrace();
            return HTTPResponse.buildErrorResponse("Error: " + e.getMessage());
        }
    }

    public String handleTransactions(HTTPRequest request) {

        return HTTPResponse.buildErrorResponse("Transactions not implemented yet");
    }
}