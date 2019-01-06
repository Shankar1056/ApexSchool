package softgalli.gurukulshikshalay.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import softgalli.gurukulshikshalay.BuildConfig;
import softgalli.gurukulshikshalay.R;
import softgalli.gurukulshikshalay.activity.ApplyLeaveActivity;
import softgalli.gurukulshikshalay.activity.LoginScreenActivity;
import softgalli.gurukulshikshalay.activity.SeeAttendenceActivity;
import softgalli.gurukulshikshalay.activity.SeeLeaveListActivity;
import softgalli.gurukulshikshalay.activity.TakeAttendenceActivity;
import softgalli.gurukulshikshalay.activity.UpdateAttendanceActivity;
import softgalli.gurukulshikshalay.activity.UpdateNoticeAndNewsActivity;
import softgalli.gurukulshikshalay.intrface.DialogClickListener;
import softgalli.gurukulshikshalay.model.CommonResponse;
import softgalli.gurukulshikshalay.model.UpcomingActivityModel;
import softgalli.gurukulshikshalay.preference.MyPreference;
import softgalli.gurukulshikshalay.retrofit.ApiUrl;
import softgalli.gurukulshikshalay.retrofit.DownlodableCallback;
import softgalli.gurukulshikshalay.retrofit.RetrofitDataProvider;


public class Utilz {
    public static boolean isSmsApiCalling = false;
    static ProgressDialog dialog;
    private static String TAG = Utilz.class.getSimpleName();
    private static boolean IS_DEBUG_ENABLE = BuildConfig.DEBUG;
    private static Calendar c;
    private static List<String> output;

    public static void showLog(String TAG, String message) {
        if (IS_DEBUG_ENABLE)
            Log.i(TAG, message);
    }

    public static String getRandomName() {

        final String AB = "abcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 8; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();

    }

    public static boolean isInternetConnected(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static boolean isValidEmail1(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    public static int getDateFromString(String dateStr) {
        int date = 0;
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parsedDate = DATE_FORMAT.parse(dateStr);
            date = parsedDate.getDate();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void showDailog(Context mActivity, String msg) {
        try {
            if (mActivity != null) {
                dialog = new ProgressDialog(mActivity);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setMessage(msg);
                if (dialog != null && !dialog.isShowing())
                    dialog.show();
            }
        } catch (Exception e) {
            Utilz.showLog(TAG, "Error : " + e);
        }
    }

    public static void closeDialog() {
        try {
            if (dialog != null && dialog.isShowing())
                dialog.cancel();
        } catch (Exception e) {
            Utilz.showLog(TAG, "Error : " + e);
        }
    }

    public static String getCurrentDate(Context askQuestion) {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String getCurrentTime(Context askQuestion) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        String currentDateTimeString = sdf.format(d);
        return currentDateTimeString;
    }

    public static String getCurrentDateInDigit(Context timeTableActivity) {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String todaysday(Context timeTableActivity) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        return dayOfTheWeek;
    }

    public static List<String> getCalendar() {
        c = Calendar.getInstance();
        output = new ArrayList<String>();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());

        //Get current Day of week and Apply suitable offset to bring the new calendar
        //back to the appropriate Monday, i.e. this week or next
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                c.add(Calendar.DATE, -1);
                break;

            case Calendar.TUESDAY:
                c.add(Calendar.DATE, -2);
                break;

            case Calendar.WEDNESDAY:
                c.add(Calendar.DATE, -3);
                break;

            case Calendar.THURSDAY:
                c.add(Calendar.DATE, -4);
                break;

            case Calendar.FRIDAY:
                c.add(Calendar.DATE, -5);
                break;

            case Calendar.SATURDAY:
                c.add(Calendar.DATE, -6);
                break;
        }

        //Add the Monday to the output
        // output.add(c.getTime().toString());
        for (int x = 1; x <= 6; x++) {
            //Add the remaining days to the output
            c.add(Calendar.DATE, 1);
            output.add(df.format(c.getTime()));
        }
        return output;
    }

    public static void setTextType(int textStyle, TextView... textViews) {
        for (TextView tv : textViews) {
            tv.setTypeface(tv.getTypeface(), textStyle);
        }
    }

    public static void setTextSize(int textSize, TextView... textViews) {
        for (TextView tv : textViews) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        }
    }

    /**
     * Check device have internet connection or not
     *
     * @param activity
     * @return
     */
    public static boolean isOnline(Context activity) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = null;
        if (activity != null)
            cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            //In some rare cases netInfo array comes as null so putting null check before using array.
            if (netInfo != null && netInfo.length > 0) {
                for (NetworkInfo ni : netInfo) {
                    if (ni.getTypeName().equalsIgnoreCase("WIFI")) {
                        if (ni.isConnected()) {
                            haveConnectedWifi = true;
                            showLog(TAG, "WIFI CONNECTION : AVAILABLE");
                        } else {
                            showLog(TAG, "WIFI CONNECTION : NOT AVAILABLE");
                        }
                    }
                    if (ni.getTypeName().equalsIgnoreCase("MOBILE")) {
                        if (ni.isConnected()) {
                            haveConnectedMobile = true;
                            showLog(TAG,
                                    "MOBILE INTERNET CONNECTION : AVAILABLE");
                        } else {
                            showLog(TAG,
                                    "MOBILE INTERNET CONNECTION : NOT AVAILABLE");
                        }
                    }
                }
            }
        }
        if (haveConnectedWifi || haveConnectedMobile)
            return true;
        else
            return false;
    }

    public static void showNoInternetConnectionDialog(final Context mActivity) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle(mActivity.getResources().getString(R.string.no_internet_connection_msg_title));
            builder.setMessage(mActivity.getResources().getString(R.string.no_internet_connection_msg));
            builder.setNegativeButton(AppConstants.OK, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AppCompatDialog alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {
            Utilz.showLog(TAG, "Show Dialog: " + e.getMessage());
        }
    }

    public static void showMessageDialog(final Context mActivity, final String msg) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setMessage(msg);
            builder.setNegativeButton(AppConstants.OK, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AppCompatDialog alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {
            Utilz.showLog(TAG, "Show Dialog: " + e.getMessage());
        }
    }

    public static void showMessageDialogForResponseCodes(final Context mActivity, final String title, final String message, final DialogClickListener clickListener) {
        try {
            final Dialog dialog = new Dialog(mActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.show_title_message_dialog);
            dialog.setTitle(null);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            TextView titleTextView = dialog.findViewById(R.id.titleTextView);
            TextView messageTextView = dialog.findViewById(R.id.messageTextView);
            if (!TextUtils.isEmpty(title))
                titleTextView.setText(title);
            else
                titleTextView.setText(AppConstants.WARNING);
            messageTextView.setText(message);
            TextView textViewCancel = dialog.findViewById(R.id.textViewCancel);
            TextView crossTv = dialog.findViewById(R.id.crossTv);
            textViewCancel.setVisibility(View.INVISIBLE);
            TextView textViewOk = dialog.findViewById(R.id.textViewOk);

            if (textViewOk != null) {
                textViewOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();
                        clickListener.onClickOfPositive();
                    }
                });
            }
            if (crossTv != null) {
                crossTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();
                    }
                });
            }
            if (dialog != null && !dialog.isShowing())
                dialog.show();
        } catch (Exception e) {
            Utilz.showLog(TAG, "Show Dialog: " + e.getMessage());
        }
    }

    public static void showMessageOnDialog(final Activity mActivity, final String title, final String message, final String negBtnTitle, final String posBtnTitle) {
        try {
            final Dialog dialog = new Dialog(mActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.show_title_message_dialog);
            dialog.setTitle(null);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            TextView titleTextView = dialog.findViewById(R.id.titleTextView);
            TextView messageTextView = dialog.findViewById(R.id.messageTextView);
            TextView crossTv = dialog.findViewById(R.id.crossTv);
            crossTv.setVisibility(View.GONE);
            titleTextView.setText(title);
            messageTextView.setText(message);
            TextView textViewCancel = dialog.findViewById(R.id.textViewCancel);
            TextView textViewOk = dialog.findViewById(R.id.textViewOk);
            if (!TextUtils.isEmpty(negBtnTitle))
                textViewCancel.setText(negBtnTitle);
            if (!TextUtils.isEmpty(posBtnTitle))
                textViewOk.setText(posBtnTitle);

            if (!TextUtils.isEmpty(posBtnTitle)) {
                textViewOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();
                        if (mActivity != null && !mActivity.isFinishing())
                            mActivity.finish();
                    }
                });
            }
            if (!TextUtils.isEmpty(negBtnTitle)) {
                textViewCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();
                    }
                });
            }
            if (dialog != null && !dialog.isShowing())
                dialog.show();
        } catch (Exception e) {
            Utilz.showLog(TAG, "Show Dialog: " + e.getMessage());
        }
    }

    public static String getCurrentDate() {
        String urrentDate = "";
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        urrentDate = df.format(c);
        return urrentDate;
    }

    public static String getCurrentDayNameAndDate() {
        String mStrCurrentDate = getCurrentDate();
        String mStrDayName = getDayNameFromDate(getCurrentDate());
        if (!TextUtils.isEmpty(mStrDayName))
            mStrDayName = mStrDayName + ", " + mStrCurrentDate;
        return mStrDayName;
    }

    public static void openDialer(final Context mActivity, final String mobileNo) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + mobileNo));
        mActivity.startActivity(intent);
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            mActivity.startActivity(intent);
            return;
        }
    }

    public static void shareContent(final Context mActivity, final String title, String message) {
        String signature = mActivity.getResources().getString(R.string.app_name);
        String shareBody = message + "\n" + signature;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        mActivity.startActivity(Intent.createChooser(sharingIntent, mActivity.getResources().getString(R.string.share_using)));
    }

    @SuppressLint("NewApi")
    public static void logout(final Context mActivity) {
        try {
            MyPreference.setLogin(false);
            MyPreference.setSignupSkipped(false);
            MyPreference.setLoginedAs("");
            MyPreference.setUserName("");
            ClsGeneral.setPreferences(AppConstants.ID, "");
            ClsGeneral.setPreferences(AppConstants.USER_ID, "");
            ClsGeneral.setPreferences(AppConstants.NAME, "");
            ClsGeneral.setPreferences(AppConstants.EMAIL, "");
            ClsGeneral.setPreferences(AppConstants.CLAS, "");
            ClsGeneral.setPreferences(AppConstants.SEC, "");
            ClsGeneral.setPreferences(AppConstants.JOINING_DATE, "");
            ClsGeneral.setPreferences(AppConstants.RESIDENTIAL_ADDRESS, "");
            ClsGeneral.setPreferences(AppConstants.PERMANENT_ADDRESS, "");
            ClsGeneral.setPreferences(AppConstants.PROFILE_PIC, "");
            ClsGeneral.setPreferences(AppConstants.STATUS, "");
            ClsGeneral.setPreferences(AppConstants.QUALIFICATION, "");
            ClsGeneral.setPreferences(AppConstants.ALTERNTE_NUMBER, "");
            ClsGeneral.setPreferences(AppConstants.PHONE_NO, "");
            ClsGeneral.setPreferences(AppConstants.WHAT_TEACH, "");
            ClsGeneral.setPreferences(AppConstants.SUBJECT, "");
            ClsGeneral.setPreferences(AppConstants.CLASS_TEACHER_FOR, "");
            ClsGeneral.setPreferences(AppConstants.ADDRESS, "");
            ClsGeneral.setPreferences(AppConstants.FACEBOOK_ID, "");
            ClsGeneral.setPreferences(AppConstants.DESIGNATION, "");
            ClsGeneral.setPreferences(AppConstants.IS_LOGINED, false);
            ClsGeneral.setPreferences(AppConstants.FATHER_NAME, "");
            ClsGeneral.setPreferences(AppConstants.LOGIN_AS, "");
            mActivity.startActivity(new Intent(mActivity, LoginScreenActivity.class));
            ((Activity) mActivity).finishAffinity();
        } catch (Exception e) {

        }
    }

    public static void openBrowser(final Activity mActivity, final String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        mActivity.startActivity(i);
    }

    public static void openMail(final Activity mActivity) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mActivity.getResources().getString(R.string.principal_email)});
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        Intent mailer = Intent.createChooser(intent, null);
        mActivity.startActivity(mailer);

    }

    public static void showLoginFirstDialog(final Activity mActivity) {
        try {
            final Dialog dialog = new Dialog(mActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.login_first_dialog);
            dialog.setTitle(null);
            dialog.setCanceledOnTouchOutside(false);

            TextView textViewWelcome = dialog.findViewById(R.id.textViewWelcome);
            textViewWelcome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.startActivity(new Intent(mActivity, LoginScreenActivity.class));
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showLeaveMgmtDialog(final Activity mActivity) {
        try {
            final Dialog dialog = new Dialog(mActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.leave_mgmt_dialog);
            dialog.setTitle(null);
            dialog.setCanceledOnTouchOutside(false);

            RelativeLayout takeLeavesRl = dialog.findViewById(R.id.takeLeavesRl);
            RelativeLayout seeLeavesRl = dialog.findViewById(R.id.seeLeavesRl);
            takeLeavesRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.startActivity(new Intent(mActivity, ApplyLeaveActivity.class));
                    dialog.dismiss();
                }
            });
            seeLeavesRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.startActivity(new Intent(mActivity, SeeLeaveListActivity.class));
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getClassList() {
        List<String> classList;
        classList = new ArrayList<>();
        classList.add("Select Class");
        classList.add("10");
        classList.add("9");
        classList.add("8");
        classList.add("7");
        classList.add("6");
        classList.add("5");
        classList.add("4");
        classList.add("3");
        classList.add("2");
        classList.add("1");
        classList.add("LKG");
        classList.add("UKG");
        classList.add("NURSERY");
        return classList;
    }

    public static List<String> getClassNameList() {
        List<String> classNameList;
        classNameList = new ArrayList<>();
        classNameList.add("Class - 10");
        classNameList.add("Class - 9");
        classNameList.add("Class - 8");
        classNameList.add("Class - 7");
        classNameList.add("Class - 6");
        classNameList.add("Class - 5");
        classNameList.add("Class - 4");
        classNameList.add("Class - 3");
        classNameList.add("Class - 2");
        classNameList.add("Class - 1");
        classNameList.add("Class - LKG");
        classNameList.add("Class - UKG");
        classNameList.add("Class - NURSERY");
        return classNameList;
    }

    public static List<String> getSectionList() {
        List<String> sectionList = new ArrayList<>();
        sectionList.add("Select Section");
        sectionList.add("A");
        sectionList.add("B");
        sectionList.add("C");
        return sectionList;
    }

    public static List<String> getSessionList() {
        List<String> sectionList = new ArrayList<>();
        sectionList.add("Select Session");
        sectionList.add("2018 - 2019");
        return sectionList;
    }

    public static void showAttendanceMgmtDialog(final Activity mActivity) {
        try {
            final Dialog dialog = new Dialog(mActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.attendance_mgmt_dialog);
            dialog.setTitle(null);
            dialog.setCanceledOnTouchOutside(false);
            List<String> classList = new ArrayList<>(), sectionList = new ArrayList<>();

            classList.addAll(getClassList());

            sectionList.addAll(getSectionList());

            final Spinner classNameSpinner, sectionNameSpinner;
            classNameSpinner = dialog.findViewById(R.id.classNameSpinner);
            sectionNameSpinner = dialog.findViewById(R.id.sectionNameSpinner);

            RelativeLayout takeAttendanceRl = dialog.findViewById(R.id.takeLeavesRl);
            RelativeLayout seeAttendanceRl = dialog.findViewById(R.id.seeLeavesRl);
            RelativeLayout updateAttendanceRl = dialog.findViewById(R.id.updateAttendanceRl);

            ArrayAdapter<String> classAdapter = new ArrayAdapter<>(mActivity,
                    android.R.layout.simple_dropdown_item_1line, classList);
            ArrayAdapter<String> sectionAdapter = new ArrayAdapter<>(mActivity,
                    android.R.layout.simple_dropdown_item_1line, sectionList);
            classAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            classNameSpinner.setAdapter(classAdapter);
            sectionNameSpinner.setAdapter(sectionAdapter);

            takeAttendanceRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isValidClassAndSection(mActivity, classNameSpinner, sectionNameSpinner)) {
                        Intent mIntent = new Intent(mActivity, TakeAttendenceActivity.class);
                        mIntent.putExtra(AppConstants.CLASS_NAME, classNameSpinner.getSelectedItem().toString());
                        mIntent.putExtra(AppConstants.SECTION_NAME, sectionNameSpinner.getSelectedItem().toString());
                        mActivity.startActivity(mIntent);
                        dialog.dismiss();
                    }
                }
            });
            seeAttendanceRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isValidClassAndSection(mActivity, classNameSpinner, sectionNameSpinner)) {
                        Intent mIntent = new Intent(mActivity, SeeAttendenceActivity.class);
                        mIntent.putExtra(AppConstants.CLASS_NAME, classNameSpinner.getSelectedItem().toString());
                        mIntent.putExtra(AppConstants.SECTION_NAME, sectionNameSpinner.getSelectedItem().toString());
                        mActivity.startActivity(mIntent);
                        dialog.dismiss();
                    }
                }
            });
            updateAttendanceRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isValidClassAndSection(mActivity, classNameSpinner, sectionNameSpinner)) {
                        Intent mIntent = new Intent(mActivity, UpdateAttendanceActivity.class);
                        mIntent.putExtra(AppConstants.CLASS_NAME, classNameSpinner.getSelectedItem().toString());
                        mIntent.putExtra(AppConstants.SECTION_NAME, sectionNameSpinner.getSelectedItem().toString());
                        mActivity.startActivity(mIntent);
                        dialog.dismiss();
                    }
                }
            });
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidClassAndSection(Activity mActivity, Spinner classNameSpinner, Spinner sectionNameSpinner) {
        boolean isValid = true;
        if (classNameSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(mActivity, "Please select class", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (sectionNameSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(mActivity, "Please select section", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }

    public static void genericAPI(final Context mActivity) {
        final RequestParams requestParams = new RequestParams();
        String url = ApiUrl.BASE_URL + ApiUrl.GENERIC_API;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(mActivity, url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                super.onSuccess(statusCode, headers, jsonObject);
                Utilz.showLog("TAG", "Response : " + jsonObject);
                try {
                    String newAppVersion = "", schoolId = "", isSmsAllowed = "";
                    if (statusCode == 200 && jsonObject.length() > 0) {
                        if (jsonObject != null && jsonObject.has(AppConstants.DATA)) {
                            JSONArray jsonArrayData = jsonObject.optJSONArray(AppConstants.DATA);
                            if (jsonArrayData != null && jsonArrayData.length() > 0) {
                                for (int i = 0; i < jsonArrayData.length(); i++) {
                                    JSONObject jsonObject1 = jsonArrayData.getJSONObject(i);
                                    if (jsonObject1 != null && jsonObject1.has(AppConstants.PACKAGE) &&
                                            (jsonObject1.optString(AppConstants.PACKAGE)).equalsIgnoreCase(BuildConfig.APPLICATION_ID)) {
                                        if (jsonObject1.has(AppConstants.VERSION))
                                            newAppVersion = jsonObject1.optString(AppConstants.VERSION);
                                        if (!TextUtils.isEmpty(newAppVersion)) {
                                            ClsGeneral.setPreferences(AppConstants.APP_VERSION_PLAY_STORE, newAppVersion);
                                        }
                                        if (jsonObject1.has(AppConstants.SMS_ALLOWED_STATUS))
                                            isSmsAllowed = jsonObject1.optString(AppConstants.SMS_ALLOWED_STATUS);
                                        if (!TextUtils.isEmpty(isSmsAllowed)) {
                                            ClsGeneral.setPreferences(AppConstants.SMS_ALLOWED_STATUS, isSmsAllowed);
                                        }
                                        if (jsonObject1.has(AppConstants.SCHOOL_ID))
                                            schoolId = jsonObject1.optString(AppConstants.SCHOOL_ID);

                                        if (!TextUtils.isEmpty(schoolId)) {
                                            ClsGeneral.setPreferences(AppConstants.SCHOOL_ID, schoolId);
                                        }
                                        Utilz.showLog(TAG, "New App version : " + newAppVersion + ", isSmsAllowed : " + isSmsAllowed + ", schoolId : " + schoolId);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    //trcking user
                    Utilz.showLog(TAG, e + "");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Utilz.showLog(TAG, "Generic Api Failed");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Utilz.showLog(TAG, "Generic Api Failed");
            }
        });
    }

    public static void updateAppDialog(final Context mActivity, String msg) {
        try {
            final Dialog dialog = new Dialog(mActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.update_app_dialog);
            dialog.setTitle(null);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            TextView updateTextView = dialog.findViewById(R.id.updateTextView);
            updateTextView.setText(msg);
            TextView textViewLater = dialog.findViewById(R.id.textViewLater);
            TextView textViewUpdate = dialog.findViewById(R.id.textViewUpdate);

            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Uri uri = Uri.parse(ApiUrl.PLAYSTORE_LINK);
                        Intent in = new Intent(Intent.ACTION_VIEW, uri);
                        mActivity.startActivity(in);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            });
            textViewLater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getSelectedClass(int position) {
        if (position == 1)
            return "10";
        else if (position == 2)
            return "9";
        else if (position == 3)
            return "8";
        else if (position == 4)
            return "7";
        else if (position == 5)
            return "6";
        else if (position == 6)
            return "5";
        else if (position == 7)
            return "4";
        else if (position == 8)
            return "3";
        else if (position == 9)
            return "2";
        else if (position == 10)
            return "1";
        else if (position == 11)
            return "LKG";
        else if (position == 12)
            return "UKG";
        else if (position == 13)
            return "Nursery";
        else
            return "";
    }

    public static String getSelectedSection(int position) {
        if (position == 1)
            return "A";
        else if (position == 2)
            return "B";
        else if (position == 3)
            return "C";
        else if (position == 4)
            return "D";
        else
            return "";
    }

    public static boolean isAttendanceTakenToday(String mStrDate) {
        boolean isAttendanceTakenToday = true;
        String mCurrentDate = getCurrentDate();
        if (mCurrentDate != null && mStrDate != null && mCurrentDate.equalsIgnoreCase(mStrDate)) {
            Date dt = new Date();
            int hours = dt.getHours();
            if (hours >= 1 && hours <= 12) {
                isAttendanceTakenToday = false;
            }
        }
        return isAttendanceTakenToday;
    }

    public static String getDayNameFromDate(String mStrDate) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy").withLocale(Locale.ENGLISH);
        LocalDate localDate = formatter.parseLocalDate(mStrDate);
        int dayOfWeek = localDate.getDayOfWeek(); // Follows ISO 8601 standard, where Monday = 1, Sunday = 7.
        if (dayOfWeek == 1)
            return AppConstants.MONDAY;
        else if (dayOfWeek == 2)
            return AppConstants.TUESDAY;
        else if (dayOfWeek == 3)
            return AppConstants.WEDNESDAY;
        else if (dayOfWeek == 4)
            return AppConstants.THURSDAY;
        else if (dayOfWeek == 5)
            return AppConstants.FRIDAY;
        else if (dayOfWeek == 6)
            return AppConstants.SATURDAY;
        else
            return "Sunday";
    }

    public static String getRandomUserIdFromName(Context mContext) {
        String userNameStr = mContext.getResources().getString(R.string.app_name);
        Random rand = new Random();
        int randomNo = rand.nextInt(90000) + 10000;
        if (!TextUtils.isEmpty(userNameStr) && userNameStr.length() > 3) {
            userNameStr = userNameStr.substring(0, 3).toUpperCase().trim();
        }
        userNameStr = userNameStr + "-" + randomNo;
        return userNameStr;
    }

    public static String getRandomUserName(String userNameStr) {
        Random rand = new Random();
        int randomNo = rand.nextInt(90000) + 10000;
        if (!TextUtils.isEmpty(userNameStr) && userNameStr.length() > 3 && userNameStr.contains(" ")) {
            userNameStr = userNameStr.substring(0, userNameStr.indexOf(" ")).trim();
        }
        userNameStr = userNameStr.trim() + "@" + randomNo;
        return userNameStr;
    }

    /**
     * Method Name: passIconTouchListener
     * Description: Method used to show hide password visibility
     */
    public static boolean passIconTouchListener(Activity mActivity, EditText mTextInputEditTexts,
                                                MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if (mTextInputEditTexts != null && !mTextInputEditTexts.getText().toString().isEmpty()) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //Getting drawable right from edittext
                Drawable mDrawableRight = mTextInputEditTexts.getCompoundDrawables()[DRAWABLE_RIGHT];
                //Putting null check before using this drawable
                if (mDrawableRight != null) {
                    if (checkDimension(mActivity) > 13) {
                        if (event.getRawX() >= ((mTextInputEditTexts.getRight() -
                                mTextInputEditTexts.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) + 20) {
                            // your action here
                            showHidePassword(mTextInputEditTexts);
                            return true;
                        }
                    } else if (checkDimension(mActivity) >= 8) {
                        if (event.getRawX() >= ((mTextInputEditTexts.getRight() -
                                mTextInputEditTexts.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) + 20) {
                            // your action here
                            showHidePassword(mTextInputEditTexts);
                            return true;
                        }
                    } else if (checkDimension(mActivity) >= 7.0) {
                        if (event.getRawX() >= ((mTextInputEditTexts.getRight() -
                                mTextInputEditTexts.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) + 150) {
                            // your action here
                            showHidePassword(mTextInputEditTexts);
                            return true;
                        }
                    } else if (checkDimension(mActivity) >= 6.9) {
                        if (event.getRawX() >= ((mTextInputEditTexts.getRight() -
                                mTextInputEditTexts.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) + 40) {
                            // your action here
                            showHidePassword(mTextInputEditTexts);
                            return true;
                        }
                    } else {
                        if (event.getRawX() >= (mTextInputEditTexts.getRight() -
                                mTextInputEditTexts.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            // your action here
                            showHidePassword(mTextInputEditTexts);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Method Name: showHidePassword
     * Description: Method used to show hide password
     */
    private static void showHidePassword(EditText mTextInputEditText) {
        if (!mTextInputEditText.getText().toString().isEmpty()) {
            if (mTextInputEditText.getTransformationMethod() == null) {
                // show password
                mTextInputEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.pass_edit, 0);
                mTextInputEditText.setTransformationMethod(new AsteriskPasswordTransformationMethod());
                mTextInputEditText.setSelection(mTextInputEditText.length());
            } else {
                //hide password
                mTextInputEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.pass, 0);
                mTextInputEditText.setTransformationMethod(null);
                mTextInputEditText.setSelection(mTextInputEditText.length());
            }
        }
    }

    public static double checkDimension(Context context) {

        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        // since SDK_INT = 1;
        int mWidthPixels = displayMetrics.widthPixels;
        int mHeightPixels = displayMetrics.heightPixels;

        // includes window decorations (statusbar bar/menu bar)
        try {
            Point realSize = new Point();
            Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
            mWidthPixels = realSize.x;
            mHeightPixels = realSize.y;
        } catch (Exception ignored) {
        }

        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(mWidthPixels / dm.xdpi, 2);
        double y = Math.pow(mHeightPixels / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        Utilz.showLog("debug", "Screen inches : " + screenInches);
        return screenInches;
    }

    public static void setMessageDialog(final Activity mContext, final String mStrPhoneNo, final boolean isStudent) {
        try {
            final Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.send_message_dialog);
            dialog.setTitle(null);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            final EditText messageEditText = dialog.findViewById(R.id.messageEditText);
            TextView textViewCancel = dialog.findViewById(R.id.textViewCancel);
            TextView textViewSend = dialog.findViewById(R.id.textViewSend);
            textViewSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String mStrMessage = messageEditText.getText().toString().trim();
                    if (!TextUtils.isEmpty(mStrMessage)) {
                        String messageBody = messageEditText.getText().toString().trim();
                        String sendTo = AppConstants.INDIVIDUAL, sectionName = "", className = "";
                        String schoolId = ClsGeneral.getStrPreferences(AppConstants.SCHOOL_ID);
                        String smsAllowedStatus = ClsGeneral.getStrPreferences(AppConstants.SMS_ALLOWED_STATUS);
                        if (isStudent) {
                            callSendMessageApi(mContext, messageBody, mStrPhoneNo, AppConstants.STUDENT, sendTo, schoolId, smsAllowedStatus, true);
                        } else {
                            callSendMessageApi(mContext, messageBody, mStrPhoneNo, AppConstants.TEACHER, sendTo, schoolId, smsAllowedStatus, true);
                        }
                    } else {
                        Toast.makeText(mContext.getApplicationContext(), mContext.getResources().getString(R.string.enter_your_message_here), Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
            });
            textViewCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void callSendMessageApi(final Activity mActivity, final String messageBody, final String userMobile, final String senderType,
                                          final String sendTo, final String schoolId, String smsAllowedStatus, final boolean isToSoLoader) {
        if (!isSmsApiCalling && mActivity != null && !mActivity.isFinishing()) {
            smsAllowedStatus = ClsGeneral.getStrPreferences(AppConstants.SMS_ALLOWED_STATUS);
            if (!TextUtils.isEmpty(smsAllowedStatus) && smsAllowedStatus.equalsIgnoreCase(AppConstants.INT_1)) {
                if (isToSoLoader)
                    Utilz.showDailog(mActivity, mActivity.getResources().getString(R.string.pleasewait));
                isSmsApiCalling = true;
                final RequestParams params = new RequestParams();
                String url = ApiUrl.SEND_MESSAGE;
                if (!TextUtils.isEmpty(ClsGeneral.getStrPreferences(AppConstants.USER_ID)))
                    params.add("sendby", ClsGeneral.getStrPreferences(AppConstants.NAME) + " (" + ClsGeneral.getStrPreferences(AppConstants.USER_ID) + ")");
                else
                    params.add("sendby", ClsGeneral.getStrPreferences(AppConstants.NAME) + " (" + ClsGeneral.getStrPreferences(AppConstants.ID) + ")");
                params.add("sendto", sendTo);//all
                params.add("mobile", userMobile);
                params.add("message", messageBody);
                params.add("schoolid", schoolId);
                params.add("clas", "");
                params.add("sec", "");
                params.add("sendertype", senderType);//Student or Teacher
                params.add("is_sms_allowed", smsAllowedStatus);
                AsyncHttpClient client = new AsyncHttpClient();
                client.post(mActivity, url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Utilz.showLog("TAG", statusCode + "Response : " + responseString);
                        if (isToSoLoader)
                            Utilz.closeDialog();
                        isSmsApiCalling = false;
                        try {
                            if (isToSoLoader)
                                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.sms_sent_successfully), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        if (isToSoLoader)
                            Utilz.closeDialog();
                        isSmsApiCalling = false;
                        if (isToSoLoader)
                            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Utilz.showMessageDialogForResponseCodes(mActivity, mActivity.getResources().getString(R.string.subscribe_sms_pack), mActivity.getResources().getString(R.string.subscribe_sms_pack_message),
                        new DialogClickListener() {
                            @Override
                            public void onClickOfPositive() {
                                openDialer(mActivity, "08002877277");
                            }
                        });
            }
        }
    }

    public static void hideKeyBoard(Activity mContext) {
        // Check if no view has focus:
        View view = mContext.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void openMoreOptionPopupWindow(final View mView, final Activity mActivity, final UpcomingActivityModel mModel,
                                                 final String mTitle, final String mMessage, final boolean isForNoticeBoard) {

        final String mApiUrl;
        if (isForNoticeBoard) {
            mApiUrl = ApiUrl.EDIT_DELETE_NOTICE;
        } else {
            mApiUrl = ApiUrl.EDIT_DELETE_EVENT;
        }
        //instantiate the popup.xml layout file
        LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.more_option_popup_dialog, null);

        Button editButton = customView.findViewById(R.id.editButton);
        Button deleteButton = customView.findViewById(R.id.deleteButton);
        Button shareButton = customView.findViewById(R.id.shareButton);

        //instantiate popup window
        final PopupWindow popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        //display the popup window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            popupWindow.showAtLocation(mView, Gravity.CENTER | Gravity.RIGHT, 0, 0);
        } else {
            popupWindow.showAsDropDown(mView);
        }
        if (mView != null && mView.getRootView() != null) {
            mView.getRootView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyPreference.isLogined()) {
                    if (AppConstants.PRINCIPAL.equalsIgnoreCase(MyPreference.getLoginedAs()) ||
                            AppConstants.VICE_PRINCIPAL.equalsIgnoreCase(MyPreference.getLoginedAs()) ||
                            AppConstants.TEACHER.equalsIgnoreCase(MyPreference.getLoginedAs())) {
                        Utilz.callUpdateDeleteApi(mActivity, mModel, mApiUrl, false);
                        popupWindow.dismiss();
                        if (isForNoticeBoard && mActivity != null && !mActivity.isFinishing())
                            mActivity.finish();
                    } else {
                        Toast.makeText(mActivity, "Sorry, This feature is not for students.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Utilz.showLoginFirstDialog(mActivity);
                }
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyPreference.isLogined()) {
                    if (AppConstants.PRINCIPAL.equalsIgnoreCase(MyPreference.getLoginedAs()) ||
                            AppConstants.VICE_PRINCIPAL.equalsIgnoreCase(MyPreference.getLoginedAs()) ||
                            AppConstants.TEACHER.equalsIgnoreCase(MyPreference.getLoginedAs())) {
                        ArrayList<UpcomingActivityModel> arrayList = new ArrayList<>();
                        arrayList.add(mModel);
                        Intent mIntent = new Intent(mActivity, UpdateNoticeAndNewsActivity.class);
                        mIntent.putExtra(AppConstants.ARRAYLIST, arrayList);
                        mIntent.putExtra(AppConstants.IS_FOR_NOTICE, isForNoticeBoard);
                        mActivity.startActivity(mIntent);
                        popupWindow.dismiss();
                    } else {
                        Toast.makeText(mActivity, "Sorry, This feature is not for students.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Utilz.showLoginFirstDialog(mActivity);
                }
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilz.shareContent(mActivity, mTitle, mMessage);
                popupWindow.dismiss();
            }
        });
    }

    public static void callUpdateDeleteApi(final Activity mActivity, final UpcomingActivityModel mModel, final String mApiUrl, final boolean isToUpdate) {
        String mTitle = mModel.getUpcomingEvetTitle();
        String mDesc = mModel.getUpcomingEvetDescription();
        String mDate = mModel.getUpcomingEventDate();
        String mNoticeEventId = mModel.getId();
        String mStatus = "1";
        String mPostedBy = mModel.getUpcomingEventPostedBy();
        Utilz.showDailog(mActivity, mActivity.getResources().getString(R.string.pleasewait));
        new RetrofitDataProvider(mActivity).callUpdateDeleteNoticeAndEvents(mActivity, mTitle, mDesc, mDate, mPostedBy, mStatus, mApiUrl, mNoticeEventId, isToUpdate, new DownlodableCallback<CommonResponse>() {
            @Override
            public void onSuccess(final CommonResponse result) {
                Utilz.closeDialog();
                if (isToUpdate)
                    Utilz.showMessageOnDialog(mActivity, mActivity.getString(R.string.success), mActivity.getString(R.string.updated_successfully), "", AppConstants.OK);
                else
                    Utilz.showMessageOnDialog(mActivity, mActivity.getString(R.string.success), mActivity.getString(R.string.deleted_successfully), AppConstants.OK, "");
            }

            @Override
            public void onFailure(String error) {
                Utilz.closeDialog();
                Toast.makeText(mActivity, R.string.something_went_wrong_error_message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onUnauthorized(int errorNumber) {
                Utilz.closeDialog();
                Toast.makeText(mActivity, R.string.something_went_wrong_error_message, Toast.LENGTH_LONG).show();
            }
        });
    }

}

