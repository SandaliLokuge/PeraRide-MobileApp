package peraride.ce.pdn.edu.peraride.data.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by user on 5/29/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DockStationSerializer implements Serializable {
    private String name;
    private LocationSerializer location;

    public DockStationSerializer() {
    }

    public DockStationSerializer(String name, LocationSerializer location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationSerializer getLocation() {
        return location;
    }

    public void setLocation(LocationSerializer location) {
        this.location = location;
    }
}
