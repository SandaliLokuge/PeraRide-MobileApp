package peraride.ce.pdn.edu.peraride.api.response.factory.impl;


import peraride.ce.pdn.edu.peraride.api.response.Ancestor;
import peraride.ce.pdn.edu.peraride.api.response.factory.AncestorsFactory;
import peraride.ce.pdn.edu.peraride.api.response.factory.UnlockResponse;
import peraride.ce.pdn.edu.peraride.util.UtilityManager;

import org.json.JSONObject;

import java.io.IOException;


public class AncestorUnlockResponseFactory implements AncestorsFactory {
    @Override
    public Ancestor parse(JSONObject response) throws IOException {
        return UtilityManager.getDefaultObjectMapper().readValue(response.toString(), UnlockResponse.class);
    }
}
