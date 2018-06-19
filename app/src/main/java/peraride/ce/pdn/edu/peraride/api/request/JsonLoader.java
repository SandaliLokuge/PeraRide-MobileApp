package peraride.ce.pdn.edu.peraride.api.request;

import android.os.Handler;
import android.os.Message;
import android.support.v4.content.Loader;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import peraride.ce.pdn.edu.peraride.RideApplication;
import peraride.ce.pdn.edu.peraride.api.VolleySingleton;
import peraride.ce.pdn.edu.peraride.api.response.Ancestor;
import peraride.ce.pdn.edu.peraride.api.response.factory.AncestorsFactory;
import peraride.ce.pdn.edu.peraride.api.util.VolleyUtils;

import org.json.JSONObject;

/**
 * Basic loader that converts server output to Ancestor business object.
 *
 * @param <T> the type of result Business Object, POJO class.
 */
public class  JsonLoader<T extends Ancestor>
        extends Loader<Ancestor>
        implements Response.ErrorListener,
        Response.Listener<JSONObject>,
        Handler.Callback,
        WebApiRequest.Converter {
  /* [ CONSTANTS ] ================================================================================================= */

    /** Our own class Logger instance. */
//  private final static Logger _log = LogEx.getLogger(JsonLoader.class);

    /**
     * Message ID used for response results delivery to UI thread.
     */
    private static final int MSG_QUERY_DONE = -1;
    /**
     * indicate normal state of the response.
     */
    private static final int STATE_OK = 0;
    /**
     * indicate error state of the response.
     */
    private static final int STATE_ERROR = -1;

	/* [ MEMBERS ] =================================================================================================== */
    /**
     * Main thread attached handler. Used for delivering server response to the UI thread.
     */
    private final Handler mHandler = new Handler(this);
    /**
     * Reference on Business Objects Layer Factory, used for parsing/converting JSON to BO.
     */
    private final AncestorsFactory mParserFactory;
    /**
     * Request to the server side.
     */
    private JsonRequest<JSONObject> mRequest;
    /**
     * Reference on parsed results.
     */
    private Ancestor mParsedResults;

  /* [ CONSTRUCTORS ] ============================================================================================== */

    public JsonLoader(final AncestorsFactory factory) {
        super(RideApplication.getInstance());

//    ValidUtils.isNull(factory, "Instance of BOL Factory required.");
        mParserFactory = factory;
    }

  /* [ GETTER / SETTER METHODS ] =================================================================================== */

    public Builder requestBuilder() {
        return new Builder();
    }

    /* package */ T getLastResult() {
        return (T) mParsedResults;
    }

    /* package */ JsonRequest<JSONObject> getRequest() {
        return mRequest;
    }

    /* package */
    private void setRequest(final JsonRequest<JSONObject> request) {
//    ValidUtils.isNull(request, "Expected instance of the JsonRequest class.");

        mRequest = request;
    }

  /* [ Interface Handler.Callback ] ================================================================================ */

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleMessage(final Message msg) {
        // WARNING: deliver Result should be always called from UI thread
        if (MSG_QUERY_DONE == msg.what) {
            deliverResult((Ancestor) msg.obj);
            return true;
        }

        return false;
    }

  /* [ Interface Response.ErrorListener ] ========================================================================== */

    /**
     * {@inheritDoc}
     */
    @Override
    public void onErrorResponse(final VolleyError error) {
        // dump error message to Logs
//    _log.severe(LogEx.dump(error));

        // this is often mean a network type error.
        final Message message = mHandler.obtainMessage(MSG_QUERY_DONE, STATE_ERROR, STATE_ERROR, VolleySingleton.getInstance().getErrorMessage(error));

        // request execution in UI thread
        mHandler.sendMessage(message);

        if (error instanceof NoConnectionError || error instanceof TimeoutError) {
//      _log.severe("No Internet Connection? loader: " + hashCode());

            // notify application about connectivity issues
//      InternetUtils.forceCheck(TheApp.context()); +++++++++++++++++++++++++++++++++++++++++++
            // TODO: 1/4/17 Show Toast
        } else if (error.networkResponse != null && error.networkResponse.statusCode == 401)
            RideApplication.getInstance().restartApplication();
    }

  /* [ Interface WebApiRequest.Converter ] ========================================================================= */

    /**
     * {@inheritDoc}
     */
    @Override
    public void onConvertJson(final JSONObject response) {
//    ValidUtils.isUiThread("Data Processing should stay in background thread.");
//    _log.info("onResponse processing for url: " + mRequest.getOriginUrl());

        Ancestor parsed;

        try {
            parsed = parseJson(response);
        } catch (final Throwable ignored) {
//      _log.severe(LogEx.dump(ignored));

            parsed = new peraride.ce.pdn.edu.peraride.api.response.Error(ignored);
        }

        // parsing error, method should always return instance of Ancestor +++++++++++++++++++++++++++++++++
        mParsedResults = ((null == parsed) ? new peraride.ce.pdn.edu.peraride.api.response.Error("") : parsed);
    }

	/* [ Interface Response.Listener ] =============================================================================== */

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResponse(final JSONObject response) {
        final int state = (mParsedResults instanceof peraride.ce.pdn.edu.peraride.api.response.Error) ? STATE_ERROR : STATE_OK;
        final Message message = mHandler.obtainMessage(MSG_QUERY_DONE, state, state, mParsedResults);

        // request execution in UI thread
        mHandler.sendMessage(message);

//    _log.info("Done - successful response. loader: " + hashCode());
    }

	/* [ IMPLEMENTATION & HELPERS ] ================================================================================== */

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onForceLoad() {
        super.onForceLoad();

//    ValidUtils.isNull(mRequest, "Expected instance of the JsonRequest class.");

        // recover web request state from CANCELED via reflection (better to modify library of course)
        try {
            VolleyUtils.resetCancelState(mRequest);
        } catch (final Throwable ignored) {
//      _log.severe(LogEx.dump(ignored));
        }

//    _log.info("[loader] scheduled web request: " + mRequest.getUrl());

        // add request into execution queue
        //TheApp.forUI().add(mRequest);
        VolleySingleton.getInstance().addToRequestQueue(mRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();

        // do cleanup
        mParsedResults = null;
    }

    /* {@inheritDoc} */
    @Override
    protected void onAbandon() {
        // in case of started loader, we have to request stop of all background things
        if (isStarted()) {
            stopLoading();
        }

        super.onAbandon();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        // deliver result if we already have it
        if (null != mParsedResults) {
            deliverResult(mParsedResults);
        }

        // If the data has changed since the last time it was loaded
        // or is not currently available, start a load.
        if (takeContentChanged() || null == mParsedResults) {
            forceLoad();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onStopLoading() {
        super.onStopLoading();

        // WARNING: cancel state of the Request cannot be reset. we should override implementation for making
        // possible state recovery. Cancel execution of the request.
        mRequest.cancel();
    }

    /**
     * Do background parsing of the server response.
     */
    private peraride.ce.pdn.edu.peraride.api.response.Ancestor parseJson(final JSONObject response) throws Exception {
//    ValidUtils.isUiThread("Json Parsing should stay in background thread.");

        return mParserFactory.parse(response);
    }

  /* [ NESTED DECLARATIONS ] ======================================================================================= */

    /**
     * Simple builder, that hides Web request creation.
     */
    public final class Builder {
        private String mUrl;
        private int mHttpMethod;
        private RetryPolicy mRetryPolicy;
        private String mTag;
        private boolean mShouldCache;

        public Builder setUrl(final String url) {
            mUrl = url;
            return this;
        }

        public Builder setHttpMethod(final int type) {
            mHttpMethod = type;
            return this;
        }

        public Builder setRetryPolicy(final RetryPolicy policy) {
            mRetryPolicy = policy;
            return this;
        }

        public Builder setTag(final String tag) {
            mTag = tag;
            return this;
        }

        public Builder setShouldCache(final boolean cache) {
            mShouldCache = cache;
            return this;
        }

        /**
         * Compose Web API request, configure it and assign to current loader.
         */
        public void build() {
            final WebApiRequest<T> request = new WebApiRequest<>(mHttpMethod, mUrl, JsonLoader.this);

            if (null != mRetryPolicy) {
                request.setRetryPolicy(mRetryPolicy);
            }

            if (null != mTag) {
                request.setTag(mTag);
            }

            request.setShouldCache(mShouldCache);

            // configure owner by newly created request
            setRequest(request);
        }
    }
}
