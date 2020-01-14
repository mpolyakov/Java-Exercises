import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;



public class HttpConnection {
    public static URL url = null;
    public static String body = null;

    public static void main(String[] args) {
        body = "<Configuration> <SystemUnit> <Name>Переговорная Основа Лаб</Name> </SystemUnit> </Configuration>";
//        body = "<Configuration> <NetworkServices> <Websocket>FollowHTTPService</Websocket> </NetworkServices> </Configuration>";

        try {
//            url = new URL ("http://10.65.26.10/status.xml");                                         //1-GET
            url = new URL ("http://10.65.26.10/putxml");                                                    //2-POST

            String userCredentials = "operator:password";
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");                                                                  //1-GET
            connection.setRequestMethod("POST");                                                                 //2-POST
            connection.setRequestProperty("Content-Type", "text/xml");                                           //2-POST

            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Authorization", basicAuth);


            OutputStream output = new BufferedOutputStream(connection.getOutputStream());               //2-запись в поток и отправка
            output.write(body.getBytes());                                                              //2-запись в поток и отправка
            output.flush();                                                                             //2-запись в поток и отправка

            InputStream content = (InputStream)connection.getInputStream();                             //чтение из потока
            BufferedReader in = new BufferedReader (new InputStreamReader (content));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }                                                                                           //чтение из потока

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}