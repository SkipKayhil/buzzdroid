package io.github.skipkayhil.buzz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class LoginDialogPreference extends DialogPreference {
    public LoginDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            EditText usernameInput = (EditText) getDialog().findViewById(R.id.usernameInput);
            EditText passwordInput = (EditText) getDialog().findViewById(R.id.passwordInput);

            SharedPreferences storage = getSharedPreferences(); //LOGIN_INFO set in xml
            storage.edit()
                    .putString("username", usernameInput.getText().toString())
                    .putString("password", passwordInput.getText().toString())
                    .apply();
        }
    }

//    @Override
//    public void onPrepareDialogBuilder(AlertDialog.Builder builder) {
//        LayoutInflater inflater = getLayoutInflater();
//        final View view = inflater.inflate(R.layout.dialog_login, null);
//
//        builder.setTitle("Save GT Login Info?")
//                .setView(view)
//                .setPositiveButton(R.string.saveLogin, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        EditText usernameInput = (EditText) view.findViewById(R.id.usernameInput);
//                        EditText passwordInput = (EditText) view.findViewById(R.id.passwordInput);
//
//                        SharedPreferences storage = getSharedPreferences();
//                        storage.edit()
//                                .putString("username", usernameInput.getText().toString())
//                                .putString("password", passwordInput.getText().toString())
//                                .apply();
//                    }
//                })
//                .setNegativeButton(R.string.cancelLogin, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//    }
}
