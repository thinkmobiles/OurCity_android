package com.crmc.ourcity.rest.responce.vote;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class VoteDetails implements Parcelable {

    @SerializedName("image")
    public String image;
    @SerializedName("optionDescroption")
    public String optionDescription;
    @SerializedName("optionId")
    public Integer optionId;
    @SerializedName("surveyId")
    public Integer surveyId;
    @SerializedName("surveyOptionId")
    public Integer surveyOptionId;
    @SerializedName("votePercent")
    public Integer votePercent;

    protected VoteDetails(Parcel _in) {
        image = _in.readString();
        optionDescription = _in.readString();
        optionId = _in.readInt();
        surveyId = _in.readInt();
        surveyOptionId = _in.readInt();
        votePercent = _in.readInt();
    }

    public static final Creator<VoteDetails> CREATOR = new Creator<VoteDetails>() {
        @Override
        public VoteDetails createFromParcel(Parcel _in) {
            return new VoteDetails(_in);
        }

        @Override
        public VoteDetails[] newArray(int _size) {
            return new VoteDetails[_size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel _dest, int _flags) {
        _dest.writeString(image);
        _dest.writeString(optionDescription);
        _dest.writeInt(optionId);
        _dest.writeInt(surveyId);
        _dest.writeInt(surveyOptionId);
        _dest.writeInt(votePercent);
    }
}
