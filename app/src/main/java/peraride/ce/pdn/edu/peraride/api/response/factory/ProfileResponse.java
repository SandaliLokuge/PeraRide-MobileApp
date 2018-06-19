package peraride.ce.pdn.edu.peraride.api.response.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

import peraride.ce.pdn.edu.peraride.api.response.Ancestor;
import peraride.ce.pdn.edu.peraride.data.model.response.ProfileSerializer;


/**
 * Created by user on 5/27/2018.
 */

public class ProfileResponse extends Ancestor<ProfileSerializer> {
    public ProfileResponse(@JsonProperty("data") ProfileSerializer data) {
        super(data);
    }
}
