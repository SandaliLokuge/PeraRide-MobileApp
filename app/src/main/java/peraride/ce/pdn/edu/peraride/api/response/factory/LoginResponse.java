package peraride.ce.pdn.edu.peraride.api.response.factory;


import com.fasterxml.jackson.annotation.JsonProperty;
import peraride.ce.pdn.edu.peraride.api.response.Ancestor;
import peraride.ce.pdn.edu.peraride.data.model.response.LoginSerializer;

public class LoginResponse extends Ancestor<LoginSerializer> {

    public LoginResponse(@JsonProperty("data") LoginSerializer data) {
        super(data);
    }
}
