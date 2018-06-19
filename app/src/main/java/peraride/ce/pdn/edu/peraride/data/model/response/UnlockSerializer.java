package peraride.ce.pdn.edu.peraride.data.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;



@JsonIgnoreProperties(ignoreUnknown = true)
public class UnlockSerializer implements Serializable {

    private String response;
    private boolean res;

    public UnlockSerializer() {
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

}
