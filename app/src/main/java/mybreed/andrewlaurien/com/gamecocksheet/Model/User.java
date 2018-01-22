package mybreed.andrewlaurien.com.gamecocksheet.Model;

/**
 * Created by andrewlaurienrsocia on 10/01/2018.
 */

public class User {

    private String mobile;
    private String Name;
    private String GameFarm;
    private String Location;


    public User() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGameFarm() {
        return GameFarm;
    }

    public void setGameFarm(String gameFarm) {
        GameFarm = gameFarm;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}

