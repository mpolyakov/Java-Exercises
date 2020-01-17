import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Base64;



public class HttpConnection {
    public static URL url = null;
    public static String body = null;
    public String ipAdress = "10.65.26.10";

    //Отключение проверки сертификата ->
    static {
        disableSslVerification();
    }

    private static void disableSslVerification() {
        try
        {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
    // <- Отключение проверки сертификата

    public static void main(String[] args) {
        //Reachability
        try{
            InetAddress address = InetAddress.getByName("10.65.26.10");
            boolean reachable = address.isReachable(10000);

            System.out.println("Проверка доступности видеотерминала: " + reachable);
        } catch (Exception e){
            e.printStackTrace();
        }
        //Reachability



//        body = "<Configuration> <SystemUnit> <Name>Переговорная Основа Laab</Name> </SystemUnit> </Configuration>";
        body = "<Configuration> <NetworkServices> <HTTP> <Mode>HTTP+HTTPS</Mode> </HTTP> </NetworkServices> </Configuration>";
//        body = "<Configuration> <NetworkServices> <Websocket>FollowHTTPService</Websocket> </NetworkServices> </Configuration>";

        try {
//            url = new URL ("http://10.65.26.10/status.xml");                                         //1-GET
            url = new URL ("https://10.65.26.10/putxml");                                                    //2-POST

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