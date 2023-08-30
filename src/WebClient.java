import java.io.*;
import java.net.*;

public class WebClient {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java WebClient <URL>");
            return;
        }

        String urlToFetch = args[0];
        try {
            // Set proxy information if needed
            // System.setProperty("http.proxyHost", "proxy.host.com");
            // System.setProperty("http.proxyPort", "8080");
            URL website = new URL(urlToFetch);
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
