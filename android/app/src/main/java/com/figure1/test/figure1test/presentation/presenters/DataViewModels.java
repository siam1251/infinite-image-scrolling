package com.figure1.test.figure1test.presentation.presenters;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.figure1.test.figure1test.model.ImageDataModel;
import com.figure1.test.figure1test.network_layer.AuthListener;
import com.figure1.test.figure1test.presentation.listeners.OnDataUpdateListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataViewModels extends ViewModel  implements AuthListener {
    public static String TAG = "DataViewModels";
    private OnDataUpdateListener dataUpdateListener;
    private ArrayList<ImageDataModel> imageList = new ArrayList<>();
    private int insertionIndex = 0;
    private int newItemsCount;



    @Override
    public void success(JSONObject response) {
        JSONArray jsonArray = null;
        try {
            jsonArray = response.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        int count = 0;
        insertionIndex = imageList.size();
        for (int i = 0; i < jsonArray.length(); i++ ) {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                ImageDataModel model = new ImageDataModel();
                model.setTitle(object.getString("title"));
                if (!object.isNull("type") && object.getString("type").equals("image/gif")) {
                    continue;
                } else {
                    JSONObject imageObject = object.getJSONArray("images").getJSONObject(0);
                    if (imageObject.getString("type").equals("image/jpeg")
                            || imageObject.getString("type").equals("image/png")
                            || imageObject.getString("type").equals("image/jpg")) {
                        model.setLinks(imageObject.getString("link"));
                    }

                }
                if (model.getLinks() != null) {
                    count++;
                    imageList.add(model);
                    if (count > 10) {
                        break;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
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
