package mybreed.andrewlaurien.com.gamecocksheet.Fragment;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mybreed.andrewlaurien.com.gamecocksheet.Adapter.FightAdapter;
import mybreed.andrewlaurien.com.gamecocksheet.Adapter.StationsAdapter;
import mybreed.andrewlaurien.com.gamecocksheet.Adapter.WarriorsAdapter;
import mybreed.andrewlaurien.com.gamecocksheet.MainActivity;
import mybreed.andrewlaurien.com.gamecocksheet.Model.Fight;
import mybreed.andrewlaurien.com.gamecocksheet.Model.Station;
import mybreed.andrewlaurien.com.gamecocksheet.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SectionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private static final String ARG_STATION_TYPE = "station_type";

    public static final int STATION_TYPE_FEATURED = 0;
    public static final int STATION_TYPE_RECENT = 1;
    public static final int STATION_TYPE_PARTY = 2;

    private int stationType;

    // TODO: Rename and change types of parameters


    ArrayList<Station> DummyBreedList = new ArrayList<>();
    ArrayList<Fight> DummyFightList = new ArrayList<>();
    public static StationsAdapter adapter = null;
    public static FightAdapter fightAdapter = null;
    public static WarriorsAdapter warriorsAdapter = null;


    public SectionFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SectionFragment newInstance(int section) {
        SectionFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STATION_TYPE, section);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stationType = getArguments().getInt(ARG_STATION_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_section, container, false);

        RecyclerView mrecyclerview = view.findViewById(R.id.mrecyclerview);
        mrecyclerview.setHasFixedSize(true);


        DummyBreedList.add(new Station("March Born", "breed"));
        DummyBreedList.add(new Station("December Born", "breed"));
        DummyBreedList.add(new Station("November Born", "breed"));

//        DummyBreedList.add(new Breed("00001", "01-03-2018", "Kelso", "01", "Sweater", "06", "100", "Left toe"));
//        DummyBreedList.add(new Breed("00002", "01-04-2018", "Kelso", "02", "Sweater", "03", "100", "Left toe"));
//        DummyBreedList.add(new Breed("00003", "01-05-2018", "Kelso", "01", "Sweater", "05", "100", "Left toe"));
//
//        DummyFightList.add(new Fight("Sinulog 3 Cock"));
//        DummyFightList.add(new Fight("Clean Up 5 Stag Derby"));
//        DummyFightList.add(new Fight("3 Cock Derby"));

//        if (MainActivity.myBreed == null) {
//            MainActivity.myBreed = new ArrayList<>();
//            MainActivity.myBreed.add(new Breed("00001", "01-03-2018", "Kelso", "01", "Sweater", "06", "100", "Left toe"));
//            MainActivity.myBreed.add(new Breed("00002", "01-04-2018", "Kelso", "02", "Sweater", "03", "100", "Left toe"));
//            MainActivity.myBreed.add(new Breed("00003", "01-05-2018", "Kelso", "01", "Sweater", "05", "100", "Left toe"));
//
//        }
//
//        if (MainActivity.myFights == null) {
//            MainActivity.myFights = new ArrayList<>();
//            MainActivity.myFights.add(new Fight("Sinulog 3 Cock"));
//            MainActivity.myFights.add(new Fight("Clean Up 5 Stag Derby"));
//            MainActivity.myFights.add(new Fight("3 Cock Derby"));
//        }


        if (stationType == STATION_TYPE_FEATURED) {
            //if (MainActivity.myBreed == null) {
            //    adapter = new StationsAdapter(DummyBreedList);
            //} else {
            adapter = new StationsAdapter(MainActivity.myBreed);
            //}

            mrecyclerview.addItemDecoration(new HorizontalSpaceItemDecorator(30));
            mrecyclerview.setAdapter(adapter);
        } else if (stationType == STATION_TYPE_RECENT) {

            //if (MainActivity.myFights == null) {
            //    fightAdapter = new FightAdapter(getActivity(), DummyFightList);
            //} else {
            fightAdapter = new FightAdapter(getActivity(), MainActivity.myFights);
            //}
            mrecyclerview.addItemDecoration(new HorizontalSpaceItemDecorator(30));
            mrecyclerview.setAdapter(fightAdapter);
        } else {
            // adapter = new StationsAdapter(DummyBreedList);
            warriorsAdapter = new WarriorsAdapter(MainActivity.myGameCock);
            mrecyclerview.addItemDecoration(new HorizontalSpaceItemDecorator(30));
            mrecyclerview.setAdapter(warriorsAdapter);
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mrecyclerview.setLayoutManager(layoutManager);


        return view;
    }

    class HorizontalSpaceItemDecorator extends RecyclerView.ItemDecoration {

        private final int spacer;

        public HorizontalSpaceItemDecorator(int spacer) {
            this.spacer = spacer;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.right = spacer;
        }
    }

}
