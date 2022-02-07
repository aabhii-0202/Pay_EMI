package com.mediustechnologies.payemi.ApiResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mediustechnologies.payemi.DTO.billFetchDTO;

import java.util.List;

public class fetchBill extends BaseApiResponse implements Parcelable {

    //todo extends BaseApiResponse

    @SerializedName("payload")
    @Expose
    private List<billFetchDTO> payload;



    @SerializedName("billerPaymentExactness")
    @Expose
    private String exactness;


    protected fetchBill(Parcel in) {
        payload = in.createTypedArrayList(billFetchDTO.CREATOR);

        exactness = in.readString();
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

    @Override
    public String toString() {
        return "fetchBill{" +
                ", payload=" + payload +

                ", exactness='" + exactness + '\'' +
                '}';
    }


    public List<billFetchDTO> getPayload() {
        return payload;
    }

    public void setPayload(List<billFetchDTO> payload) {
        this.payload = payload;
    }



    public String getExactness() {
        return exactness;
    }

    public void setExactness(String exactness) {
        this.exactness = exactness;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(payload);

        parcel.writeString(exactness);
    }
}
