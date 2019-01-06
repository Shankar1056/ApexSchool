package softgalli.gurukulshikshalay.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import softgalli.gurukulshikshalay.R;
import softgalli.gurukulshikshalay.common.Utilz;


public class AboutUsActivity extends AppCompatActivity {
    @BindView(R.id.callUsLL)
    LinearLayout callUsLL;
    @BindView(R.id.bannerImage)
    ImageView bannerImage;
    @BindView(R.id.ratingCount)
    TextView ratingCount;
    @BindView(R.id.reviewCount)
    TextView reviewCount;
    @BindView(R.id.aboutUsDescription)
    TextView aboutUsDescription;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus_activity);
        ButterKnife.bind(this);
        mActivity = this;

        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(mActivity.getResources().getString(R.string.about_us));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.callUsLL)
    public void onViewClicked() {
        Utilz.openDialer(mActivity, mActivity.getResources().getString(R.string.helpline_no1));
    }
}
