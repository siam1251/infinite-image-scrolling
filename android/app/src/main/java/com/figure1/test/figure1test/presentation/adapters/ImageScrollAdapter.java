package com.figure1.test.figure1test.presentation.adapters;

import android.support.annotation.Dimension;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;

import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.figure1.test.figure1test.helper.ImageLoaderView;
import com.figure1.test.figure1test.model.ImageDataModel;
import com.figure1.test.figure1test.R;

import java.util.ArrayList;

public class ImageScrollAdapter extends RecyclerView.Adapter<ImageScrollAdapter.ImageViewHolder> {
    ArrayList<ImageDataModel> images;
    public ImageScrollAdapter(ArrayList<ImageDataModel> list) {
        configureImageSize();
        this.images = list;
    }

    private void configureImageSize() {
        ImageLoaderView.width = 500;
        ImageLoaderView.height = 300;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item_image, parent, false));

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.updateView(images.get(position));
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{
        private ImageLoaderView imageLoaderView;
        private TextView titleTextView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            imageLoaderView = itemView.findViewById(R.id.iv_list_item);
            titleTextView   = itemView.findViewById(R.id.tv_list_item_title);

        }
        public void updateView(ImageDataModel model) {
            if (model != null) {
                imageLoaderView.setImageFromURL(model.getLinks(), R.layout.progress, true);
                titleTextView.setText(model.getTitle());
            }
        }
    }
}
