package peraride.ce.pdn.edu.peraride.api.request.helper.impl;

import com.android.volley.Request;

import org.json.JSONException;

import peraride.ce.pdn.edu.peraride.api.APIHelper;
import peraride.ce.pdn.edu.peraride.api.APIURLHelper;
import peraride.ce.pdn.edu.peraride.api.request.helper.DockStationHelper;
import peraride.ce.pdn.edu.peraride.api.response.factory.impl.AncestorDockStationResponseFactory;
import peraride.ce.pdn.edu.peraride.api.response.factory.impl.AncestorLoginResponseFactory;
import peraride.ce.pdn.edu.peraride.api.util.JsonService;
import peraride.ce.pdn.edu.peraride.data.model.DockStation;

/**
 * Created by user on 5/29/2018.
 */

public class DockStationHelperImpl implements DockStationHelper {
    @Override
    public void getStations(DockStation dockStation, APIHelper.PostManResponseListener context) throws JSONException {
        APIHelper.getInstance().sendJsonObjectRequestWithoutAuth(context, new AncestorDockStationResponseFactory(),
                Request.Method.POST, APIURLHelper.getStationsURL(), JsonService.toJsonNode(dockStation));
    }
}
