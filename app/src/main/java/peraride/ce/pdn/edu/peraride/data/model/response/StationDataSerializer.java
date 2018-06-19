package peraride.ce.pdn.edu.peraride.data.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by user on 6/10/2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class StationDataSerializer implements Serializable {
    private String noOfBikes;
    private String noOfEmpty;

    public StationDataSerializer() {

    }

    public StationDataSerializer(String noOfBikes, String noOfEmpty) {
        this.setNoOfBikes(noOfBikes);
        this.setNoOfEmpty(noOfEmpty);
    }


    public String getNoOfBikes() {
        return noOfBikes;
    }

    public void setNoOfBikes(String noOfBikes) {
        this.noOfBikes = noOfBikes;
    }

    public String getNoOfEmpty() {
        return noOfEmpty;
    }

    public void setNoOfEmpty(String noOfEmpty) {
        this.noOfEmpty = noOfEmpty;
    }
}
