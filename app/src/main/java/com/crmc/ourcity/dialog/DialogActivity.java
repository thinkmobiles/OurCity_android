package com.crmc.ourcity.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.crmc.ourcity.R;
import com.crmc.ourcity.activity.BaseFragmentActivity;
import com.crmc.ourcity.callback.CallBackWithData;
import com.crmc.ourcity.callback.OnActionDialogListener;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.model.MapMarker;
import com.crmc.ourcity.rest.responce.vote.VoteFull;
import com.crmc.ourcity.utils.EnumUtil;

import java.util.ArrayList;
import java.util.List;

public class DialogActivity extends BaseFragmentActivity implements OnActionDialogListener, CallBackWithData {

    private Intent intent = new Intent();
    private final int FRAGMENT_CONTAINER = R.id.fragment_dialog_container;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_dialog);

        DialogType type = EnumUtil.deserialize(DialogType.class).from(getIntent());
        switch (type) {

            case VOTE_CHOICE:
                ArrayList<VoteFull> mVoteFull = getIntent().getParcelableArrayListExtra(Constants.BUNDLE_INTEGER);
                replaceFragmentWithoutBackStack(FRAGMENT_CONTAINER, VoteChoiceDialog.newInstance
                        (mVoteFull));
                break;

            case MARKER_FILTER:
                ArrayList<MapMarker> mMapMarkers = getIntent().getParcelableArrayListExtra(Constants.BUNDLE_MARKERS);
                replaceFragmentWithoutBackStack(FRAGMENT_CONTAINER, MarkerFilterDialog.newInstance
                        (mMapMarkers));
                break;

            case AGE:
                Integer age = getIntent().getIntExtra(Constants.BUNDLE_INTEGER, 25);
                replaceFragmentWithoutBackStack(FRAGMENT_CONTAINER, AgeDialog.newInstance((age)));
                break;

            case GENDER:
                Integer gender = getIntent().getIntExtra(Constants.BUNDLE_INTEGER, -1);
                replaceFragmentWithoutBackStack(FRAGMENT_CONTAINER, GenderDialog.newInstance(gender));
                break;

            case PHOTO:
                replaceFragmentWithoutBackStack(FRAGMENT_CONTAINER, new PhotoChooseDialog());
                break;

            case SETTING:
                replaceFragmentWithoutBackStack(FRAGMENT_CONTAINER, new SettingDialog());
                break;

            case LOGIN:
                replaceFragmentWithoutBackStack(FRAGMENT_CONTAINER, new SignInDialog());
                break;

            case HOT_CALLS:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, new HotCallsDialog());
                break;
        }


    }

    @Override
    public void onActionDialogSelected(DialogType _action) {
        switch (_action) {
            case PHOTO_CAM:
                intent.putExtra(Constants.REQUEST_INTENT_TYPE_PHOTO, Constants.REQUEST_PHOTO);
                setResult(RESULT_OK, intent);
                finish();
                break;

            case PHOTO_GALLERY:
                intent.putExtra(Constants.REQUEST_INTENT_TYPE_PHOTO, Constants.REQUEST_GALLERY_IMAGE);
                setResult(RESULT_OK, intent);
                finish();
                break;

            case HOT_CALLS_EDITABLE:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, new HotCallsEditableDialog());
                break;

            case LOGIN:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, new SignInDialog());
                break;

            case REGISTER:
                Bundle args = new Bundle();
                args.putBoolean(Constants.BUNDLE_CONSTANT_EDITABLE_RESIDENT, false);
                SignUpDialog signUpDialog = new SignUpDialog();
                signUpDialog.setArguments(args);
                replaceFragmentWithBackStack(R.id.fragment_dialog_container, signUpDialog);
                break;

            case INTEREST_AREAS:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, new InterestingAreasDialog());
                break;

            case UPDATE_RESIDENT_INFO:
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constants.BUNDLE_CONSTANT_EDITABLE_RESIDENT, true);
                SignUpDialog editableResidentInfoDialog = new SignUpDialog();
                editableResidentInfoDialog.setArguments(bundle);
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, editableResidentInfoDialog);
                break;

            case VISIBLE_TICKETS:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, new VisibleTicketsDialog());
                break;

            case LANGUAGE:
                replaceFragmentWithBackStack(FRAGMENT_CONTAINER, new LanguageDialog());
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isTaskRoot()) {
            finish();
        }
    }

    @Override
    public void onActionDialogDataMarker(List<MapMarker> _list) {
        intent.putParcelableArrayListExtra(Constants.BUNDLE_MARKERS, (ArrayList<? extends Parcelable>) _list);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onActionDialogDataInteger(Integer _integer) {
        intent.putExtra(Constants.BUNDLE_INTEGER, _integer);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onActionDialogVote(Integer _integer, boolean _active) {
        intent.putExtra(Constants.BUNDLE_BOOLEAN, _active);
        intent.putExtra(Constants.BUNDLE_INTEGER, _integer);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onActionDialogCancel(boolean _check) {
        intent.putExtra(Constants.REQUEST_TYPE, Constants.REQUEST_CANCEL);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onLogoutListener() {
        Intent intent = new Intent();
        intent.putExtra(Constants.BUNDLE_LOGOUT, Constants.LOGOUT_KEY);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void doPositiveClick(){
        popBackStack();
    }
}
