package com.appgrouplab.taskmanager.Manager.view;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.appgrouplab.taskmanager.Alarm.AlarmReceiver;
import com.appgrouplab.taskmanager.Manager.presenter.ManagerPresenter;
import com.appgrouplab.taskmanager.Manager.presenter.ManagerPresenterImpl;
import com.appgrouplab.taskmanager.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DialogTaskNew extends DialogFragment implements ManagerActivityView {

    EditText etTitleTask,etDescriptionTask;
    TextView txtCancelTaskNew, txtSaveTaskNew,txtSelectDate,txtSelectAlarm;
    ManagerPresenter managerPresenter;
    public Integer idList;
    private static final int ALARM_REQUEST_CODE= 1;

    public interface OnSaveListener{
        void sendSaveTask();
    }
    public DialogTaskNew.OnSaveListener mOnSAveListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_task_new,container,false);

        getDialog().setTitle(R.string.registerTask);

        etTitleTask = view.findViewById(R.id.etTitleTask);
        etDescriptionTask = view.findViewById(R.id.etDescriptionTask);
        txtCancelTaskNew = view.findViewById(R.id.txtCancelTaskNew);
        txtSaveTaskNew = view.findViewById(R.id.txtSaveTaskNew);
        txtSelectDate = view.findViewById(R.id.txtSelectDate);
        txtSelectAlarm = view.findViewById(R.id.txtSelectAlarm);

        managerPresenter = new ManagerPresenterImpl(this);

        eventClick();

        return view;
    }

    private void eventClick() {
        txtCancelTaskNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        txtSaveTaskNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!etTitleTask.getText().toString().equals("")) {
                    Date cDate = new Date();
                    String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                    if(txtSelectDate.getText().equals("Agenda tarea")) {
                        managerPresenter.addTask(idList, etTitleTask.getText().toString(), etDescriptionTask.getText().toString(), fDate, "", 0, "", 1, v.getContext());
                    }
                    else
                    {
                        if(!txtSelectAlarm.getText().equals("Crea tu alarma")) {
                            managerPresenter.addTask(idList, etTitleTask.getText().toString(), etDescriptionTask.getText().toString(), fDate, txtSelectDate.getText().toString(), 0, txtSelectAlarm.getText().toString(), 1, v.getContext());
                            createAlarm();
                        }
                        else
                            managerPresenter.addTask(idList,etTitleTask.getText().toString(),etDescriptionTask.getText().toString(),fDate,txtSelectDate.getText().toString(),0,"",1,v.getContext());

                    }


                    mOnSAveListener.sendSaveTask();
                }else
                    alert("Ingrese título antes de guardar.");
                getDialog().dismiss();
            }
        });

        txtSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        final int monthNow = month +1;
                        String dayFormat = (dayOfMonth<10)? "0" + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                        String monthFormat = (monthNow <10)? "0" + String.valueOf(monthNow):String.valueOf(monthNow);
                        txtSelectDate.setText(year + "-" + monthFormat + "-" + dayFormat);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });

        txtSelectAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!txtSelectDate.getText().equals("Agenda tarea")) {

                    Calendar cal = Calendar.getInstance();
                    int hour = cal.get(Calendar.HOUR_OF_DAY);
                    int minute = cal.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String hourFormat = (hourOfDay < 10) ? String.valueOf("0" + hourOfDay) : String.valueOf(hourOfDay);
                            String minuteFormat = (minute < 10) ? String.valueOf("0" + minute) : String.valueOf(minute);
                            String AM_PM;
                            if (hourOfDay < 12)
                                AM_PM = "a.m.";
                            else
                                AM_PM = "p.m.";

                            //txtSelectAlarm.setText(hourFormat + ":" + minuteFormat + " " + AM_PM);
                            txtSelectAlarm.setText(String.valueOf(hourFormat)  + ":" + String.valueOf(minuteFormat));

                        }
                    }, hour, minute, false);
                    timePickerDialog.show();
                }
                else
                    alert("Agenda primero tu tarea");
            }
        });
    }

    void createAlarm() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try{
            Date d = sdf.parse(txtSelectDate.getText().toString() + " " + txtSelectAlarm.getText().toString() );
            long timeInMilliseconds = d.getTime();

            Intent intent = new Intent(getActivity().getApplicationContext(), AlarmReceiver.class);
            intent.putExtra("title",etTitleTask.getText().toString());
            final PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),AlarmReceiver.REQUEST_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            manager.set(AlarmManager.RTC_WAKEUP,timeInMilliseconds,pendingIntent);

            Toast.makeText(this.getContext(),"Tarea y alarma creadas", Toast.LENGTH_SHORT).show();
        }catch (ParseException ex){
            ex.printStackTrace(); }


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnSAveListener = (OnSaveListener)getActivity();
        }catch (ClassCastException e){}
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(getContext());
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        bld.create().show();
    }



}
