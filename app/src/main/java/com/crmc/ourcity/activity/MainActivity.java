package com.crmc.ourcity.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.OnItemActionListener;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.dialog.DialogActivity;
import com.crmc.ourcity.dialog.DialogType;
import com.crmc.ourcity.fragment.AppealsFragment;
import com.crmc.ourcity.fragment.EventsFragment;
import com.crmc.ourcity.fragment.EventsItemFragment;
import com.crmc.ourcity.fragment.MainMenuFragment;
import com.crmc.ourcity.fragment.MapsFragment;
import com.crmc.ourcity.fragment.PhonesFragment;
import com.crmc.ourcity.fragment.SubMenuFragment;
import com.crmc.ourcity.fragment.VoteFragment;
import com.crmc.ourcity.fragment.WebViewFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.responce.events.Events;
import com.crmc.ourcity.rest.responce.menu.MenuModel;
import com.crmc.ourcity.ticker.Ticker;
import com.crmc.ourcity.utils.EnumUtil;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.utils.IntentUtils;

import java.util.List;

public class MainActivity extends BaseFragmentActivity implements OnItemActionListener, OnListItemActionListener {

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
//            setTopFragment(VoteFragment.newInstance());
//            //getSupportActionBar().setTitle("MayorSpeech");
//        }
//
//        if (getFragmentById(FRAGMENT_CONTAINER) == null) {
//            setTopFragment(MapsFragment.newInstance());
//            //getSupportActionBar().setTitle("MayorSpeech");
//        }
//        if (getFragmentById(FRAGMENT_CONTAINER) == null) {
//            setTopFragment(AppealsFragment.newInstance());
//            //getSupportActionBar().setTitle("CatalogTest");
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
    public void onItemAction(MenuModel _menuModel) {
        switch (_menuModel.actionType) {
            case Constants.ACTION_TYPE_LIST:
                switch (_menuModel.listType) {
                    case Constants.ACTION_TYPE_LIST_EVENTS:
                        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, EventsFragment.newInstance(_menuModel
                                .colorItem, _menuModel.requestJson, _menuModel.requestRoute));
                        break;
                    case Constants.ACTION_TYPE_LIST_MESSAGE_TO_RESIDENT:
                        break;
                    case Constants.ACTION_TYPE_LIST_PHONE_LiST:
                        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, PhonesFragment.newInstance());
                        break;
                }
                break;
            case Constants.ACTION_TYPE_LINK:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, WebViewFragment.newInstance(_menuModel.link, Color
                        .parseColor(_menuModel.colorItem)));
                break;
            case Constants.ACTION_TYPE_MAP:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, MapsFragment.newInstance(_menuModel.getLat(),
                        _menuModel.getLon(), _menuModel.colorItem, _menuModel.requestJson, _menuModel.requestRoute));
                break;
            case Constants.ACTION_TYPE_ADD_APPEALS:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, AppealsFragment.newInstance(_menuModel.colorItem,
                        _menuModel.requestJson, _menuModel.requestRoute));
                break;
            case Constants.ACTION_TYPE_VOTE:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, VoteFragment.newInstance(_menuModel.colorItem,
                        _menuModel.requestJson, _menuModel.requestRoute));
                break;
            case Constants.ACTION_TYPE_CALL_SKYPE:
                try {
                    startActivity(Intent.createChooser(IntentUtils.getIntentSkype(_menuModel.phoneNumber),
                            getResources().getString(R.string.call_skype_hint)));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, getResources().getString(R.string.app_no_skype_client), Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case Constants.ACTION_TYPE_MAIL:
                try {
                    startActivity(Intent.createChooser(IntentUtils.getIntentMail(_menuModel.email), getString(R
                            .string.send_mail_hint)));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, getString(R.string.app_no_mail_client), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onEventsItemAction(Events _events) {
        if (!TextUtils.isEmpty(_events.link)) {
            replaceFragmentWithBackStack(FRAGMENT_CONTAINER, WebViewFragment.newInstance(_events.link, Image
                    .darkenColor(0.0)));

        } else if (!TextUtils.isEmpty(_events.email)) {
            try {
                startActivity(Intent.createChooser(IntentUtils.getIntentMail(_events.email), getString(R.string
                        .send_mail_hint)));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, getString(R.string.app_no_mail_client), Toast.LENGTH_SHORT).show();
            }
        } else if (!TextUtils.isEmpty(_events.phone)) {
            try {
                startActivity(Intent.createChooser(IntentUtils.getIntentSkype(_events.phone), getResources()
                        .getString(R.string.call_skype_hint)));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, getResources().getString(R.string.app_no_skype_client), Toast.LENGTH_SHORT).show();
            }
        } else {
            replaceFragmentWithBackStack(FRAGMENT_CONTAINER, EventsItemFragment.newInstance());
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
