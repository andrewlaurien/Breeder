package mybreed.andrewlaurien.com.gamecocksheet.Model;

/**
 * Created by andrewlaurienrsocia on 09/01/2018.
 */

public class GameCock {


    private String DateHatch;
    private String WingBand;
    private String Bloodline;
    private String Markings;
    private String Age;
    private String Win;
    private String SireWB;
    private String DamWB;
    private String ParentCockBL;
    private String ParentHenBL;
    private String yearBorn;
    private String fightsWon;

    public GameCock() {
    }

    public String getWingBand() {
        return WingBand;
    }

    public void setWingBand(String wingBand) {
        WingBand = wingBand;
    }

    public String getBloodline() {
        return Bloodline;
    }

    public void setBloodline(String bloodline) {
        Bloodline = bloodline;
    }

    public String getMarkings() {
        return Markings;
    }

    public void setMarkings(String markings) {
        Markings = markings;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getWin() {
        return Win;
    }

    public void setWin(String win) {
        Win = win;
    }

    public String getDateHatch() {
        return DateHatch;
    }

    public void setDateHatch(String dateHatch) {
        DateHatch = dateHatch;
    }

    public String getSireWB() {
        return SireWB;
    }

    public void setSireWB(String sireWB) {
        SireWB = sireWB;
    }

    public String getDamWB() {
        return DamWB;
    }

    public void setDamWB(String damWB) {
        DamWB = damWB;
    }

    public String getParentCockBL() {
        return ParentCockBL;
    }

    public void setParentCockBL(String parentCockBL) {
        ParentCockBL = parentCockBL;
    }

    public String getParentHenBL() {
        return ParentHenBL;
    }

    public void setParentHenBL(String parentHenBL) {
        ParentHenBL = parentHenBL;
    }

    public String getYearBorn() {
        return yearBorn;
    }

    public void setYearBorn(String yearBorn) {
        this.yearBorn = yearBorn;
    }

    public String getFightsWon() {
        if (fightsWon == null) {
            return "0";
        }
        return fightsWon;
    }

    public void setFightsWon(String fightsWon) {
        this.fightsWon = fightsWon;
    }
}
