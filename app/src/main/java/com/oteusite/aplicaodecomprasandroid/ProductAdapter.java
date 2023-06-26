package com.oteusite.aplicaodecomprasandroid;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private List<Product> cartProducts;


    public ProductAdapter(Context context, List<Product> cartProducts) {
        this.context = context;
        this.cartProducts = cartProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = cartProducts.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewProduct;
        private TextView textViewName;
        private TextView textViewPrice;
        private TextView textViewQuantity;
        private TextView textViewTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageView_product);
            textViewName = itemView.findViewById(R.id.textView_name);
            textViewPrice = itemView.findViewById(R.id.textView_price);
            textViewQuantity = itemView.findViewById(R.id.textView_quantity);
            textViewTotal = itemView.findViewById(R.id.textView_total);
        }

        public void bind(Product product) {
            // Set the data to the views
            Glide.with(context)
                    .load(product.getImageResource())
                    .placeholder(R.drawable.default_image) // Imagem de placeholder enquanto a imagem real está sendo carregada
                    .error(R.drawable.a1) // Imagem de erro se a imagem não puder ser carregada
                    .into(imageViewProduct);
            textViewName.setText(product.getName());
            textViewPrice.setText("Price: $" + product.getPrice());
            textViewQuantity.setText("Quantity: " + product.getQuantity());
            double totalProductPrice = Double.parseDouble(product.getPrice()) * product.getQuantity();
            textViewTotal.setText("Total: $" + totalProductPrice);
        }



    }
}
