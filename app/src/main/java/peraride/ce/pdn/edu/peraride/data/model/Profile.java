package peraride.ce.pdn.edu.peraride.data.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;

/**
 * Created by user on 5/27/2018.
 */

public class Profile implements Serializable {
    private String jwtToken;

    public Profile(String jwtToken) {
        this.setJwtToken(jwtToken);
    }

    @JsonGetter("token")
    public String getJwtToken() {
        return jwtToken;
    }

    @JsonSetter("token")
    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
