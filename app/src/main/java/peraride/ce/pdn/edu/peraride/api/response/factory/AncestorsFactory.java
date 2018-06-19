package peraride.ce.pdn.edu.peraride.api.response.factory;


import peraride.ce.pdn.edu.peraride.api.response.Ancestor;

import org.json.JSONObject;

import java.io.IOException;


public interface AncestorsFactory {
    Ancestor parse(JSONObject response) throws IOException;
}
