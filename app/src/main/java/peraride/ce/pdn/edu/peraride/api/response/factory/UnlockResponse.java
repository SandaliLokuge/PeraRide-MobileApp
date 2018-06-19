package peraride.ce.pdn.edu.peraride.api.response.factory;

import com.fasterxml.jackson.annotation.JsonProperty;
import peraride.ce.pdn.edu.peraride.api.response.Ancestor;
import peraride.ce.pdn.edu.peraride.data.model.response.UnlockSerializer;

public class UnlockResponse extends Ancestor<UnlockSerializer> {

    public UnlockResponse(@JsonProperty("data") UnlockSerializer data) {
        super(data);
    }
}
