package peraride.ce.pdn.edu.peraride.api.response.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

import peraride.ce.pdn.edu.peraride.api.response.Ancestor;
import peraride.ce.pdn.edu.peraride.data.model.response.ProfileSerializer;
import peraride.ce.pdn.edu.peraride.data.model.response.StationDataSerializer;

/**
 * Created by user on 6/10/2018.
 */

public class StationDataResponse extends Ancestor<StationDataSerializer> {
    public StationDataResponse(@JsonProperty("data") StationDataSerializer data) {

        super(data);
    }
}
