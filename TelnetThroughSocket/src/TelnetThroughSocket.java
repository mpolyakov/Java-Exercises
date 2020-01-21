import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TelnetThroughSocket {
    public static void main(String[] args) throws IOException {

        Socket pingSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String text;

        try {
            pingSocket = new Socket("10.65.26.10", 22);
            out = new PrintWriter(pingSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(pingSocket.getInputStream()));

            System.out.println(in.readLine());

            // The standard input via BufferedReader.
            BufferedReader inPut = getInput();

            // Read lines until "exit" is entered.
            while ((text = inPut.readLine()) != null)
            {
                // If the input string is "exit".
                if (text.equals("exit"))
                {
                    // Finish this application.
                    break;
                }

                // Send the text to the server.
                out.println(text);
                System.out.println(in.readLine());
                System.out.println(pingSocket.getPort());
                System.out.println(pingSocket.getRemoteSocketAddress());
            }


        } catch (IOException e) {
            return;
        }


        out.close();
        in.close();
        pingSocket.close();
    }

    /**
     * Wrap the standard input with BufferedReader.
     */
    private static BufferedReader getInput() throws IOException
    {
        return new BufferedReader(new InputStreamReader(System.in));
    }

}
