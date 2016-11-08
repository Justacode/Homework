import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client {
    public static void main(String[] ar) throws IOException {
        String address = "127.0.0.1";
        System.out.println("Connecting to server...");
        InetAddress ipAddress = InetAddress.getByName(address);
        Socket socket = new Socket(ipAddress, Server.PORT);
        System.out.println("Connected");
        try {
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();
            DataInputStream in = new DataInputStream(new BufferedInputStream(sin));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(sout));
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String serverLine = null;
            String clientLine;
            ArrayList<String> words = new ArrayList();
            System.out.println("Game started, write \"exit\" to finish game");
            System.out.println("Input city name");
            while (true) {
                clientLine = console.readLine().toLowerCase();
                if (!words.contains(clientLine) && (serverLine == null || clientLine.charAt(0) == serverLine.charAt(serverLine.length() - 1))) {
                    words.add(clientLine);
                    System.out.println("Data transfer to the server...");
                    out.writeUTF(clientLine);
                    out.flush();
                    serverLine = in.readUTF();
                    System.out.println("Server responsed : " + serverLine);
                    words.add(serverLine);
                    System.out.println("Your letter:" + serverLine.charAt(serverLine.length() - 1));
                    System.out.println("You can write");
                    System.out.println();
                } else if (clientLine.equals("exit")) {
                    break;
                } else {
                    System.out.println("You entered incorrect word. Try again");
                }
            }
        } finally {
            socket.close();
        }
    }
}
