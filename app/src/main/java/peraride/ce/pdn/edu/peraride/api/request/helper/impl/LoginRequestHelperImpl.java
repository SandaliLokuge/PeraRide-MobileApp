package peraride.ce.pdn.edu.peraride.api.request.helper.impl;

import com.android.volley.Request;
import peraride.ce.pdn.edu.peraride.api.APIHelper;
import peraride.ce.pdn.edu.peraride.api.APIURLHelper;
import peraride.ce.pdn.edu.peraride.api.request.helper.LoginRequestHelper;
import peraride.ce.pdn.edu.peraride.api.response.factory.impl.AncestorLoginResponseFactory;
import peraride.ce.pdn.edu.peraride.api.util.JsonService;
import peraride.ce.pdn.edu.peraride.data.model.Login;

import org.json.JSONException;


public class LoginRequestHelperImpl implements LoginRequestHelper {
    @Override
    public void loginUser(Login login, APIHelper.PostManResponseListener context) throws JSONException {
        APIHelper.getInstance().sendJsonObjectRequestWithoutAuth(context, new AncestorLoginResponseFactory(),
                Request.Method.POST, APIURLHelper.getLoginUrl(), JsonService.toJsonNode(login));
    }

}
