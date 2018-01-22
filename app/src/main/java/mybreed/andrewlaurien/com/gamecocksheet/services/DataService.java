package mybreed.andrewlaurien.com.gamecocksheet.services;


import java.util.ArrayList;

import mybreed.andrewlaurien.com.gamecocksheet.Model.Station;

/**
 * Created by markprice on 5/21/16.
 */
public class DataService {

    private static DataService ourInstance = new DataService();

    public static DataService getInstance() {
        return ourInstance;
    }

    private DataService() {

    }

    public ArrayList<Station> getFeaturedStations() {
        //Pretend we just downloaded featured stations from the Internet

        ArrayList<Station> list = new ArrayList<>();
        list.add(new Station("Flight Plan (Tunes for Travel)", "breed"));
        list.add(new Station("Two-Wheelin' (Biking Playlist)", "breed"));
        list.add(new Station("Kids Jams (Music for Children", "breed"));

        return list;
    }

    public ArrayList<Station> getRecentStations() {
        ArrayList<Station> list = new ArrayList<>();
        return list;
    }

    public ArrayList<Station> getPartyStations() {
        ArrayList<Station> list = new ArrayList<>();
        return list;
    }


}
