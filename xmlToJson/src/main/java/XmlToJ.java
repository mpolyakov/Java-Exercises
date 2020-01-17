import org.json.JSONObject;
import org.json.XML;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class XmlToJ {

    public static URL url = null;
    public static String body = null;
    public String ipAdress = "10.65.26.10";
    public static String xmlString = null;

    //Отключение проверки сертификата ->
    static {
        disableSslVerification();
    }

    private static void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
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

        try {
            url = new URL("https://10.65.26.10/getxml?location=/Status/Audio");                                         //Запрос статуса кодека

            String userCredentials = "operator:password";
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");                                                                  //1-GET

            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Authorization", basicAuth);


            InputStream content = (InputStream) connection.getInputStream();                             //чтение из потока
            BufferedReader in = new BufferedReader(new InputStreamReader(content));
            String line;

            while ((line = in.readLine()) != null) {
                xmlString += line;
//                System.out.println(line);
            }                                                                                           //чтение из потока

        } catch (Exception e) {
            e.printStackTrace();
        }

//        String soapmessageString = "";
        JSONObject soapDatainJsonObject = XML.toJSONObject(xmlString);
        System.out.println(soapDatainJsonObject);
    }


}
