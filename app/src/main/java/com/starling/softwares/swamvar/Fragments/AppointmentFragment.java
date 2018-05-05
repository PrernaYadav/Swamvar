package com.starling.softwares.swamvar.Fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.starling.softwares.swamvar.Activities.Home;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Constant;
import com.starling.softwares.swamvar.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;

public class AppointmentFragment extends Fragment{

//    @BindView( R.id.btn_appo_status)
    Button btnStatus;
//    @BindView(R.id.btn_appo_save)
    Button btnSave;
//    @BindView(R.id.et_appo_subject)
    EditText etSubject;
//    @BindView(R.id.et_appo_date)
    EditText etDate;
//    @BindView(R.id.et_appo_time)
    EditText etTime;
//    @BindView(R.id.et_appo_address)
    EditText etAddress;
    Calendar myCalendar = Calendar.getInstance();
    String subject,datee,timee,address;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        etDate=view.findViewById(R.id.et_appo_date);
        etTime=view.findViewById(R.id.et_appo_time);
        etSubject=view.findViewById(R.id.et_appo_subject);
        etAddress=view.findViewById(R.id.et_appo_address);
        btnSave=view.findViewById(R.id.btn_appo_save);
        btnStatus=view.findViewById(R.id.btn_appo_status);

        setHasOptionsMenu(true);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "askkfsjhjksdhgjd", Toast.LENGTH_SHORT).show();
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subject=etSubject.getText().toString();
                datee=etDate.getText().toString();
                timee=etTime.getText().toString();
                address=etAddress.getText().toString();

                if (subject.equals("")||datee.equals("")||timee.equals("")||address.equals("")){
                    Toast.makeText(getActivity(), "Empty Fields", Toast.LENGTH_SHORT).show();
                }else {
                    saveAppointment();
                }

            }
        });

        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Home) getActivity()).takeToShowAppointment();
                DrawerFragment.closeDrawerMenu();
            }
        });


        return view;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void saveAppointment(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_SET_APPOINTMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("success")){
                            Toast.makeText(getActivity(), "Appointment Saved Successfully!", Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(getActivity(), "Something Wrong", Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(), "Slow internet,Please Try Again", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();
                params.put("access_token", Constant.access_token);
                params.put("user_id", Utils.getActiveUser(getActivity()).getUser_id());
                params.put("subject", subject);
                params.put("date", datee);
                params.put("time", timee);
                params.put("address", address);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        int scoket = 30000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(scoket, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);
    }

}
