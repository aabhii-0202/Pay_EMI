package com.mediustechnologies.payemi.ApiResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mediustechnologies.payemi.DTO.billFetchDTO;

import java.util.List;

public class fetchBill implements Parcelable {

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("payload")
    @Expose
    private List<billFetchDTO> payload;

    @SerializedName("message")
    @Expose
    private String message;

    protected fetchBill(Parcel in) {
        status = in.readInt();
        payload = in.createTypedArrayList(billFetchDTO.CREATOR);
        message = in.readString();
    }

    public static final Creator<fetchBill> CREATOR = new Creator<fetchBill>() {
        @Override
        public fetchBill createFromParcel(Parcel in) {
            return new fetchBill(in);
        }

        @Override
        public fetchBill[] newArray(int size) {
            return new fetchBill[size];
        }
    };

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<billFetchDTO> getPayload() {
        return payload;
    }

    public void setPayload(List<billFetchDTO> payload) {
        this.payload = payload;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "fetchBill{" +
                "status=" + status +
                ", payload=" + payload +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(status);
        parcel.writeTypedList(payload);
        parcel.writeString(message);
    }
}
