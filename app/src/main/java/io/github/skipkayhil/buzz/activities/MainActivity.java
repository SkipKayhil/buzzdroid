package io.github.skipkayhil.buzz.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import io.github.skipkayhil.buzz.LoginDialog;
import io.github.skipkayhil.buzz.R;
import io.github.skipkayhil.buzz.fragments.BusesView;
import io.github.skipkayhil.buzz.fragments.BuzzWebView;
import io.github.skipkayhil.buzz.fragments.OldTsquareView;
import io.github.skipkayhil.buzz.fragments.SiteCategoryView;
import io.github.skipkayhil.buzz.fragments.SiteListView;
import io.github.skipkayhil.buzz.fragments.TsquareView;
import io.github.skipkayhil.buzz.model.User;

public class MainActivity extends AppCompatActivity {

    private enum ViewType {
        TSQUARE, BUSES, PLACES, SITES
    }

    private ViewType currentView = ViewType.TSQUARE;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private User user = User.getInstance();

    public void refreshView() {

        //TODO use {@code AccountManager} instead of shared preferences
        /*
         * Grab the saved username and password if they exist
         */
        SharedPreferences storage = getSharedPreferences("LOGIN_INFO", 0);
        user.setUsername(storage.getString("username", ""));
        user.setPassword(storage.getString("password", ""));

        Fragment newView = new OldTsquareView();
        switch(currentView) {
            case TSQUARE:
                newView = new TsquareView();
                toolbar.setTitle("T-Square");
                break;
            case SITES:
                newView = new SiteCategoryView();
                toolbar.setTitle("Sites");
                break;
            case BUSES:
                newView = new BusesView();
                toolbar.setTitle("Buses");
                break;
            case PLACES:
                newView = new BusesView();
                toolbar.setTitle("Places");
                break;
        }
        //newView.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, newView, "currentView").commit();

        String navUsername = user.getUsername();
        if (navUsername.equals("")) {
            navUsername = "Account not saved";
        }
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.drawerUsername))
                .setText(navUsername);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UNCOMMENT TO RESET THE USERNAME/PASSWORD IN STORAGE
        // getSharedPreferences("LOGIN_INFO", 0).edit().putString("username", "").putString("password", "").apply();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        setSupportActionBar(toolbar);

        // listItems = getResources().getStringArray(R.array.drawerList);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navDrawer);

        refreshView();

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //toolbar.setTitle(title);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //toolbar.setTitle(title);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView.getHeaderView(0).findViewById(R.id.drawerEditLogin)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new LoginDialog().show(getSupportFragmentManager(), "login");
                    }
                });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                ViewType newView = ViewType.TSQUARE;
                switch(item.getItemId()) {
                case R.id.navTsquare:
                    newView = ViewType.TSQUARE;
                    break;
                case R.id.navSites:
                    newView = ViewType.SITES;
                    break;
                case R.id.navBuses:
                    newView = ViewType.BUSES;
                    break;
                case R.id.navPlaces:
                    newView = ViewType.PLACES;
                    break;
                }
                if (!newView.equals(currentView)) {
                    currentView = newView;
                    refreshView();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);

        // drawerList.setAdapter(new ArrayAdapter<String>(this,
        //        R.layout.drawer_list_item, listItems));
        // drawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    /* Called whenever we call invalidateOptionsMenu() */
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = drawerLayout.isDrawerOpen(navigationView);
//
//        /*
//            Use this to remove anything that the fragment needed in the Action bar
//         */
//        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
//        return super.onPrepareOptionsMenu(menu);
//   }

    // Handles the Action Bar's Left Icon press
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container)
                instanceof SiteListView
            || getSupportFragmentManager().findFragmentById(R.id.fragment_container)
                instanceof BuzzWebView) {
            onBackPressed();

            // I'm commenting this line because I'm pretty sure
            // onBackPressed() calls this already
            //
            // getSupportFragmentManager().popBackStack();
            return true;
        } else if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle any other action

        return super.onOptionsItemSelected(item);
    }

//    private class DrawerItemClickListener implements ListView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            selectItem(position);
//        }
//    }

    /** Swaps fragments in the main content view */
//    private void selectItem(int position) {
//        // Create a new fragment and specify the planet to show based on position
//        Fragment fragment = new BuzzWebView();
//        //Bundle args = new Bundle();
//        //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//        //fragment.setArguments(args);
//
//        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.main_content, fragment)
//                .commit();
//
//        // Highlight the selected item, update the title, and close the drawer
//        // drawerList.setItemChecked(position, true);
//        // setTitle(listItems[position]);
//        drawerLayout.closeDrawer(navigationView);
//    }

//    @Override
//    public void setTitle(CharSequence title) {
//        this.title = title;
//        getActionBar().setTitle(this.title);
//    }
    @Override
    public void onBackPressed() {
        Fragment currentFrag = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFrag instanceof OldTsquareView) {
            OldTsquareView view = (OldTsquareView) currentFrag;
            if (view.getWebView().canGoBack()) {
                view.getWebView().goBack();
            } else {
                super.onBackPressed();
            }
        } else if (currentFrag instanceof BuzzWebView) {
//            BuzzWebView view = (BuzzWebView) currentFrag;
//            if (view.getWebView().canGoBack()) {
//                view.getWebView().goBack();
//            } else {
                super.onBackPressed();
//            }
        } else {
            if (currentFrag instanceof SiteListView) {
                // If on a sub list of sites, set the toolbar back to Sites
                toolbar.setTitle("Sites");
                toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
            } else {
                toolbar.setTitle("T-Square");
                currentView = ViewType.TSQUARE;
                refreshView();
            }
            super.onBackPressed();
        }
    }
}
