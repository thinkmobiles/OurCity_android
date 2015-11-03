package com.crmc.ourcity.rest.responce.vote;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VoteFull implements Parcelable{

    @SerializedName("optionsList")
    public List<VoteDetails> optionsList;

    @SerializedName("imagePath")
    public String imagePath;
    @SerializedName("isActive")
    public Boolean isActive;
    @SerializedName("surveyId")
    public Integer surveyId;
    @SerializedName("surveyTitle")
    public String surveyTitle;

    protected VoteFull(Parcel _in) {
        imagePath = _in.readString();
        isActive = Boolean.valueOf(_in.readString());
        surveyId = _in.readInt();
        surveyTitle = _in.readString();

    }

    public static final Creator<VoteFull> CREATOR = new Creator<VoteFull>() {
        @Override
        public VoteFull createFromParcel(Parcel _in) {
            return new VoteFull(_in);
        }

        @Override
        public VoteFull[] newArray(int _size) {
            return new VoteFull[_size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel _dest, int _flags) {
        _dest.writeString(imagePath);
        _dest.writeString(String.valueOf(isActive));
        _dest.writeInt(surveyId);
        _dest.writeString(surveyTitle);
    }
}
