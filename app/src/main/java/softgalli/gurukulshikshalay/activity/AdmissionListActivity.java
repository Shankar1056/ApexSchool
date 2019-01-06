package softgalli.gurukulshikshalay.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import softgalli.gurukulshikshalay.R;
import softgalli.gurukulshikshalay.adapter.AdmissionListAdapter;
import softgalli.gurukulshikshalay.common.Utilz;
import softgalli.gurukulshikshalay.model.AdmissionListModel;
import softgalli.gurukulshikshalay.retrofit.ApiUrl;
import softgalli.gurukulshikshalay.retrofit.RetrofitDataProvider;

public class AdmissionListActivity extends AppCompatActivity {

    Activity mActivity;
    @BindView(R.id.rv_common)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.noRecordFoundTv)
    TextView noRecordFoundTv;
    @BindView(R.id.mainCardView)
    CardView mainCardView;
    @BindView(R.id.noRecordFoundCardView)
    CardView noRecordFoundCardView;
    private ArrayList<AdmissionListModel> mStudentsArrayList = new ArrayList();
    private RetrofitDataProvider retrofitDataProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_list_activity);
        mActivity = this;
        ButterKnife.bind(this);
        noRecordFoundTv.setText(mActivity.getResources().getString(R.string.no_admission_requested_till_now));
        retrofitDataProvider = new RetrofitDataProvider(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        initToolbar();

        if (Utilz.isOnline(mActivity)) {
            getAdmissionList();
        } else {
            Utilz.showNoInternetConnectionDialog(mActivity);
        }
    }

    private void getAdmissionList() {
        Utilz.showDailog(AdmissionListActivity.this, getResources().getString(R.string.pleasewait));
        final RequestParams requestParams = new RequestParams();
        AsyncHttpClient client = new AsyncHttpClient();
        Utilz.showLog("TAG", "Request : " + requestParams);
        client.get(getApplicationContext(), ApiUrl.GET_ADMISSION_LIST, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                super.onSuccess(statusCode, headers, jsonArray);
                Utilz.showLog("TAG", "Response : " + jsonArray);
                try {
                    Utilz.closeDialog();
                    if (statusCode == 200 && jsonArray != null && jsonArray.length() > 0) {
                        noRecordFoundCardView.setVisibility(View.GONE);
                        mainCardView.setVisibility(View.VISIBLE);
                        mStudentsArrayList.clear();
                        mStudentsArrayList = parsAndGetAdmisionList(jsonArray);
                        recyclerView.setAdapter(new AdmissionListAdapter(AdmissionListActivity.this, mStudentsArrayList));
                    } else {
                        noRecordFoundCardView.setVisibility(View.VISIBLE);
                        mainCardView.setVisibility(View.GONE);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Utilz.closeDialog();
                Toast.makeText(mActivity, R.string.something_went_wrong_error_message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private ArrayList<AdmissionListModel> parsAndGetAdmisionList(JSONArray jsonArray) {
        ArrayList<AdmissionListModel> listRoomsDetails = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jObj = (JSONObject) jsonArray.get(i);
                String name = jObj.getString("name").trim();
                String phone = jObj.getString("phone").trim();
                String email = jObj.getString("email").trim();
                String className = jObj.getString("class").trim();
                String address = jObj.getString("address").trim();
                if (!TextUtils.isEmpty(phone))
                    listRoomsDetails.add(new AdmissionListModel(name, phone, email, className, address));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listRoomsDetails;
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.admission_requested);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}