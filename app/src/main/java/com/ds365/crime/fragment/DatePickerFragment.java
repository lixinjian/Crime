package com.ds365.crime.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.ds365.crime.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Administrator on 2017/7/11 0011.
 * 显示日历的Fragment
 * <p>
 * 建议将AlertDialog封装在DialogFragment（Fragment的子类）实例中使用。当然，不使
 * 用DialogFragment也可显示AlertDialog视图，但不推荐这样做。使用FragmentManager管理
 * 对话框，可以更灵活地显示对话框。
 * 另外，如果旋转设备，单独使用的AlertDialog会消失，而封装在fragment中的AlertDialog
 * 则不会有此问题（旋转后，对话框会被重建恢复）
 */

public class DatePickerFragment extends DialogFragment {


    private static final String ARG_DATE = "date";
    private DatePicker mDatePicker;
    public static final String EXTRA_DATE =
            "com.bignerdranch.android.criminalintent.date";

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDatePicker = new DatePicker(getActivity());

        mDatePicker.init(year, month, day, null);

        return new AlertDialog.Builder(getActivity())
                .setView(mDatePicker)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getData();
                    }
                })
                .create();
    }

    private void getData() {
        int y = mDatePicker.getYear();
        int m = mDatePicker.getMonth();
        int d = mDatePicker.getDayOfMonth();
        Date date = new GregorianCalendar(y, m, d).getTime();
        sendResult(Activity.RESULT_OK, date);
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
