package com.example.pk.myapplication.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by pk on 04.06.2017.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    Listener listener;

    public DatePickerFragment(Listener listener) {
        this.listener = listener;

    }

    public DatePickerFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        dialog.setTitle("");
        return dialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        listener.onDataSet(view, year, month, day);
    }

}

abstract class Listener {
    abstract void onDataSet(DatePicker view, int year, int month, int day);

}