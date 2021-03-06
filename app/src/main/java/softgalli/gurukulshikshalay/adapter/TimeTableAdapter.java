package softgalli.gurukulshikshalay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import softgalli.gurukulshikshalay.R;
import softgalli.gurukulshikshalay.intrface.OnClickListener;
import softgalli.gurukulshikshalay.model.TimeTableDataModel;

/**
 * Created by Shankar on 2/10/2018.
 */

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.MyViewHolder> {

    private List<TimeTableDataModel> noticeBoardModels;
    private OnClickListener onClickListener;
    private int timetablerow;


    public TimeTableAdapter(Context mContext, List<TimeTableDataModel> noticeBoardModels, int timetablerow, OnClickListener onClickListener) {
        this.noticeBoardModels = noticeBoardModels;
        this.timetablerow = timetablerow;
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(timetablerow, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final TimeTableDataModel sa = noticeBoardModels.get(position);
        // holder.CatTextView.setTypeface(Utilz.font(mContext, "bold"));

        if (!TextUtils.isEmpty(sa.getFrom_time()) && !TextUtils.isEmpty(sa.getTo_time()))
            holder.tv_timetable.setText(sa.getFrom_time() + " - " + sa.getTo_time());
        else
            holder.tv_timetable.setText("N/A - N/A");

        if (!TextUtils.isEmpty(sa.getSubject())) {
            holder.tv_subject.setText(sa.getSubject());
            holder.tv_subject.setVisibility(View.VISIBLE);
        } else {
            holder.tv_subject.setVisibility(View.GONE);
            holder.tv_subject.setText("N/A");
        }
        if (!TextUtils.isEmpty(sa.getTeacher_name()))
            holder.tv_teac_name.setText("Teacher - " + sa.getTeacher_name());
        else
            holder.tv_teac_name.setText("N/A");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return noticeBoardModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_timetable, tv_subject, tv_teac_name;

        public MyViewHolder(View view) {
            super(view);
            tv_timetable = (TextView) view.findViewById(R.id.tv_timetable);
            tv_subject = (TextView) view.findViewById(R.id.tv_subject);
            tv_teac_name = (TextView) view.findViewById(R.id.tv_teac_name);
        }
    }

}


