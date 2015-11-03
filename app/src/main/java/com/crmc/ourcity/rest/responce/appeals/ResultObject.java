package com.crmc.ourcity.rest.responce.appeals;


import android.os.Parcel;
import android.os.Parcelable;

public class ResultObject implements Parcelable {
    public String AttachedFiles;
    public int CRMCTicketID;
    public String DateCreated;
    public String DateCreatedClient;
    public String DepartmentName;
    public String Description;
    public String ReferenceID;
    public Location Location;
    public String Status;
    public String Subject;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.AttachedFiles);
        dest.writeInt(this.CRMCTicketID);
        dest.writeString(this.DateCreated);
        dest.writeString(this.DateCreatedClient);
        dest.writeString(this.DepartmentName);
        dest.writeString(this.Description);
        dest.writeString(this.ReferenceID);
        dest.writeParcelable(this.Location, 0);
        dest.writeString(this.Status);
        dest.writeString(this.Subject);
    }

    public ResultObject() {
    }

    protected ResultObject(Parcel in) {
        this.AttachedFiles = in.readString();
        this.CRMCTicketID = in.readInt();
        this.DateCreated = in.readString();
        this.DateCreatedClient = in.readString();
        this.DepartmentName = in.readString();
        this.Description = in.readString();
        this.ReferenceID = in.readString();
        this.Location = in.readParcelable(com.crmc.ourcity.rest.responce.appeals.Location.class.getClassLoader());
        this.Status = in.readString();
        this.Subject = in.readString();
    }

    public static final Creator<ResultObject> CREATOR = new Creator<ResultObject>() {
        public ResultObject createFromParcel(Parcel source) {
            return new ResultObject(source);
        }

        public ResultObject[] newArray(int size) {
            return new ResultObject[size];
        }
    };
}
