package peraride.ce.pdn.edu.peraride.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import peraride.ce.pdn.edu.peraride.R;
import peraride.ce.pdn.edu.peraride.api.APIHelper;
import peraride.ce.pdn.edu.peraride.api.request.helper.impl.LoginRequestHelperImpl;
import peraride.ce.pdn.edu.peraride.api.response.Ancestor;
import peraride.ce.pdn.edu.peraride.api.response.Error;
import peraride.ce.pdn.edu.peraride.api.response.factory.LoginResponse;
import peraride.ce.pdn.edu.peraride.data.model.Login;
import peraride.ce.pdn.edu.peraride.data.model.response.LoginSerializer;
import peraride.ce.pdn.edu.peraride.util.SessionManager;

import org.json.JSONException;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText mUserName;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button mEmailSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        setListeners();
        ssl();

        startActivity(new Intent(LoginActivity.this, MapActivity.class));
    }

    private void ssl() {
        try {
            TrustManager[] victimizedManager = new TrustManager[]{

                    new X509TrustManager() {

                        public X509Certificate[] getAcceptedIssuers() {

                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];

                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, victimizedManager, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeViews() {
        // Set up the login form.
        mUserName = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);
        mEmailSignInButton = findViewById(R.id.login_button);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

    }

    private void setListeners() {
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateLogin()) {
                    attemptLogin();
                }
            }
        });
    }

    private boolean validateLogin() {
        boolean isValid = true;
        if (TextUtils.isEmpty(mUserName.getText().toString())) {
            isValid = false;
            mUserName.setError(getString(R.string.error_field_required));
        }
        if (TextUtils.isEmpty(mPasswordView.getText().toString())) {
            isValid = false;
            mPasswordView.setError(getString(R.string.error_field_required));
        }
        return isValid;
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        showProgress(true);
        try {
            new LoginRequestHelperImpl().loginUser(
                    new Login(mUserName.getText().toString(), mPasswordView.getText().toString()),
                    new APIHelper.PostManResponseListener() {
                        @Override
                        public void onResponse(Ancestor ancestor) {
                            showProgress(false);
                            if (ancestor instanceof LoginResponse) {
                                LoginSerializer data = ((LoginResponse) ancestor).getData();
                                processResponseData(data);
                            }
                        }

                        @Override
                        public void onError(Error error) {
                            showProgress(false);
                            if (error != null && !TextUtils.isEmpty(error.getData())) {
                                Toast.makeText(LoginActivity.this, error.getData(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
            showProgress(false);
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * save token in pref if login success
     * show error message if not
     *
     * @param data response data
     */
    private void processResponseData(LoginSerializer data) {
        if (data.getRes()) {
            SessionManager.getInstance().createLoginSession(data.getToken(), mUserName.getText().toString());
            startActivity(new Intent(LoginActivity.this, MapActivity.class));
            finish();
        } else {
            if (TextUtils.isEmpty(data.getResponse())) {
                Toast.makeText(LoginActivity.this, R.string.error_invalid_credential,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, data.getResponse(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

}

