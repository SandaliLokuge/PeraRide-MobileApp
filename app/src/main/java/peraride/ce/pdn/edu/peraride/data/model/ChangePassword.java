package peraride.ce.pdn.edu.peraride.data.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;

/**
 * Created by user on 5/28/2018.
 */

public class ChangePassword implements Serializable {
    private String token;
    private String currentPass;
    private String newPass;

    public ChangePassword(String token, String currentPass, String newPass) {
        this.setToken(token);
        this.setCurrentPass(currentPass);
        this.setNewPass(newPass);
    }

    @JsonGetter("token")
    public String getToken() {
        return token;
    }

    @JsonSetter("token")
    public void setToken(String token) {
        this.token = token;
    }

    @JsonGetter("currentPass")
    public String getCurrentPass() {
        return currentPass;
    }

    @JsonSetter("currentPass")
    public void setCurrentPass(String currentPass) {
        this.currentPass = currentPass;
    }

    @JsonGetter("newPass")
    public String getNewPass() {
        return newPass;
    }

    @JsonSetter("newPass")
    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
