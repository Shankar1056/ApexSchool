package softgalli.gurukulshikshalay.model;

import com.google.gson.annotations.SerializedName;

public class AboutUsModel {
    @SerializedName("status")
    String status;
    @SerializedName("msg")
    String msg;
    @SerializedName("data")
    AboutUsModelData data;

    public AboutUsModelData getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

}
