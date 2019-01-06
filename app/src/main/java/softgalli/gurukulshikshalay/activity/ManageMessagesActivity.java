package softgalli.gurukulshikshalay.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import softgalli.gurukulshikshalay.R;
import softgalli.gurukulshikshalay.common.AppConstants;
import softgalli.gurukulshikshalay.common.ClsGeneral;
import softgalli.gurukulshikshalay.common.Utilz;

public class ManageMessagesActivity extends AppCompatActivity {
    private static final String TAG = ManageMessagesActivity.class.getSimpleName();
    @BindView(R.id.allTeachers)
    Button allTeachers;
    @BindView(R.id.toAll)
    Button toAll;
    @BindView(R.id.allStudents)
    Button allStudents;
    @BindView(R.id.descriptionEt)
    EditText mDescriptionEt;
    @BindView(R.id.submitButtonLl)
    LinearLayout submitButtonLl;
    private Activity mActivity;
    private String sendTo = AppConstants.ALL, userMobile = "", schoolId = "", senderType = "", smsAllowedStatus = "", messageBody = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message_activity);
        ButterKnife.bind(this);
        mActivity = this;
        schoolId = ClsGeneral.getStrPreferences(AppConstants.SCHOOL_ID);
        smsAllowedStatus = ClsGeneral.getStrPreferences(AppConstants.SMS_ALLOWED_STATUS);
        initToolbar();
        Utilz.hideKeyBoard(mActivity);
    }


    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(mActivity.getResources().getString(R.string.send_message));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.allTeachers, R.id.toAll, R.id.allStudents, R.id.submitButtonLl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.allTeachers: {
                unSelectAll();
                allTeachers.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checked_circle, 0);
                senderType = AppConstants.TEACHER;
                break;
            }
            case R.id.toAll: {
                unSelectAll();
                toAll.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checked_circle, 0);
                senderType = AppConstants.ALL;
                break;
            }
            case R.id.allStudents: {
                unSelectAll();
                allStudents.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checked_circle, 0);
                senderType = AppConstants.STUDENT;
                break;
            }
            case R.id.submitButtonLl: {
                String mDescriptionStr = mDescriptionEt.getText().toString();
                if (Utilz.isOnline(mActivity)) {
                    if (TextUtils.isEmpty(mDescriptionStr)) {
                        Toast.makeText(mActivity, mActivity.getResources().getString(R.string.please_enter_message), Toast.LENGTH_LONG).show();
                    } else {
                        messageBody = mDescriptionEt.getText().toString().trim();
                        if (!TextUtils.isEmpty(senderType) && senderType.equalsIgnoreCase(AppConstants.ALL)) {
                            Utilz.callSendMessageApi(mActivity, messageBody, userMobile, AppConstants.STUDENT, sendTo, schoolId, smsAllowedStatus, true);
                            //Sending Message for this event or notice. Sending to All Teachers as well
                            new android.os.Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Utilz.callSendMessageApi(mActivity, messageBody, userMobile, AppConstants.TEACHER, sendTo, schoolId, smsAllowedStatus, false);
                                }
                            }, (10 * AppConstants.ONE_SECOND));
                        } else {
                            Utilz.callSendMessageApi(mActivity, messageBody, userMobile, senderType, sendTo, schoolId, smsAllowedStatus, true);
                        }
                    }
                } else {
                    Utilz.showNoInternetConnectionDialog(mActivity);
                }
                break;
            }
        }
    }

    private void unSelectAll() {
        allStudents.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.unchecked_circled, 0);
        allTeachers.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.unchecked_circled, 0);
        toAll.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.unchecked_circled, 0);
    }
}

