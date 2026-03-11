public class HTTPResponse {

    public static String buildResponse(int statusCode, String contentType, String body) {
        StringBuilder response = new StringBuilder();

        // Status line
        response.append("HTTP/1.1 ").append(statusCode).append(" ");

        if (statusCode == 200) {
            response.append("OK");
        } else if (statusCode == 400) {
            response.append("Bad Request");
        } else if (statusCode == 404) {
            response.append("Not Found");
        } else if (statusCode == 500) {
            response.append("Internal Server Error");
        }

        response.append("\r\n");

        // Headers
        response.append("Content-Type: ").append(contentType).append("\r\n");
        response.append("Content-Length: ").append(body.length()).append("\r\n");

        // Blank line (separates headers from body)
        response.append("\r\n");

        // Body
        response.append(body);

        // Debug print (optional - you can remove later)
        System.out.println("=== RESPONSE ===");
        System.out.println(response.toString());
        System.out.println("=== END ===");

        return response.toString();
    }

    public static String buildJSONResponse(int statusCode, String jsonBody) {
        return buildResponse(statusCode, "application/json", jsonBody);
    }

    public static String buildSuccessResponse(String jsonBody) {
        return buildJSONResponse(200, jsonBody);
    }

    public static String buildErrorResponse(String errorMessage) {
        String json = "{\"error\":\"" + errorMessage + "\"}";
        return buildJSONResponse(400, json);
    }
}