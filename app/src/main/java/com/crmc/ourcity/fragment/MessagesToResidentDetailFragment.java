package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.responce.events.MassageToResident;
import com.crmc.ourcity.utils.Image;

/**
 * Created by andrei on 20.10.15.
 */
public class MessagesToResidentDetailFragment extends BaseFourStatesFragment {

    private String message;
    private String link;
    private View vUnderLine_MTRDF, vBottomLine_MTRDF;
    private TextView tvMessage_MTRDF;
    private ImageView ivLink_MTRDF;
    private LinearLayout llMessages;
    private MassageToResident mtrItem;
    private String title;
    private OnListItemActionListener mOnListItemActionListener;


    public static MessagesToResidentDetailFragment newInstance(MassageToResident _mtr) {
        MessagesToResidentDetailFragment fr = new MessagesToResidentDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CONFIGURATION_KEY_MESSAGE_TO_RESIDENT, _mtr);
        fr.setArguments(args);
        return fr;
    }

    public static MessagesToResidentDetailFragment newInstance(String _message, String _link) {
        MessagesToResidentDetailFragment fr = new MessagesToResidentDetailFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_MESSAGE, _message);
        args.putString(Constants.CONFIGURATION_KEY_LINK, _link);
        fr.setArguments(args);
        return fr;
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        try {
            mOnListItemActionListener = (OnListItemActionListener) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString() + " must implement OnListItemActionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnListItemActionListener = null;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        mtrItem = getArguments().getParcelable(Constants.CONFIGURATION_KEY_MESSAGE_TO_RESIDENT);
        if (mtrItem != null) {
            message = mtrItem.message;
            link = mtrItem.link;
        } else {
            message = getArguments().getString(Constants.CONFIGURATION_KEY_MESSAGE, "");
            link = getArguments().getString(Constants.CONFIGURATION_KEY_LINK, "");
            title = "הודעות עבורך";
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(title)) {
            configureActionBar(true, true, title);
        }

    }

    @Override
    protected void initViews() {
        super.initViews();
        vUnderLine_MTRDF = findView(R.id.vUnderLine_MTRF);
        vBottomLine_MTRDF = findView(R.id.vBottomLine_MTRF);
        llMessages= findView(R.id.llMessages_MTRF);
        tvMessage_MTRDF = findView(R.id.tvMessage_Text_MTRF);
        ivLink_MTRDF = findView(R.id.ivLink_MTRF);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        ivLink_MTRDF.setOnClickListener(v -> {
            mOnListItemActionListener.onEventsClickLinkAction(link, "");
        });
    }

    @Override
    public void onViewCreated(View _view, Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        checkData(message, tvMessage_MTRDF, llMessages);
        setImage();
        showContent();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_messages_to_resident_detail;
    }

    @Override
    public void onRetryClick() {

    }

    private void setImage() {
        vUnderLine_MTRDF.setBackgroundColor(Image.darkenColor(0.2));
        vBottomLine_MTRDF.setBackgroundColor(Image.darkenColor(0.2));
        if (!TextUtils.isEmpty(link)) {
                ivLink_MTRDF.setImageDrawable(Image.setDrawableImageColor(getActivity().getApplicationContext(), R.drawable.link,
                        Image.darkenColor(0.2)));
                ivLink_MTRDF.setVisibility(View.VISIBLE);
            } else {
                ivLink_MTRDF.setVisibility(View.GONE);
            }
    }
}
