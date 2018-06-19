package peraride.ce.pdn.edu.peraride.api.request.helper;

import org.json.JSONException;

import peraride.ce.pdn.edu.peraride.api.APIHelper;
import peraride.ce.pdn.edu.peraride.data.model.ChangePassword;

/**
 * Created by user on 5/28/2018.
 */

public interface ChangePasswordHelper {
    void passwordChange(ChangePassword changePassword, APIHelper.PostManResponseListener context) throws JSONException;
}
