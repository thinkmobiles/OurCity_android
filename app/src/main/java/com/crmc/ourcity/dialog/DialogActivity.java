package com.crmc.ourcity.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.crmc.ourcity.R;
import com.crmc.ourcity.activity.BaseFragmentActivity;
import com.crmc.ourcity.callback.CallBackWithData;
import com.crmc.ourcity.callback.OnActionDialogListener;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.model.Marker;
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
                ArrayList<VoteFull> mVoteFull = getIntent().getParcelableArrayListExtra(Constants.BUNDLE_VOTE);
                replaceFragmentWithoutBackStack(R.id.fragment_dialog_container, VoteChoiceDialog.newInstance
                        (mVoteFull));
                break;

            case MARKER_FILTER:
                ArrayList<Marker> mMarkers = getIntent().getParcelableArrayListExtra(Constants.BUNDLE_MARKERS);
                replaceFragmentWithoutBackStack(R.id.fragment_dialog_container, MarkerFilterDialog.newInstance
                        (mMarkers));
                break;

            case PHOTO:
                replaceFragmentWithoutBackStack(R.id.fragment_dialog_container, new PhotoChooseDialog());
                break;

            case SETTING:
                replaceFragmentWithoutBackStack(R.id.fragment_dialog_container, new SettingDialog());
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
            case REGISTER:
                replaceFragmentWithBackStack(R.id.fragment_dialog_container, new TestDialog());
                break;
            case CONFIRMATION:
                replaceFragmentWithBackStack(R.id.fragment_dialog_container, new ConfirmationDialog());
                break;
        }
    }


    @Override
    public void onActionDialogDataMarker(List<Marker> _list) {
        intent.putParcelableArrayListExtra(Constants.BUNDLE_MARKERS, (ArrayList<? extends Parcelable>) _list);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onActionDialogDataVote(Integer _surveyId) {
        intent.putExtra(Constants.BUNDLE_VOTE, _surveyId);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onActionDialogCancel(boolean _check) {
        intent.putExtra(Constants.REQUEST_MARKER_FILTER_TYPE, Constants.REQUEST_MARKER_SELECTED_CANCEL);
        setResult(RESULT_OK, intent);
        finish();
    }
}
