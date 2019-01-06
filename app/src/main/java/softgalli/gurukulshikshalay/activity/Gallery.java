package softgalli.gurukulshikshalay.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import softgalli.gurukulshikshalay.R;
import softgalli.gurukulshikshalay.adapter.GalleryAdapter;
import softgalli.gurukulshikshalay.fragments.SlideshowDialogFragment;
import softgalli.gurukulshikshalay.intrface.OnClickListener;
import softgalli.gurukulshikshalay.model.GalleryImages;

/**
 * Created by Shankar on 3/18/2018.
 */

public class Gallery extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ArrayList<GalleryImages> galleryModelsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);
        ButterKnife.bind(this);
        initToolbar();
        initWidgit();

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.gallery_list));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initWidgit() {
        galleryModelsList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            galleryModelsList = bundle.getParcelableArrayList("images");
        GalleryAdapter adapter = new GalleryAdapter(Gallery.this, galleryModelsList, new OnClickListener() {
            @Override
            public void onClick(int pos) {
                if (galleryModelsList.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("images", galleryModelsList);
                    bundle.putInt("position", pos);

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                    newFragment.setArguments(bundle);
                    newFragment.show(ft, "slideshow");

                }
            }


        });
        recyclerView.setAdapter(adapter);

    }


}
