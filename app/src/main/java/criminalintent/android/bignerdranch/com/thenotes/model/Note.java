package criminalintent.android.bignerdranch.com.thenotes.model;


import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private String mTextNote;
    private Date mDate;

    public Note(){
        mDate = new Date();
    }

    public String getTextNote() {
        return mTextNote;
    }

    public void setTextNote(String mTextNote) {
        this.mTextNote = mTextNote;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }
}
