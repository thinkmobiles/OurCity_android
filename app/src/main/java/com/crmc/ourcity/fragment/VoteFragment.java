package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.activity.MainActivity;
import com.crmc.ourcity.adapter.VoteGridAdapter;
import com.crmc.ourcity.dialog.DialogActivity;
import com.crmc.ourcity.dialog.DialogType;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.VoteAlreadyLoader;
import com.crmc.ourcity.loader.VoteReplyLoader;
import com.crmc.ourcity.rest.responce.vote.VoteDetails;
import com.crmc.ourcity.rest.responce.vote.VoteFull;
import com.crmc.ourcity.utils.EnumUtil;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.utils.SPManager;
import com.crmc.ourcity.view.RecyclerItemClickListener;

import java.lang.ref.WeakReference;

public class VoteFragment extends BaseFourStatesFragment implements OnClickListener {

    private static final String CONFIGURATION_KEY = "CONFIGURATION_KEY";
    private View vUnderLine_VF;
    private View vUnderLine1_VF;
    private boolean isVote = false;
    private TextView tvAge;
    private TextView tvGender;
    private LinearLayout llAge;
    private LinearLayout llGender;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private VoteGridAdapter mAdapter;
    private Integer age;
    private Integer gender;
    private VoteFull mVoteFulls;
    private WeakReference<MainActivity> mActivity;

    public static VoteFragment newInstance(VoteFull _mVoteFull) {
        VoteFragment mVoteFragment = new VoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(CONFIGURATION_KEY, _mVoteFull);
        mVoteFragment.setArguments(args);
        return mVoteFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = new WeakReference<>((MainActivity) activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity.clear();
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        mVoteFulls = getArguments().getParcelable(CONFIGURATION_KEY);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }

    @Override
    protected void initViews() {
        super.initViews();
        vUnderLine_VF = findView(R.id.vUnderLine_VF);
        vUnderLine1_VF = findView(R.id.vUnderLine1_VF);
        tvAge = findView(R.id.tvAge_VF);
        tvGender = findView(R.id.tvGender_VF);
        llAge = findView(R.id.llAge_VF);
        llGender = findView(R.id.llGender_VF);
        mRecyclerView = findView(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(mActivity.get(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        vUnderLine_VF.setBackgroundColor(Image.darkenColor(0.2));
        vUnderLine1_VF.setBackgroundColor(Image.darkenColor(0.2));
        tvAge.setCompoundDrawablesWithIntrinsicBounds(Image.setDrawableImageColor(mActivity.get(), R.drawable
                .arrow_red, Image.darkenColor(0.2)), null, null, null);
        tvGender.setCompoundDrawablesWithIntrinsicBounds(Image.setDrawableImageColor(mActivity.get(), R.drawable
                .arrow_red, Image.darkenColor(0.2)), null, null, null);
        Image.setBoarderBackgroundColorArray(mActivity.get(), String.format("#%06X", 0xFFFFFF & Image.darkenColor(0.0))
                , 2, 5, "#ffffff", new View[]{llAge, llGender});
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_SURVEY_ID, mVoteFulls.surveyId);
        bundle.putInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID, SPManager.getInstance(mActivity.get()).getResidentId());
        getLoaderManager().initLoader(Constants.LOADER_VOTE_ALREADY_ID, bundle, mVoteAlreadyCallBack);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        llAge.setOnClickListener(this);
        llGender.setOnClickListener(this);
    }

    private LoaderManager.LoaderCallbacks<String> mVoteReplyCallBack = new LoaderManager.LoaderCallbacks<String>() {

        @Override
        public Loader<String> onCreateLoader(int _id, Bundle _args) {
            return new VoteReplyLoader(mActivity.get(), _args);
        }

        @Override
        public void onLoadFinished(Loader<String> _loader, String _data) {
            //TODO:add checked vote
            mAdapter.setVisibleVotePercent(true);
            isVote = true;
        }

        @Override
        public void onLoaderReset(Loader<String> _loader) {
        }
    };

    private LoaderManager.LoaderCallbacks<String> mVoteAlreadyCallBack = new LoaderManager.LoaderCallbacks<String>() {

        @Override
        public Loader<String> onCreateLoader(int _id, Bundle _args) {
            return new VoteAlreadyLoader(mActivity.get(), _args);
        }

        @Override
        public void onLoadFinished(Loader<String> _loader, String _data) {
            if (mVoteFulls != null && mVoteFulls.optionsList != null) {
                mAdapter = new VoteGridAdapter(mVoteFulls.optionsList, mActivity.get());
                mRecyclerView.setAdapter(mAdapter);
                if (_data.equals("false") && mVoteFulls.isActive != null && mVoteFulls.isActive && !isVote) {
                    mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mActivity.get()
                            .getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(Context _context, View _view, int _position) {
                            VoteDetails voteDetails = mAdapter.getItem(_position);
                            if (voteDetails.surveyOptionId != null) {
                                if (gender != null && age != null) {
                                    voteReply(voteDetails.surveyOptionId, gender, age);
                                } else {
                                    Toast.makeText(mActivity.get(), getResources().getString(R.string
                                            .not_selected_age_or_gender), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }));
                } else {
                    mAdapter.setVisibleVotePercent(true);
                    isVote = true;
                    if (mVoteFulls.isActive) {
                        Toast.makeText(mActivity.get(), getResources().getString(R.string.you_already_survey), Toast
                                .LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mActivity.get(), getResources().getString(R.string.vote_is_already_close), Toast
                                .LENGTH_SHORT).show();
                    }
                }
                showContent();
            } else {
                showEmpty();
            }
        }

        @Override
        public void onLoaderReset(Loader<String> _loader) {
        }
    };


    @Override
    public void onResume() {
        configureActionBar(true, true, mVoteFulls.surveyTitle);
        super.onResume();
    }

    public void voteReply(Integer _surveyOptionId, Integer _gender, Integer _age) {
        mActivity.get().getSupportLoaderManager().destroyLoader(Constants.LOADER_VOTE_ID);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_SELECTED_OPTION_ID, _surveyOptionId);
        bundle.putInt(Constants.BUNDLE_CONSTANT_AGE, _age);
        bundle.putInt(Constants.BUNDLE_CONSTANT_GENDER, _gender);
        bundle.putInt(Constants.BUNDLE_CONSTANT_LAST_USER, SPManager.getInstance(mActivity.get()).getResidentId());
        if (getLoaderManager().getLoader(Constants.LOADER_VOTE_REPLY_ID) == null) {
            getLoaderManager().initLoader(Constants.LOADER_VOTE_REPLY_ID, bundle, mVoteReplyCallBack);
        } else {
            getLoaderManager().restartLoader(Constants.LOADER_VOTE_REPLY_ID, bundle, mVoteReplyCallBack);
        }
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_vote;
    }

    @Override
    public void onRetryClick() {
    }

    @Override
    public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);
        switch (_requestCode) {
            case Constants.REQUEST_AGE:
                if (_data != null) {
                    age = _data.getIntExtra(Constants.BUNDLE_INTEGER, 0);
                    tvAge.setText(age + "");
                }
                break;

            case Constants.REQUEST_GENDER:
                if (_data != null) {
                    gender = _data.getIntExtra(Constants.BUNDLE_INTEGER, 0);
                    tvGender.setText((gender == 0) ? getString(R.string.male) : getString(R.string.female));
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llAge_VF:
                Intent intentAge = new Intent(mActivity.get(), DialogActivity.class);
                EnumUtil.serialize(DialogType.class, DialogType.AGE).to(intentAge);
                intentAge.putExtra(Constants.BUNDLE_INTEGER, age);
                startActivityForResult(intentAge, Constants.REQUEST_AGE);
                break;

            case R.id.llGender_VF:
                Intent intentGender = new Intent(mActivity.get(), DialogActivity.class);
                EnumUtil.serialize(DialogType.class, DialogType.GENDER).to(intentGender);
                intentGender.putExtra(Constants.BUNDLE_INTEGER, gender);
                startActivityForResult(intentGender, Constants.REQUEST_GENDER);
                break;
        }
    }
}
