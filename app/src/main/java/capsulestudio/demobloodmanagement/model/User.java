package capsulestudio.demobloodmanagement.model;

import com.google.gson.annotations.SerializedName;

public class User
{
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("blood")
    private String blood_group;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;
    @SerializedName("address")
    private String address;
    @SerializedName("city")
    private String city;

    @SerializedName("donation")
    private String lastDonation;

    public User(int id, String name, String blood_group, String email, String phone, String address, String city, String lastDonation) {
        this.id = id;
        this.name = name;
        this.blood_group = blood_group;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.lastDonation = lastDonation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLastDonation() {
        return lastDonation;
    }

    public void setLastDonation(String lastDonation) {
        this.lastDonation = lastDonation;
    }
}
