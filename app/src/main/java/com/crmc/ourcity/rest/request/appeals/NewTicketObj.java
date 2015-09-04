package com.crmc.ourcity.rest.request.appeals;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by podo on 04.09.15.
 */
public class NewTicketObj implements Parcelable {
    public CreateNewTicketWrapper newTicketWrapper;

    public NewTicketObj(CreateNewTicketWrapper newTicket) {

        this.newTicketWrapper = newTicket;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.newTicketWrapper, 0);
    }

    protected NewTicketObj(Parcel in) {
        this.newTicketWrapper = in.readParcelable(CreateNewTicketWrapper.class.getClassLoader());
    }

    public static final Creator<NewTicketObj> CREATOR = new Creator<NewTicketObj>() {
        public NewTicketObj createFromParcel(Parcel source) {
            return new NewTicketObj(source);
        }

        public NewTicketObj[] newArray(int size) {
            return new NewTicketObj[size];
        }
    };
}
