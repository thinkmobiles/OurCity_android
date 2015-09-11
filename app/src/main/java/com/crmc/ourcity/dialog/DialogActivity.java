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

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_dialog);

        DialogType type = EnumUtil.deserialize(DialogType.class).from(getIntent());
        switch (type) {
            case VOTE_CHOICE:
                ArrayList<VoteFull> mVoteFull = getIntent().getParcelableArrayListExtra(Constants.BUNDLE_INTEGER);
                replaceFragmentWithoutBackStack(R.id.fragment_dialog_container, VoteChoiceDialog.newInstance
                        (mVoteFull));
                break;

            case MARKER_FILTER:
                ArrayList<MapMarker> mMapMarkers = getIntent().getParcelableArrayListExtra(Constants.BUNDLE_MARKERS);
                replaceFragmentWithoutBackStack(R.id.fragment_dialog_container, MarkerFilterDialog.newInstance
                        (mMapMarkers));
                break;

            case AGE:
                Integer age = getIntent().getIntExtra(Constants.BUNDLE_INTEGER, 25);
                replaceFragmentWithoutBackStack(R.id.fragment_dialog_container, AgeDialog.newInstance
                        ((age)));
                break;

            case GENDER:
                Integer gender = getIntent().getIntExtra(Constants.BUNDLE_INTEGER, -1);
                replaceFragmentWithoutBackStack(R.id.fragment_dialog_container, GenderDialog.newInstance(gender));
                break;

            case PHOTO:
                replaceFragmentWithoutBackStack(R.id.fragment_dialog_container, new PhotoChooseDialog());
                break;

            case SETTING:
                replaceFragmentWithoutBackStack(R.id.fragment_dialog_container, new SettingDialog());
                break;
            case LOGIN:
                replaceFragmentWithoutBackStack(R.id.fragment_dialog_container, new SignInDialog());
                break;
            case HOT_CALLS:
                replaceFragmentWithBackStack(R.id.fragment_dialog_container, new HotCallsDialog());
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
                replaceFragmentWithBackStack(R.id.fragment_dialog_container, new HotCallsEditableDialog());
                break;
            case LOGIN:
                replaceFragmentWithBackStack(R.id.fragment_dialog_container, new SignInDialog());
                break;
            case REGISTER:
                replaceFragmentWithBackStack(R.id.fragment_dialog_container, new SignUpDialog());
                break;
            case CONFIRMATION:
                replaceFragmentWithBackStack(R.id.fragment_dialog_container, new ConfirmationDialog());
                break;
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
}
