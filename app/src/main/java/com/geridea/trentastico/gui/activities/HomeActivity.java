package com.geridea.trentastico.gui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.geridea.trentastico.R;
import com.geridea.trentastico.gui.fragments.CalendarFragment;
import com.geridea.trentastico.gui.fragments.IFragmentWithMenuItems;
import com.geridea.trentastico.gui.fragments.SettingsFragment;
import com.threerings.signals.Listener0;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)       Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view)      NavigationView navigationView;

    private IFragmentWithMenuItems currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //Setting calendar fragment as the first fragment
        switchToCalendarFragment();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fragment_calendar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide all action items
        hideAllMenuItems(menu);

        for (int id : currentFragment.getIdsOfMenuItemsToMakeVisible()) {
            MenuItem menuItem = menu.findItem(id);
            currentFragment.bindMenuItem(menuItem);
            menuItem.setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    private void hideAllMenuItems(Menu menu) {
        for(int i = 0; i<menu.size(); i++){
            menu.getItem(i).setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment instanceof CalendarFragment) {
                super.onBackPressed();
            } else {
                switchToCalendarFragment();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_timetables){
            switchToCalendarFragment();
        } else if(id == R.id.menu_settings) {
            SettingsFragment settingsFragment = new SettingsFragment();
            settingsFragment.onChoiceMade.connect(new Listener0() {
                @Override
                public void apply() {
                    //The user might have changed his/ser courses or not. However he/she will have
                    //to return to the calendar fragment
                    switchToCalendarFragment();
                }
            });
            setCurrentFragment(settingsFragment);
        } else if(id == R.id.menu_extra_times) {
            Toast.makeText(this, "EXTRA", Toast.LENGTH_SHORT).show();
        } else if(id == R.id.menu_about){
            Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchToCalendarFragment() {
        navigationView.setCheckedItem(R.id.menu_timetables);

        setCurrentFragment(new CalendarFragment());
    }


    private void setCurrentFragment(IFragmentWithMenuItems nextFragment) {
        currentFragment = nextFragment;

        invalidateOptionsMenu();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, nextFragment)
                .commit();
    }
}