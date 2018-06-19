package peraride.ce.pdn.edu.peraride.data.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginSerializer implements Serializable {

    private String response;
    private boolean res;
    private String token;

    public LoginSerializer() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean getRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
