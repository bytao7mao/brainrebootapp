package com.taozen.quithabit.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import com.taozen.quithabit.R;
import com.taozen.quithabit.options.OptionsActivity;

import java.util.Objects;

import static com.taozen.quithabit.MainActivity.HIGHEST;
import static com.taozen.quithabit.MainActivity.ONE;
import static com.taozen.quithabit.MainActivity.ONE_HUNDRED;

public class NumberPickerDialog extends DialogFragment {
    private NumberPicker.OnValueChangeListener valueChangeListener;

    //shared preferences
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //shared preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getContext()));
        editor = preferences.edit();

        final NumberPicker numberPicker = new NumberPicker(getActivity());

        if (preferences.contains(HIGHEST)){
            final int HIGHEST_STREAK = preferences.getInt(HIGHEST, ONE);
            numberPicker.setMinValue(HIGHEST_STREAK);
        } else {
            numberPicker.setMinValue(ONE);
        }
        numberPicker.setMaxValue(ONE_HUNDRED);

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle(getResources().getString(R.string.choose_value));
        builder.setMessage(getResources().getString(R.string.choose_number));

        builder.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                valueChangeListener.onValueChange(numberPicker,
                        numberPicker.getValue(), numberPicker.getValue());
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                valueChangeListener.onValueChange(numberPicker,
//                        numberPicker.getValue(), numberPicker.getValue());
                //do nothing if cancel
            }
        });

        builder.setView(numberPicker);
        return builder.create();
    }

    public NumberPicker.OnValueChangeListener getValueChangeListener() {
        return valueChangeListener;
    }

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }
}
