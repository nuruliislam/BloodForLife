package capsulestudio.demobloodmanagement.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListDonationModel
{
    @SerializedName("donation")
    private List<donation_model> listPersonM;
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;

    public ListDonationModel(List<donation_model> listPersonM, int status, String message) {
        this.listPersonM = listPersonM;
        this.status = status;
        this.message = message;
    }

    public List<donation_model> getListPersonM() {
        return listPersonM;
    }

    public void setListPersonM(List<donation_model> listPersonM) {
        this.listPersonM = listPersonM;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
