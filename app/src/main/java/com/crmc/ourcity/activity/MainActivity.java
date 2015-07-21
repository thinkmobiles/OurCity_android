package com.crmc.ourcity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fragment.CatalogFragment;
import com.crmc.ourcity.fragment.CatalogFragment.ListItemAction;
import com.crmc.ourcity.fragment.CatalogItemFragment;
import com.crmc.ourcity.model.CatalogItemModel;
import com.crmc.ourcity.ticker.Ticker;
import com.crmc.ourcity.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseFragmentActivity implements ListItemAction {

    private Toolbar mToolbar;
    private Ticker mTicker;
    private final int FRAGMENT_CONTAINER = R.id.flContainer_MA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTicker = (Ticker) findViewById(R.id.ticker_MA);


        //insert List<string> with breaking news when it will be ready
        mTicker.setData(null);
        mTicker.startAnimation();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("OutCity");

//        if (getFragmentById(FRAGMENT_CONTAINER) == null) {
//            setTopFragment(WebViewWithDataFragment.newInstance());
//            getSupportActionBar().setTitle("MayorSpeech");
//        }
        if (getFragmentById(FRAGMENT_CONTAINER) == null) {
            setTopFragment(CatalogFragment.newInstance());
            //getSupportActionBar().setTitle("CatalogTest");
        }
    }

    private void setTopFragment(final Fragment fragment) {
        clearBackStack();
        replaceFragmentWithoutBackStack(FRAGMENT_CONTAINER, fragment);
    }

    @Override
    public void onItemAction(CatalogItemModel catalogItemModel) {
        switch (catalogItemModel.itemStatus) {
            case ITEM:
                addFragmentWithBackStack(FRAGMENT_CONTAINER, CatalogItemFragment.newInstance(catalogItemModel));
                break;
            case MAIL:
                try {
                    startActivity(Intent.createChooser(IntentUtils.getIntentMail("recipient@example.com"), getString
                            (R.string.send_mail_hint)));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, getString(R.string.app_no_mail_client), Toast.LENGTH_SHORT).show();
                }
                break;
            case LINK:
                //addFragmentWithBackStack(FRAGMENT_CONTAINER, WebView.newInstance(catalogItemModel.link));
                break;
            case FALSE:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
