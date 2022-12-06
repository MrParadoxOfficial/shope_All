package shope.three3pro.shope_All.screen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import shope.three3pro.shope_All.model.sharedPerferance;


import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import shope.three3pro.shope_All.R;

public class home_main extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    shope.three3pro.shope_All.screen.homeFragment homeFragment = new homeFragment();
    shope.three3pro.shope_All.screen.menuFragment menuFragment = new menuFragment();
    shope.three3pro.shope_All.screen.loveFragment loveFragment = new loveFragment();
    notifcationFragment notificationFragment = new notifcationFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_home_main);
        bottomNavigationView  = findViewById(R.id.menu_nav_bar);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.notification);


        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(8);


        if (!sharedPerferance.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Log_In.class));
        }


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.notification:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,notificationFragment).commit();
                        return true;
                    case R.id.love_from_menu_xml:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,loveFragment).commit();
                        return true;
                    case R.id.menu_from_xml_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,menuFragment).commit();
                        return true;
                }

                return false;
            }
        });
    }
}