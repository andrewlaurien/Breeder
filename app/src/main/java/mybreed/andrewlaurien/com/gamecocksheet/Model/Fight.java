package mybreed.andrewlaurien.com.gamecocksheet.Model;

import java.util.ArrayList;

import mybreed.andrewlaurien.com.gamecocksheet.Common.CommonFunc;

/**
 * Created by andrewlaurienrsocia on 09/01/2018.
 */

public class Fight {


    private String UniqueID;
    private String fightName;
    private String fightDate;
    private String entryName;
    private String numberOfFights;
    private String prize;
    private ArrayList<GameCock> gameCocks;
    private String fightLocation;
    private String fightScore;
    private String fightYear;
    private String Result;
    private String AmountWin;

    public Fight() {
    }

    public Fight(String fightName) {
        this.fightName = fightName;
    }

    public String getFightName() {
        return fightName;
    }

    public void setFightName(String fightName) {
        this.fightName = fightName;
    }

    public String getFightDate() {
        return fightDate;
    }

    public void setFightDate(String fightDate) {
        this.fightDate = fightDate;
    }

    public String getNumberOfFights() {
        return numberOfFights;
    }

    public void setNumberOfFights(String numberOfFights) {
        this.numberOfFights = numberOfFights;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public ArrayList<GameCock> getGameCocks() {
        return gameCocks;
    }

    public void setGameCocks(ArrayList<GameCock> gameCocks) {
        this.gameCocks = gameCocks;
    }

    public String getFightLocation() {
        return fightLocation;
    }

    public void setFightLocation(String fightLocation) {
        this.fightLocation = fightLocation;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getFightScore() {
        return fightScore;
    }

    public void setFightScore(String fightScore) {
        this.fightScore = fightScore;
    }

    public String getFightYear() {
        return fightYear;
    }

    public void setFightYear(String fightYear) {
        this.fightYear = fightYear;
    }

    public String getUniqueID() {
        return UniqueID;
    }

    public void setUniqueID(String uniqueID) {
        UniqueID = uniqueID;
    }

    public String getResult() {
        if (Result == null) {
            return Result = "NA";
        }
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getAmountWin() {
        if (AmountWin == null) {
            return AmountWin = "0";
        }
        return AmountWin;
    }

    public void setAmountWin(String amountWin) {
        AmountWin = amountWin;
    }

    public float getPrizeFloat() {
        if (CommonFunc.isNumeric(prize)) {
            return Float.parseFloat(prize);
        }
        return 0;
    }

    public float getFloatAmountWin() {
        if (CommonFunc.isNumeric(AmountWin)) {
            return Float.parseFloat(AmountWin);
        }
        return 0;
    }
}

