package com.cab404.chumroll.example;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Simple showcase of ChumrollAdapter
 *
 * @author cab404
 */
public class TestActivity extends AppCompatActivity {

    ChumrollTestFragment currentPage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("ListView"));
        tabs.addTab(tabs.newTab().setText("PagerView"));
        tabs.addTab(tabs.newTab().setText("RecyclerView"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setFragment(new ListTestFragment());
                        break;
                    case 1:
                        setFragment(new PagerTestFragment());
                        break;
                    case 2:
                        setFragment(new RecyclerTestFragment());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        setFragment(new ListTestFragment());

        ((BottomNavigationView) findViewById(R.id.adder)).setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (currentPage != null)
                            currentPage.addItem(item.getItemId());
                        return true;
                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    public void openGithub(MenuItem item) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/cab404/Chumroll")));
    }


    public void setFragment(ChumrollTestFragment fragment) {
        currentPage = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }
}
