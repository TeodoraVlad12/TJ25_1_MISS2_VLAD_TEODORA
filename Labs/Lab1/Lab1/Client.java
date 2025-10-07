import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:8080/Lab1_war_exploded/controller");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("User-Agent", "JavaClient");
        
        String data = "pageChoice=1";
        conn.getOutputStream().write(data.getBytes());
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        System.out.println(reader.readLine());
        reader.close();
    }
}