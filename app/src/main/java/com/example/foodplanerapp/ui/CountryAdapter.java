package com.example.foodplanerapp.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanerapp.R;
import com.example.foodplanerapp.model.Country;
import com.example.foodplanerapp.model.Product;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryHolder>{

    private List<Country> countryList;
    private Context context;
    private OnCountryClicked onCountryClicked;

    public CountryAdapter(List<Country> countryList,Context context, OnCountryClicked onCountryClicked)
    {
        this.context = context;
        this.countryList = countryList;
        this.onCountryClicked = onCountryClicked;
    }

    @NonNull
    @Override
    public CountryAdapter.CountryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.country_item, parent, false);
        return new CountryAdapter.CountryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryHolder holder, int position) {
       // holder.bind(countryList.get(position), onCountryClicked);
        String name = countryList.get(position).getStrArea();
        Country country=countryList.get(position);
        if (name.equals("Unknown"))
            name = "Palestine";
        holder.countryTextView.setText(name);
        holder.flag.setImageResource(getImage(name));

        String finalName = name;
        holder.flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCountryClicked.onCountryClicked(country);
            }
        });


        holder.itemView.setOnClickListener(view->{
            onCountryClicked.onCountryClicked(country);
        });
    }
    public void updateCountries(List<Country> countryList, Context context, OnCountryClicked   onCountryClicked) {
        this.countryList = countryList;
        this.context = context;
        this.onCountryClicked = onCountryClicked;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public class CountryHolder extends RecyclerView.ViewHolder {

        TextView countryTextView;
        ImageView flag;
        CardView cardView;


        public CountryHolder(@NonNull View v) {
            super(v);

            flag = v.findViewById(R.id.flagImg);
            countryTextView = v.findViewById(R.id.countryTextViewForCountryItem);
            Log.i("texxxxt", "TextView " + countryTextView);
            cardView = v.findViewById(R.id.cardViewCountry);
        }
//        void bind(Country country, OnCountryClicked listener) {
//            countryTextView.setText(country.getStrArea());
//            itemView.setOnClickListener(v -> listener.onCountryClicked(country));
//        }
    }

    int getImage(String cuisine)
    {
        int flag;
        switch (cuisine)
        {
            case "American":
                return R.drawable.us;
            case "British":
                return R.drawable.uk;
            case "Canadian":
                return R.drawable.canada;
            case "Chinese":
                return R.drawable.china;
            case "Croatian":
                return R.drawable.croatian;
            case "Dutch":
                return R.drawable.netherlands;
            case "Egyptian":
                return R.drawable.egypt;
            case "Filipino":
                return R.drawable.philippines;
            case "French":
                return R.drawable.france;
            case "Greek":
                return R.drawable.greek;
            case "Indian":
                return R.drawable.indian;
            case "Irish":
                return R.drawable.irish;
            case "Italian":
                return R.drawable.italy;
            case "Jamaican":
                return R.drawable.jamaican;
            case "Japanese":
                return R.drawable.japan;
            case "Kenyan":
                return R.drawable.kenya;
            case "Malaysian":
                return R.drawable.malaysia;
            case "Mexican":
                return R.drawable.mexican;
            case "Moroccan":
                return R.drawable.morocco;
            case "Polish":
                return R.drawable.polish;
            case "Portuguese":
                return R.drawable.portuguese;
            case "Russian":
                return R.drawable.russia;
            case "Spanish":
                return R.drawable.spanish;
            case "Thai":
                return R.drawable.thailand;
            case "Tunisian":
                return R.drawable.tunisian;
            case "Turkish":
                return R.drawable.turkey;
            case "Vietnamese":
                return R.drawable.vietnam;
            default:
                return R.drawable.palestine;
        }
    }
}
