package peraride.ce.pdn.edu.peraride.api.response.factory.impl;

import org.json.JSONObject;

import java.io.IOException;

import peraride.ce.pdn.edu.peraride.api.response.Ancestor;
import peraride.ce.pdn.edu.peraride.api.response.factory.AncestorsFactory;
import peraride.ce.pdn.edu.peraride.api.response.factory.DockStationResponse;
import peraride.ce.pdn.edu.peraride.api.response.factory.LoginResponse;
import peraride.ce.pdn.edu.peraride.util.UtilityManager;

/**
 * Created by user on 5/29/2018.
 * koheda err thiyenne?
 */

public class AncestorDockStationResponseFactory implements AncestorsFactory {
    @Override
    public Ancestor parse(JSONObject response) throws IOException {
        return UtilityManager.getDefaultObjectMapper().readValue(response.toString(), DockStationResponse.class);
    }
}
