package capsulestudio.demobloodmanagement.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class campaign_model
{
    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("organizer")
    private String mOrganizer;

    @SerializedName("contact")
    private String mContact;

    @SerializedName("place")
    private String mPlace;

    @SerializedName("date")
    private String mDate;

    @SerializedName("start")
    private String mStart;

    @SerializedName("end")
    private String mEnd;

    @SerializedName("sponsor")
    private String mSponsor;

    public campaign_model(int mId, String mName, String mOrganizer, String mContact, String mPlace, String mDate, String mStart, String mEnd, String mSponsor) {
        this.mId = mId;
        this.mName = mName;
        this.mOrganizer = mOrganizer;
        this.mContact = mContact;
        this.mPlace = mPlace;
        this.mDate = mDate;
        this.mStart = mStart;
        this.mEnd = mEnd;
        this.mSponsor = mSponsor;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmOrganizer() {
        return mOrganizer;
    }

    public void setmOrganizer(String mOrganizer) {
        this.mOrganizer = mOrganizer;
    }

    public String getmContact() {
        return mContact;
    }

    public void setmContact(String mContact) {
        this.mContact = mContact;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmPlace()
    {
        Log.d("nurul",mPlace);
        return mPlace;
    }

    public void setmPlace(String mPlace) {
        this.mPlace = mPlace;
    }

    public String getmDate()
    {
        Log.d("nurul",mDate);
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmStart() {
        return mStart;
    }

    public void setmStart(String mStart) {
        this.mStart = mStart;
    }

    public String getmEnd() {
        return mEnd;
    }

    public void setmEnd(String mEnd) {
        this.mEnd = mEnd;
    }

    public String getmSponsor() {
        return mSponsor;
    }

    public void setmSponsor(String mSponsor) {
        this.mSponsor = mSponsor;
    }
}
