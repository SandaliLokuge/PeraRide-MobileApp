package peraride.ce.pdn.edu.peraride.api.response.factory.impl;

import org.json.JSONObject;

import java.io.IOException;

import peraride.ce.pdn.edu.peraride.api.response.Ancestor;
import peraride.ce.pdn.edu.peraride.api.response.factory.AncestorsFactory;
import peraride.ce.pdn.edu.peraride.api.response.factory.ProfileResponse;
import peraride.ce.pdn.edu.peraride.util.UtilityManager;

/**
 * Created by user on 5/27/2018.
 */

public class AncestorProfileResponseFactory implements AncestorsFactory{
    @Override
    public Ancestor parse(JSONObject response) throws IOException {
        return UtilityManager.getDefaultObjectMapper().readValue(response.toString(), ProfileResponse.class);
    }
}
