package peraride.ce.pdn.edu.peraride.api.request.helper;


import peraride.ce.pdn.edu.peraride.api.APIHelper;
import peraride.ce.pdn.edu.peraride.data.model.Unlock;

import org.json.JSONException;


public interface UnlockRequestHelper {

    void unlockBike(Unlock unlock, APIHelper.PostManResponseListener context) throws JSONException;

}
