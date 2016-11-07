import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final String pdfLinkRegexp = "http([^\"]+)\\.pdf";
    private static final String pdfFileRegexp = "(\\w)+\\.pdf";

    public static void main(String[] args) throws IOException {
        BufferedReader reader = null;
        try {
            URL site = new URL("https://ru.wikipedia.org/wiki/%D0%9B%D0%BE%D0%BD%D0%B4%D0%BE%D0%BD");
            reader = new BufferedReader(new InputStreamReader(site.openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String text = sb.toString();
            Pattern pat = Pattern.compile(pdfLinkRegexp);
            Matcher matcher = pat.matcher(text);
            reader.close();
            while (matcher.find()) {
                String link = text.substring(matcher.start(), matcher.end());
                try {
                    UrlDownloader ud = new UrlDownloader(link);
                    Pattern pat2 = Pattern.compile(pdfFileRegexp);
                    Matcher matcher2 = pat2.matcher(link);
                    if (matcher2.find()) {
                        String filename = link.substring(matcher2.start(), matcher2.end());
                        ud.create(filename);
                    }
                } catch (IOException e) {
                    continue;
                }
            }
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}