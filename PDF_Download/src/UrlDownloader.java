import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlDownloader {
    private URL path;
    private HttpURLConnection urlCon;

    public UrlDownloader(String link) throws IOException {
        path = new URL(link);
        urlCon = (HttpURLConnection) path.openConnection();
    }

    public boolean create(String name) {
        try {
            BufferedInputStream bis = new BufferedInputStream(urlCon.getInputStream());
            File pdf = new File(name);
            FileOutputStream fis = new FileOutputStream(pdf);
            byte[] data = new byte[1024];
            int count = 0;
            while ((count = bis.read(data)) != -1) {
                fis.write(data, 0, count);
            }
            fis.flush();
            fis.close();
            bis.close();
        } catch (IOException e) {
            System.err.println("Can't download file with name:"+name);
        }
        return true;
    }
}
