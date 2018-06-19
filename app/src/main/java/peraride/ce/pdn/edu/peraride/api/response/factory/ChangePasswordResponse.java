package peraride.ce.pdn.edu.peraride.api.response.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

import peraride.ce.pdn.edu.peraride.api.response.Ancestor;
import peraride.ce.pdn.edu.peraride.data.model.response.ChangePasswordSerializer;
import peraride.ce.pdn.edu.peraride.data.model.response.LoginSerializer;

/**
 * Created by user on 5/28/2018.
 */

public class ChangePasswordResponse extends Ancestor<ChangePasswordSerializer> {
    public ChangePasswordResponse(@JsonProperty("data") ChangePasswordSerializer data) {
        super(data);
    }
}
