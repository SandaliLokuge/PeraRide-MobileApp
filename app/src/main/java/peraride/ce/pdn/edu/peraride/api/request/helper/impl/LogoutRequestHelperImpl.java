package peraride.ce.pdn.edu.peraride.api.request.helper.impl;

import com.android.volley.Request;

import org.json.JSONException;

import peraride.ce.pdn.edu.peraride.api.APIHelper;
import peraride.ce.pdn.edu.peraride.api.APIURLHelper;
import peraride.ce.pdn.edu.peraride.api.request.helper.LogoutRequestHelper;
import peraride.ce.pdn.edu.peraride.api.response.factory.impl.AncestorLogoutResponseFactory;
import peraride.ce.pdn.edu.peraride.api.util.JsonService;
import peraride.ce.pdn.edu.peraride.data.model.Logout;

/**
 * Created by user on 5/28/2018.
 */

public class LogoutRequestHelperImpl implements LogoutRequestHelper {
    @Override
    public void logoutUser(Logout logout, APIHelper.PostManResponseListener context) throws JSONException {
        APIHelper.getInstance().sendJsonObjectRequestWithoutAuth(context, new AncestorLogoutResponseFactory(),
                Request.Method.POST, APIURLHelper.getLogoutURL(), JsonService.toJsonNode(logout));
    }
}
