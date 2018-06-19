package peraride.ce.pdn.edu.peraride.data.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;

/**
 * Created by user on 6/10/2018.
 */

public class StationData implements Serializable {
    private String token;
    private String name;

    public StationData(String token, String name) {
        this.setToken(token);
        this.setName(name);
    }

    @JsonGetter("token")
    public String getToken() {
        return token;
    }

    @JsonSetter("token")
    public void setToken(String token) {
        this.token = token;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }
}
