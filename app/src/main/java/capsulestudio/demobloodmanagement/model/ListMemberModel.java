package capsulestudio.demobloodmanagement.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;


/**
 * Created by MY_PC on 09/02/2018.
 */

public class ListMemberModel
{
    @SerializedName("member")
    private List<member_model> listPersonM;
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;

    public ListMemberModel(List<member_model> listPersonM, int status, String message) {
        this.listPersonM = listPersonM;
        this.status = status;
        this.message = message;
    }

    public List<member_model> getListPersonM() {
        return listPersonM;
    }

    public void setListPersonM(List<member_model> listPersonM) {
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
