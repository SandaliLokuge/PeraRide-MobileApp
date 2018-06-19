package peraride.ce.pdn.edu.peraride.util;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import peraride.ce.pdn.edu.peraride.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UtilityManager {


    public static boolean isServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void showSnackBar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    public static boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        return providers != null && providers.contains(LocationManager.GPS_PROVIDER);
    }


    public static void showToast(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    public static void showSnackBarWithAction(String msg, int duration, String actionMsg,
                                              CoordinatorLayout coordinatorLayout, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, msg, duration)
                .setAction(actionMsg, listener);
        snackbar.show();
    }


    public static ProgressDialog showProgress(Context context, String message) {
        ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage(message);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        return progress;
    }

    public static AlertDialog showAlert(final Context context, String msg, String btnPositive,
                                        String btnNegative, boolean cancelable,
                                        DialogInterface.OnClickListener positiveClick,
                                        DialogInterface.OnClickListener negativeClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogStyle);
        builder.setMessage(msg);
        builder.setCancelable(cancelable);
        builder.setPositiveButton(btnPositive, positiveClick);
        builder.setNegativeButton(btnNegative, negativeClick);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }

    public static EditText showAlertWithEditText(final Context context, String title, String msg,
                                                 String btnPositive, String btnNegative, boolean cancelable,
                                                 DialogInterface.OnClickListener positiveClick,
                                                 DialogInterface.OnClickListener negativeClick) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);


        LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(params);

        layout.setGravity(Gravity.CLIP_VERTICAL);
        layout.setPadding(2, 2, 2, 2);

        final TextView tvLiveCount = new TextView(context);
        String firstLive = "0/250";
        tvLiveCount.setText(firstLive);
        tvLiveCount.setPadding(5, 0, 16, 0);
        tvLiveCount.setGravity(Gravity.END);
        tvLiveCount.setTextSize(12);

        if (title != null)
            alert.setTitle(title);
        alert.setMessage(msg);
        alert.setCancelable(cancelable);
        final EditText input = new EditText(context);
        input.setMaxLines(5);
        input.setMaxEms(5);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(250)});

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (input.getText() != null)
                    tvLiveCount.setText(String.valueOf(input.getText().length()).concat("/250"));
            }
        });

        LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv1Params.bottomMargin = 5;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(16, 0, 16, 0);
        layout.addView(input, layoutParams);
        layout.addView(tvLiveCount, tv1Params);

        alert.setView(layout);

        alert.setPositiveButton(btnPositive, positiveClick);
        alert.setNegativeButton(btnNegative, negativeClick);
        alert.create().show();
        return input;
    }

    public static String getLocationURL(double latitudeFinal, double longitudeFinal) {
        return "https://maps.googleapis.com/maps/api/staticmap?center=" + latitudeFinal + "," + longitudeFinal + "&zoom=16&size=170x90&markers=color:red|" + latitudeFinal + "," + longitudeFinal;
    }

    public static void goToPlayStore(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(getDefaultSimpleDateFormat());
        return objectMapper;
    }

    public static String formatDate_MMM_D(Date date) {
        return new SimpleDateFormat("MMM d", java.util.Locale.getDefault()).format(date);
    }

    public static String formatDateFull(Date date) {
        return new SimpleDateFormat("d MMM yyyy hh:mm a", java.util.Locale.getDefault()).format(date);
    }

    public static String formatDate_HH_MM(Date date) {
        return new SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(date);
    }

    private static SimpleDateFormat getDefaultSimpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z", java.util.Locale.getDefault());
    }

    public static SimpleDateFormat getDefaultSimpleDateFormatFilter() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
    }

    public static SimpleDateFormat getDefaultSimpleDateFormatWithoutZone() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
    }

    public static SimpleDateFormat getSimpleDateFormatJob() {
        return new SimpleDateFormat("EEE, d MMM yyyy", java.util.Locale.getDefault());
    }

    public static SimpleDateFormat getSimpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
    }

    public static SimpleDateFormat getSimpleDateFormatEvent() {
        return new SimpleDateFormat("EEE, d MMM yyyy HH:mm", java.util.Locale.getDefault());
    }

    public static String getSimpleDateFormatYear(Date date) {
        return new SimpleDateFormat("yyyy", java.util.Locale.getDefault()).format(date);
    }

    public static String getSimpleDateFormatMonth(Date date) {
        return new SimpleDateFormat("MM", java.util.Locale.getDefault()).format(date);
    }

    public static String getSimpleDateFormatDay(Date date) {
        return new SimpleDateFormat("dd", java.util.Locale.getDefault()).format(date);
    }

    public static String formatDateEventDetail(Date date) {
        return new SimpleDateFormat("EEE, d MMM yyyy hh:mm a", java.util.Locale.getDefault()).format(date);
    }

    public static String getSimpleDateFormatToInt(Date date) {
        return new SimpleDateFormat("yyyyMMdd", java.util.Locale.getDefault()).format(date);
    }

    public static String getSimpleDateFormatToMessage(Date date) {
        return new SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(date);
    }

    public static String formatDateAddEvent(Date date) {
        return new SimpleDateFormat("EEE, d MMM yyyy", java.util.Locale.getDefault()).format(date);
    }

    public static String getDateFromLong(Long l) {
        return new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(new Date(l));
    }

}
