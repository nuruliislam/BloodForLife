package capsulestudio.demobloodmanagement.network;

import capsulestudio.demobloodmanagement.model.InsertDataResponseModel;
import capsulestudio.demobloodmanagement.model.ListAdminModel;
import capsulestudio.demobloodmanagement.model.ListCampaignModel;
import capsulestudio.demobloodmanagement.model.ListDonationModel;
import capsulestudio.demobloodmanagement.model.ListDonorModel;
import capsulestudio.demobloodmanagement.model.ListHospitalModel;
import capsulestudio.demobloodmanagement.model.ListMemberModel;
import capsulestudio.demobloodmanagement.model.ListRequestModel;
import capsulestudio.demobloodmanagement.model.MSG;
import capsulestudio.demobloodmanagement.model.User;
import capsulestudio.demobloodmanagement.model.member_model;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface APIService
{
    @FormUrlEncoded
    @POST("phpFile/view/signup.php")
    Call<MSG> userSignUp(@Field("name") String name,
                         @Field("email") String email, @Field("phone") String phone,
                         @Field("password") String password, @Field("address") String address, @Field("city") String city,
                         @Field("postal") String postal, @Field("country") String country);

    @FormUrlEncoded
    @POST("phpFile/view/signupDonors.php")
    Call<MSG> donorSignUp(@Field("name") String name,@Field("group") String group,
                         @Field("email") String email, @Field("phone") String phone,
                         @Field("password") String password,@Field("city") String city,
                         @Field("lastDonation") String lastDonation);

    @FormUrlEncoded
    @POST("phpFile/view/add_hospital.php")
    Call<MSG> addHospital(@Field("name") String name,
                         @Field("email") String email, @Field("phone") String phone,
                         @Field("password") String password, @Field("address") String address, @Field("city") String city);

    @FormUrlEncoded
    @POST("phpFile/view/add_admin.php")
    Call<MSG> addAdmin(@Field("name") String name,
                          @Field("email") String email, @Field("password") String password);

    /*@FormUrlEncoded
    @POST("newaz/view/orders.php")
    Call<UserResponseModel> userOrder(@Field("id") int  id,
                                      @Field("phone") String phone, @Field("address") String address,
                                      @Field("city") String city, @Field("postal") String postal, @Field("country") String country,@Field("collection") String collection);*/

    // Login Method's
    @FormUrlEncoded
    @POST("phpFile/view/login.php")
    Call<MSG> userLogIn(@Field("email") String email,
                        @Field("password") String password);


    @FormUrlEncoded
    @POST("phpFile/view/donor_login.php")
    Call<MSG> donorLogIn(@Field("email") String email,
                        @Field("password") String password);

    @FormUrlEncoded
    @POST("phpFile/view/hospital_login.php")
    Call<MSG> hospitalLogIn(@Field("email") String email,
                         @Field("password") String password);

    @FormUrlEncoded
    @POST("phpFile/view/admin_login.php")
    Call<MSG> adminLogIn(@Field("email") String email,
                            @Field("password") String password);



    // End Login Method




    //Adding Member
    @FormUrlEncoded
    @POST("phpFile/adding_member.php")
    Call<InsertDataResponseModel> insertMemberData(@Field("a") String A, @Field("b") String B, @Field("c") String C, @Field("d") String D,
                                                @Field("e") String E, @Field("f") String F, @Field("g") String G, @Field("h") String H);

        @FormUrlEncoded
    @POST("phpFile/blood_request.php")
    Call<InsertDataResponseModel> insertReqData(@Field("name") String name,@Field("contact") String contact,@Field("a") String A, @Field("b") String B, @Field("c") String C, @Field("d") String D,
                                             @Field("e") String E, @Field("f") String F, @Field("g") String G, @Field("h") String H);

    @FormUrlEncoded
    @POST("phpFile/add_campaign.php")
    Call<InsertDataResponseModel> insertCampData(@Field("a") String A, @Field("b") String B, @Field("c") String C, @Field("d") String D,
                                                @Field("e") String E,@Field("f") String F,@Field("g") String G,@Field("h") String H);

    //For Donor

    @GET("phpFile/fetch_donor.php")
    Call<ListDonorModel> getAllDonor();

    @FormUrlEncoded
    @POST ("phpFile/delete_donor.php")
    Call<InsertDataResponseModel> deleteDonor(@Field("email") String email);

    @FormUrlEncoded
    @POST ("phpFile/edit_donor.php")
    Call<InsertDataResponseModel> editPost(@Field("personname") String personName,@Field("address") String address,@Field("group") String group,@Field("contact") String contact,@Field("email") String email);
    // End Donor

    // Fetch Member Request
    @GET("phpFile/fetch_member_request.php")
    Call<ListMemberModel> getAllMemberRequest();


    //Member Start
    @GET("phpFile/fetch_member.php")
    Call<ListMemberModel> getAllMember();

    @FormUrlEncoded
    @POST ("phpFile/delete_member.php")
    Call<InsertDataResponseModel> deleteMember(@Field("email") String email);

    @FormUrlEncoded
    @POST ("phpFile/edit_member.php")
    Call<InsertDataResponseModel> editMember(@Field("personname") String personName,@Field("email") String email,@Field("address") String group,@Field("contact") String contact);

    // End Member

    //Hospital Start

    @GET("phpFile/fetch_hospital.php")
    Call<ListHospitalModel> getAllHospital();

    @FormUrlEncoded
    @POST ("phpFile/delete_hospital.php")
    Call<InsertDataResponseModel> deleteHospital(@Field("email") String email);

    @FormUrlEncoded
    @POST ("phpFile/edit_hospital.php")
    Call<InsertDataResponseModel> editHospital(@Field("personname") String personName,@Field("contact") String contact,@Field("address") String group,@Field("city") String city,@Field("id") int id);

    // End Hospital


    //Campaign Start

    @GET("phpFile/fetch_campaign.php")
    Call<ListCampaignModel> getAllCampaign();

    @FormUrlEncoded
    @POST ("phpFile/delete_campaign.php")
    Call<InsertDataResponseModel> deleteCampaign(@Field("id") int id);

    @FormUrlEncoded
    @POST ("phpFile/edit_campaign.php")
    Call<InsertDataResponseModel> editCampaign(@Field("a") String a,@Field("b") String b,@Field("c") String c,@Field("d") String d,@Field("e") String e,@Field("f") int f);

    // Campaign End

    //GET SINGLE DONOR
    @GET("phpFile/view/getDonor.php")
    Call<User> getDonorData(@Query("email")  String email);

    //GET SINGLE MEMBER
    @GET("phpFile/view/getMember.php")
    Call<member_model> getMemberData(@Query("email")  String email);


    //Update Donation donor_email
    @FormUrlEncoded
    @POST("phpFile/view/update_donation.php")
    Call<MSG> updateDonation(@Field("email") String donor_email,@Field("name") String name,
                         @Field("group") String group, @Field("patient") String patient,
                         @Field("date") String date, @Field("disease") String disease,
                         @Field("hospital") String hospital);


    //Donation History Start

    @GET("phpFile/fetch_donationHistory.php")
    Call<ListDonationModel> getAllDonationHistory();

    @FormUrlEncoded
    @POST ("phpFile/delete_donationHistory.php")
    Call<InsertDataResponseModel> deleteDonationHistory(@Field("id") int id);

    @FormUrlEncoded
    @POST ("phpFile/edit_donationHistory.php")
    Call<InsertDataResponseModel> editDonationHistory(@Field("a") String a,@Field("b") String b,@Field("c") String c,@Field("d") int d);

    //Donation History End

    //Blood Request Start

    @GET("phpFile/fetch_request.php")
    Call<ListRequestModel> getAllBloodRequest();

    @FormUrlEncoded
    @POST ("phpFile/delete_request.php")
    Call<InsertDataResponseModel> deleteBloodRequest(@Field("id") int id);

    @FormUrlEncoded
    @POST ("phpFile/edit_request.php")
    Call<InsertDataResponseModel> editBloodRequest(@Field("a") String a,@Field("b") String b,@Field("c") String c,@Field("d") String d,@Field("e") String e,@Field("f") int f);

    //Blood Request End

    //Blood Request Start

    @GET("phpFile/fetch_admin.php")
    Call<ListAdminModel> getAllAdmin();

    @FormUrlEncoded
    @POST ("phpFile/delete_admin.php")
    Call<InsertDataResponseModel> deleteAdmin(@Field("id") int id);

    @FormUrlEncoded
    @POST ("phpFile/edit_admin.php")
    Call<InsertDataResponseModel> editAdmin(@Field("personname") String personName,@Field("email") String email,@Field("contact") String contact,@Field("address") String group,@Field("city") String city);

    //Blood Request End


    // For Search Donor
    @GET("phpFile/search_donor.php")
    Call<ListDonorModel> getAllSearchDonor(@Query("group") String group);
}