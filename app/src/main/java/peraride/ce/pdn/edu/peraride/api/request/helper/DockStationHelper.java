package peraride.ce.pdn.edu.peraride.api.request.helper;

import org.json.JSONException;

import peraride.ce.pdn.edu.peraride.api.APIHelper;
import peraride.ce.pdn.edu.peraride.data.model.DockStation;

/**
 * Created by user on 5/29/2018.
 */

public interface DockStationHelper {
    void getStations(DockStation dockStation, APIHelper.PostManResponseListener context) throws JSONException;
}
