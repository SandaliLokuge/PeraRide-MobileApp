package peraride.ce.pdn.edu.peraride.api.request.helper.impl;

import com.android.volley.Request;

import org.json.JSONException;

import peraride.ce.pdn.edu.peraride.api.APIHelper;
import peraride.ce.pdn.edu.peraride.api.APIURLHelper;
import peraride.ce.pdn.edu.peraride.api.request.helper.ChangePasswordHelper;
import peraride.ce.pdn.edu.peraride.api.response.factory.impl.AncestorChangePasswordResponseFactory;
import peraride.ce.pdn.edu.peraride.api.response.factory.impl.AncestorLoginResponseFactory;
import peraride.ce.pdn.edu.peraride.api.util.JsonService;
import peraride.ce.pdn.edu.peraride.data.model.ChangePassword;
import peraride.ce.pdn.edu.peraride.data.model.Login;

/**
 * Created by user on 5/28/2018.
 */

public class ChangePasswordRequestHelperImpl implements ChangePasswordHelper{
    @Override
    public void passwordChange(ChangePassword changePassword, APIHelper.PostManResponseListener context) throws JSONException {
        APIHelper.getInstance().sendJsonObjectRequestWithoutAuth(context, new AncestorChangePasswordResponseFactory(),
                Request.Method.POST, APIURLHelper.getChgPasswordURL(), JsonService.toJsonNode(changePassword));
    }
}
