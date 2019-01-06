package softgalli.gurukulshikshalay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import softgalli.gurukulshikshalay.R;
import softgalli.gurukulshikshalay.common.Utilz;
import softgalli.gurukulshikshalay.model.AdmissionListModel;

public class AdmissionListAdapter extends RecyclerView.Adapter<AdmissionListAdapter.MyViewHolder> {

    private List<AdmissionListModel> admissionList = new ArrayList<>();
    private Context mContext;

    public AdmissionListAdapter(Context context, List<AdmissionListModel> admissionList) {
        mContext = context;
        this.admissionList = admissionList;
    }

    @Override
    public AdmissionListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admission_list_itmeview, parent, false);

        return new AdmissionListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdmissionListAdapter.MyViewHolder holder, final int position) {
        final AdmissionListModel AdmissionListModel = admissionList.get(position);
        holder.studentNameTv.setText(AdmissionListModel.getName());
        if (!TextUtils.isEmpty(AdmissionListModel.getPhoneNo())) {
            holder.phoneTv.setVisibility(View.VISIBLE);
            holder.phoneTv.setText("Phone - " + AdmissionListModel.getPhoneNo());
        } else {
            holder.phoneTv.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(AdmissionListModel.getEmailId())) {
            holder.emailTv.setVisibility(View.VISIBLE);
            holder.emailTv.setText("Email - " + AdmissionListModel.getEmailId());
        } else {
            holder.emailTv.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(AdmissionListModel.getClassName())) {
            holder.classNameTv.setVisibility(View.VISIBLE);
            holder.classNameTv.setText("Class - " + AdmissionListModel.getClassName());
        } else {
            holder.classNameTv.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(AdmissionListModel.getComment())) {
            holder.messageTv.setVisibility(View.VISIBLE);
            holder.messageTv.setText("Message - " + AdmissionListModel.getComment());
        } else {
            holder.messageTv.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onClickListener.onClick(position);
                if (AdmissionListModel != null) {
                    Utilz.openDialer(mContext, AdmissionListModel.getPhoneNo());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return admissionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView studentNameTv;
        public TextView messageTv, phoneTv;
        public TextView emailTv, classNameTv;

        public MyViewHolder(View view) {
            super(view);
            studentNameTv = view.findViewById(R.id.studentNameTv);
            phoneTv = view.findViewById(R.id.phoneTv);
            emailTv = view.findViewById(R.id.emailTv);
            classNameTv = view.findViewById(R.id.classNameTv);
            messageTv = view.findViewById(R.id.messageTv);
        }
    }


}