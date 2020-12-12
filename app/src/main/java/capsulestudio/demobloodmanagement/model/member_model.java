package capsulestudio.demobloodmanagement.model;

import com.google.gson.annotations.SerializedName;

public class member_model
{

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("contact")
    private String mContact;

    @SerializedName("password")
    private String mPassword;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("city")
    private String mCity;

    @SerializedName("post")
    private String mPost;

    @SerializedName("country")
    private String mCountry;

    public member_model(int mId, String mName, String mEmail, String mContact, String mPassword, String mAddress, String mCity, String mPost, String mCountry) {
        this.mId = mId;
        this.mName = mName;
        this.mEmail = mEmail;
        this.mContact = mContact;
        this.mPassword = mPassword;
        this.mAddress = mAddress;
        this.mCity = mCity;
        this.mPost = mPost;
        this.mCountry = mCountry;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmContact() {
        return mContact;
    }

    public void setmContact(String mContact) {
        this.mContact = mContact;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmPost() {
        return mPost;
    }

    public void setmPost(String mPost) {
        this.mPost = mPost;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }
}
