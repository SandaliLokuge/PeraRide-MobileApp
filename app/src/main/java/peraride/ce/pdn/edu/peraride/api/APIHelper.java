package peraride.ce.pdn.edu.peraride.api;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.databind.JsonMappingException;

import peraride.ce.pdn.edu.peraride.api.response.Ancestor;
import peraride.ce.pdn.edu.peraride.api.response.Error;
import peraride.ce.pdn.edu.peraride.api.response.factory.AncestorsFactory;
import peraride.ce.pdn.edu.peraride.api.util.JsonService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;


public class APIHelper {

    private static APIHelper instance;

    private APIHelper() {
    }

    public static APIHelper getInstance() {
        if (instance == null) instance = new APIHelper();
        return instance;
    }

    public void sendJsonObjectRequest(final PostManResponseListener context, final AncestorsFactory factory, int httpMethod, String apiUrl,
                                      JSONObject requestBody) {
        JsonObjectRequest request = new JsonObjectRequest(httpMethod, apiUrl, requestBody, new Response.Listener<JSONObject>() {
            /**
             * Called when a response is received.
             *
             * @param response
             */
            @Override
            public void onResponse(JSONObject response) {
                if (context != null) {
                    try {
                        context.onResponse(factory.parse(JsonService.toJsonNode("{\"data\":" + response.toString() + "}")));
                    } catch (IOException e) {
                        e.printStackTrace();
                        context.onError(new Error(e.getLocalizedMessage()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        context.onError(new Error(e.getLocalizedMessage()));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (context != null)
                    context.onError(VolleySingleton.getInstance().getErrorMessage(error));
            }
        }) {
            /**
             * Returns a list of extra HTTP headers to go along with this request. Can
             * throw {@link AuthFailureError} as authentication may be required to
             * provide these values.
             *
             * @throws AuthFailureError In the event of auth failure
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return VolleySingleton.getInstance().getAPIHeaderJson();
            }
        };


        VolleySingleton.getInstance().addToRequestQueue(request);

    }

    public void sendJsonObjectRequestWithJWTToken(final PostManResponseListener context, final AncestorsFactory factory, int httpMethod, String apiUrl,
                                                  JSONObject requestBody) {
        JsonObjectRequest request = new JsonObjectRequest(httpMethod, apiUrl, requestBody, new Response.Listener<JSONObject>() {
            /**
             * Called when a response is received.
             *
             * @param response
             */
            @Override
            public void onResponse(JSONObject response) {
                if (context != null) {
                    try {
                        context.onResponse(factory.parse(JsonService.toJsonNode("{\"data\":" + response.toString() + "}")));
                    } catch (IOException e) {
                        e.printStackTrace();
                        context.onError(new Error(e.getLocalizedMessage()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        context.onError(new Error(e.getLocalizedMessage()));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (context != null)
                    context.onError(VolleySingleton.getInstance().getErrorMessage(error));
            }
        }) {
            /**
             * Returns a list of extra HTTP headers to go along with this request. Can
             * throw {@link AuthFailureError} as authentication may be required to
             * provide these values.
             *
             * @throws AuthFailureError In the event of auth failure
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return VolleySingleton.getInstance().getAPIHeaderJsonWithJwtToken();
            }
        };


        VolleySingleton.getInstance().addToRequestQueue(request);

    }

    public void sendTextRequest(final PostManResponseListener context, final AncestorsFactory factory, int httpMethod, String apiUrl) {
        StringRequest request = new StringRequest(httpMethod, apiUrl, new Response.Listener<String>() {
            /**
             * Called when a response is received.
             *
             * @param response
             */
            @Override
            public void onResponse(String response) {
                if (context != null) {
                    try {
                        context.onResponse(factory.parse(JsonService.toJsonNode("{\"data\":" + new JSONObject(response).toString() + "}")));
                    } catch (IOException e) {
                        e.printStackTrace();
                        context.onError(new Error(e.getLocalizedMessage()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        context.onError(new Error(e.getLocalizedMessage()));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (context != null)
                    context.onError(VolleySingleton.getInstance().getErrorMessage(error));
            }
        }) {
            /**
             * Returns a list of extra HTTP headers to go along with this request. Can
             * throw {@link AuthFailureError} as authentication may be required to
             * provide these values.
             *
             * @throws AuthFailureError In the event of auth failure
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return VolleySingleton.getInstance().getAPIHeaderText();
            }
        };


        VolleySingleton.getInstance().addToRequestQueue(request);

    }

    public void sendJsonObjectRequestWithoutAuth(final PostManResponseListener context, final AncestorsFactory factory, int httpMethod, String apiUrl,
                                                 JSONObject requestBody) {
        JsonObjectRequest request = new JsonObjectRequest(httpMethod, apiUrl, requestBody, new Response.Listener<JSONObject>() {
            /**
             * Called when a response is received.
             *
             * @param response
             */
            @Override
            public void onResponse(JSONObject response) {
                if (context != null) {
                    try {
                        context.onResponse(factory.parse(new JSONObject("{\"data\":" + response.toString() + "}")));
                    } catch (IOException e) {
                        e.printStackTrace();
                        context.onError(new Error(e.getLocalizedMessage()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        context.onError(new Error(e.getLocalizedMessage()));
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (context != null)
                    context.onError(VolleySingleton.getInstance().getErrorMessage(error));
            }
        }) {
            /**
             * Returns a list of extra HTTP headers to go along with this request. Can
             * throw {@link AuthFailureError} as authentication may be required to
             * provide these values.
             *
             * @throws AuthFailureError In the event of auth failure
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return VolleySingleton.getInstance().getAPIHeaderJsonWithJwtToken();
            }
        };

        VolleySingleton.getInstance().addToRequestQueue(request);

    }

    public interface PostManResponseListener {
        void onResponse(Ancestor ancestor);

        void onError(Error error);
    }
}
