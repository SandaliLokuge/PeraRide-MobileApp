package peraride.ce.pdn.edu.peraride.data.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 5/29/2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DockStationResponseSerializer implements Serializable{

    private List<DockStationSerializer> response;

    public DockStationResponseSerializer() {
    }

    public List<DockStationSerializer> getResponse() {
        return response;
    }

    public void setResponse(List<DockStationSerializer> response) {
        this.response = response;
    }
}
