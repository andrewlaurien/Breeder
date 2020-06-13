package mybreed.andrewlaurien.com.gamecocksheet.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mybreed.andrewlaurien.com.gamecocksheet.Common.CommonFunc;
import mybreed.andrewlaurien.com.gamecocksheet.MainActivity;
import mybreed.andrewlaurien.com.gamecocksheet.Model.Breed;
import mybreed.andrewlaurien.com.gamecocksheet.R;

/**
 * Created by markprice on 5/21/16.
 */
public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.StationViewHolder> {

    private ArrayList<Breed> myBreed;

    public StationsAdapter(ArrayList<Breed> breeds) {
        this.myBreed = breeds;
    }

    @Override
    public void onBindViewHolder(StationViewHolder holder, final int position) {
        final Breed breed = myBreed.get(position);
        holder.updateUI(breed);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.selectedBreed = breed;
                CommonFunc.showAddBreedingDialog(v.getContext(),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myBreed.size();
    }

    @Override
    public StationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View stationCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cardview, parent, false);
        return new StationViewHolder(stationCard);
    }


    public class StationViewHolder extends RecyclerView.ViewHolder {

        private ImageView mainImage;
        private TextView titleTextView;

        public StationViewHolder(View itemView) {
            super(itemView);

            this.mainImage = (ImageView) itemView.findViewById(R.id.main_image);
            this.titleTextView = (TextView) itemView.findViewById(R.id.main_text);
        }

        public void updateUI(Breed breed) {
            String uri = breed.getImgUri();
            int resource = mainImage.getResources().getIdentifier(uri, null, mainImage.getContext().getPackageName());
            mainImage.setImageResource(resource);

            titleTextView.setText(breed.getDateHatched());
        }
    }


}
