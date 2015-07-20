package com.crmc.ourcity.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fragment.CatalogTestFragment;
import com.crmc.ourcity.fragment.MapsFragment;

public class MainActivity extends BaseFragmentActivity{

    private Toolbar mToolbar;
    private final int FRAGMENT_CONTAINER = R.id.flContainer_MA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
        getSupportActionBar().setTitle(null);

        if (getFragmentById(FRAGMENT_CONTAINER) == null) {
            setTopFragment(MapsFragment.newInstance());
        }
    }


    private void setTopFragment(final Fragment fragment) {
        clearBackStack();
        replaceFragmentWithoutBackStack(FRAGMENT_CONTAINER, fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public final void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

}
