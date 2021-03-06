package softgalli.gurukulshikshalay.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import softgalli.gurukulshikshalay.R;
import softgalli.gurukulshikshalay.adapter.TimeTableAdapter;
import softgalli.gurukulshikshalay.common.AppConstants;
import softgalli.gurukulshikshalay.common.ClsGeneral;
import softgalli.gurukulshikshalay.common.Utilz;
import softgalli.gurukulshikshalay.intrface.OnClickListener;
import softgalli.gurukulshikshalay.model.FeeDetailsModel;
import softgalli.gurukulshikshalay.model.FeeTypeModel;
import softgalli.gurukulshikshalay.model.TimeTableDataModel;
import softgalli.gurukulshikshalay.preference.MyPreference;
import softgalli.gurukulshikshalay.retrofit.ApiUrl;

/**
 * Created by Shankar on 3/22/2018.
 */

public class TimeTableActivity extends AppCompatActivity {
    @BindView(R.id.classNameSpinner)
    Spinner classNameSpinner;
    @BindView(R.id.sectionNameSpinner)
    Spinner sectionNameSpinner;
    @BindView(R.id.tv_monday)
    TextView tvMonday;
    @BindView(R.id.tv_tuesday)
    TextView tvTuesday;
    @BindView(R.id.tv_wednesday)
    TextView tvWednesday;
    @BindView(R.id.tv_thursday)
    TextView tvThursday;
    @BindView(R.id.tv_friday)
    TextView tvFriday;
    @BindView(R.id.tv_saturday)
    TextView tvSaturday;
    @BindView(R.id.rv_common)
    RecyclerView mTimeTableRecyclerView;
    ArrayList<TimeTableDataModel> timetableList = new ArrayList<>();
    @BindView(R.id.classSectionLl)
    LinearLayout classSectionLl;
    @BindView(R.id.noRecordFoundTv)
    TextView noRecordFoundTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private TimeTableAdapter timeTableAdapter;
    private String mStrSelectedDay;
    private String mStrClass = "", mStrSection = "";
    private Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        ButterKnife.bind(this);
        mActivity = this;

        initToolbar();

        manageSelectingClassAndSec();

        getTodayday();

        initWidgit();

        manageDefaultSelection();
    }

    private void manageDefaultSelection() {
        noRecordFoundTv.setText(mActivity.getResources().getString(R.string.timetable_not_uploaded_yet));
        String dayOfTheWeek = new SimpleDateFormat("EEEE").format(new Date());
        if (TextUtils.isEmpty(dayOfTheWeek))
            dayOfTheWeek = AppConstants.MONDAY;
        if (dayOfTheWeek.contains(AppConstants.MONDAY))
            changeBackNText(tvMonday);
        else if (dayOfTheWeek.contains(AppConstants.TUESDAY))
            changeBackNText(tvTuesday);
        else if (dayOfTheWeek.contains(AppConstants.WEDNESDAY))
            changeBackNText(tvWednesday);
        else if (dayOfTheWeek.contains(AppConstants.THURSDAY))
            changeBackNText(tvThursday);
        else if (dayOfTheWeek.contains(AppConstants.FRIDAY))
            changeBackNText(tvFriday);
        else if (dayOfTheWeek.contains(AppConstants.SATURDAY))
            changeBackNText(tvSaturday);
        if (!dayOfTheWeek.contains(AppConstants.SUNDAY)) {
            //Calling API to get timetable of selected date
            callTimeTableApi(dayOfTheWeek);
        } else {
            noRecordFoundTv.setVisibility(View.VISIBLE);
            noRecordFoundTv.setText(mActivity.getResources().getString(R.string.today_is_sunday_msg));
        }
    }

    private void manageSelectingClassAndSec() {
        if (AppConstants.STUDENT.equalsIgnoreCase(MyPreference.getLoginedAs())) {
            classSectionLl.setVisibility(View.GONE);
            mStrClass = ClsGeneral.getStrPreferences(AppConstants.CLAS);
            mStrSection = ClsGeneral.getStrPreferences(AppConstants.SEC);
            if (TextUtils.isEmpty(mStrClass) || TextUtils.isEmpty(mStrSection)) {
                makeVisibleClassAndSecToSelect();
            }
        } else {
            makeVisibleClassAndSecToSelect();
        }
    }

    private void makeVisibleClassAndSecToSelect() {
        classSectionLl.setVisibility(View.VISIBLE);
        List<String> classList = new ArrayList<>(), sectionList = new ArrayList<>();

        classList.addAll(Utilz.getClassList());

        sectionList.addAll(Utilz.getSectionList());
        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(mActivity,
                android.R.layout.simple_dropdown_item_1line, classList);
        ArrayAdapter<String> sectionAdapter = new ArrayAdapter<>(mActivity,
                android.R.layout.simple_dropdown_item_1line, sectionList);
        classAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        classNameSpinner.setAdapter(classAdapter);
        sectionNameSpinner.setAdapter(sectionAdapter);
        classNameSpinner.setSelection(1);
        sectionNameSpinner.setSelection(1);
        classNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mStrClass = Utilz.getSelectedClass(position);
                callTimeTableApi(mStrSelectedDay);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sectionNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mStrSection = Utilz.getSelectedSection(position);
                callTimeTableApi(mStrSelectedDay);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.text_timetable));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getTodayday() {
        String dayOfTheWeek = Utilz.todaysday(TimeTableActivity.this);
        if (dayOfTheWeek.equalsIgnoreCase(AppConstants.MONDAY)) {
            tvMonday.setBackgroundResource(R.drawable.circle);
            tvMonday.setTextColor(getResources().getColor(R.color.colorTextWhite));
            mStrSelectedDay = AppConstants.MONDAY;
        } else if (dayOfTheWeek.equalsIgnoreCase(AppConstants.TUESDAY)) {
            tvTuesday.setBackgroundResource(R.drawable.circle);
            tvTuesday.setTextColor(getResources().getColor(R.color.colorTextWhite));
            mStrSelectedDay = AppConstants.TUESDAY;
        } else if (dayOfTheWeek.equalsIgnoreCase(AppConstants.WEDNESDAY)) {
            tvWednesday.setBackgroundResource(R.drawable.circle);
            tvWednesday.setTextColor(getResources().getColor(R.color.colorTextWhite));
            mStrSelectedDay = AppConstants.WEDNESDAY;
        } else if (dayOfTheWeek.equalsIgnoreCase(AppConstants.THURSDAY)) {
            tvThursday.setBackgroundResource(R.drawable.circle);
            tvThursday.setTextColor(getResources().getColor(R.color.colorTextWhite));
            mStrSelectedDay = AppConstants.THURSDAY;
        } else if (dayOfTheWeek.equalsIgnoreCase(AppConstants.FRIDAY)) {
            tvFriday.setBackgroundResource(R.drawable.circle);
            tvFriday.setTextColor(getResources().getColor(R.color.colorTextWhite));
            mStrSelectedDay = AppConstants.FRIDAY;
        } else if (dayOfTheWeek.equalsIgnoreCase(AppConstants.SATURDAY)) {
            tvSaturday.setBackgroundResource(R.drawable.circle);
            tvSaturday.setTextColor(getResources().getColor(R.color.colorTextWhite));
            mStrSelectedDay = AppConstants.SATURDAY;
        }

    }

    private void callTimeTableApi(String mStrSelectedDay) {
        if (TextUtils.isEmpty(mStrSelectedDay)) {
            mStrSelectedDay = AppConstants.MONDAY;
        }
        if (TextUtils.isEmpty(mStrClass)) {
            Toast.makeText(mActivity, getString(R.string.please_select_class), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mStrSection)) {
            Toast.makeText(mActivity, getString(R.string.please_select_section), Toast.LENGTH_SHORT).show();
        } else {
            Utilz.showDailog(mActivity, mActivity.getResources().getString(R.string.pleasewait));
            final RequestParams params = new RequestParams();
            params.add("class", mStrClass);
            params.add("sec", mStrSection);
            params.add("school_id", ClsGeneral.getStrPreferences(AppConstants.SCHOOL_ID));
            params.add("date", mStrSelectedDay);
            params.add("type", AppConstants.TEACHER);
            String finalReqUrl = ApiUrl.BASE_URL + ApiUrl.TIME_TABLE;
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(mActivity, finalReqUrl, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Utilz.closeDialog();
                    if (response != null && response.length() > 0) {
                        timetableList.clear();
                        try {
                            String className = "", sec = "", date = "";
                            if (response.optString("status").equals("true")) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                if (jsonArray != null && jsonArray.length() > 0) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    if (jsonObject != null && jsonObject.length() > 0) {

                                        if (jsonObject.has("class"))
                                            className = jsonObject.optString("class");
                                        if (jsonObject.has("sec"))
                                            sec = jsonObject.optString("sec");
                                        if (jsonObject.has("date"))
                                            date = jsonObject.optString("date");

                                        JSONArray jsonArraySubDetails = null;
                                        if (jsonObject.has("subjectdetails"))
                                            jsonArraySubDetails = jsonObject.optJSONArray("subjectdetails");
                                        if (jsonArraySubDetails != null && jsonArraySubDetails.length() > 0) {
                                            for (int i = 0; i < jsonArraySubDetails.length(); i++) {
                                                JSONObject oneClassJsonObj = jsonArraySubDetails.getJSONObject(i);
                                                String name = "", id = "", schoolName = "", from_time = "", to_time = "", subject = "";
                                                if (oneClassJsonObj != null && oneClassJsonObj.length() > 0) {
                                                    JSONObject teacherDetailJsonObj = null;
                                                    if (oneClassJsonObj.has("teacherdetails"))
                                                        teacherDetailJsonObj = oneClassJsonObj.optJSONObject("teacherdetails");

                                                    if (teacherDetailJsonObj != null && teacherDetailJsonObj.has("name"))
                                                        name = teacherDetailJsonObj.optString("name");

                                                    if (oneClassJsonObj.has("id"))
                                                        id = oneClassJsonObj.optString("id");
                                                    if (oneClassJsonObj.has("from_time"))
                                                        from_time = oneClassJsonObj.optString("from_time");
                                                    if (oneClassJsonObj.has("to_time"))
                                                        to_time = oneClassJsonObj.optString("to_time");
                                                    if (oneClassJsonObj.has("subject"))
                                                        subject = oneClassJsonObj.optString("subject");
                                                    timetableList.add(new TimeTableDataModel(id, schoolName, className, sec, date, from_time, to_time, subject, name));
                                                }
                                            }
                                        }
                                    }
                                    timeTableAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(TimeTableActivity.this, "" + response.optString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (timetableList != null && timetableList.size() > 0) {
                        noRecordFoundTv.setVisibility(View.GONE);
                    } else {
                        noRecordFoundTv.setVisibility(View.VISIBLE);
                        noRecordFoundTv.setText(mActivity.getResources().getString(R.string.timetable_not_uploaded_yet));
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Utilz.closeDialog();
                    Toast.makeText(mActivity, getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initWidgit() {
        mTimeTableRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        timeTableAdapter = new TimeTableAdapter(TimeTableActivity.this, timetableList, R.layout.timetable_row, new OnClickListener() {
            @Override
            public void onClick(int pos) {

            }
        });
        mTimeTableRecyclerView.setAdapter(timeTableAdapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void changeBackNText(TextView tv_day) {
        tvMonday.setBackgroundResource(R.drawable.circle);
        tvMonday.setTextColor(getResources().getColor(R.color.colorTextWhite));

        tvTuesday.setBackgroundResource(R.drawable.circle);
        tvTuesday.setTextColor(getResources().getColor(R.color.colorTextWhite));

        tvWednesday.setBackgroundResource(R.drawable.circle);
        tvWednesday.setTextColor(getResources().getColor(R.color.colorTextWhite));

        tvThursday.setBackgroundResource(R.drawable.circle);
        tvThursday.setTextColor(getResources().getColor(R.color.colorTextWhite));

        tvFriday.setBackgroundResource(R.drawable.circle);
        tvFriday.setTextColor(getResources().getColor(R.color.colorTextWhite));

        tvSaturday.setBackgroundResource(R.drawable.circle);
        tvSaturday.setTextColor(getResources().getColor(R.color.colorTextWhite));

        tv_day.setBackgroundResource(R.drawable.ss_hollow_rect_green);
        tv_day.setTextColor(getResources().getColor(R.color.colorAccent));

    }

    @OnClick({R.id.tv_monday, R.id.tv_tuesday, R.id.tv_wednesday, R.id.tv_thursday, R.id.tv_friday, R.id.tv_saturday})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_monday:
                changeBackNText(tvMonday);
                callTimeTableApi(AppConstants.MONDAY);
                break;
            case R.id.tv_tuesday:
                changeBackNText(tvTuesday);
                callTimeTableApi(AppConstants.TUESDAY);
                break;
            case R.id.tv_wednesday:
                changeBackNText(tvWednesday);
                callTimeTableApi(AppConstants.WEDNESDAY);
                break;
            case R.id.tv_thursday:
                changeBackNText(tvThursday);
                callTimeTableApi(AppConstants.THURSDAY);
                break;
            case R.id.tv_friday:
                changeBackNText(tvFriday);
                callTimeTableApi(AppConstants.FRIDAY);
                break;
            case R.id.tv_saturday:
                changeBackNText(tvSaturday);
                callTimeTableApi(AppConstants.SATURDAY);
                break;
        }
    }

}

