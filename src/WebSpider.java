import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class WebSpider {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java WebSpider <URL>");
            return;
        }

        String urlToFetch = args[0];
        try {
            // Fetch the initial web page
            String webpageContent = fetchWebPage(urlToFetch);
            saveToFile(urlToFetch, webpageContent);

            // Scan for links and fetch linked web pages
            Set<String> links = extractLinks(webpageContent);
            for (String link : links) {
                String linkedWebpageContent = fetchWebPage(link);
                saveToFile(link, linkedWebpageContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String fetchWebPage(String url) throws IOException {
        URL website = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) website.openConnection();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
            return content.toString();
        }
    }

    private static Set<String> extractLinks(String content) {
        Set<String> links = new HashSet<>();
        Pattern pattern = Pattern.compile("a href=\"(http[^\"]+)\"");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            links.add(matcher.group(1));
        }
        return links;
    }

    private static void saveToFile(String url, String content) throws IOException {
        String fileName = url.replaceAll("[^a-zA-Z0-9]", "_") + ".html";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }
}
