package com.example.momooczzi_fe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.momooczzi_fe.R;
import com.example.momooczzi_fe.network.PlaceResult;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private List<PlaceResult> placeList;

    public PlaceAdapter(List<PlaceResult> placeList) {
        this.placeList = placeList;
    }

    public void setPlaceList(List<PlaceResult> placeList) {
        this.placeList = placeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        PlaceResult place = placeList.get(position);
        holder.nameText.setText(place.getName());
        holder.addrText.setText(place.getVicinity());
    }

    @Override
    public int getItemCount() {
        return placeList != null ? placeList.size() : 0;
    }

    static class PlaceViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, addrText;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.tv_place_name);
            addrText = itemView.findViewById(R.id.tv_place_addr);
        }
    }
}
