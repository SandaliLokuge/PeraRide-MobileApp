package peraride.ce.pdn.edu.peraride.api.response;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.annotation.JsonProperty;



public class Error extends Ancestor<String> {


    public Error(@JsonProperty("data") String data) {
        super(data);
    }

    public Error(VolleyError ignored) {
        super(new String(ignored.networkResponse.data));
    }

    public Error(Throwable ignored) {
        super(ignored.toString());
    }

}
