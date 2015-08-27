package com.crmc.ourcity.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.crmc.ourcity.fragment.MapClearFragment;
import com.crmc.ourcity.fragment.MapInterestPointFragment;
import com.crmc.ourcity.fragment.MapTripsFragment;
import com.crmc.ourcity.fragment.PhoneBookFragment;
import com.crmc.ourcity.fragment.PhonesFragment;
import com.crmc.ourcity.fragment.SubMenuFragment;
import com.crmc.ourcity.fragment.TripsFragment;
import com.crmc.ourcity.fragment.VoteFragment;
import com.crmc.ourcity.fragment.WebViewFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.notification.RegistrationIntentService;
import com.crmc.ourcity.rest.responce.events.Events;
import com.crmc.ourcity.rest.responce.events.Phones;
import com.crmc.ourcity.rest.responce.map.MapTrips;
import com.crmc.ourcity.rest.responce.menu.MenuModel;
import com.crmc.ourcity.ticker.Ticker;
import com.crmc.ourcity.utils.EnumUtil;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.utils.IntentUtils;
import com.crmc.ourcity.utils.SPManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.text.Bidi;
import java.util.List;

public class MainActivity extends BaseFragmentActivity implements OnItemActionListener, OnListItemActionListener {

    private Toolbar mToolbar;
    private Ticker mTicker;
    private final int FRAGMENT_CONTAINER = R.id.flContainer_MA;
    private boolean isLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkPlayServices()) {
            if (!TextUtils.isEmpty(SPManager.getInstance(this).getAuthToken())) {
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }

        isLogIn = SPManager.getInstance(this).getIsLoggedStatus();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTicker = (Ticker) findViewById(R.id.ticker_MA);

        //insert List<string> with breaking news when it will be ready
        mTicker.setData(null);
        mTicker.startAnimation();

        setSupportActionBar(mToolbar);
        //getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //getSupportActionBar().setTitle("OurCity");

//            Configuration config = getResources().getConfiguration();
//            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
//                mToolbar.setNavigationIcon(R.drawable.ic_back_rtl);
//            } else {
//                mToolbar.setNavigationIcon(R.drawable.ic_back_ltr);
//            }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        if (getFragmentById(FRAGMENT_CONTAINER) == null) {
            setTopFragment(MainMenuFragment.newInstance());
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
                    case Constants.ACTION_TYPE_LIST_PHONE_LIST:
                        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, PhonesFragment.newInstance(_menuModel
                                .colorItem, _menuModel.requestJson, _menuModel.requestRoute, Constants.PHONE_LIST));
                        break;
                }
                break;
            case Constants.ACTION_TYPE_LINK:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, WebViewFragment.newInstance(_menuModel.link,
                        _menuModel.colorItem));
                break;
            case Constants.ACTION_TYPE_DOCUMENT:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, WebViewFragment.newInstance(_menuModel.colorItem,
                        _menuModel.requestJson, _menuModel.requestRoute));
                break;
            case Constants.ACTION_TYPE_MAP:
                switch (_menuModel.showOnMap) {
                    case 0:
                        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, MapClearFragment.newInstance(_menuModel
                                .getLat(), _menuModel.getLon()));
                        break;
                    case 1:
                        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, MapInterestPointFragment.newInstance
                                (_menuModel.getLat(), _menuModel.getLon(), _menuModel.colorItem, _menuModel
                                        .requestJson, _menuModel.requestRoute));
                        break;
                    case 2:
                        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, TripsFragment.newInstance(_menuModel.getLat
                                (), _menuModel.getLon(), _menuModel.colorItem, _menuModel.requestJson, _menuModel
                                .requestRoute));
                        break;
                }
                break;
            case Constants.ACTION_TYPE_ADD_APPEALS:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, AppealsFragment.newInstance(_menuModel.colorItem,
                        _menuModel.requestJson, _menuModel.requestRoute));
                break;
            case Constants.ACTION_TYPE_VOTE:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, VoteFragment.newInstance(_menuModel.colorItem,
                        _menuModel.requestJson, _menuModel.requestRoute));
                break;
            case Constants.ACTION_TYPE_CALL:
                try {
                    startActivity(Intent.createChooser(IntentUtils.getIntentCall(_menuModel.phoneNumber),
                            getResources().getString(R.string.call_hint)));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, getResources().getString(R.string.app_no_call_client), Toast.LENGTH_SHORT)
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
            case Constants.ACTION_TYPE_PHONE_BOOK:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, PhoneBookFragment.newInstance(_menuModel.colorItem,
                        _menuModel.requestJson, _menuModel.requestRoute));
                break;
        }
    }

    @Override
    public void onEventsItemAction(Events _events) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, EventsItemFragment.newInstance(_events));
    }

    @Override
    public void onTripsItemAction(MapTrips _trips, Double _lat, Double _lon) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, MapTripsFragment.newInstance(_trips, _lat, _lon));
    }

    @Override
    public void onEventsClickLinkAction(String _link) {
        if (!TextUtils.isEmpty(_link)) {
            replaceFragmentWithBackStack(FRAGMENT_CONTAINER, WebViewFragment.newInstance(_link, Image.getStringColor
                    ()));
        }
    }

    @Override
    public void onPhoneBookItemAction(List<Phones> _phones) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, PhonesFragment.newInstance(_phones, Constants.PHONE_BOOK_LIST));
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
//        int id = item.getItemId();
//
//        if (id == R.id.menu_settings) {
//            Fragment fragment = getSupportFragmentManager().findFragmentById(FRAGMENT_CONTAINER);
//            boolean isFromMainActivity = fragment instanceof MainMenuFragment || fragment instanceof SubMenuFragment;
//
//            Intent intent = new Intent(this, DialogActivity.class);
//            intent.putExtra(Constants.IS_FROM_MAIN_ACTIVITY, isFromMainActivity);
//            EnumUtil.serialize(DialogType.class, DialogType.SETTING).to(intent);
//            startActivity(intent);
//            return true;
//        }
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Fragment fragment = getSupportFragmentManager().findFragmentById(FRAGMENT_CONTAINER);
                boolean isFromMainActivity = fragment instanceof MainMenuFragment || fragment instanceof SubMenuFragment;
                Intent intent = new Intent(this, DialogActivity.class);
                intent.putExtra(Constants.IS_FROM_MAIN_ACTIVITY, isFromMainActivity);
                EnumUtil.serialize(DialogType.class, DialogType.SETTING).to(intent);
                startActivity(intent);
            break;
            case android.R.id.home:
                popBackStack();
                break;
            case R.id.menu_home:
                clearBackStack();
                setTopFragment(MainMenuFragment.newInstance());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, Constants.PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                //Device not supported.
                finish();
            }
            return false;
        }
        return true;
    }
}
