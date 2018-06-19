package peraride.ce.pdn.edu.peraride.api.request.helper.impl;

import com.android.volley.Request;

import org.json.JSONException;

import peraride.ce.pdn.edu.peraride.api.APIHelper;
import peraride.ce.pdn.edu.peraride.api.APIURLHelper;
import peraride.ce.pdn.edu.peraride.api.request.helper.ProfileRequestHelper;
import peraride.ce.pdn.edu.peraride.api.response.factory.impl.AncestorProfileResponseFactory;
import peraride.ce.pdn.edu.peraride.api.util.JsonService;
import peraride.ce.pdn.edu.peraride.data.model.Profile;

/**
 * Created by user on 5/27/2018.
 */

public class ProfileRequestHelperImpl implements ProfileRequestHelper {
    @Override
    public void getProfile(Profile profile, APIHelper.PostManResponseListener context) throws JSONException {
        APIHelper.getInstance().sendJsonObjectRequestWithoutAuth(context, new AncestorProfileResponseFactory(),
                Request.Method.POST, APIURLHelper.getProfileUrl(), JsonService.toJsonNode(profile));
    }
}
