package com.crmc.ourcity.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fragment.CatalogTestFragment;

public class MainActivity extends BaseFragmentActivity{

    private Toolbar mToolbar;
    private final int FRAGMENT_CONTAINER = R.id.flContainer_MA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("OutCity");

//        if (getFragmentById(FRAGMENT_CONTAINER) == null) {
//            setTopFragment(MayorSpeechFragment.newInstance());
//            getSupportActionBar().setTitle("MayorSpeech");
//        }
        if (getFragmentById(FRAGMENT_CONTAINER) == null) {
            setTopFragment(CatalogTestFragment.newInstance());
            getSupportActionBar().setTitle("CatalogTest");
        }
    }


    private void setTopFragment(final Fragment fragment) {
        clearBackStack();
        replaceFragmentWithoutBackStack(FRAGMENT_CONTAINER, fragment);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
