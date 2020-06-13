package mybreed.andrewlaurien.com.gamecocksheet.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mybreed.andrewlaurien.com.gamecocksheet.Common.CommonFunc;
import mybreed.andrewlaurien.com.gamecocksheet.MainActivity;
import mybreed.andrewlaurien.com.gamecocksheet.Model.GameCock;
import mybreed.andrewlaurien.com.gamecocksheet.R;

/**
 * Created by andrewlaurienrsocia on 09/01/2018.
 */

public class WarriorsAdapter extends RecyclerView.Adapter<WarriorsAdapter.WarriorsHolder> {


    ArrayList<GameCock> mGameCocks;
    Context mcontext;

    public WarriorsAdapter(ArrayList<GameCock> gameCocks) {
        mGameCocks = gameCocks;
    }

    @Override
    public WarriorsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cardview, parent, false);
        return new WarriorsHolder(view);

    }

    @Override
    public void onBindViewHolder(WarriorsHolder holder, final int position) {

        final GameCock gameCock = mGameCocks.get(position);

        holder.mainImage.setImageResource(R.drawable.breed);
        holder.titleTextView.setText(gameCock.getWingBand());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.selectedCock = gameCock;
                CommonFunc.showAddCock(view.getContext(), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mGameCocks.size();
    }

    public class WarriorsHolder extends RecyclerView.ViewHolder {

        private ImageView mainImage;
        private TextView titleTextView;

        public WarriorsHolder(View itemView) {
            super(itemView);
            this.mainImage = (ImageView) itemView.findViewById(R.id.main_image);
            this.titleTextView = (TextView) itemView.findViewById(R.id.main_text);
        }
    }

}
