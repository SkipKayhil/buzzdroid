package io.github.skipkayhil.buzz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import io.github.skipkayhil.buzz.activities.MainActivity;
import io.github.skipkayhil.buzz.model.User;

public class LoginDialog extends DialogFragment {
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View view = View.inflate(getActivity(), R.layout.dialog_login, null);

        String title = "";
        final String username = User.getInstance().getUsername();
        final String password = User.getInstance().getPassword();

        if (!username.equals("") && !password.equals("")) {
            ((EditText) view.findViewById(R.id.usernameInput)).setText(username);
            ((EditText) view.findViewById(R.id.passwordInput)).setText(password);
        }

        builder.setTitle("Save GT Login Info?")
                .setView(view)
                .setPositiveButton(R.string.saveLogin, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText usernameInput = (EditText) view.findViewById(R.id.usernameInput);
                        EditText passwordInput = (EditText) view.findViewById(R.id.passwordInput);

                        String newUsername = usernameInput.getText().toString();
                        String newPassword = passwordInput.getText().toString();

                        // eventually update the shared prefs in User?
                        User.getInstance().setUsername(newUsername);
                        User.getInstance().setPassword(newPassword);

                        SharedPreferences storage = LoginDialog.this.getActivity()
                                .getSharedPreferences("LOGIN_INFO", 0);
                        storage.edit()
                                .putString("username", newUsername)
                                .putString("password", newPassword)
                                .apply();
                        ((MainActivity) LoginDialog.this.getActivity()).refreshView();
                    }
                })
                .setNegativeButton(R.string.cancelLogin, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        super.onActivityCreated(savedInstanceState);
    }
}
