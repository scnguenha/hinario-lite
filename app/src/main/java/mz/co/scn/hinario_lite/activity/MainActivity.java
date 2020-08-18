package mz.co.scn.hinario_lite.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import mz.co.scn.hinario_lite.R;
import mz.co.scn.hinario_lite.fragment.AboutFragment;
import mz.co.scn.hinario_lite.fragment.SongListFragment;
import mz.co.scn.hinario_lite.util.AppConstants;

/**
 * Created by Sidónio Goenha on 14/08/2020.
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private boolean doubleBackPressed = false;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getString(R.string.lblTv));

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();

        String book = sharedPreferences.getString("book", "tv_rg");
        switch (book) {
            case "tv_rg":
                fragmentManager.beginTransaction().replace(R.id.framLayout, new SongListFragment(AppConstants.TV_RONGA_BOOK)).commit();
                setTitle(getString(R.string.lblTv));
                navigationView.setCheckedItem(R.id.nav_tv_rg);
                break;
            case "tv_tg":
                fragmentManager.beginTransaction().replace(R.id.framLayout, new SongListFragment(AppConstants.TV_TSONGA_BOOK)).commit();
                setTitle(getString(R.string.lblTv));
                navigationView.setCheckedItem(R.id.nav_tv_tg);
                break;
            case "ct":
                fragmentManager.beginTransaction().replace(R.id.framLayout, new SongListFragment(AppConstants.CANTEMOS_BOOK)).commit();
                setTitle(getString(R.string.lblCantemos));
                navigationView.setCheckedItem(R.id.nav_cantemos);
                break;
        }
    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Sync the drawerToggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    /**
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Pass any configuration change to the drawerLayout toggls
        toggle.onConfigurationChanged(newConfig);
    }

    /**
     * @param item
     * @return boolean
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        uncheckAllMenuItems(navigationView);

        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.nav_tv_rg:
                fragmentManager.beginTransaction().replace(R.id.framLayout, new SongListFragment(AppConstants.TV_RONGA_BOOK)).commit();
                setTitle(getString(R.string.app_name));
                navigationView.setCheckedItem(R.id.nav_tv_rg);
                break;
            case R.id.nav_tv_tg:
                fragmentManager.beginTransaction().replace(R.id.framLayout, new SongListFragment(AppConstants.TV_TSONGA_BOOK)).commit();
                setTitle(getString(R.string.app_name));
                navigationView.setCheckedItem(R.id.nav_tv_tg);
                break;
            case R.id.nav_cantemos:
                fragmentManager.beginTransaction().replace(R.id.framLayout, new SongListFragment(AppConstants.CANTEMOS_BOOK)).commit();
                setTitle(getString(R.string.lblCantemos));
                navigationView.setCheckedItem(R.id.nav_cantemos);
                break;
            case R.id.nav_favorites:

                break;
            case R.id.nav_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.nav_about:
                fragmentManager.beginTransaction().replace(R.id.framLayout, new AboutFragment()).commit();
                setTitle(getString(R.string.lblAbout));
                navigationView.setCheckedItem(R.id.nav_about);
                break;

            default:

        }

        checkItemAndCloseDrawer(item, drawer);

        return true;
    }

    /**
     * This method is used to check selected item in navigation drawer and close de drawer.
     *
     * @param item
     * @param drawer
     */
    private void checkItemAndCloseDrawer(MenuItem item, DrawerLayout drawer) {
        item.setChecked(true);

        drawer.closeDrawers();
        drawer.closeDrawer(GravityCompat.START);
    }

    /**
     * Este metodo serve para desselecionar o item anterioremente selecionado.
     * This method is used to uncheck the checked item.
     *
     * @param navigationView - O navigation view.
     */
    private void uncheckAllMenuItems(NavigationView navigationView) {
        final Menu menu = navigationView.getMenu();

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);

            if (item.hasSubMenu()) {
                SubMenu subMenu = item.getSubMenu();

                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    subMenuItem.setChecked(false);
                }
            } else {
                item.setChecked(false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackPressed)
                super.onBackPressed();
            else {
                doubleBackPressed = true;
                Snackbar.make(findViewById(R.id.drawer_layout), getString(R.string.prompt_back_pressed), Snackbar.LENGTH_SHORT).show();
                new Handler().postDelayed(() -> {
                    doubleBackPressed = false;
                }, 2000);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}