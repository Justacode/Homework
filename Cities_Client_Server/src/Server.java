import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    protected static final int PORT = 3333;

    public static void main(String[] ar) throws IOException {
        ServerSocket ss = new ServerSocket(PORT);
        System.out.println("Waiting for client...");
        Socket socket = ss.accept();
        try {
            System.out.println("Client connected");
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();
            DataInputStream in = new DataInputStream(new BufferedInputStream(sin));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(sout));
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            char last;
            String clientLine;
            String serverLine;
            ArrayList<String> words = new ArrayList<>();
            while (true) {
                clientLine = in.readUTF().toLowerCase();
                last = clientLine.charAt(clientLine.length() - 1);
                System.out.println("Client's word : " + clientLine + " Last leter: " + last);
                words.add(clientLine);
                System.out.println("Next city for client:");
                serverLine = console.readLine().toLowerCase();
                if (!words.contains(serverLine) && serverLine.charAt(0) == last) {
                    words.add(serverLine.toLowerCase());
                    out.writeUTF(serverLine);
                    out.flush();
                    System.out.println("Waiting new word...");
                    System.out.println();
                } else {
                    System.out.println("Uncorrect word");
                }
            }
        } finally {
            socket.close();
            ss.close();
        }
    }
}
