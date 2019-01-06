package softgalli.gurukulshikshalay.model;

import com.google.gson.annotations.SerializedName;

public class AboutUsModelData {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("heading")
    private String heading;
    @SerializedName("contant")
    private String contant;
    @SerializedName("status")
    private String status;
    @SerializedName("rating_count")
    private String ratingCount;
    @SerializedName("banner_image")
    private String bannerImage;
    @SerializedName("review_count")
    private String review_count;
    @SerializedName("principal_name")
    private String principalName;
    @SerializedName("principal_no")
    private String principalNo;
    @SerializedName("vice_principal_name")
    private String vicePrincipalName;
    @SerializedName("vice_principal_no")
    private String vicePrincipalNo;
    @SerializedName("email")
    private String email;
    @SerializedName("address")
    private String address;

    public String getName() {
        return name;
    }

    public String getHeading() {
        return heading;
    }

    public String getContant() {
        return contant;
    }

    public String getStatus() {
        return status;
    }

    public String getSchool_id() {
        return school_id;
    }

    @SerializedName("school_id")

    private String school_id;
}
