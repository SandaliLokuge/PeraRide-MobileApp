package peraride.ce.pdn.edu.peraride.api.request.helper;

import org.json.JSONException;

import peraride.ce.pdn.edu.peraride.api.APIHelper;
import peraride.ce.pdn.edu.peraride.data.model.Profile;
import peraride.ce.pdn.edu.peraride.data.model.StationData;

/**
 * Created by user on 6/10/2018.
 */

public interface StationDataRequestHelper {
    void getStationData(StationData stationData, APIHelper.PostManResponseListener context) throws JSONException;
}
