package com.crmc.ourcity.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.OnItemActionListener;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.dialog.DialogActivity;
import com.crmc.ourcity.dialog.DialogType;
import com.crmc.ourcity.fragment.AppealsFragment;
import com.crmc.ourcity.fragment.AppealsListFragment;
import com.crmc.ourcity.fragment.CityEntitiesFragment;
import com.crmc.ourcity.fragment.CityEntitiesItemFragment;
import com.crmc.ourcity.fragment.EventsFragment;
import com.crmc.ourcity.fragment.EventsItemFragment;
import com.crmc.ourcity.fragment.LinkListFragment;
import com.crmc.ourcity.fragment.MainMenuFragment;
import com.crmc.ourcity.fragment.MapClearFragment;
import com.crmc.ourcity.fragment.MapInterestPointFragment;
import com.crmc.ourcity.fragment.MapTripsFragment;
import com.crmc.ourcity.fragment.MessageToResidentFragment;
import com.crmc.ourcity.fragment.PhoneBookFragment;
import com.crmc.ourcity.fragment.PhonesFragment;
import com.crmc.ourcity.fragment.RSSEntryFragment;
import com.crmc.ourcity.fragment.RSSListFragment;
import com.crmc.ourcity.fragment.SendMailFragment;
import com.crmc.ourcity.fragment.SubMenuFragment;
import com.crmc.ourcity.fragment.TripsFragment;
import com.crmc.ourcity.fragment.VoteFragment;
import com.crmc.ourcity.fragment.WebViewFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.model.rss.RSSEntry;
import com.crmc.ourcity.notification.GCMListenerService;
import com.crmc.ourcity.notification.RegistrationIntentService;
import com.crmc.ourcity.rest.responce.events.CityEntities;
import com.crmc.ourcity.rest.responce.events.Events;
import com.crmc.ourcity.rest.responce.events.Phones;
import com.crmc.ourcity.rest.responce.map.MapTrips;
import com.crmc.ourcity.rest.responce.menu.MenuModel;
import com.crmc.ourcity.rest.responce.ticker.TickerModel;
import com.crmc.ourcity.ticker.Ticker;
import com.crmc.ourcity.utils.EnumUtil;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.utils.IntentUtils;
import com.crmc.ourcity.utils.SPManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseFragmentActivity implements OnItemActionListener,
        OnListItemActionListener, FragmentManager.OnBackStackChangedListener {

   // private Toolbar mToolbar;
    private Ticker mTicker;
    private ImageView mActionHome;
    private ImageView mActionBack;
    private ImageView mActionSettings;
    private TextView mTitle;
    private final int FRAGMENT_CONTAINER = R.id.flContainer_MA;
    private ArrayList<TickerModel> tickers;
    private boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main);
        isLoggedIn = SPManager.getInstance(this).getIsLoggedStatus();
        if (isLoggedIn) {
            if (checkPlayServices()) {
                if (TextUtils.isEmpty(SPManager.getInstance(this).getPushToken())) {
                    Intent intent = new Intent(this, RegistrationIntentService.class);
                    startService(intent);
                } else {
                    Intent intent = new Intent(this, GCMListenerService.class);
                    startService(intent);
                }
            }
        }

        mActionHome = findView(R.id.action_home);
        mActionSettings = findView(R.id.action_settings);
        mTitle = findView(R.id.action_title);
        mActionBack = findView(R.id.action_back);

        mActionSettings.setOnClickListener(handleClicks());
        mActionHome.setOnClickListener(handleClicks());
        mActionBack.setOnClickListener(handleClicks());





//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTicker = (Ticker) findViewById(R.id.ticker_MA);
        tickers = getIntent().getParcelableArrayListExtra(Constants.BUNDLE_TICKERS_LIST);
        if (tickers != null) {
            mTicker.setData((List) tickers);
            mTicker.setOnTickerActionListener(this);
            mTicker.startAnimation();
        }

      //  setSupportActionBar(mToolbar);


        if (getFragmentById(FRAGMENT_CONTAINER) == null) {
            setTopFragment(MainMenuFragment.newInstance());
        }
       // getDelegate().getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_right);

        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @NonNull
    private View.OnClickListener handleClicks() {
        return v -> {
            switch (v.getId()) {
                case R.id.action_home:
                    clearBackStack();
                    setTopFragment(MainMenuFragment.newInstance());
                    break;
                case R.id.action_settings:
                    Fragment fragment = getSupportFragmentManager().findFragmentById(FRAGMENT_CONTAINER);
                    boolean isFromMainActivity = fragment instanceof MainMenuFragment || fragment instanceof
                            SubMenuFragment;
                    Intent intent = new Intent(this, DialogActivity.class);
                    intent.putExtra(Constants.IS_FROM_MAIN_ACTIVITY, isFromMainActivity);
                    EnumUtil.serialize(DialogType.class, DialogType.SETTING).to(intent);
                    startActivity(intent);
                    break;
                case R.id.action_back:
                    setTitle();
                    popBackStack();
                    break;
            }
        };
    }

    private void setTopFragment(final Fragment _fragment) {
        clearBackStack();
        replaceFragmentWithoutBackStack(FRAGMENT_CONTAINER, _fragment);
    }

    @Override
    public void onItemAction(MenuModel _menuModel) {
//        getDelegate().getSupportActionBar().setTitle(_menuModel.title);
        mTitle.setText(_menuModel.title);
        switch (_menuModel.actionType) {
            case Constants.ACTION_TYPE_LIST:
                switch (_menuModel.listType) {
                    case Constants.ACTION_TYPE_LIST_EVENTS:
                        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, EventsFragment.newInstance(_menuModel
                                .colorItem, _menuModel.requestJson, _menuModel.requestRoute, _menuModel.title));
                        break;
                    case Constants.ACTION_TYPE_LIST_APPEALS:
                        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, AppealsListFragment.newInstance(_menuModel
                                .colorItem, _menuModel.requestJson, _menuModel.requestRoute, _menuModel.title));
                        break;
                    case Constants.ACTION_TYPE_LIST_LINK:
                        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, LinkListFragment.newInstance(_menuModel
                                .colorItem, _menuModel.requestJson, _menuModel.requestRoute, _menuModel.title));
                        break;
                }
                break;
            case Constants.ACTION_TYPE_LINK:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, WebViewFragment.newInstance(_menuModel.link,
                        _menuModel.colorItem, _menuModel.title));
                break;
            case Constants.ACTION_TYPE_DOCUMENT:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, WebViewFragment.newInstance(_menuModel.colorItem,
                        _menuModel.requestJson, _menuModel.requestRoute, _menuModel.title));
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
                                .requestRoute, _menuModel.title));
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
            case Constants.ACTION_TYPE_RSS:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, RSSListFragment.newInstance(_menuModel.colorItem,
                        _menuModel.link));
                break;
            case Constants.ACTION_TYPE_MAP_TRIPS:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, TripsFragment.newInstance(_menuModel.getLat(),
                        _menuModel.getLon(), _menuModel.colorItem, _menuModel.requestJson, _menuModel.requestRoute, _menuModel.title));
                break;
            case Constants.ACTION_TYPE_ENTITIES:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, CityEntitiesFragment.newInstance(_menuModel
                        .colorItem, _menuModel.requestJson, _menuModel.requestRoute, _menuModel.title));
                break;
            case Constants.ACTION_SEND_MAIL_FRAGMENT:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, SendMailFragment.newInstance(_menuModel.colorItem,
                        _menuModel.email));
                break;
            case Constants.ACTION_HOT_CALL:
                Intent intent = new Intent(this, DialogActivity.class);
                EnumUtil.serialize(DialogType.class, DialogType.HOT_CALLS).to(intent);
                startActivity(intent);
                break;
            case Constants.ACTION_TYPE_MESSAGE_TO_RESIDENT:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, MessageToResidentFragment.newInstance(_menuModel
                        .colorItem, _menuModel.requestJson, _menuModel.requestRoute, _menuModel.title));
                break;
        }
    }

    @Override
    public void onEventsItemAction(Events _events) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, EventsItemFragment.newInstance(_events));
    }

    @Override
    public void onRSSItemAction(RSSEntry _entry) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, RSSEntryFragment.newInstance(_entry));
    }

    @Override
    public void onTickerAction(View _view, String _link, String _title) {
        if (!TextUtils.isEmpty(_link)) {
            replaceFragmentWithBackStack(FRAGMENT_CONTAINER, WebViewFragment.newInstance(_link, Image.getStringColor
                    (), _title));
        }
    }

    @Override
    public void onCityEntitiesItemAction(CityEntities _cityEntities) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, CityEntitiesItemFragment.newInstance(_cityEntities));
    }

    @Override
    public void onTripsItemAction(MapTrips _trips, Double _lat, Double _lon) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, MapTripsFragment.newInstance(_trips, _lat, _lon, _trips.tripName));
    }

    @Override
    public void onEventsClickLinkAction(String _link, String _title) {
        if (!TextUtils.isEmpty(_link)) {
            replaceFragmentWithBackStack(FRAGMENT_CONTAINER, WebViewFragment.newInstance(_link, Image.getStringColor
                    (), _title));
        }
    }

    @Override
    public void onPhoneBookItemAction(List<Phones> _phones) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, PhonesFragment.newInstance(_phones));
    }

    @Override
    public void onMenuModelPrepared(List<MenuModel> _menuModel) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, SubMenuFragment.newInstance(_menuModel, Constants.PREVIOUSTITLE));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu _menu) {
//        getMenuInflater().inflate(R.menu.menu_main, _menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem _item) {
//        switch (_item.getItemId()) {
//            case R.id.menu_settings:
//                Fragment fragment = getSupportFragmentManager().findFragmentById(FRAGMENT_CONTAINER);
//                boolean isFromMainActivity = fragment instanceof MainMenuFragment || fragment instanceof
//                        SubMenuFragment;
//                Intent intent = new Intent(this, DialogActivity.class);
//                intent.putExtra(Constants.IS_FROM_MAIN_ACTIVITY, isFromMainActivity);
//                EnumUtil.serialize(DialogType.class, DialogType.SETTING).to(intent);
//                startActivity(intent);
//                break;
//            case android.R.id.home:
//                setTitle();
//                popBackStack();
//                break;
//            case R.id.menu_home:
//                clearBackStack();
//                setTopFragment(MainMenuFragment.newInstance());
//                break;
//        }
//        return super.onOptionsItemSelected(_item);
//    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, Constants.PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.device_not_supported), Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onBackStackChanged() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.flContainer_MA);

        if (f instanceof MainMenuFragment || f instanceof SubMenuFragment) {
            findViewById(R.id.ticker_container_MA).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.ticker_container_MA).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard(this);
    }

    private void hideKeyboard(Context _context) {
        InputMethodManager inputManager = (InputMethodManager) _context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        View v = ((Activity) _context).getCurrentFocus();
        if (v == null)
            return;
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void setTitle() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.flContainer_MA);
        if (!TextUtils.isEmpty(Constants.PREVIOUSTITLE) && !(f instanceof MainMenuFragment)) {
 //           getDelegate().getSupportActionBar().setTitle(Constants.PREVIOUSTITLE);
            mTitle.setText(Constants.PREVIOUSTITLE);
        } else if (f instanceof MainMenuFragment) {
//           getDelegate().getSupportActionBar().setTitle("");
            mTitle.setText("");
        }
    }
}
