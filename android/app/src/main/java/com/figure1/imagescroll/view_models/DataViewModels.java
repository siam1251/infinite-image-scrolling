package com.figure1.imagescroll.view_models;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.figure1.imagescroll.model.ImageDataModel;
import com.figure1.imagescroll.network.AuthListener;
import com.figure1.imagescroll.presentation.activities.MainActivity;
import com.figure1.imagescroll.presentation.listeners.OnDataUpdateListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataViewModels extends ViewModel  implements AuthListener {
    public static String TAG = "DataViewModels";
    private OnDataUpdateListener dataUpdateListener;
    private ArrayList<ImageDataModel> imageList = new ArrayList<>();



    @Override
    public void success(JSONObject response) {
        JSONArray jsonArray = null;
        try {
            jsonArray = response.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < jsonArray.length(); i++ ) {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                ImageDataModel model = new ImageDataModel();
                model.setTitle(object.getString("title"));
                if (!object.isNull("type") && object.getString("type").equals("image/gif")) {
                    model.setLinks(object.getString("link"));
                } else {
                    JSONObject imageObject = object.getJSONArray("images").getJSONObject(0);
                    if (imageObject.getString("type").equals("image/gif")) {
                        model.setLinks(imageObject.getString("link"));
                    }

                }
                if (model.getLinks() != null) {
                    imageList.add(model);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        int s = imageList.size();
        dataUpdateListener.onDataUpdate();
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
