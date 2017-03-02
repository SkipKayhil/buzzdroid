package io.github.skipkayhil.buzz.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import io.github.skipkayhil.buzz.R;
import io.github.skipkayhil.buzz.SiteAdapter;

public class SiteListView extends Fragment {
    public class Site{
        public String name;
        public String defaultUrl;
        public String loginUrl;

        Site(String name, String defaultUrl, String loginUrl) {
            this.name = name;
            this.defaultUrl = defaultUrl;
            this.loginUrl = loginUrl;
        }
    }

    private CharSequence previousTitle;

    private final List<Site> accountSites = Arrays.asList(
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
    private List<Site> campusSites = Arrays.asList(
            new Site("Barnes & Noble Bookstore", "http://shopgatech.edu", "")
    );

    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_sites_view, container, false);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        ListView listView = (ListView) inflatedView.findViewById(R.id.siteList);

        List<Site> siteList = accountSites;
        switch(getArguments().getInt("position")) {
            case 0:
                siteList = accountSites;
                toolbar.setTitle("Account");
                break;
        }

        SiteAdapter adapter = new SiteAdapter(getActivity(), siteList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (adapter.getItem(position).loginUrl.equals("")) {
                String url = adapter.getItem(position).defaultUrl;
            } else {
                String url = adapter.getItem(position).loginUrl;
            }

        });

        return inflatedView;
    }
}
