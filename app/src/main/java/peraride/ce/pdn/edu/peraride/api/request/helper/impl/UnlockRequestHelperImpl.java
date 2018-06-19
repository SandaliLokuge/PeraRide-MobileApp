package peraride.ce.pdn.edu.peraride.api.request.helper.impl;

import com.android.volley.Request;
import peraride.ce.pdn.edu.peraride.api.APIHelper;
import peraride.ce.pdn.edu.peraride.api.APIURLHelper;
import peraride.ce.pdn.edu.peraride.api.request.helper.UnlockRequestHelper;
import peraride.ce.pdn.edu.peraride.api.response.factory.impl.AncestorUnlockResponseFactory;
import peraride.ce.pdn.edu.peraride.api.util.JsonService;
import peraride.ce.pdn.edu.peraride.data.model.Unlock;

import org.json.JSONException;


public class UnlockRequestHelperImpl implements UnlockRequestHelper {
    @Override
    public void unlockBike(Unlock unlock, APIHelper.PostManResponseListener context) throws JSONException {
        APIHelper.getInstance().sendJsonObjectRequestWithoutAuth(context, new AncestorUnlockResponseFactory(),
                Request.Method.POST, APIURLHelper.getUnlockUrl(), JsonService.toJsonNode(unlock));
    }
}
