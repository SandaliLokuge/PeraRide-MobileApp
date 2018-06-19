package peraride.ce.pdn.edu.peraride.api.request.helper;


import peraride.ce.pdn.edu.peraride.api.APIHelper;
import peraride.ce.pdn.edu.peraride.data.model.Login;

import org.json.JSONException;

public interface LoginRequestHelper {

    void loginUser(Login login, APIHelper.PostManResponseListener context) throws JSONException;

}
