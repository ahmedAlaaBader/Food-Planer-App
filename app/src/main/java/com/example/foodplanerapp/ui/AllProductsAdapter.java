package com.example.foodplanerapp.ui;

import android.content.Context;
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
import com.example.foodplanerapp.model.Product;

import java.util.List;

public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.ProductHolder> {

    private List<Product> products;
    private Context context;
    private OnCountryClicked listener;

    public AllProductsAdapter(List<Product> products, Context context,OnCountryClicked listener) {
        this.products = products;
        this.context = context;
        this.listener = listener;
    }

    public void updateCategories(List<Product> products, Context context,OnCountryClicked listener) {
        this.products = products;
        this.context = context;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.country_item, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product currentProduct = products.get(position);
        holder.title.setText(currentProduct.getStrCategory());
        Glide.with(context).load(currentProduct.getStrCategoryThumb()).into(holder.imageView);
       // holder.desc.setText(currentProduct.getStrCategoryDescription());

//        holder.btnAddToFav.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.onClick(currentProduct);
//            }
//        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCatClicked(currentProduct);
            }
        });
        holder.itemView.setOnClickListener(view->{
            listener.onCatClicked(currentProduct);
        });
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    static class ProductHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, desc;
//        Button btnAddToFav;
       //   CardView cardView;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            //cardView = itemView.findViewById(R.id.recyclerView2);
            imageView = itemView.findViewById(R.id.flagImg);
            title = itemView.findViewById(R.id.countryTextViewForCountryItem);
//            desc = itemView.findViewById(R.id.tv_desc2);
//            btnAddToFav = itemView.findViewById(R.id.btn_add_to_fav);
        }
    }
}
