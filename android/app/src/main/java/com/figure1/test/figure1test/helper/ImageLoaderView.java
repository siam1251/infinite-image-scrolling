package com.figure1.test.figure1test.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Size;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class ImageLoaderView extends AppCompatImageView {
    public static int width;
    public static int height;
    public ImageLoaderView(Context context) {
        super(context);
    }

    public ImageLoaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageLoaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public void setImageFromURL(String URL, int placeholder, boolean resize) {
        imageFromURL(URL, placeholder, resize);
    }

    private void imageFromURL(String URL, final int placeholder, final boolean resize) {
        /** Nothing provided, return null */
        if (placeholder == 0 && (URL == null || URL.isEmpty())){
            return;
        }

        if (placeholder == 0) { /** Attempt URL load without placeholder  */
            if (resize) {
                Picasso.with(getContext())
                        .load(URL)
                        .transform(new RectangleTransform())
                        .into(this);
            }
            else {
                Picasso.with(getContext())
                        .load(URL)
                        .into(this);
            }
        }

        else if (resize){ /** Attempt to load URL load with placeholder  */
            Picasso.with(getContext())
                    .load(URL)
                    .placeholder(placeholder)
                    .transform(new RectangleTransform())
                    .into(this);
        }
        else {
            Picasso.with(getContext())
                    .load(URL)
                    .placeholder(placeholder)
                    .into(this);
        }

    }

    private static class RectangleTransform implements Transformation {

        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            Bitmap bitmap;
            if (ImageLoaderView.width != 0 && ImageLoaderView.height != 0) {
                bitmap = Bitmap.createScaledBitmap(source, ImageLoaderView.width, ImageLoaderView.height, true);
            } else {
                bitmap = Bitmap.createScaledBitmap(source, 300, 300, true);

            }

            if (bitmap != source) {
                source.recycle();
            }


            return bitmap;
        }

        @Override
        public String key() {
            return "rectangular_image";
        }
    }
}

