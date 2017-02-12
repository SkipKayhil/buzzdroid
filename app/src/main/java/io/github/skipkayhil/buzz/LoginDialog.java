package io.github.skipkayhil.buzz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

public class LoginDialog extends DialogFragment {
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View view = View.inflate(getActivity(), R.layout.dialog_login, null);

        String username = "";
        String password = "";
        String title = "";

        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username", "");
            password = bundle.getString("password", "");
        }

        if (!username.equals("") && !password.equals("")) {
            ((EditText) view.findViewById(R.id.usernameInput)).setText(username);
            ((EditText) view.findViewById(R.id.passwordInput)).setText(password);
        }

        builder.setTitle("Save GT Login Info?")
                .setView(view)
                .setPositiveButton(R.string.saveLogin, (DialogInterface dialog, int which) -> {
                        EditText usernameInput = (EditText) view.findViewById(R.id.usernameInput);
                        EditText passwordInput = (EditText) view.findViewById(R.id.passwordInput);

                        SharedPreferences storage = getActivity()
                                .getSharedPreferences("LOGIN_INFO", 0);
                        storage.edit()
                                .putString("username", usernameInput.getText().toString())
                                .putString("password", passwordInput.getText().toString())
                                .apply();
                        ((MainNavigation) getActivity()).refreshView();
                })
                .setNegativeButton(R.string.cancelLogin, (DialogInterface dialog, int which) -> {});
        return builder.create();
    }
}
