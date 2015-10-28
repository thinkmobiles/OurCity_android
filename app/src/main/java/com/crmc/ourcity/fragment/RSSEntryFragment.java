package com.crmc.ourcity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.utils.Image;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by podo on 02.09.15.
 */
public class RSSEntryFragment extends BaseFourStatesFragment {

    private TextView tvTitle;
    private TextView tvDate_Text;
    private TextView tvDescription_Text;

    private View vUnderLine_RssEFrgm;
    private View vBottomLine_RssEFrgm;

    private LinearLayout llDate;
    private LinearLayout llDescription;

    private ImageView ivLink;
    private OnListItemActionListener mOnListItemActionListener;
    private String title;
    private String entryTitle;
    private String entryDescription;
    private String entryDate;
    private String entryLink;



    public static RSSEntryFragment newInstance(String _entryTitle, String _description, String _link, String _date) {
        RSSEntryFragment rssEntryFragment = new RSSEntryFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_ENTRY_TITLE, _entryTitle);
        args.putString(Constants.CONFIGURATION_KEY_ENTRY_DESCR, _description);
        args.putString(Constants.CONFIGURATION_KEY_ENTRY_DATE, _date);
        args.putString(Constants.CONFIGURATION_KEY_LINK, _link);
        rssEntryFragment.setArguments(args);
        return rssEntryFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        title = getArguments().getString(Constants.NODE_TITLE);
        entryTitle = getArguments().getString(Constants.CONFIGURATION_KEY_ENTRY_TITLE);
        entryDescription = getArguments().getString(Constants.CONFIGURATION_KEY_ENTRY_DESCR);
        entryDate = getArguments().getString(Constants.CONFIGURATION_KEY_ENTRY_DATE);
        entryLink = getArguments().getString(Constants.CONFIGURATION_KEY_LINK);
    }

    @Override
    public void onResume() {
        configureActionBar(true, true, entryTitle);
        super.onResume();
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
        mOnListItemActionListener = null;
        super.onDetach();
    }

    @Override
    protected void initViews() {
        super.initViews();
        tvTitle = findView(R.id.tvTitle_RssEFrgm);
        tvDate_Text = findView(R.id.tvDate_Text_RssEFrgm);
        tvDescription_Text = findView(R.id.tvDescription_Text_RssEFrgm);
        llDate = findView(R.id.llDate_RssEFrgm);
        llDescription = findView(R.id.llDescription_RssEFrgm);
        ivLink = findView(R.id.ivLink_RssEFrgm);
        vUnderLine_RssEFrgm = findView(R.id.vUnderLine_RssEFrgm);
        vBottomLine_RssEFrgm = findView(R.id.vBottomLine_RssEFrgm);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        checkData(entryTitle, tvTitle, tvTitle);
        //checkData(getDateTime(entry.getPubDate()) + " ", tvDate_Text, llDate);
        if (TextUtils.isEmpty(getDateTime(entryDate))) {
            tvDate_Text.setVisibility(View.GONE);
            llDate.setVisibility(View.GONE);
        } else {
            tvDate_Text.setVisibility(View.VISIBLE);
            llDate.setVisibility(View.VISIBLE);
            tvDate_Text.setText(getDateTime(entryDate).trim() + " ");
        }
        if (TextUtils.isEmpty(entryDescription)) {
            llDescription.setVisibility(View.GONE);
        } else {
            tvDescription_Text.setText(Html.fromHtml(entryDescription.substring(0, entryDescription.length() - 3)));
        }
        setImage();
        showContent();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        ivLink.setOnClickListener(v -> mOnListItemActionListener.onEventsClickLinkAction(entryLink, entryTitle));
    }

    private void setImage() {

        if (!TextUtils.isEmpty(entryLink)) {
            ivLink.setImageDrawable(Image.setDrawableImageColor(getActivity(), R.drawable.link,
                    Image.darkenColor(0.2)));
        } else {
            ivLink.setVisibility(View.GONE);
        }
        vUnderLine_RssEFrgm.setBackgroundColor(Image.darkenColor(0.2));
        vBottomLine_RssEFrgm.setBackgroundColor(Image.darkenColor(0.2));
    }

    @Override
    public void onRetryClick() {}

    @Override
    protected int getContentView() {
        return R.layout.fragment_rss_entry;
    }

    @SuppressLint("SimpleDateFormat")
    public String getDateTime(String _data) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        if (_data != null) {
            return formatter.format(new Date(_data));
        } else return null;
    }
}
