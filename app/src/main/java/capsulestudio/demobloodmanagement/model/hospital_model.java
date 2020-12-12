package capsulestudio.demobloodmanagement.model;

import com.google.gson.annotations.SerializedName;

public class hospital_model
{
    @SerializedName("id")
    private int hId;

    @SerializedName("name")
    private String hName;

    @SerializedName("email")
    private String hEmail;

    @SerializedName("contact")
    private String hContact;

    @SerializedName("address")
    private String hAddress;

    @SerializedName("city")
    private String hCity;

    public hospital_model(int hId, String hName, String hEmail, String hContact, String hAddress, String hCity) {
        this.hId = hId;
        this.hName = hName;
        this.hEmail = hEmail;
        this.hContact = hContact;
        this.hAddress = hAddress;
        this.hCity = hCity;
    }


    public int gethId() {
        return hId;
    }

    public void sethId(int hId) {
        this.hId = hId;
    }

    public String gethName() {
        return hName;
    }

    public void sethName(String hName) {
        this.hName = hName;
    }

    public String gethEmail() {
        return hEmail;
    }

    public void sethEmail(String hEmail) {
        this.hEmail = hEmail;
    }

    public String gethContact() {
        return hContact;
    }

    public void sethContact(String hContact) {
        this.hContact = hContact;
    }

    public String gethAddress() {
        return hAddress;
    }

    public void sethAddress(String hAddress) {
        this.hAddress = hAddress;
    }

    public String gethCity() {
        return hCity;
    }

    public void sethCity(String hCity) {
        this.hCity = hCity;
    }
}
