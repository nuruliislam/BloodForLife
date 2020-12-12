package capsulestudio.demobloodmanagement.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListAdminModel
{
    @SerializedName("admin")
    private List<admin_model> listPersonAdmin;
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;

    public ListAdminModel(List<admin_model> listPersonAdmin, int status, String message) {
        this.listPersonAdmin = listPersonAdmin;
        this.status = status;
        this.message = message;
    }

    public List<admin_model> getListPersonAdmin() {
        return listPersonAdmin;
    }

    public void setListPersonAdmin(List<admin_model> listPersonAdmin) {
        this.listPersonAdmin = listPersonAdmin;
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
