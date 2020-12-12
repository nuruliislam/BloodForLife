package capsulestudio.demobloodmanagement.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListHospitalModel
{
    @SerializedName("hospital")
    private List<hospital_model> listHospitalModel;
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;

    public ListHospitalModel(List<hospital_model> listHospitalModel, int status, String message) {
        this.listHospitalModel = listHospitalModel;
        this.status = status;
        this.message = message;
    }

    public List<hospital_model> getListHospitalModel() {
        return listHospitalModel;
    }

    public void setListHospitalModel(List<hospital_model> listHospitalModel) {
        this.listHospitalModel = listHospitalModel;
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
