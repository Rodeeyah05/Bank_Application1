import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HTTPRequest {
    private String method;
    private String path;
    private Map<String, String> headers;
    private String body;

    public HTTPRequest(BufferedReader in) {
        method = "";
        path = "";
        headers = new HashMap<>();
        body = "";
        parseRequest(in);
    }

    private void parseRequest(BufferedReader in) {
        try {
            // Read request line
            String requestLine = in.readLine();
            System.out.println("Request line: " + requestLine);

            if (requestLine != null && !requestLine.isEmpty()) {
                String[] parts = requestLine.split(" ");
                if (parts.length >= 2) {
                    method = parts[0].trim();
                    path = parts[1].trim();
                }
            }

            // Read headers
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                int colon = line.indexOf(':');
                if (colon > 0) {
                    String name = line.substring(0, colon).trim();
                    String value = line.substring(colon + 1).trim();
                    headers.put(name, value);
                }
            }

            // Read body
            if (headers.containsKey("Content-Length")) {
                int length = Integer.parseInt(headers.get("Content-Length"));
                char[] buffer = new char[length];
                in.read(buffer, 0, length);
                body = new String(buffer);
                System.out.println("Body received: " + body);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMethod() { return method; }
    public String getPath() { return path; }
    public String getBody() { return body; }
    public Map<String, String> getHeaders() { return headers; }
}