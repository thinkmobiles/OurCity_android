package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.crmc.ourcity.model.rss.RSSEntry;
import com.crmc.ourcity.utils.Image;

/**
 * Created by podo on 02.09.15.
 */
public class RSSEntryFragment extends BaseFourStatesFragment implements View.OnClickListener {


    private RSSEntry entry;
    private TextView tvTitle;
    private TextView tvDate_Text;
    private TextView tvDescription_Text;

    private View vUnderLine_RssEFrgm;
    private View vBottomLine_RssEFrgm;

    private LinearLayout llDate;
    private LinearLayout llDescription;

    private ImageView ivLink;
    private OnListItemActionListener mOnListItemActionListener;


    public static RSSEntryFragment newInstance(RSSEntry _entry) {
        RSSEntryFragment rssEntryFragment = new RSSEntryFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CONFIGURATION_KEY_RSS, _entry);
        rssEntryFragment.setArguments(args);
        return rssEntryFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        entry = getArguments().getParcelable(Constants.CONFIGURATION_KEY_RSS);
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        checkData(entry.getTitle(), tvTitle, tvTitle);
        checkData(entry.getPubDate(), tvDate_Text, llDate);
        //checkData(entry.getDescription(), tvDescription_Text, llDescription);
        if(TextUtils.isEmpty(entry.getDescription())){
            llDescription.setVisibility(View.GONE);
        } else {
            tvDescription_Text.setText(Html.fromHtml(entry.getDescription()));
        }
        setImage();
        showContent();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        ivLink.setOnClickListener(this);
    }

    private void checkData(String _text, TextView _tvView, View _view) {

        if (!TextUtils.isEmpty(_text)) {
            _tvView.setText(_text);
        } else {
            _view.setVisibility(View.GONE);
        }
    }

    private void setImage() {

        if (!TextUtils.isEmpty(entry.getLink())) {
            ivLink.setImageDrawable(Image.setDrawableImageColor(getActivity(), R.drawable.link,
                    Image.darkenColor(0.2)));
        } else {
            ivLink.setVisibility(View.GONE);
        }
        vUnderLine_RssEFrgm.setBackgroundColor(Image.darkenColor(0.2));
        vBottomLine_RssEFrgm.setBackgroundColor(Image.darkenColor(0.2));
    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public void onClick(View v) {
        mOnListItemActionListener.onEventsClickLinkAction(entry.getLink());
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_rss_entry;
    }


}
