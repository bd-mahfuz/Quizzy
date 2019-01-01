package com.example.mahfuz.quizzy.Utility;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahfuz.quizzy.R;

public class CustomToast {


    public static void showCustomToast(Activity activity, String message) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);

        View view = layoutInflater.inflate(R.layout.layout_custom_toast,
                (ViewGroup) activity.findViewById(R.id.toast_layout));


        // set a message
        TextView text = (TextView) view.findViewById(R.id.toast_text);
        text.setText(message);

        // Toast...
        Toast toast = new Toast(activity.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();

    }
}
