package com.crmc.ourcity.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.rest.responce.events.Phones;
import com.crmc.ourcity.utils.IntentUtils;

import java.util.List;

/**
 * Created by SetKrul on 15.07.2015.
 */
public class PhonesListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Phones> mPhonesList;
    private Context mContext;

    public PhonesListAdapter(Context _context, List<Phones> _phonesList) {
        this.mPhonesList = _phonesList;
        this.mContext = _context;
        mInflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return mPhonesList.size();
    }

    @Override
    public Phones getItem(int _position) {
        return mPhonesList.get(_position);
    }

    @Override
    public long getItemId(int _position) {
        return _position;
    }

    @Override
    public View getView(final int _position, View _convertView, ViewGroup _parent) {
        final ViewHolder holder;
        if (_convertView == null) {
            _convertView = mInflater.inflate(R.layout.listview_row_fp, _parent, false);
            holder = new ViewHolder(_convertView);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }
        holder.setData(getItem(_position), _position);
        return _convertView;
    }

    private class ViewHolder implements View.OnClickListener {
        final TextView nameInstitution;
        final ImageView callSkype;
        final ImageView sendMail;
        View view;
        private int position;

        public ViewHolder(@NonNull final View _view) {
            nameInstitution = (TextView) _view.findViewById(R.id.tvNameInstitution_FP);
            callSkype = (ImageView) _view.findViewById(R.id.btnCallSkype_FP);
            sendMail = (ImageView) _view.findViewById(R.id.btnSendMail_FP);
            callSkype.setOnClickListener(this);
            sendMail.setOnClickListener(this);
            this.view = _view;
            view.setTag(this);
        }

        public void setData(Phones _item, int _position) {
            this.position = _position;
            nameInstitution.setText(_item.entityName);
        }

        @Override
        public void onClick(View _view) {
            switch (_view.getId()) {
                case R.id.btnCallSkype_FP:
                    try {
                        mContext.startActivity(Intent.createChooser(IntentUtils.getIntentSkype(getItem(position)
                                .phoneNumber), mContext.getResources().getString(R.string.call_skype_hint)));
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.app_no_skype_client),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btnSendMail_FP:
                    try {
                        mContext.startActivity(Intent.createChooser(IntentUtils.getIntentMail(getItem(position)
                                .emailAddress), mContext.getResources().getString(R.string.send_mail_hint)));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.app_no_mail_client),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
}
