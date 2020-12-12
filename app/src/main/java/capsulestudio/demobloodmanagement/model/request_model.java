package capsulestudio.demobloodmanagement.model;

import com.google.gson.annotations.SerializedName;

public class request_model
{
    @SerializedName("id")
    private int id;

    @SerializedName("uName")
    private String uName;

    @SerializedName("uContact")
    private String uContact;

    @SerializedName("pName")
    private String pName;

    @SerializedName("group")
    private String pGroup;

    @SerializedName("require_bag")
    private String require_bag;

    @SerializedName("disease")
    private String disease;

    @SerializedName("hospital")
    private String hospital;

    @SerializedName("pContact")
    private String pContact;

    @SerializedName("require_date")
    private String require_date;

    @SerializedName("require_time")
    private String require_time;

    @SerializedName("post_date")
    private String post_date;

    public request_model(int id, String uName, String uContact, String pName, String pGroup, String require_bag, String disease, String hospital, String pContact, String require_date, String require_time, String post_date) {
        this.id = id;
        this.uName = uName;
        this.uContact = uContact;
        this.pName = pName;
        this.pGroup = pGroup;
        this.require_bag = require_bag;
        this.disease = disease;
        this.hospital = hospital;
        this.pContact = pContact;
        this.require_date = require_date;
        this.require_time = require_time;
        this.post_date = post_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuContact() {
        return uContact;
    }

    public void setuContact(String uContact) {
        this.uContact = uContact;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpGroup() {
        return pGroup;
    }

    public void setpGroup(String pGroup) {
        this.pGroup = pGroup;
    }

    public String getRequire_bag() {
        return require_bag;
    }

    public void setRequire_bag(String require_bag) {
        this.require_bag = require_bag;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getpContact() {
        return pContact;
    }

    public void setpContact(String pContact) {
        this.pContact = pContact;
    }

    public String getRequire_date() {
        return require_date;
    }

    public void setRequire_date(String require_date) {
        this.require_date = require_date;
    }

    public String getRequire_time() {
        return require_time;
    }

    public void setRequire_time(String require_time) {
        this.require_time = require_time;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }
}
