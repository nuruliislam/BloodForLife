package capsulestudio.demobloodmanagement.model;

import com.google.gson.annotations.SerializedName;

public class admin_model
{

    @SerializedName("id")
    private int aId;

    @SerializedName("name")
    private String aName;

    @SerializedName("email")
    private String aEmail;

    @SerializedName("contact")
    private String aContact;

    public admin_model(int aId, String aName, String aEmail, String aContact) {
        this.aId = aId;
        this.aName = aName;
        this.aEmail = aEmail;
        this.aContact = aContact;
    }

    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getaEmail() {
        return aEmail;
    }

    public void setaEmail(String aEmail) {
        this.aEmail = aEmail;
    }

    public String getaContact() {
        return aContact;
    }

    public void setaContact(String aContact) {
        this.aContact = aContact;
    }
}
