package peraride.ce.pdn.edu.peraride.api.response.factory.impl;

import org.json.JSONObject;

import java.io.IOException;

import peraride.ce.pdn.edu.peraride.api.response.Ancestor;
import peraride.ce.pdn.edu.peraride.api.response.factory.AncestorsFactory;
import peraride.ce.pdn.edu.peraride.api.response.factory.LogoutResponse;
import peraride.ce.pdn.edu.peraride.util.UtilityManager;

/**
 * Created by user on 5/28/2018.
 */

public class AncestorLogoutResponseFactory implements AncestorsFactory {
    @Override
    public Ancestor parse(JSONObject response) throws IOException {
        return UtilityManager.getDefaultObjectMapper().readValue(response.toString(), LogoutResponse.class);
    }
}
