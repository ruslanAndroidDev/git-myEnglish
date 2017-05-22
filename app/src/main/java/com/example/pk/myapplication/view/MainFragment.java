package com.example.pk.myapplication.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pk.myapplication.Constants;
import com.example.pk.myapplication.R;
import com.example.pk.myapplication.adapter.MainRecyclerViewAdapter;
import com.example.pk.myapplication.model.VkPosts;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pk on 08.10.2016.
 */
public class MainFragment extends Fragment {
    RecyclerView mainRecyclerView;
    ParseTask parseTask;
    ArrayList<VkPosts> mydata;
    MainRecyclerViewAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VKRequest request = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, "-" + 12648877, VKApiConst.COUNT, 10));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                parseTask = new ParseTask();
                parseTask.execute(response.responseString);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment, container, false);

        mainRecyclerView = (RecyclerView) v.findViewById(R.id.mainRecyclerView);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        return v;
    }

    class ParseTask extends AsyncTask<String, Void, ArrayList<VkPosts>> {
        @Override
        protected ArrayList<VkPosts> doInBackground(String... json) {
            mydata = parseJson(json[0]);
            return mydata;
        }

        @Override
        protected void onPostExecute(ArrayList<VkPosts> vkPostses) {
            super.onPostExecute(vkPostses);
            adapter = new MainRecyclerViewAdapter(mydata, getContext());
            mainRecyclerView.setAdapter(adapter);
            Log.d("tag", "adapter set");
        }

        public ArrayList<VkPosts> parseJson(String json) {
            ArrayList<VkPosts> data = new ArrayList<>();
            JSONObject jsonFromString = null;
            JSONArray itemsArray = null;
            JSONObject photo = null;
            JSONObject responseObj = null;
            String post_text = "";
            VkPosts vkPosts;
            try {
                jsonFromString = new JSONObject(json);
                responseObj = jsonFromString.getJSONObject("response");
                itemsArray = responseObj.getJSONArray("items");
            } catch (JSONException main) {
                Log.d("tag", main.getMessage());
            }
            for (int i = itemsArray.length(); i > 0; i--) {
                try {
                    JSONObject post = itemsArray.getJSONObject(i);
                    post_text = post.getString("text");
                    JSONArray attachments = post.getJSONArray("attachments");
                    JSONObject allatach = attachments.getJSONObject(0);
                    photo = allatach.getJSONObject("photo");
                    if (photo != null) {
                        vkPosts = new VkPosts(post_text, getMaxPhotoQualityUrlFromJson(photo));
                        data.add(0, vkPosts);
                    }
                } catch (JSONException noPhoto) {
                }

            }
            return data;
        }

        private String getMaxPhotoQualityUrlFromJson(JSONObject jsonPhoto) {
            String url = "";
            if (jsonPhoto != null) {
                try {
                    url = jsonPhoto.getString("photo_604");
                    return url;
                } catch (JSONException e) {

                }
                try {
                    url = jsonPhoto.getString("photo_320");
                    return url;
                } catch (JSONException e) {

                }
                try {
                    url = jsonPhoto.getString("photo_130");
                    return url;
                } catch (JSONException e) {

                }
            }
            return "";
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Constants.TRUE_ANSWER = 0;
        Constants.NUM_OF_ITEM = 0;
    }
}
