package com.appsforprogress.android.mycareerpath;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Oswald on 1/28/2016.
 */
public class DatePickerFragment extends DialogFragment
{
    // Key to hash value
    private static final String ARG_DATE = "date";

    //
    public static final String EXTRA_DATE =
            "com.appsforprogress.android.mycareerpath.date";

    // Member variable for DatePicker reference
    private DatePicker mDatePicker;

    // New dates must be sent to the DatePickerFragment's argument bundle
    public static DatePickerFragment newInstance(Date date)
    {
        // Create a new bundle Hash
        Bundle args = new Bundle();
        // Insert the key, value pair into the Bundle Hash
        args.putSerializable(ARG_DATE, date);

        // Create a new Instance
        DatePickerFragment fragment = new DatePickerFragment();
        // Pass the new hash value to the new instance
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Retrieve the date from the hash value stored in the Argument
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        // Need to convert Date into a Calendar object TO EXTRACT INT VALUES
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Inflate the View associated to the Date Picker resource file
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        // Get reference to DatePicker widget in the layout file
        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);

        // Set up the Date picker with the extracted Date
        mDatePicker.init(year, month, day, null);

        // Create an AlertDialog instance to display the DatePicket
        return new AlertDialog.Builder(getActivity())
                .setView(v) // Set the view for the AlertDialog to the DatePicker
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                int year = mDatePicker.getYear();
                                int month = mDatePicker.getMonth();
                                int day = mDatePicker.getDayOfMonth();
                                Date date = new GregorianCalendar(year, month, day).getTime();
                                sendResult(Activity.RESULT_OK, date);
                            }
                        }) // Return date picked to activity when setPositive Button clicked using send_result()
                .create();
    }

    // method which will return date in an Intent to SkillFragment by calling it's onActivityResult()
    private void sendResult(int resultCode, Date date)
    {
        if (getTargetFragment() == null)
        {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        // start the target fragment's onActivityResult to send Date picked data back to SkillFragment
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
