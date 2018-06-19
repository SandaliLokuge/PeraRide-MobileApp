package peraride.ce.pdn.edu.peraride.api.request.helper;

import org.json.JSONException;

import peraride.ce.pdn.edu.peraride.api.APIHelper;
import peraride.ce.pdn.edu.peraride.data.model.Profile;

/**
 * Created by user on 5/27/2018.
 */

public interface ProfileRequestHelper {
    void getProfile(Profile profile, APIHelper.PostManResponseListener context) throws JSONException;
}
