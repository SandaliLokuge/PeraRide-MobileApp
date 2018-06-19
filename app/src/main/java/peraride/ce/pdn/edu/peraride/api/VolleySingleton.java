package peraride.ce.pdn.edu.peraride.api;

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
import peraride.ce.pdn.edu.peraride.api.util.ClientSSLSocketFactory;
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

/**
 * Volley - Using for Transmitting data over network
 * <p/>
 * Volley is designed to queue all your requests.
 * It wouldn't make sense to have more than one queue,
 * and that's why it's a singleton.
 * <p/>
 *
 */
public class VolleySingleton {

    private static final String TAG = VolleySingleton.class.getSimpleName();
    private static VolleySingleton mInstance;
    private static SSLContext sslContext;
    private RequestQueue mRequestQueue;

    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(RideApplication.getInstance());
    }

    /**
     * Singleton class for VolleySingleton
     *
     * @return volley singleton object
     */
    public static synchronized VolleySingleton getInstance() {
        if (mInstance == null) {
            mInstance = new VolleySingleton();
        }
        return mInstance;
    }

    public void cancelAll(Object o) {
        mRequestQueue.cancelAll(o);
    }

    /**
     * Get the Volley Request Queue
     *
     * @return Request Queue
     */
    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(RideApplication.getInstance(),
                     new HurlStack(null, ClientSSLSocketFactory.getSocketFactory()));
        }
        return mRequestQueue;
    }

    /**
     * Adding API request to Volley
     *
     * @param req request
     * @param <T> getting the request in queue
     */
    public <T> void addToRequestQueue(Request<T> req) {
        //getRequestQueue().getCache().clear();
        if (/*CommonUtils.isInternetAvailable()*/true) {
            req.setRetryPolicy(new DefaultRetryPolicy(
                    30 * 1000, 0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            req.setTag(TAG);
            req.setShouldCache(false);
            getRequestQueue().add(req);
        }
    }

    /**
     * Getting the ErrorResponse description from VolleyError
     *
     * @param error volley error
     * @return ErrorResponse message
     */
    public Error getErrorMessage(VolleyError error) {
//        Log.v("Data :" , new String(error.networkResponse.data));
        if (error != null) {
            if (error instanceof com.android.volley.TimeoutError) {
                String code = "Error response";
                String message = "Network Timeout";
                return new Error(error.getMessage());
            } else if (error instanceof com.android.volley.NoConnectionError) {
                String code = "No network";
                String message = "Please Check Your Internet Connection";
                return new Error(error.getMessage());
            } else if (error instanceof com.android.volley.ParseError) {
                String code = "Parse error response";
                String message = "Can't Proceed This Task";
                return new Error(error.getMessage());
            } else {
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()));
                    try {
                        return objectMapper.readValue(error.networkResponse.data, Error.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return new Error(new String(error.networkResponse != null ? error.networkResponse.data != null ? error.networkResponse.data
                        : String.valueOf("Couldn't proceed this request").getBytes() : String.valueOf("Couldn't proceed this request").getBytes()));
            }

        } else {
            return null;
        }
    }


    /**
     * Prepare API Header with x-www-form-urlencoded
     *
     * @return headers
     */
    public HashMap<String, String> getAPIHeaderUrlEncoded() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        // headers.put("Content-Encoding", "gzip");
        return headers;
    }

    /**
     * Prepare API Header with UserAuth Token
     *
     * @return headers
     */
    public HashMap<String, String> getAPIHeaderJson() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("X-AUTH-TOKEN", SessionManager.getInstance().getJWTToken());
        // headers.put("Content-Encoding", "gzip");
        return headers;
    }

    /**
     * Prepare API Header with UserAuth jwt Token
     *
     * @return headers
     */
    public HashMap<String, String> getAPIHeaderJsonWithJwtToken() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("X-AUTH-TOKEN", SessionManager.getInstance().getJWTToken());
        // headers.put("Content-Encoding", "gzip");
        return headers;
    }

    /**
     * Prepare API Header with UserAuth Token
     *
     * @return headers
     */
    public HashMap<String, String> getAPIHeaderText() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain; charset=utf-8");
        headers.put("X-AUTH-TOKEN", SessionManager.getInstance().getJWTToken());
        // headers.put("Content-Encoding", "gzip");
        return headers;
    }

    /**
     * Prepare API Header with UserAuth Token
     *
     * @return headers
     */
    public HashMap<String, String> getAPIHeaderWithoutAuth() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        // headers.put("Content-Encoding", "gzip");
        return headers;
    }
}
