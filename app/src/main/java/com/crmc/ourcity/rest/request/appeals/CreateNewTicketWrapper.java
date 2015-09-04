package com.crmc.ourcity.rest.request.appeals;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by podo on 04.09.15.
 */
public class CreateNewTicketWrapper implements Parcelable {
    public int clientId;
    public String userName;
    public String password;
    public NewTicket newTicket;
    public int ResidentId;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.clientId);
        dest.writeString(this.userName);
        dest.writeString(this.password);
        dest.writeParcelable(this.newTicket, 0);
        dest.writeInt(this.ResidentId);
    }

    public CreateNewTicketWrapper() {
    }

    protected CreateNewTicketWrapper(Parcel in) {
        this.clientId = in.readInt();
        this.userName = in.readString();
        this.password = in.readString();
        this.newTicket = in.readParcelable(NewTicket.class.getClassLoader());
        this.ResidentId = in.readInt();
    }

    public static final Creator<CreateNewTicketWrapper> CREATOR = new Creator<CreateNewTicketWrapper>() {
        public CreateNewTicketWrapper createFromParcel(Parcel source) {
            return new CreateNewTicketWrapper(source);
        }

        public CreateNewTicketWrapper[] newArray(int size) {
            return new CreateNewTicketWrapper[size];
        }
    };
}
