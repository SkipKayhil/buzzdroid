package io.github.skipkayhil.buzz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class LoginDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_login, null);

        builder.setTitle("Save GT Login Info?")
                .setView(view)
                .setPositiveButton(R.string.saveLogin, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText usernameInput = (EditText) view.findViewById(R.id.usernameInput);
                        EditText passwordInput = (EditText) view.findViewById(R.id.passwordInput);

                        SharedPreferences storage = getActivity()
                                .getSharedPreferences("LOGIN_INFO", 0);
                        storage.edit()
                                .putString("username", usernameInput.getText().toString())
                                .putString("password", passwordInput.getText().toString())
                                .apply();
                        getActivity().finish();
                        getActivity().startActivity(getActivity().getIntent());
                    }
                })
                .setNegativeButton(R.string.cancelLogin, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
