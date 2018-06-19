package peraride.ce.pdn.edu.peraride.data.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by user on 5/28/2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class ChangePasswordSerializer implements Serializable {
    private String response;
    private boolean res;

    public ChangePasswordSerializer() {
    }

    public ChangePasswordSerializer(String response, boolean res) {
        this.setResponse(response);
        this.setRes(res);
    }


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }
}
