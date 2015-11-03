package com.crmc.ourcity.rest.request.appeals;

import android.os.Parcel;
import android.os.Parcelable;

import com.crmc.ourcity.rest.responce.appeals.Location;

public class NewTicket implements Parcelable{
    public int CRMCTicketID;
    public String ReferenceID;
    public WSDate DateCreatedMobile;
    public String Subject;
    public String Description;
    public String DepartmentName;
    public com.crmc.ourcity.rest.responce.appeals.Location Location;
    public String Status ;
    public String ReporterFirstName;
    public String ReporterLastName;
    public String ReporterEmailAddress;
    public WSAddress ReporterAddress;
    public WSPhoneNumber ReporterHomePhoneNumber;
    public WSPhoneNumber ReporterMobilePhoneNumber;
    public WSPhoneNumber ReporterFaxNumber;
    public String AttachedFiles;
    public String ExtraParam1 ;


    public String ExtraParam2 ;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.CRMCTicketID);
        dest.writeString(this.ReferenceID);
        dest.writeParcelable(this.DateCreatedMobile, flags);
        dest.writeString(this.Subject);
        dest.writeString(this.Description);
        dest.writeString(this.DepartmentName);
        dest.writeParcelable(this.Location, flags);
        dest.writeString(this.Status);
        dest.writeString(this.ReporterFirstName);
        dest.writeString(this.ReporterLastName);
        dest.writeString(this.ReporterEmailAddress);
        dest.writeParcelable(this.ReporterAddress, flags);
        dest.writeParcelable(this.ReporterHomePhoneNumber, flags);
        dest.writeParcelable(this.ReporterMobilePhoneNumber, flags);
        dest.writeParcelable(this.ReporterFaxNumber, flags);
        dest.writeString(this.AttachedFiles);
        dest.writeString(this.ExtraParam1);
        dest.writeString(this.ExtraParam2);
    }

    public NewTicket() {
    }

    protected NewTicket(Parcel in) {
        this.CRMCTicketID = in.readInt();
        this.ReferenceID = in.readString();
        this.DateCreatedMobile = in.readParcelable(WSDate.class.getClassLoader());
        this.Subject = in.readString();
        this.Description = in.readString();
        this.DepartmentName = in.readString();
        this.Location = in.readParcelable(com.crmc.ourcity.rest.responce.appeals.Location.class.getClassLoader());
        this.Status = in.readString();
        this.ReporterFirstName = in.readString();
        this.ReporterLastName = in.readString();
        this.ReporterEmailAddress = in.readString();
        this.ReporterAddress = in.readParcelable(WSAddress.class.getClassLoader());
        this.ReporterHomePhoneNumber = in.readParcelable(WSPhoneNumber.class.getClassLoader());
        this.ReporterMobilePhoneNumber = in.readParcelable(WSPhoneNumber.class.getClassLoader());
        this.ReporterFaxNumber = in.readParcelable(WSPhoneNumber.class.getClassLoader());
        this.AttachedFiles = in.readString();
        this.ExtraParam1 = in.readString();
        this.ExtraParam2 = in.readString();
    }

    public static final Creator<NewTicket> CREATOR = new Creator<NewTicket>() {
        public NewTicket createFromParcel(Parcel source) {
            return new NewTicket(source);
        }

        public NewTicket[] newArray(int size) {
            return new NewTicket[size];
        }
    };
}
