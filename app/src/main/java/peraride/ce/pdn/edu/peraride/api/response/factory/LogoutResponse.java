package peraride.ce.pdn.edu.peraride.api.response.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

import peraride.ce.pdn.edu.peraride.api.response.Ancestor;
import peraride.ce.pdn.edu.peraride.data.model.response.LogoutSerializer;

/**
 * Created by user on 5/28/2018.
 */

public class LogoutResponse extends Ancestor<LogoutSerializer> {
    public LogoutResponse(@JsonProperty("data") LogoutSerializer data) {
        super(data);
    }
}
