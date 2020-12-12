package capsulestudio.demobloodmanagement.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListRequestModel
{
    @SerializedName("request")
    private List<request_model> listPersonM;
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;

    public ListRequestModel(List<request_model> listPersonM, int status, String message) {
        this.listPersonM = listPersonM;
        this.status = status;
        this.message = message;
    }

    public List<request_model> getListPersonM() {
        return listPersonM;
    }

    public void setListPersonM(List<request_model> listPersonM) {
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
