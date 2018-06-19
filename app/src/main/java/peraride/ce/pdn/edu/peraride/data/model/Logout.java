package peraride.ce.pdn.edu.peraride.data.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;

/**
 * Created by user on 5/28/2018.
 */

public class Logout implements Serializable {
    private String token;

    public Logout(String token) {
        this.setToken(token);
    }

    @JsonGetter("token")
    public String getToken() {
        return token;
    }

    @JsonSetter("token")
    public void setToken(String token) {
        this.token = token;
    }
}
