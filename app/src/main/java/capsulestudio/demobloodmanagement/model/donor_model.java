package capsulestudio.demobloodmanagement.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MY_PC on 08/02/2018.
 */

public class donor_model
{
    @SerializedName("id")
    private int dId;
    @SerializedName("name")
    private String dName;

    @SerializedName("group")
    private String dGroup;

    @SerializedName("email")
    private String dEmail;

    @SerializedName("contact")
    private String dContact;

    @SerializedName("address")
    private String dAddress;

    @SerializedName("city")
    private String dCity;

    @SerializedName("last_donation")
    private String dDonation;

    public donor_model(int dId, String dName, String dGroup, String dEmail, String dContact, String dAddress, String dCity, String dDonation) {
        this.dId = dId;
        this.dName = dName;
        this.dGroup = dGroup;
        this.dEmail = dEmail;
        this.dContact = dContact;
        this.dAddress = dAddress;
        this.dCity = dCity;
        this.dDonation = dDonation;
    }

    public int getdId() {
        return dId;
    }

    public void setdId(int dId) {
        this.dId = dId;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdGroup() {
        return dGroup;
    }

    public void setdGroup(String dGroup) {
        this.dGroup = dGroup;
    }

    public String getdEmail() {
        return dEmail;
    }

    public void setdEmail(String dEmail) {
        this.dEmail = dEmail;
    }

    public String getdContact() {
        return dContact;
    }

    public void setdContact(String dContact) {
        this.dContact = dContact;
    }

    public String getdAddress() {
        return dAddress;
    }

    public void setdAddress(String dAddress) {
        this.dAddress = dAddress;
    }

    public String getdCity() {
        return dCity;
    }

    public void setdCity(String dCity) {
        this.dCity = dCity;
    }

    public String getdDonation() {
        return dDonation;
    }

    public void setdDonation(String dDonation) {
        this.dDonation = dDonation;
    }
}