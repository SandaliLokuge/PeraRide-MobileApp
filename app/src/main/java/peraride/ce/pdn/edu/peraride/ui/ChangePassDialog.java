package peraride.ce.pdn.edu.peraride.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
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
import peraride.ce.pdn.edu.peraride.api.request.helper.impl.ChangePasswordRequestHelperImpl;
import peraride.ce.pdn.edu.peraride.api.response.Ancestor;
import peraride.ce.pdn.edu.peraride.api.response.Error;
import peraride.ce.pdn.edu.peraride.api.response.factory.ChangePasswordResponse;
import peraride.ce.pdn.edu.peraride.data.model.ChangePassword;
import peraride.ce.pdn.edu.peraride.data.model.response.ChangePasswordSerializer;
import peraride.ce.pdn.edu.peraride.util.SessionManager;

/**
 * Created by user on 5/28/2018.
 */

@SuppressLint("ValidFragment")
public class ChangePassDialog extends AppCompatDialogFragment {
    private EditText mOldPass;
    private EditText mNewPass;
    private EditText mRePass;
    private TextView mPassProb;
    private AlertDialog dialog;
    private ProfileActivity profileActivity;
    private boolean isMatch=false;

    @SuppressLint("ValidFragment")
    public ChangePassDialog(ProfileActivity profileActivity) {
        this.profileActivity=profileActivity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        ssl();
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.change_pass,null);


        builder.setView(view)
                .setTitle("Change Password")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changePassword();

                    }
                });



        mOldPass = (EditText) view.findViewById(R.id.oldpwd);
        mNewPass = (EditText) view.findViewById(R.id.newpwd);
        mRePass = (EditText) view.findViewById(R.id.repwd);
        mPassProb = (TextView) view.findViewById(R.id.TextView_PwdProblem);

        mRePass.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String strPass1 = mRePass.getText().toString();
                String strPass2 = mNewPass.getText().toString();
                if (strPass1.equals(strPass2)) {
                    mPassProb.setText("Password Match");
                    isMatch=true;
                } else {
                    mPassProb.setText("Miss-match");
                    isMatch=false;
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        return dialog=builder.create();



    }

    private void changePassword() {
        if(isMatch){
            try {
                new ChangePasswordRequestHelperImpl().passwordChange(
                        new ChangePassword(SessionManager.getInstance().getJWTToken(),mOldPass.getText().toString(), mNewPass.getText().toString()),
                        new APIHelper.PostManResponseListener() {
                            @Override
                            public void onResponse(Ancestor ancestor) {
                                if (ancestor instanceof ChangePasswordResponse) {
                                    ChangePasswordSerializer data = ((ChangePasswordResponse) ancestor).getData();
                                    confirmChange(data);
                                }
                            }

                            @Override
                            public void onError(Error error) {
                                if (error != null && !TextUtils.isEmpty(error.getData())) {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                    alertDialogBuilder.setTitle("PeraRide");
                                    alertDialogBuilder.setMessage("Something went wrong!");
                                    alertDialogBuilder.setPositiveButton("Ok",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {

                                                }
                                            });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                }
                            }
                        });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this.getActivity(),"Passwords should be matched",
                    Toast.LENGTH_SHORT).show();
        }


    }

    private void confirmChange(ChangePasswordSerializer data) {

        if(data.isRes()){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(profileActivity);
            alertDialogBuilder.setTitle("PeraRide");
            alertDialogBuilder.setMessage("Password changed successfully");
            alertDialogBuilder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }else{
            if(data.getResponse().equals("Something Wrong")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(profileActivity);
                alertDialogBuilder.setTitle("PeraRide");
                alertDialogBuilder.setMessage("Something went wrong! \nTry again.");
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                profileActivity.openDialog();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(profileActivity);
                alertDialogBuilder.setTitle("PeraRide");
                alertDialogBuilder.setMessage("Invalid Old Password. \nTry again !");
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                profileActivity.openDialog();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }
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
}
