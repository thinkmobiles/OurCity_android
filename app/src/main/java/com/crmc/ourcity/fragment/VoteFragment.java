package com.crmc.ourcity.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.VoteGridAdapter;
import com.crmc.ourcity.dialog.DialogActivity;
import com.crmc.ourcity.dialog.DialogType;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.VoteLoader;
import com.crmc.ourcity.loader.VoteReplyLoader;
import com.crmc.ourcity.rest.responce.vote.VoteDetails;
import com.crmc.ourcity.rest.responce.vote.VoteFull;
import com.crmc.ourcity.utils.EnumUtil;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.view.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SetKrul on 10.08.2015.
 */
public class VoteFragment extends BaseFourStatesFragment implements OnClickListener {

    private static final String CONFIGURATION_KEY_COLOR = "KEY_COLOR";
    private static final String CONFIGURATION_KEY_jSON = "KEY_JSON";
    private static final String CONFIGURATION_KEY_ROUTE = "KEY_ROUTE";
    private View vUnderLine_VF;
    private String color;
    private String json;
    private String route;
    private Button btnChooseAnotherVote;
    private TextView tvAge;
    private TextView tvGender;
    private ImageView ivVoteError;
    private LinearLayout llAge;
    private LinearLayout llGender;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private VoteGridAdapter mAdapter;
    private Integer surveyId;
    private Integer age;
    private Integer gender;
    private List<VoteFull> mVoteFull;

    public static VoteFragment newInstance(String _colorItem, String _json, String _route) {
        VoteFragment mVoteFragment = new VoteFragment();
        Bundle args = new Bundle();
        args.putString(CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(CONFIGURATION_KEY_jSON, _json);
        args.putString(CONFIGURATION_KEY_ROUTE, _route);
        mVoteFragment.setArguments(args);
        return mVoteFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        color = getArguments().getString(CONFIGURATION_KEY_COLOR);
        json = getArguments().getString(CONFIGURATION_KEY_jSON);
        route = getArguments().getString(CONFIGURATION_KEY_ROUTE);
    }


    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }

    @Override
    protected void initViews() {
        super.initViews();
        //noinspection ConstantConditions
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnChooseAnotherVote = findView(R.id.btnChooseAnotherVote_VF);
        vUnderLine_VF = findView(R.id.vUnderLine_VF);
        tvAge = findView(R.id.tvAge_VF);
        tvGender = findView(R.id.tvGender_VF);
        llAge = findView(R.id.llAge_VF);
        llGender = findView(R.id.llGender_VF);
        ivVoteError = findView(R.id.ivVoteError_VF);
        mRecyclerView = findView(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        try {
            Image.init(Color.parseColor(color));
        } catch (Exception e){
            Image.init(Color.BLACK);
        }
        vUnderLine_VF.setBackgroundColor(Image.darkenColor(0.2));
        tvAge.setCompoundDrawablesWithIntrinsicBounds(Image.setDrawableImageColor(getActivity(), R.drawable
                .arrow_red, Image.darkenColor(0.2)), null, null, null);
        tvGender.setCompoundDrawablesWithIntrinsicBounds(Image.setDrawableImageColor(getActivity(), R.drawable
                .arrow_red, Image.darkenColor(0.2)), null, null, null);
        Image.setBackgroundColorArrayView(getActivity(), new View[]{llAge, llGender}, R.drawable.boarder_round_red_vf);
        Image.setBackgroundColorView(getActivity(), btnChooseAnotherVote, R.drawable.selector_button_red_vf);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        btnChooseAnotherVote.setOnClickListener(this);
        llAge.setOnClickListener(this);
        llGender.setOnClickListener(this);
    }

    private LoaderManager.LoaderCallbacks<List<VoteFull>> mVoteCallBack = new LoaderManager
            .LoaderCallbacks<List<VoteFull>>() {

        @Override
        public Loader<List<VoteFull>> onCreateLoader(int _id, Bundle _args) {
            return new VoteLoader(getActivity(), _args);
        }

        @Override
        public void onLoadFinished(Loader<List<VoteFull>> _loader, List<VoteFull> _data) {
            if (mVoteFull == null) {
                if (_data.size() > 0) {
                    Intent intent = new Intent(getActivity(), DialogActivity.class);
                    EnumUtil.serialize(DialogType.class, DialogType.VOTE_CHOICE).to(intent);
                    intent.putParcelableArrayListExtra(Constants.BUNDLE_INTEGER, (ArrayList<? extends Parcelable>) _data);

                    startActivityForResult(intent, Constants.REQUEST_VOTE);
                    mVoteFull = _data;
                } else {
                    ivVoteError.setImageResource(R.drawable.error_vote);
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<List<VoteFull>> _loader) {
        }
    };

    private LoaderManager.LoaderCallbacks<String> mVoteReplyCallBack = new LoaderManager.LoaderCallbacks<String>() {

        @Override
        public Loader<String> onCreateLoader(int _id, Bundle _args) {
            return new VoteReplyLoader(getActivity(), _args);
        }

        @Override
        public void onLoadFinished(Loader<String> _loader, String _data) {
            //TODO:add checked vote
                mAdapter.setVisibleVotePercent(true);
        }

        @Override
        public void onLoaderReset(Loader<String> _loader) {
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().initLoader(Constants.LOADER_VOTE_ID, bundle, mVoteCallBack);
    }

    public void voteReply(Integer _surveyOptionId, Integer _gender, Integer _age) {
        getActivity().getSupportLoaderManager().destroyLoader(Constants.LOADER_VOTE_ID);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_SELECTED_OPTION_ID, _surveyOptionId);
        bundle.putInt(Constants.BUNDLE_CONSTANT_AGE, _age);
        bundle.putInt(Constants.BUNDLE_CONSTANT_GENDER, _gender);
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
            case Constants.REQUEST_VOTE:
                if (_data != null) {
                    surveyId = _data.getIntExtra(Constants.BUNDLE_INTEGER, 0);
                    showVote(surveyId);
                } else {
                    if (surveyId == null) {
                        popBackStack();
                    }
                }
                break;

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

    private List<VoteDetails> getVote(Integer _surveyId) {
        for (int i = 0; i < mVoteFull.size(); i++) {
            if (_surveyId.equals(mVoteFull.get(i).surveyId)) {
                if (mVoteFull.get(i).optionsList != null) {
                    return mVoteFull.get(i).optionsList;
                } else {
                    mVoteFull.get(i).optionsList = new ArrayList<>();
                    return mVoteFull.get(i).optionsList;
                }
            }
        }
        return new ArrayList<>();
    }

    private void showVote(Integer _surveyId) {
        List<VoteDetails> voteDetails = getVote(_surveyId);
        if (voteDetails.size() > 0) {
            mAdapter = new VoteGridAdapter(getVote(_surveyId), color, getActivity());
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(Context _context, View _view, int _position) {
                    VoteDetails voteDetails = mAdapter.getItem(_position);
                    if (voteDetails.surveyOptionId != null) {
                        if (gender != null && age != null) {
                            voteReply(voteDetails.surveyOptionId, gender, age);
                        } else {
                            Toast.makeText(getActivity(), "Not selected age or gender!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }));
        } else {
            Toast.makeText(getActivity(), "Vote is empty!", Toast.LENGTH_SHORT).show();
            ivVoteError.setImageResource(R.drawable.error_vote);
        }
        showContent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChooseAnotherVote_VF:
                Intent intent = new Intent(getActivity(), DialogActivity.class);
                EnumUtil.serialize(DialogType.class, DialogType.VOTE_CHOICE).to(intent);
                intent.putParcelableArrayListExtra(Constants.BUNDLE_INTEGER, (ArrayList<? extends Parcelable>)
                        mVoteFull);
                startActivityForResult(intent, Constants.REQUEST_VOTE);
                break;

            case R.id.llAge_VF:
                Intent intentAge = new Intent(getActivity(), DialogActivity.class);
                EnumUtil.serialize(DialogType.class, DialogType.AGE).to(intentAge);
                intentAge.putExtra(Constants.BUNDLE_INTEGER, age);
                startActivityForResult(intentAge, Constants.REQUEST_AGE);
                break;

            case R.id.llGender_VF:
                Intent intentGender = new Intent(getActivity(), DialogActivity.class);
                EnumUtil.serialize(DialogType.class, DialogType.GENDER).to(intentGender);
                intentGender.putExtra(Constants.BUNDLE_INTEGER, gender);
                startActivityForResult(intentGender, Constants.REQUEST_GENDER);
                break;
        }

    }
}
