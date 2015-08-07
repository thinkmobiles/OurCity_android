package com.crmc.ourcity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.dialog.DialogActivity;
import com.crmc.ourcity.dialog.DialogType;
import com.crmc.ourcity.fragment.EventsItemFragment;
import com.crmc.ourcity.fragment.OnItemActionListener;
import com.crmc.ourcity.fragment.SubMenuFragment;
import com.crmc.ourcity.fragment.MainMenuFragment;
import com.crmc.ourcity.model.EventsItemModel;
import com.crmc.ourcity.rest.responce.menu.MenuModel;
import com.crmc.ourcity.ticker.Ticker;
import com.crmc.ourcity.utils.EnumUtil;
import com.crmc.ourcity.utils.IntentUtils;

import java.util.List;

public class MainActivity extends BaseFragmentActivity implements OnItemActionListener {

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
//            setTopFragment(TestApiFragment.newInstance());
//            //getSupportActionBar().setTitle("MayorSpeech");
//        }

//        if (getFragmentById(FRAGMENT_CONTAINER) == null) {
//            setTopFragment(MapsFragment.newInstance());
//            //getSupportActionBar().setTitle("MayorSpeech");
//        }
        if (getFragmentById(FRAGMENT_CONTAINER) == null) {
            setTopFragment(MainMenuFragment.newInstance());
            //getSupportActionBar().setTitle("CatalogTest");
        }
    }

    private void setTopFragment(final Fragment fragment) {
        clearBackStack();
        replaceFragmentWithoutBackStack(FRAGMENT_CONTAINER, fragment);
    }

    @Override
    public void onItemAction(EventsItemModel _eventsItemModel) {
        switch (_eventsItemModel.actionType) {
            case LIST:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, EventsItemFragment.newInstance(_eventsItemModel));
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
    public void onMenuModelPrepared(List<MenuModel> _menuModel) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, SubMenuFragment.newInstance(_menuModel));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_settings) {
            Intent intent = new Intent(this, DialogActivity.class);
            EnumUtil.serialize(DialogType.class, DialogType.SETTING).to(intent);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
