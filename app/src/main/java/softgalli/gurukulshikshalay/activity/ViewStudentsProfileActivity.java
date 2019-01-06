package softgalli.gurukulshikshalay.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import softgalli.gurukulshikshalay.BuildConfig;
import softgalli.gurukulshikshalay.R;
import softgalli.gurukulshikshalay.common.AppConstants;
import softgalli.gurukulshikshalay.common.ClsGeneral;
import softgalli.gurukulshikshalay.common.Utilz;
import softgalli.gurukulshikshalay.model.UserDetailsDataModel;

public class ViewStudentsProfileActivity extends AppCompatActivity {
    @BindView(R.id.userProfilePicIv)
    CircleImageView userProfilePicIv;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.userNameTv)
    TextView userNameTv;
    @BindView(R.id.userClassTv)
    TextView userClassTv;
    @BindView(R.id.userPhoneTv)
    TextView userPhoneTv;
    @BindView(R.id.callButton)
    Button callButton;
    @BindView(R.id.userEmailIdTv)
    TextView userEmailIdTv;
    @BindView(R.id.userRollNoTv)
    TextView userRollNoTv;
    @BindView(R.id.userFatherNameTv)
    TextView userFatherNameTv;
    @BindView(R.id.userAddressTv)
    TextView userAddressTv;
    private Activity mActivity;
    private int mPosition;
    private ArrayList<UserDetailsDataModel> mStudentsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_student_profile_activity);
        ButterKnife.bind(this);
        mActivity = this;
        initToolbar();
        getIntentData();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("My Profile");
    }

    private void getIntentData() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            if (mBundle.containsKey(AppConstants.POSITION))
                mPosition = mBundle.getInt(AppConstants.POSITION);
            if (mBundle.containsKey(AppConstants.TEACHER_DETAILS))
                mStudentsArrayList = (ArrayList<UserDetailsDataModel>) mBundle.getSerializable(AppConstants.TEACHER_DETAILS);
        }
        if (mStudentsArrayList != null && mStudentsArrayList.size() > 0)
            updateOnUI(mStudentsArrayList.get(mPosition));
    }

    private void updateOnUI(UserDetailsDataModel mStudentListDataModel) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.logo);
        requestOptions.error(R.drawable.logo);
        requestOptions.fitCenter();
        String finalImageUrl = mStudentListDataModel.getProfile_pic();
        if (!TextUtils.isEmpty(finalImageUrl) && !finalImageUrl.contains(AppConstants.FACEBOOK_IMAGE_URL)) {
            finalImageUrl = BuildConfig.WEBSITE_URL + finalImageUrl;
        }
        Glide.with(mActivity)
                .load(finalImageUrl)
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progress.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(userProfilePicIv);
        if (!TextUtils.isEmpty(mStudentListDataModel.getName())) {
            userNameTv.setText(mStudentListDataModel.getName());
        } else {
            userNameTv.setText(mActivity.getResources().getString(R.string.na));
        }
        if (!TextUtils.isEmpty(mStudentListDataModel.getClas())) {
            userClassTv.setText(mStudentListDataModel.getClas());
        } else {
            userClassTv.setText(mActivity.getResources().getString(R.string.na));
        }
    }

    @OnClick(R.id.callButton)
    public void onViewClicked() {
        if (mStudentsArrayList != null && mStudentsArrayList.size() > 0 && !TextUtils.isEmpty(mStudentsArrayList.get(mPosition).getMobile())) {
            Utilz.openDialer(mActivity, mStudentsArrayList.get(mPosition).getMobile());
        } else if (mStudentsArrayList != null && mStudentsArrayList.size() > 0 && !TextUtils.isEmpty(mStudentsArrayList.get(mPosition).getAlternate_number())) {
            Utilz.openDialer(mActivity, mStudentsArrayList.get(mPosition).getAlternate_number());
        } else {
            Toast.makeText(mActivity, R.string.phone_no_not_provide_yet, Toast.LENGTH_SHORT).show();
        }
    }
}
