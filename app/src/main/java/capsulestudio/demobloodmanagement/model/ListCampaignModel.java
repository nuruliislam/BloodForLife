package capsulestudio.demobloodmanagement.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListCampaignModel
{

    @SerializedName("campaign")
    private List<campaign_model> listPersonM;
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;

    public ListCampaignModel(List<campaign_model> listPersonM, int status, String message) {
        this.listPersonM = listPersonM;
        this.status = status;
        this.message = message;
    }

    public List<campaign_model> getListPersonM() {
        return listPersonM;
    }

    public void setListPersonM(List<campaign_model> listPersonM) {
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
