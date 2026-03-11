public class Router {
    private RequestHandler requestHandler;

    public Router() {
        this.requestHandler = new RequestHandler();
    }

    public String route(HTTPRequest request) {
        try {
            String method = request.getMethod();
            String path = request.getPath();

            System.out.println("Method: [" + method + "]");
            System.out.println("Path: [" + path + "]");

//            if (method == null || !method.toUpperCase().equals("POST")) {
//                return HTTPResponse.buildErrorResponse("Only POST method supported");
//            }

            if (path.equals("/api/register")) {
                return requestHandler.handleRegister(request);
            } else if (path.equals("/api/login")) {
                return requestHandler.handleLogin(request);
            } else if (path.equals("/api/deposit")) {
                return requestHandler.handleDeposit(request);
            } else if (path.equals("/api/withdraw")) {
                return requestHandler.handleWithdraw(request);
            } else if (path.equals("/api/transfer")) {
                return requestHandler.handleTransfer(request);
            } else if (path.equals("/api/balance")) {
                return requestHandler.handleBalance(request);
            } else if (path.equals("/api/transactions")) {
                return requestHandler.handleTransactions(request);
            } else {
                return HTTPResponse.buildErrorResponse("Endpoint not found: " + path);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return HTTPResponse.buildErrorResponse("Server error: " + e.getMessage());
        }
    }
}