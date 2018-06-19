package peraride.ce.pdn.edu.peraride.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import peraride.ce.pdn.edu.peraride.R;
import peraride.ce.pdn.edu.peraride.api.APIHelper;
import peraride.ce.pdn.edu.peraride.api.request.helper.impl.ProfileRequestHelperImpl;
import peraride.ce.pdn.edu.peraride.api.response.Ancestor;
import peraride.ce.pdn.edu.peraride.api.response.Error;
import peraride.ce.pdn.edu.peraride.api.response.factory.ProfileResponse;
import peraride.ce.pdn.edu.peraride.data.model.Profile;
import peraride.ce.pdn.edu.peraride.data.model.response.ProfileSerializer;
import peraride.ce.pdn.edu.peraride.util.SessionManager;

public class ProfileActivity extends AppCompatActivity {

    private TextView mName;
    private TextView mRegNo;
    private TextView mPhoneNo;
    private TextView mEmail;
    private TextView mNIC;
    private Button mChangePass;
    private TextView mNameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initializeViews();
        setListeners();
        loadProfileDetails();

        ssl();
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


    private void loadProfileDetails() {
        try {
            new ProfileRequestHelperImpl().getProfile(
                new Profile(SessionManager.getInstance().getJWTToken()),
                new APIHelper.PostManResponseListener() {
                    @Override
                    public void onResponse(Ancestor ancestor) {
                        if (ancestor instanceof ProfileResponse) {
                            ProfileSerializer data = ((ProfileResponse) ancestor).getData();
                            getProfile(data);
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        if (error != null && !TextUtils.isEmpty(error.getData())) {
                            Toast.makeText(ProfileActivity.this, error.getData(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getProfile(ProfileSerializer data){
        mName.setText(data.getRider_firstName()+ " "+data.getRider_lastName());
        mRegNo.setText(data.getRider_regNo());
        mPhoneNo.setText(data.getRider_phone());
        mEmail.setText(data.getRider_email());
        mNIC.setText(data.getNic());
        mNameView.setText(data.getRider_firstName()+" "+data.getRider_lastName());

    }

    private void setListeners() {
        mChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });


    }

    public void openDialog() {
        ChangePassDialog changePassDialog=new ChangePassDialog(this);
        changePassDialog.show(getSupportFragmentManager(),"change password dialog");
    }

    private void initializeViews() {
        mName = (TextView)findViewById(R.id.fullnameView);
        mRegNo = (TextView)findViewById(R.id.regnoView);
        mPhoneNo = (TextView)findViewById(R.id.phonenumberView);
        mEmail = (TextView)findViewById(R.id.emailView);
        mNIC=(TextView)findViewById(R.id.nicView);
        mChangePass=(Button)findViewById(R.id.pass_button);
        mNameView=(TextView)findViewById(R.id.nameView);


    }
}
