import java.io.*;
import java.net.Socket;
import java.net.http.HttpRequest;

public class ClientHandler implements Runnable {
    Socket socket;
    Router router;
    BufferedReader in;
    PrintWriter out;
    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.router = new Router();
    }
    @Override
    public void run() {
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out= new PrintWriter((socket.getOutputStream()),true);

            HTTPRequest httpRequest = new HTTPRequest(in);
            String Response = router.route(httpRequest);
            out.println(Response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try{
                if(socket!= null){
                    socket.close();
                }
                if(in!=null){
                    in.close();
                }
                if(out!=null){
                    out.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
