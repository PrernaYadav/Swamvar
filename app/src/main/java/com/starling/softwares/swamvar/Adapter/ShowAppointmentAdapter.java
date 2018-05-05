package com.starling.softwares.swamvar.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starling.softwares.swamvar.Model.ShowAppointment;
import com.starling.softwares.swamvar.R;

import java.util.ArrayList;

public class ShowAppointmentAdapter extends RecyclerView.Adapter<ShowAppointmentAdapter.ShowAppointmentHolder> {

    private ArrayList<ShowAppointment> showAppointmentArrayList;
    Activity activity;

    public ShowAppointmentAdapter(ArrayList<ShowAppointment> showAppointmentArrayList, Activity activity) {
        this.showAppointmentArrayList = showAppointmentArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ShowAppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_show_appo, parent, false);
        return new ShowAppointmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAppointmentAdapter.ShowAppointmentHolder holder, int position) {
        final ShowAppointment showAppointment = showAppointmentArrayList.get(position);

        holder.id.setText(showAppointment.getId());
        holder.subject.setText(showAppointment.getSubject());
        holder.date.setText(showAppointment.getDate());
        holder.status.setText(showAppointment.getStatus());
        holder.status_comment.setText(showAppointment.getStatus_comment());
        holder.time.setText(showAppointment.getTime());
        holder.address.setText(showAppointment.getAddress());
        holder.email.setText(showAppointment.getEmail());
        holder.role_name.setText(showAppointment.getRole_name());

        if (showAppointment.getStatus().equals("1")) {
            holder.status.setText("Approved");
            holder.status.setTextColor(Color.GREEN);
        } else if (showAppointment.getStatus().equals("2")) {
            holder.status.setText("Rejected");
            holder.status.setTextColor(Color.RED);

        }
        if (showAppointment.getStatus_comment().equals("")){
            holder.status_comment.setText("No Comments");
        }
    }

    @Override
    public int getItemCount() {
        if (showAppointmentArrayList == null)
            return 0;
        return showAppointmentArrayList.size();
    }


    public class ShowAppointmentHolder extends RecyclerView.ViewHolder {


        TextView id, subject, date, status, status_comment, time, address, email, role_name;

        public ShowAppointmentHolder(View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.appo_id);
            subject = itemView.findViewById(R.id.appo_subject);
            date = itemView.findViewById(R.id.appo_date);
            status = itemView.findViewById(R.id.appo_status);
            status_comment = itemView.findViewById(R.id.appo_comments);
            time = itemView.findViewById(R.id.appo_time);
            address = itemView.findViewById(R.id.appo_address);
            email = itemView.findViewById(R.id.appo_email);
            role_name = itemView.findViewById(R.id.appo_name);


        }
    }
}
