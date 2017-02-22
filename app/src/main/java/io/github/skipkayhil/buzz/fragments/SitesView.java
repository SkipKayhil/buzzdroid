package io.github.skipkayhil.buzz.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import io.github.skipkayhil.buzz.R;
import io.github.skipkayhil.buzz.Site;
import io.github.skipkayhil.buzz.SitesAdapter;

public class SitesView extends Fragment {

    private ListView listView;
    private List<Site> accountSites = Arrays.asList(
            new Site("Bursar", "http://bursar.gatech.edu", ""), //Login page multiple clicks away
            new Site("Buzzcard", "http://buzzcard.gatech.edu", ""), //Login page multiple clicks away
            new Site("Buzzport", "http://buzzport.gatech.edu", "https://buzzport.gatech.edu/misc/preauth.html"),
            new Site("DegreeWorks", "http://degreeaudit.gatech.edu", "http://degreeaudit.gatech.edu"),
            new Site("Mail", "http://mail.gatech.edu", "http://mail.gatech.edu/GTpreauth?login=true"),
            new Site("Oscar", "http://oscar.gatech.edu", ""), //No login?
            new Site("Passport", "https://passport.gatech.edu", ""), //Passport IS the login page
            new Site("Registrar", "http://registrar.gatech.edu", ""), //No login?
            new Site("T-Square", "http://tsquare.gatech.edu", "https://t-square.gatech.edu/portal/pda/?force.login=yes")
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_sites_view, container, false);

        listView = (ListView) inflatedView.findViewById(R.id.sitesList);

        SitesAdapter adapter = new SitesAdapter(getActivity(), accountSites);
        listView.setAdapter(adapter);

        return inflatedView;
    }
}
