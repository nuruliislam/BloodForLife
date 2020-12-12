package capsulestudio.demobloodmanagement.model;

import com.google.gson.annotations.SerializedName;


public class InsertDataResponseModel
{
    @SerializedName("success")
    private int status;
    @SerializedName("message")
    private String message;

    public InsertDataResponseModel(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public InsertDataResponseModel()
    {
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
