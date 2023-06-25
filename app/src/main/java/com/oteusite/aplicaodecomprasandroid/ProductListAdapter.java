package com.oteusite.aplicaodecomprasandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductListAdapter extends BaseAdapter {
    private Context context;
    private List<Product> productList;

    public ProductListAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_product, parent, false);
        }

        TextView productNameTextView = convertView.findViewById(R.id.product_name);
        TextView productPriceTextView = convertView.findViewById(R.id.product_price);
        ImageView productImageView = convertView.findViewById(R.id.img_product);

        Product product = productList.get(position);
        productNameTextView.setText(product.getName());
        productPriceTextView.setText(product.getPrice());

        // Use a biblioteca Glide para carregar e exibir a imagem
        Glide.with(context)
                .load(product.getImageResource())
                .placeholder(R.drawable.default_image) // Imagem de placeholder, caso a imagem principal não esteja disponível
                .error(R.drawable.pag3) // Imagem de erro, caso ocorra algum problema ao carregar a imagem
                .into(productImageView);

        return convertView;
    }

}
