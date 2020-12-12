package capsulestudio.demobloodmanagement.model;

import com.google.gson.annotations.SerializedName;

public class donation_model
{
    @SerializedName("id")
    private int id;
    @SerializedName("dName")
    private String dName;

    @SerializedName("group")
    private String dGroup;

    @SerializedName("pName")
    private String pName;

    @SerializedName("date")
    private String date;

    @SerializedName("qty")
    private String qty;

    @SerializedName("disease")
    private String disease;

    @SerializedName("hospital")
    private String hospital;


    public donation_model(int id, String dName, String dGroup, String pName, String date, String qty, String disease, String hospital) {
        this.id = id;
        this.dName = dName;
        this.dGroup = dGroup;
        this.pName = pName;
        this.date = date;
        this.qty = qty;
        this.disease = disease;
        this.hospital = hospital;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
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
}
