package com.oteusite.aplicaodecomprasandroid;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private int id;
    private String name;
    private String price;
    private String imageResource;
    private int quantity;
    private int orderId;
    private String category;

    public Product(int id, String name, String price, String imageResource, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageResource = imageResource;
        this.category = category;

    }

    public Product(int id, String name, double price, String imageResource, int quantity, String category) {
        this.id = id;
        this.name = name;
        this.price = String.valueOf(price);
        this.imageResource = imageResource;
        this.quantity = quantity;
        this.category = category;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    // Implement the Parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(imageResource);
        dest.writeInt(quantity);
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    private Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readString();
        imageResource = in.readString();
        quantity = in.readInt();
    }

}
