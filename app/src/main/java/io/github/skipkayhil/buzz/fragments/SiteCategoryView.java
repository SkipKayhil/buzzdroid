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
import io.github.skipkayhil.buzz.SiteCategoryAdapter;

public class SiteCategoryView extends Fragment {

    public class SiteCategory {
        public final String name;
        public final String description;
        public final int icon;

        SiteCategory(String name, String description, int icon) {
            this.name = name;
            this.description = description;
            this.icon = icon;
        }
    }

    private List<SiteCategory> categoryList = Arrays.asList(
            new SiteCategory("Account", "Services dealing with your Georgia Tech account", R.drawable.ic_account_box_black_24dp),
            new SiteCategory("Campus", "Buildings and services related to campus", R.drawable.ic_business_black_24dp),
            new SiteCategory("Colleges", "Home pages of the Georgia Tech colleges", R.drawable.ic_account_balance_black_24dp),
            new SiteCategory("Community", "Find out more about the Georgia Tech community", R.drawable.ic_people_black_24dp),
            new SiteCategory("Prospective Students", "Places for prospective students to learn about Tech", R.drawable.ic_favorite_black_24dp),
            new SiteCategory("Schools", "Sites for the individual schools at Georgia Tech", R.drawable.ic_school_black_24dp),
            new SiteCategory("Services", "Detailed information about campus services", R.drawable.ic_local_hospital_black_24dp)
    );

    private String[] sections = {
            "Account",
            "Campus",
            "Colleges",
            "Community",
            "Prospective Students",
            "Schools",
            "Services"
    };

    ;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_sites_view, container, false);

        ListView listView = (ListView) inflatedView.findViewById(R.id.siteList);

        SiteCategoryAdapter adapter = new SiteCategoryAdapter(getActivity(), categoryList);

//        adapter.addSection("Account", new SiteAdapter(getActivity(), accountSites));
//        adapter.addSection("Campus", new SiteAdapter(getActivity(), campusSites));

        // ArrayAdapter<Site> contentsAdapter = );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
//            Intent intent = new Intent(getActivity(), SiteListView.class);

            Bundle bundle = new Bundle();
            bundle.putString("username", getArguments().getString("username", ""));
            bundle.putString("password", getArguments().getString("password", ""));
            bundle.putInt("position", position);

            Fragment fragment = new SiteListView();
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, "currentView")
                    .addToBackStack(null).commit();

        });

        return inflatedView;
    }
}
