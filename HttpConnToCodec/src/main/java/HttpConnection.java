import javax.net.ssl.*;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
//import java.util.Base64;


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
//        //Проверка доступности узла
//        try{
//            InetAddress address = InetAddress.getByName("10.65.26.10");
//            boolean reachable = address.isReachable(3000);
//
//            System.out.println("Проверка доступности видеотерминала: " + reachable);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        //Проверка доступности узла



        body = "<Configuration> <SystemUnit> <Name>Переговорная Основа Лаб</Name> </SystemUnit> </Configuration>";
//        body = "<Configuration> <NetworkServices> <HTTP> <Mode>HTTP+HTTPS</Mode> </HTTP> </NetworkServices> </Configuration>";
//        body = "<Configuration> <NetworkServices> <Websocket>FollowHTTPService</Websocket> </NetworkServices> </Configuration>";

        try {
            url = new URL ("https://10.65.26.11/getxml?location=/Status/UserInterface/ContactInfo");                                         //1-GET
//            url = new URL ("https://10.65.26.10/putxml");                                                    //2-POST

            String userCredentials = "operator:password";
//            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes())); //Работает только с Java 8

            String encoded = DatatypeConverter.printBase64Binary(userCredentials.getBytes()); //Работает со старыми версиями Java
            String basicAuth = "Basic " + new String(encoded);                                //Работает со старыми версиями Java

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");                                                                  //1-GET
//            connection.setRequestMethod("POST");                                                                 //2-POST
            connection.setRequestProperty("Content-Type", "text/xml");                                           //2-POST
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestProperty("Authorization", basicAuth);
            connection.connect();

            System.out.println("Внимание! Ответ сервера " + connection.getResponseMessage());

//            OutputStream output = new BufferedOutputStream(connection.getOutputStream());               //2-запись в поток и отправка
//            output.write(body.getBytes());                                                              //2-запись в поток и отправка
//            output.flush();                                                                             //2-запись в поток и отправка

                InputStream content = (InputStream)connection.getInputStream();                             // -> чтение из потока
                BufferedReader in = new BufferedReader (new InputStreamReader (content));
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }                                                                                           // <- чтение из потока


        } catch (SocketTimeoutException e){
            System.out.println("Видеотерминал не отвечает");
        } catch (UnknownHostException e) {
            System.out.println("Неизвестный хост");
        } catch (IOException e) {
            System.out.println("Запрос не авторизован");
        } catch(Exception e) {
            e.printStackTrace();
        }


    }

}