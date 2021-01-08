//package ru.orfac.lab2spring.config;
//
//import org.springframework.context.annotation.Configuration;
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import java.security.cert.X509Certificate;
//
//
//public class SslConfig {
//  public SslConfig() {
//    TrustManager[] trustAllCerts = new X509TrustManager[]{new X509TrustManager() {
//       public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
//
//       public void checkClientTrusted(X509Certificate[] certs, String authType) {}
//
//       public void checkServerTrusted(X509Certificate[] certs, String authType) {}
//    }};
//    try {
//      SSLContext sslContext = SSLContext.getInstance("SSL");
//      sslContext.init(null, trustAllCerts, null);
//
//      HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//    } catch (Exception e) {
//      System.out.println(e.getMessage());
//    }
//
//    HttpsURLConnection
//        .setDefaultHostnameVerifier((hostname, session) -> true);
//
//
//  }
//}
