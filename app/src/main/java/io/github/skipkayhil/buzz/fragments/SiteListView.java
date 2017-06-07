package io.github.skipkayhil.buzz.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import io.github.skipkayhil.buzz.R;
import io.github.skipkayhil.buzz.SiteAdapter;

public class SiteListView extends Fragment {
    public class Site {
        public String name;
        public String defaultUrl;
        public String loginUrl;

        Site(String name, String defaultUrl, String loginUrl) {
            this.name = name;
            this.defaultUrl = defaultUrl;
            this.loginUrl = loginUrl;
        }
        Site(String name, String defaultUrl) {
            this.name = name;
            this.defaultUrl = defaultUrl;
            this.loginUrl = "";
        }
    }
//    This was used for back stack popping, but I don't think we need it now
//    private CharSequence previousTitle;


    //TODO: convert this to a database and have it imported programmatically?
    public final List<Site> accountSites = Arrays.asList(
            new Site("Bursar", "http://bursar.gatech.edu"), //Login page multiple clicks away
            new Site("Buzzcard", "http://buzzcard.gatech.edu"), //Login page multiple clicks away
            new Site("Buzzport", "http://buzzport.gatech.edu", "https://buzzport.gatech.edu/misc/preauth.html"),
            new Site("DegreeWorks", "http://degreeaudit.gatech.edu", "http://degreeaudit.gatech.edu"),
            new Site("Mail", "http://mail.gatech.edu", "http://mail.gatech.edu/GTpreauth?login=true"),
            new Site("Oscar", "http://oscar.gatech.edu"), //No login?
            new Site("Passport", "https://passport.gatech.edu"), //Passport IS the login page
            new Site("Registrar", "http://registrar.gatech.edu"), //No login?
            new Site("T-Square", "http://tsquare.gatech.edu", "https://t-square.gatech.edu/portal/pda/?force.login=yes")
    );
    public final List<Site> campusSites = Arrays.asList(
            new Site("Barnes & Noble Bookstore", "http://shopgatech.edu"),
            new Site("Campus Recreation Center", "http://crc.gatech.edu"),
            new Site("Housing", "http://housing.gatech.edu"),
            new Site("Library", "http://library.gatech.edu"),
            new Site("Parking & Transportation Services", "http://pts.gatech.edu"),
            new Site("Post Office", "http://postoffice.gatech.edu"),
            new Site("Stingerette", "http://stingerette.com"),
            new Site("Student Center", "http://studentcenter.gatech.edu"),
            new Site("Student Success Center", "http://ssc.gatech.edu")
    );
    public final List<Site> collegeSites = Arrays.asList(
            new Site("College of Architecture", "http://coa.gatech.edu"),
            new Site("College of Computing", "http://cc.gatech.edu"),
            new Site("College of Engineering", "http://coe.gatech.edu"),
            new Site("College of Sciences", "http://cos.gatech.edu"),
            new Site("College of Liberal Arts", "http://iac.gatech.edu"),
            new Site("Scheller College of Business", "http://scheller.gatech.edu")
    );
    public final List<Site> communitySites = Arrays.asList(
            new Site("Alumni Association", "http://gtalumni.org"),
            new Site("Arts @ Tech", "http://arts.gatech.edu"),
            new Site("Athletics", "http://ramblinwreck.com"),
            new Site("Calendar", "http://calendar.gatech.edu"),
            new Site("Directory", "http://directory.gatech.edu"),
            new Site("Faculty", "http://faculty.gatech.edu"),
            new Site("Greek Affairs", "http://greek.gatech.edu"),
            new Site("News", "http://news.gatech.edu"),
            new Site("OrgSync", "http://orgsync.com/home/884"),
            new Site("Student Government Association", "http://sga.gatech.edu"),
            new Site("Student Life", "http://studentlift.gatech.edu"),
            new Site("The Technique", "http://nique.net"),
            new Site("The Whistle", "http://whistle.gatech.edu"),
            new Site("WREK Radio", "http://wrek.org")
    );
    public final List<Site> prospectiveSites = Arrays.asList(
            new Site("Admissions", "http://admissions.gatech.edu"),
            new Site("Campus Visits", "http://admission.gatech.edu/visit"),
            new Site("FASET", "http://faset.gatech.edu"),
            new Site("Financial Aid", "http://finaid.gatech.edu"),
            new Site("Graduate Studies", "http://grad.gatech.edu"),
            new Site("Parents", "http://parents.gatech.edu")
    );
    public final List<Site> schoolSites = Arrays.asList(
            new Site("There are so many of these", "")
    );
    public final List<Site> serviceSites = Arrays.asList(
            new Site("Advising", "http://advising.gatech.edu"),
            new Site("Career Services", "http://career.gatech.edu"),
            new Site("Counseling Center", "http://counseling.gatech.edu"),
            new Site("Dining @ Tech", "http://gatechdining.com"),
            new Site("Georgia Tech Police Department", "http://police.gatech.edu"),
            new Site("Institute Communications", "http://comm.gatech.edu"),
            new Site("Office of Information Technology", "http://oit.gatech.edu"),
            new Site("Offices and Departments", "http://gatech.edu/offices-and-departments"),
            new Site("Stamps Health Services", "http://health.gatech.edu"),
            new Site("Study Abroad", "http://oie.gatech.edu")
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
        switch (getArguments().getInt("position")) {
            case 0:
                siteList = accountSites;
                toolbar.setTitle("Account");
                break;
            case 1:
                siteList = campusSites;
                toolbar.setTitle("Campus");
                break;
            case 2:
                siteList = collegeSites;
                toolbar.setTitle("Colleges");
                break;
            case 3:
                siteList = communitySites;
                toolbar.setTitle("Community");
                break;
            case 4:
                siteList = prospectiveSites;
                toolbar.setTitle("Prospective Students");
                break;
            case 5:
                siteList = schoolSites;
                toolbar.setTitle("Schools");
                break;
            case 6:
                siteList = serviceSites;
                toolbar.setTitle("Services");
                break;
        }

        final SiteAdapter adapter = new SiteAdapter(getActivity(), siteList);
        listView.setAdapter(adapter);

        // Android isn't letting me use a lambda here for some reason.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url;
                if (adapter.getItem(position).loginUrl.equals("")) {
                    url = adapter.getItem(position).defaultUrl;
                } else {
                    url = adapter.getItem(position).loginUrl;
                }
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                bundle.putString("username", getArguments().getString("username", ""));
                bundle.putString("password", getArguments().getString("password", ""));

                BuzzWebView buzzWebView = new BuzzWebView();
                buzzWebView.setArguments(bundle);

                toolbar.setTitle(adapter.getItem(position).name);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, buzzWebView, "currentView")
                        .addToBackStack(null).commit();
            }
        });

        return inflatedView;
    }
}
