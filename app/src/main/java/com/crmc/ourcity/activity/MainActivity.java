package com.crmc.ourcity.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
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
import com.crmc.ourcity.fragment.AppealItemFragment;
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
import com.crmc.ourcity.fragment.MessagesToResidentDetailFragment;
import com.crmc.ourcity.fragment.OldDAppealsFragment;
import com.crmc.ourcity.fragment.PhoneBookFragment;
import com.crmc.ourcity.fragment.PhonesFragment;
import com.crmc.ourcity.fragment.RSSEntryFragment;
import com.crmc.ourcity.fragment.RSSListFragment;
import com.crmc.ourcity.fragment.SendMailFragment;
import com.crmc.ourcity.fragment.SubMenuFragment;
import com.crmc.ourcity.fragment.TripsFragment;
import com.crmc.ourcity.fragment.VoteFragment;
import com.crmc.ourcity.fragment.VoteListFragment;
import com.crmc.ourcity.fragment.WebViewFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.MenuLoader;
import com.crmc.ourcity.notification.RegistrationIntentService;
import com.crmc.ourcity.rest.responce.appeals.ResultObject;
import com.crmc.ourcity.rest.responce.events.CityEntities;
import com.crmc.ourcity.rest.responce.events.Events;
import com.crmc.ourcity.rest.responce.events.MassageToResident;
import com.crmc.ourcity.rest.responce.events.Phones;
import com.crmc.ourcity.rest.responce.map.MapTrips;
import com.crmc.ourcity.rest.responce.menu.MenuFull;
import com.crmc.ourcity.rest.responce.menu.MenuModel;
import com.crmc.ourcity.rest.responce.ticker.TickerModel;
import com.crmc.ourcity.rest.responce.vote.VoteFull;
import com.crmc.ourcity.ticker.Ticker;
import com.crmc.ourcity.utils.EnumUtil;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.utils.IntentUtils;
import com.crmc.ourcity.utils.SPManager;
import com.crmc.ourcity.utils.rss.RssItem;
import com.crmc.ourcity.utils.updateApp.VersionManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseFragmentActivity implements OnItemActionListener, OnListItemActionListener,
        FragmentManager.OnBackStackChangedListener, LoaderManager.LoaderCallbacks {

    private Ticker mTicker;
    private ImageView mActionHome;
    private ImageView mActionBack;
    private ImageView mActionSettings;
    private TextView mTitle;
    private WebViewFragment webViewFragment;
    private final int FRAGMENT_CONTAINER = R.id.flContainer_MA;
    private ArrayList<TickerModel> tickers;
    private boolean isLoggedIn;
    private int cityNumber, residentId;
    private String lng;
    private Handler mHandler;
    private VersionManager versionManager;
    private Configuration config;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
//        String lang = "iw";
//        String country = "IL";
//        Locale locale = new Locale(lang, country);
//        Locale.setDefault(locale);
//
//        config = new Configuration(getBaseContext(). getResources().getConfiguration());
//        config.locale = locale;
//        getBaseContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        super.onCreate(_savedInstanceState);

        setContentView(R.layout.activity_main);

        config = new Configuration(getBaseContext().getResources().getConfiguration());
        Log.d("LOC", config.locale.getDisplayLanguage());

        mHandler = new Handler(this.getMainLooper());
        isLoggedIn = SPManager.getInstance(this).getIsLoggedStatus();
        if (isLoggedIn) {
            if (checkPlayServices()) {
                if (TextUtils.isEmpty(SPManager.getInstance(this).getPushToken())) {
                    Intent intent = new Intent(this, RegistrationIntentService.class);
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

        mTicker = (Ticker) findViewById(R.id.ticker_MA);
        tickers = getIntent().getParcelableArrayListExtra(Constants.BUNDLE_TICKERS_LIST);
        if (tickers != null) {
            mTicker.setData((List) tickers);
            mTicker.setOnTickerActionListener(this);
            mTicker.startAnimation();
        }

        cityNumber = getResources().getInteger(R.integer.city_id);
        if (Locale.getDefault().toString().equals("en_US")) {
            lng = "en";
        } else {
            lng = "he";
        }
        residentId = SPManager.getInstance(this).getResidentId();

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        bundle.putString(Constants.BUNDLE_CONSTANT_LANG, lng);
        bundle.putInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID, residentId);
        getSupportLoaderManager().initLoader(Constants.LOADER_MENU_ID, bundle, this);

        if (getFragmentById(FRAGMENT_CONTAINER) == null) {
            setTopFragment(MainMenuFragment.newInstance());
        }

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
//                    Fragment fragment = getSupportFragmentManager().findFragmentById(FRAGMENT_CONTAINER);
//                    boolean isFromMainActivity = fragment instanceof MainMenuFragment || fragment instanceof
//                            SubMenuFragment;
                    Intent intent = new Intent(this, DialogActivity.class);
                    //intent.putExtra(Constants.IS_FROM_MAIN_ACTIVITY, isFromMainActivity);
                    EnumUtil.serialize(DialogType.class, DialogType.SETTING).to(intent);

                    //startActivity(intent);
                    startActivityForResult(intent, 1);
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
                webViewFragment = WebViewFragment.newInstance(_menuModel.link, _menuModel.colorItem, _menuModel.title);
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, webViewFragment);
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
                                        .requestJson, _menuModel.requestRoute, _menuModel.title));
                        break;
                    case 2:
                        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, TripsFragment.newInstance(_menuModel.getLat
                                (), _menuModel.getLon(), _menuModel.colorItem, _menuModel.requestJson, _menuModel
                                .requestRoute, _menuModel.title));
                        break;
                }
                break;
            case Constants.ACTION_TYPE_ADD_APPEALS:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, OldDAppealsFragment.newInstance(_menuModel.colorItem,
                        _menuModel.requestJson, _menuModel.requestRoute, _menuModel.title));
                break;
            case Constants.ACTION_TYPE_VOTE:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, VoteListFragment.newInstance(_menuModel.colorItem,
                        _menuModel.requestJson, _menuModel.requestRoute, _menuModel.title));
                break;
            case Constants.ACTION_TYPE_CALL:
                try {
                    startActivityForResult(Intent.createChooser(IntentUtils.getIntentCall(_menuModel.phoneNumber),
                            getResources().getString(R.string.call_hint)), 55); //spike
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
                        _menuModel.requestJson, _menuModel.requestRoute, _menuModel.title));
                break;
            case Constants.ACTION_TYPE_RSS:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, RSSListFragment.newInstance(_menuModel.colorItem,
                        _menuModel.link, _menuModel.title));
                break;
            case Constants.ACTION_TYPE_MAP_TRIPS:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, TripsFragment.newInstance(_menuModel.getLat(),
                        _menuModel.getLon(), _menuModel.colorItem, _menuModel.requestJson, _menuModel.requestRoute,
                        _menuModel.title));
                break;
            case Constants.ACTION_TYPE_ENTITIES:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, CityEntitiesFragment.newInstance(_menuModel
                        .colorItem, _menuModel.requestJson, _menuModel.requestRoute, _menuModel.title));
                break;
            case Constants.ACTION_SEND_MAIL_FRAGMENT:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, SendMailFragment.newInstance(_menuModel.colorItem,
                        _menuModel.email, _menuModel.title));
                break;
            case Constants.ACTION_HOT_CALL:
                Intent intent = new Intent(this, DialogActivity.class);
                EnumUtil.serialize(DialogType.class, DialogType.HOT_CALLS).to(intent);
                startActivityForResult(intent, Constants.REQUEST_HOT_CALLS);
                break;
            case Constants.ACTION_TYPE_MESSAGE_TO_RESIDENT:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, MessageToResidentFragment.newInstance(_menuModel
                        .colorItem, _menuModel.requestJson, _menuModel.requestRoute, _menuModel.title));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if((data != null ? data.getIntExtra(Constants.BUNDLE_LOGOUT, 0) : 0) == Constants.LOGOUT_KEY)
            setTopFragment(MainMenuFragment.newInstance());
        }
    }

    @Override
    public void onAppealsItemAction(ResultObject resultObject) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, AppealItemFragment.newInstance(resultObject));
    }

    @Override
    public void onEventsItemAction(Events _events) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, EventsItemFragment.newInstance(_events));
    }

    @Override
    public void onRSSItemAction(RssItem _entry) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER,
                RSSEntryFragment.newInstance(_entry.getTitle(),
                                             _entry.getDescription(),
                                             _entry.getLink(),
                                             _entry.getPubDate()));
    }

    @Override
    public void onTickerAction(View _view, String _link, String _title) {
        if (!TextUtils.isEmpty(_link)) {
            webViewFragment = WebViewFragment.newInstance(_link, Image.getStringColor
                    (), _title);
            replaceFragmentWithBackStack(FRAGMENT_CONTAINER, webViewFragment);
        }
    }

    @Override
    public void onActionMail(String _mail) {
        startActivity(Intent.createChooser(IntentUtils.getIntentMail(_mail),
                getResources().getString(R.string.send_mail_hint)));
    }

    @Override
    public void onActionCall(String _number) {
        startActivityForResult(Intent.createChooser(IntentUtils.getIntentCall(_number), getResources().getString(R
                .string.call_hint)), 56);
    }

    @Override
    public void onVoteAction(VoteFull _voteFull) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, VoteFragment.newInstance(_voteFull));
    }

    @Override
    public void onMessageItenAction(MassageToResident _mtr) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, MessagesToResidentDetailFragment.newInstance(_mtr));
    }

    @Override
    public void onCityEntitiesItemAction(CityEntities _cityEntities) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, CityEntitiesItemFragment.newInstance(_cityEntities));
    }

    @Override
    public void onTripsItemAction(MapTrips _trips, Double _lat, Double _lon) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, MapTripsFragment.newInstance(_trips, _lat, _lon, _trips
                .tripName));
    }

    @Override
    public void onEventsClickLinkAction(String _link, String _title) {
        if (!TextUtils.isEmpty(_link)) {
            webViewFragment = WebViewFragment.newInstance(_link, Image.getStringColor
                    (), _title);
            replaceFragmentWithBackStack(FRAGMENT_CONTAINER, webViewFragment);
        }
    }

    @Override
    public void onPhoneBookItemAction(List<Phones> _phones) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, PhonesFragment.newInstance(_phones));
    }

    @Override
    public void onMenuModelPrepared(List<MenuModel> _menuModel) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, SubMenuFragment.newInstance(_menuModel, Constants
                .PREVIOUSTITLE));
    }

    @Override
    public void onMessageToResidentDetailTransition(String _message, String _link) {
        replaceFragmentWithBackStack(FRAGMENT_CONTAINER, MessagesToResidentDetailFragment.newInstance(_message, _link));
    }


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, Constants.PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.device_not_supported), Toast.LENGTH_SHORT)
                        .show();
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
        checkForUpdates();
    }

    private void checkForUpdates() {
        versionManager = new VersionManager(this);
        versionManager.setVersionContentUrl(getString(R.string.update_url));
        versionManager.setTitle(getString(R.string.alertdialog_update_available));
        versionManager.setUpdateNowLabel(getString(R.string.alertdialog_update_now));
        versionManager.setRemindMeLaterLabel(getString(R.string.alertdialog_remind_me_later));
        versionManager.setReminderTimer(Integer.valueOf(getString(R.string.update_timer)));
        versionManager.checkVersion();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        goToDetailResidentMessage(intent);
    }

    @Override
    public void onBackPressed() {
//        if (webViewFragment != null && this.webViewFragment.canGoBack()) {
//            this.webViewFragment.goBack();
//
//        } else {
        super.onBackPressed();
        hideKeyboard(this);
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            setTopFragment(MainMenuFragment.newInstance());
        }
    }


    private void hideKeyboard(Context _context) {
        InputMethodManager inputManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        View v = ((Activity) _context).getCurrentFocus();
        if (v == null) return;
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void setTitle() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.flContainer_MA);
        if (!TextUtils.isEmpty(Constants.PREVIOUSTITLE) && !(f instanceof MainMenuFragment)) {
            mTitle.setText(Constants.PREVIOUSTITLE);
        } else if (f instanceof MainMenuFragment) {
            mTitle.setText("");
        }
    }

    //--> loading menu nodes
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new MenuLoader(this, args);
    }

    @Override
    public void onLoadFinished(Loader loader, Object _data) {
        mHandler.postAtFrontOfQueue(() -> {
            Constants.mMenuFull = (MenuFull) _data;
            Intent intent = getIntent();
            if (!TextUtils.isEmpty(intent.getExtras().getString(Constants.BUNDLE_CONSTANT_PUSH_MESSAGE))) {
                goToDetailResidentMessage(intent);
            } else {
                if (getFragmentById(FRAGMENT_CONTAINER) == null) {
                    setTopFragment(MainMenuFragment.newInstance());
                }
            }
        });
    }

    private void goToDetailResidentMessage(Intent intent) {
        for (int i = 0; i < Constants.mMenuFull.getNodes().size(); i++) {
            if (Constants.mMenuFull.getNodes().get(i).actionType == Constants.ACTION_TYPE_MESSAGE_TO_RESIDENT) {
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, MessageToResidentFragment.newInstance(
                        Constants.mMenuFull.getNodes().get(i).colorItem,
                        Constants.mMenuFull.getNodes().get(i).requestJson,
                        Constants.mMenuFull.getNodes().get(i).requestRoute,
                        Constants.mMenuFull.getNodes().get(i).title, true,
                        intent.getStringExtra(Constants.BUNDLE_CONSTANT_PUSH_MESSAGE),
                        intent.getStringExtra(Constants.BUNDLE_CONSTANT_PUSH_LINK)));

                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}