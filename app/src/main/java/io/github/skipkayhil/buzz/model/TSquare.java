package io.github.skipkayhil.buzz.model;

import java.util.ArrayList;

public class TSquare {

    private ArrayList<GTClass> classList = new ArrayList<>();
    private boolean loggedIn = false;

    public void addClass(GTClass gtClass) {
        classList.add(gtClass);
    }

    public ArrayList<GTClass> getClassList() { return classList; }

    public boolean isLoggedIn() { return loggedIn; }
}
