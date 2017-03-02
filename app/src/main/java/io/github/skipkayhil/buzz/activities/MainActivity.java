package io.github.skipkayhil.buzz.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import io.github.skipkayhil.buzz.fragments.BusesView;
import io.github.skipkayhil.buzz.fragments.BuzzportView;
import io.github.skipkayhil.buzz.LoginDialog;
import io.github.skipkayhil.buzz.R;
import io.github.skipkayhil.buzz.fragments.SiteCategoryView;
import io.github.skipkayhil.buzz.fragments.TsquareView;

public class MainActivity extends AppCompatActivity {

    private enum ViewType {
        TSQUARE, BUSES, PLACES, SITES
    }

    private ViewType currentView = ViewType.TSQUARE;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private String username;
    private String password;

    public void refreshView() {

        //TODO use {@code AccountManager} instead of shared preferences
        /*
         * Grab the saved username and password if they exist
         */
        SharedPreferences storage = getSharedPreferences("LOGIN_INFO", 0);
        username = storage.getString("username", "");
        password = storage.getString("password", "");

        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);

        Fragment newView;
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
            default:
                newView = new BuzzportView();
        }
        newView.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, newView, "currentView").commit();

        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.drawerUsername))
                .setText(username);
    }

    public void showLoginDialog() {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);

        DialogFragment loginDiag = new LoginDialog();
        loginDiag.setArguments(bundle);
        loginDiag.show(getSupportFragmentManager(), "login");
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
                .setOnClickListener(v -> showLoginDialog());
        navigationView.setNavigationItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.navTsquare:
                    currentView = ViewType.TSQUARE;
                    break;
                case R.id.navSites:
                    currentView = ViewType.SITES;
                    break;
                case R.id.navBuses:
                    currentView = ViewType.BUSES;
                    break;
                case R.id.navPlaces:
                    currentView = ViewType.PLACES;
                    break;
            }
            refreshView();
            drawerLayout.closeDrawers();
            return true;
        });

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

    // Handles the touch event to open the Navigation bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container)
                instanceof SiteListView) {
            onBackPressed();
            getSupportFragmentManager().popBackStack();
            return true;
        } else if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items

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
//        Fragment fragment = new BuzzportView();
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
        if(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof SiteListView) {
            toolbar.setTitle("Sites");
            toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        }
        super.onBackPressed();
    }
}
