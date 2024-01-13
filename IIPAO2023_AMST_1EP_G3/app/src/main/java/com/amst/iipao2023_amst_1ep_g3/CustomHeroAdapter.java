package com.amst.iipao2023_amst_1ep_g3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomHeroAdapter extends ArrayAdapter<Superhero> {

    public CustomHeroAdapter(Context context, List<Superhero> superheroes) {
        super(context, 0, superheroes);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_custom_list_view, parent, false);
        }

        Superhero superhero = getItem(position);

        TextView textViewName = convertView.findViewById(R.id.textViewHeroe);
        ImageView imageViewHero = convertView.findViewById(R.id.imgHeroe);

        if (superhero != null) {
            textViewName.setText(superhero.getName());
            // Load image using Glide or Picasso
            Picasso.get().load(superhero.getImageUrl()).into(imageViewHero);
        }

        return convertView;
    }


}
