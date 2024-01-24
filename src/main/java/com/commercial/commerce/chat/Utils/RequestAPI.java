package com.commercial.commerce.chat.Utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class RequestAPI {

    public static String sendFormData(String url, String jsonData) throws Exception {
        HttpRequest request = HttpRequest.newBuilder().uri(new URI(url))
                .header("Content-type",
                        "multipart/form-data; boundary=boundary")
                .POST(buildMultipartData(jsonData)).build();

        HttpClient httpClient = HttpClient.newBuilder()
                .sslContext(RequestAPI.disableCertificateValidation())
                .build();

        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        return response.body();
    }

    private static HttpRequest.BodyPublisher buildMultipartData(String base64FileContent) {
        // Build the multipart/form-data request body with Base64-encoded file content
        String requestBody = "--boundary\r\n" +
                "Content-Disposition: form-data; name=\"image\"\r\n" +
                "Content-Type: application/octet-stream\r\n\r\n" +
                base64FileContent + "\r\n" +
                "--boundary--\r\n";

        return HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8);
    }

    public static SSLContext disableCertificateValidation() {
        try {
            TrustManager[] trustAllCertificates = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }

                    }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new SecureRandom());
            return sslContext;
        } catch (Exception e) {
            return null;
        }
    }
}
