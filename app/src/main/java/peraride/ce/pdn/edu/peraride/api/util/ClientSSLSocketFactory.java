package peraride.ce.pdn.edu.peraride.api.util;

import android.net.SSLCertificateSocketFactory;
import android.net.SSLSessionCache;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import peraride.ce.pdn.edu.peraride.RideApplication;
import peraride.ce.pdn.edu.peraride.api.response.Error;
import peraride.ce.pdn.edu.peraride.util.SessionManager;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ClientSSLSocketFactory extends SSLCertificateSocketFactory {
    private static SSLContext sslContext;

        /**
         * @param handshakeTimeoutMillis
         * @deprecated Use {@link #getDefault(int)} instead.
         */
        public ClientSSLSocketFactory(int handshakeTimeoutMillis) {
            super(handshakeTimeoutMillis);
        }

        public static SSLSocketFactory getSocketFactory(){
            try
            {
                X509TrustManager tm = new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {}

                    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {}

                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };
                   sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[] { tm }, null);

                SSLSocketFactory ssf = ClientSSLSocketFactory.getDefault(10000, new SSLSessionCache(RideApplication.getInstance()));

                return ssf;
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }