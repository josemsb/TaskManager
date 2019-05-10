package com.appgrouplab.taskmanager.Manager.view;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.appgrouplab.taskmanager.Alarm.AlarmReceiver;
import com.appgrouplab.taskmanager.Manager.presenter.ManagerPresenter;
import com.appgrouplab.taskmanager.Manager.presenter.ManagerPresenterImpl;
import com.appgrouplab.taskmanager.R;
import com.appgrouplab.taskmanager.Repository.Data.TaskData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DialogTaskEdit extends DialogFragment implements ManagerActivityView {


    EditText etTitleTaskEdit,etDescriptionTaskEdit;
    ImageButton ibCompleteTask,ibDeleteTask;
    TextView txtCancelTasEdit,txtEditTaskEdit,txtSelectDateEdit,txtSelectAlarmEdit;
    ManagerPresenter managerPresenter;
    public Integer id;

    public interface OnEditListener{
        void sendEditTask();
    }
    public DialogTaskEdit.OnEditListener mOnEditListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_task_edit,container,false);

        getDialog().setTitle(R.string.titleTaskEdit);

        etTitleTaskEdit = view.findViewById(R.id.etTitleTaskEdit);
        etDescriptionTaskEdit = view.findViewById(R.id.etDescriptionTaskEdit);
        txtCancelTasEdit = view.findViewById(R.id.txtCancelTasEdit);
        txtEditTaskEdit = view.findViewById(R.id.txtEditTaskEdit);
        txtSelectDateEdit = view.findViewById(R.id.txtSelectDateEdit);
        txtSelectAlarmEdit = view.findViewById(R.id.txtSelectAlarmEdit);
        ibDeleteTask = view.findViewById(R.id.ibDeleteTask);
        ibCompleteTask = view.findViewById(R.id.ibCompleteTask);

        managerPresenter = new ManagerPresenterImpl(this);

        eventClick();
        viewInfoTask();

        return view;
    }

    private void viewInfoTask() {
        TaskData taskData = managerPresenter.getTask(getContext(),String.valueOf(id));
        if(taskData!=null)
        {
            etTitleTaskEdit.setText(taskData.getTitle());
            etDescriptionTaskEdit.setText(taskData.getDescription());

            if(taskData.getDateReminder().equals(""))
                txtSelectDateEdit.setText(R.string.agendTask);
            else
                txtSelectDateEdit.setText(taskData.getDateReminder().toString());

            if(taskData.getHourRemider().equals(""))
                txtSelectAlarmEdit.setText(R.string.createAlarm);
            else
                txtSelectAlarmEdit.setText(taskData.getHourRemider().toString());

        }
    }

    private void eventClick() {
        txtCancelTasEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        ibDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managerPresenter.deleteTask(v.getContext(),String.valueOf(id));
                mOnEditListener.sendEditTask();
                getDialog().dismiss();
            }
        });

        ibCompleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managerPresenter.terminateTask(v.getContext(),String.valueOf(id));
                mOnEditListener.sendEditTask();
                getDialog().dismiss();
            }
        });



        txtEditTaskEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!etTitleTaskEdit.getText().toString().equals("")) {

                    if(txtSelectDateEdit.getText().equals("Agenda tarea"))
                         managerPresenter.editTask(String.valueOf(id),etTitleTaskEdit.getText().toString(),etDescriptionTaskEdit.getText().toString(),"",0,"",1,v.getContext());
                    else
                    {
                        if(!txtSelectAlarmEdit.getText().equals("Crea tu alarma"))
                        {
                            managerPresenter.editTask(String.valueOf(id),etTitleTaskEdit.getText().toString(),etDescriptionTaskEdit.getText().toString(),txtSelectDateEdit.getText().toString(),0,txtSelectAlarmEdit.getText().toString(),1,v.getContext());
                            createAlarm();
                        }
                        else
                            managerPresenter.editTask(String.valueOf(id),etTitleTaskEdit.getText().toString(),etDescriptionTaskEdit.getText().toString(),txtSelectDateEdit.getText().toString(),0,"",1,v.getContext());
                    }
                    mOnEditListener.sendEditTask();
                }else
                    alert("Ingrese título antes de editar.");
                getDialog().dismiss();
            }
        });

        txtSelectDateEdit.setOnClickListener(new View.OnClickListener() {
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
                        txtSelectDateEdit.setText(year + "-" + monthFormat + "-" + dayFormat);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });

        txtSelectAlarmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!txtSelectDateEdit.getText().equals("Agenda tarea")) {

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
                            txtSelectAlarmEdit.setText(String.valueOf(hourFormat)  + ":" + String.valueOf(minuteFormat));

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
            Date d = sdf.parse(txtSelectDateEdit.getText().toString() + " " + txtSelectAlarmEdit.getText().toString() );
            long timeInMilliseconds = d.getTime();

            Intent intent = new Intent(getActivity().getApplicationContext(), AlarmReceiver.class);
            intent.putExtra("title",etTitleTaskEdit.getText().toString());
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
            mOnEditListener = (DialogTaskEdit.OnEditListener)getActivity();
        }catch (ClassCastException e){}
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(getContext());
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        bld.create().show();
    }
}
