package capsulestudio.demobloodmanagement.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by MY_PC on 09/02/2018.
 */

public class ListDonorModel
{
    @SerializedName("donor")
    private List<donor_model> listPersonM;
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;

    public ListDonorModel(List<donor_model> listPersonM, String message, int status) {
        this.listPersonM = listPersonM;
        this.message = message;
        this.status = status;
    }

    public List<donor_model> getListPersonM() {
        return listPersonM;
    }

    public void setListPersonM(List<donor_model> listPersonM) {
        this.listPersonM = listPersonM;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
