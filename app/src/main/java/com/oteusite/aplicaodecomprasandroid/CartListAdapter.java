package com.oteusite.aplicaodecomprasandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CartListAdapter extends ArrayAdapter<Product> {
    private Context context;
    private List<Product> productList;

    public CartListAdapter(Context context, List<Product> productList) {
        super(context, 0, productList);
        this.context = context;
        this.productList = productList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_cart, parent, false);
        }

        Product product = productList.get(position);

        TextView nameTextView = convertView.findViewById(R.id.product_name);
        TextView quantityTextView = convertView.findViewById(R.id.product_quantity);
        TextView priceTextView = convertView.findViewById(R.id.product_price);

        nameTextView.setText(product.getName());
        quantityTextView.setText("Quantity: " + product.getQuantity());
        priceTextView.setText("Price: " + product.getPrice());

        return convertView;
    }
}
