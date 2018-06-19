package peraride.ce.pdn.edu.peraride.data.model;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;

public class DockStation implements Serializable{
    private String token;

    public DockStation(String token) {
        this.token = token;
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
