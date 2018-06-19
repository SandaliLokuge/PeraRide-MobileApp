package peraride.ce.pdn.edu.peraride.api.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import peraride.ce.pdn.edu.peraride.api.VolleySingleton;
import peraride.ce.pdn.edu.peraride.api.response.Ancestor;

import org.json.JSONObject;

import java.util.Map;

/**
 * JSON Object request, deeply integrated with JsonLoader class.
 *
 * @see <a href="http://tiku.io/questions/4747168/android-volley-does-not-work-offline-with-cache">Offline Cache</a>
 */

/* package */ class WebApiRequest<T extends Ancestor> extends JsonObjectRequest {
  /* [ MEMBERS ] =================================================================================================== */

    /**
     * Reference on owner of this request.
     */
    private final JsonLoader<T> mOwner;
    /**
     * Reference on data processing routine.
     */
    private Converter mConverter;

	/* [ CONSTRUCTORS ] ============================================================================================== */

    public WebApiRequest(final int method, final String url, final JsonLoader<T> owner) {
        super(method, url, null, owner /*Listener<JSONObject>*/, owner /*ErrorListener*/);
        mOwner = owner;

        // Say Volley that JSON response should be cached
        setShouldCache(true);

        // register data parsing
        setDataConverter(owner /*DataProcessingListener*/);
    }

	/* [ GETTER / SETTER METHODS ] =================================================================================== */

    /**
     * Gets loader with easy type definition over generics.
     *
     * @return the loader instance.
     */

    public JsonLoader<T> getLoader() {
        return mOwner;
    }

    /**
     * Sets data processing converter.
     *
     * @param converter the converter instance
     * @return this instance
     */

    private WebApiRequest setDataConverter(final Converter converter) {
        mConverter = converter;

        return this;
    }

	/* [ IMPLEMENTATION & HELPERS ] ================================================================================== */

    /**
     * {@inheritDoc}
     */
    @Override
    protected Response<JSONObject> parseNetworkResponse(final NetworkResponse response) {
        // NOTE: excellent place for adding Cache header modifiers, Allows to override
        // server cache policy in easy way.

        final Response<JSONObject> result = super.parseNetworkResponse(response);

        // if we have something for parsing, then forward it to 'callback'
        if (null != mConverter && result.isSuccess()) {
            mConverter.onConvertJson(result.result);
        }

        return result;
    }

    /**
     * Returns a list of extra HTTP headers to go along with this request. Can
     * throw {@link AuthFailureError} as authentication may be required to
     * provide these values.
     *
     * @throws AuthFailureError In the event of auth failure
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return VolleySingleton.getInstance().getAPIHeaderJson();
    }

  /* [ NESTED DECLARATIONS ] ======================================================================================= */

    /**
     * Callback interface that give a chance to convert JSON to Business Object and store it.
     */
    public interface Converter {
        /**
         * Parse server side response.
         */
        void onConvertJson(final JSONObject response);
    }
}
