package mybreed.andrewlaurien.com.gamecocksheet.Model;

import android.util.Log;

/**
 * Created by andrewlaurienrsocia on 08/01/2018.
 */

public class Breed {

    private String UniqueID;
    private String DateHatched;
    private String BroodCock;
    private String BroodHen;
    private String NumberHeads;
    private String Markings;
    private String BroodCockWB;
    private String BroodHenWB;
    private String BreedYear = "2018";

    final String DRAWABLE = "drawable/breed";


    public Breed() {
    }

    public Breed(String uniqueID, String dateHatched, String broodCock, String broodCockWB, String broodHen, String broodHenWB, String numberHeads, String markings) {
        UniqueID = uniqueID;
        DateHatched = dateHatched;
        BroodCock = broodCock;
        BroodHen = broodHen;
        NumberHeads = numberHeads;
        Markings = markings;
        BroodCockWB = broodCockWB;
        BroodHenWB = broodHenWB;
        BreedYear = dateHatched.split("-")[0];
        Log.d("BreedYear", BreedYear);
    }

    public String getUniqueID() {
        return UniqueID;
    }

    public void setUniqueID(String uniqueID) {
        UniqueID = uniqueID;
    }

    public String getDateHatched() {
        return DateHatched;
    }

    public void setDateHatched(String dateHatched) {
        DateHatched = dateHatched;
    }

    public String getBroodCock() {
        return BroodCock;
    }

    public void setBroodCock(String broodCock) {
        BroodCock = broodCock;
    }

    public String getBroodHen() {
        return BroodHen;
    }

    public void setBroodHen(String broodHen) {
        BroodHen = broodHen;
    }

    public String getNumberHeads() {
        return NumberHeads;
    }

    public void setNumberHeads(String numberHeads) {
        NumberHeads = numberHeads;
    }

    public String getMarkings() {
        return Markings;
    }

    public void setMarkings(String markings) {
        Markings = markings;
    }


    public String getImgUri() {
        return DRAWABLE;
    }

    public String getBroodCockWB() {
        return BroodCockWB;
    }

    public void setBroodCockWB(String broodCockWB) {
        BroodCockWB = broodCockWB;
    }

    public String getBroodHenWB() {
        return BroodHenWB;
    }

    public void setBroodHenWB(String broodHenWB) {
        BroodHenWB = broodHenWB;
    }

    public String getBreedYear() {
        return BreedYear;
    }

    public void setBreedYear(String breedYear) {
        BreedYear = breedYear;
    }

    public String getDRAWABLE() {
        return DRAWABLE;
    }
}
