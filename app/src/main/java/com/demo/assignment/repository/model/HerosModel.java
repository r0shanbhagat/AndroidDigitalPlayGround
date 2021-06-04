package com.demo.assignment.repository.model;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HerosModel extends BaseObservable {


    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private String image;
    @SerializedName("rating")
    private String rating;
    @SerializedName("releaseYear")
    private String releaseYear;
    @SerializedName("genre")
    private List<String> genre = null;

    @BindingAdapter(value = {"imageUrl"}, requireAll = false)
    public static void loadImage(ImageView iv, String imageUrl) {
        Glide.with(iv.getContext())
                .load(imageUrl)
                .into(iv);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

}