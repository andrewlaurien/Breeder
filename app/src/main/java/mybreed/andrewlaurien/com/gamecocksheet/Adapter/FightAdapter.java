package mybreed.andrewlaurien.com.gamecocksheet.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mybreed.andrewlaurien.com.gamecocksheet.Common.CommonFunc;
import mybreed.andrewlaurien.com.gamecocksheet.MainActivity;
import mybreed.andrewlaurien.com.gamecocksheet.Model.Fight;
import mybreed.andrewlaurien.com.gamecocksheet.R;

/**
 * Created by andrewlaurienrsocia on 09/01/2018.
 */

public class FightAdapter extends RecyclerView.Adapter<FightAdapter.FightHolder> {

    Context mcontext;
    ArrayList<Fight> mFights;

    public FightAdapter(Context c, ArrayList<Fight> fights) {
        mcontext = c;
        mFights = fights;
    }

    @Override
    public FightHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.list_cardview, parent, false);

        return new FightHolder(view);

    }

    @Override
    public void onBindViewHolder(FightHolder holder, final int position) {

        final Fight fight = mFights.get(position);
        holder.mainImage.setImageResource(R.drawable.breed);
        holder.titleTextView.setText(fight.getFightName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.selectedFight = fight;
                CommonFunc.showFightDialog(mcontext, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFights.size();
    }

    public class FightHolder extends RecyclerView.ViewHolder {

        private ImageView mainImage;
        private TextView titleTextView;


        public FightHolder(View itemView) {
            super(itemView);

            this.mainImage = (ImageView) itemView.findViewById(R.id.main_image);
            this.titleTextView = (TextView) itemView.findViewById(R.id.main_text);

        }
    }

}
