package peraride.ce.pdn.edu.peraride.api.request.helper.impl;

import com.android.volley.Request;

import org.json.JSONException;

import peraride.ce.pdn.edu.peraride.api.APIHelper;
import peraride.ce.pdn.edu.peraride.api.APIURLHelper;
import peraride.ce.pdn.edu.peraride.api.request.helper.StationDataRequestHelper;
import peraride.ce.pdn.edu.peraride.api.response.factory.impl.AncestorDockStationResponseFactory;
import peraride.ce.pdn.edu.peraride.api.response.factory.impl.AncestorProfileResponseFactory;
import peraride.ce.pdn.edu.peraride.api.response.factory.impl.AncestorStationDataResponseFactory;
import peraride.ce.pdn.edu.peraride.api.util.JsonService;
import peraride.ce.pdn.edu.peraride.data.model.StationData;

/**
 * Created by user on 6/10/2018.
 */

public class StationDataRequestHelperImpl implements StationDataRequestHelper {
    @Override
    public void getStationData(StationData stationData, APIHelper.PostManResponseListener context) throws JSONException {
        APIHelper.getInstance().sendJsonObjectRequestWithoutAuth(context, new AncestorStationDataResponseFactory(),
                Request.Method.POST, APIURLHelper.getStationDataURL(), JsonService.toJsonNode(stationData));
    }
}
