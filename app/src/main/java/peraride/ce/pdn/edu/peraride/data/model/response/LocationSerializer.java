package peraride.ce.pdn.edu.peraride.data.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by user on 5/29/2018.
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationSerializer implements Serializable {
    private String lat;
    private String lon;

    public LocationSerializer() {
    }

    public LocationSerializer(String lat, String lon) {
        this.setLat(lat);
        this.setLon(lon);
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
