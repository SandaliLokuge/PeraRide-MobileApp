package peraride.ce.pdn.edu.peraride.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;



public class Ancestor<T> {

    private T data;


    @JsonCreator
    protected Ancestor(@JsonProperty("data") T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }


}
