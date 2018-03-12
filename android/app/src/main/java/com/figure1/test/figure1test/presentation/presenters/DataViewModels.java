package com.figure1.test.figure1test.presentation.presenters;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.figure1.test.figure1test.model.GalleryResponse;
import com.figure1.test.figure1test.model.GalleryPostResponse;
import com.figure1.test.figure1test.model.ImageDataModel;
import com.figure1.test.figure1test.model.ImageResponse;
import com.figure1.test.figure1test.network_layer.AuthListener;
import com.figure1.test.figure1test.presentation.listeners.OnDataUpdateListener;

import java.util.ArrayList;

public class DataViewModels extends ViewModel  implements AuthListener {
    public static String TAG = "DataViewModels";
    private OnDataUpdateListener dataUpdateListener;
    private ArrayList<ImageDataModel> imageList = new ArrayList<>();
    private int insertionIndex = 0;
    private int newItemsCount;



    @Override
    public void success(Object response) {
        if (!(response instanceof GalleryResponse)) return;
        GalleryResponse gallery = (GalleryResponse) response;
        int count = 0;
        insertionIndex = imageList.size();
        for (GalleryPostResponse post: gallery.getData()) {

                ImageDataModel model = new ImageDataModel();
                model.setTitle(post.getTitle());

                if (post.getImages().size() > 0) {
                    ImageResponse imageResponse = post.getImages().get(0);
                    if (imageResponse.getType().equals("image/jpeg")
                            || imageResponse.getType().equals("image/png")
                            || imageResponse.getType().equals("image/jpg")) {
                        model.setLinks(imageResponse.getLink());
                    }
                }


                if (!model.getLinks().isEmpty()) {
                    count++;
                    imageList.add(model);
                    // for pagination purpose we are only fetching 11 items from each page
                    if (count > 10) {
                        break;
                    }
                }

            }
            newItemsCount = imageList.size();
            dataUpdateListener.onDataUpdate(insertionIndex, newItemsCount);

    }

    @Override
    public void failure(String failureMsg) {
        Log.d(TAG, "failed");
    }

    public void setDataUpdateListener(OnDataUpdateListener dataUpdateListener) {
        this.dataUpdateListener = dataUpdateListener;
    }

    public ArrayList<ImageDataModel> getImageList() {
        return imageList;
    }
}
