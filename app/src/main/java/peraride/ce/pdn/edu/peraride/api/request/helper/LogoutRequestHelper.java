package peraride.ce.pdn.edu.peraride.api.request.helper;

import org.json.JSONException;

import peraride.ce.pdn.edu.peraride.api.APIHelper;
import peraride.ce.pdn.edu.peraride.data.model.Login;
import peraride.ce.pdn.edu.peraride.data.model.Logout;

/**
 * Created by user on 5/28/2018.
 */

public interface LogoutRequestHelper {
    void logoutUser(Logout logout, APIHelper.PostManResponseListener context) throws JSONException;
}
