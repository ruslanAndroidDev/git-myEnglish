package com.example.pk.myapplication.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.pk.myapplication.R;
import com.example.pk.myapplication.data.NasaService;
import com.example.pk.myapplication.databinding.MainFragmentBinding;
import com.example.pk.myapplication.model.NasaArticle;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pk on 08.10.2016.
 */
public class InterestingFactFragment extends MvpAppCompatFragment {

    MainFragmentBinding binding;
    NasaService service;
    Calendar c;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(NasaService.class);
        c = Calendar.getInstance();
        Call<NasaArticle> call = service.loadArticle(getResources().getString(R.string.nasa_key), getCurrendDate());
        call.enqueue(new Callback<NasaArticle>() {
            @Override
            public void onResponse(Call<NasaArticle> call, Response<NasaArticle> response) {
                binding.setNasaArticle(response.body());
                Picasso.with(getContext()).load(response.body().getUrl()).into(binding.interestingIv);
            }

            @Override
            public void onFailure(Call<NasaArticle> call, Throwable t) {
            }
        });
        return binding.getRoot();
    }

    public void updateData(String date) {
        Call<NasaArticle> call = service.loadArticle(getResources().getString(R.string.nasa_key), date);
        call.enqueue(new Callback<NasaArticle>() {
            @Override
            public void onResponse(Call<NasaArticle> call, Response<NasaArticle> response) {
                binding.setNasaArticle(response.body());
                Picasso.with(getContext()).load(response.body().getUrl()).into(binding.interestingIv);
            }

            @Override
            public void onFailure(Call<NasaArticle> call, Throwable t) {
            }
        });
    }

    String getCurrendDate() {
        int month = c.get(Calendar.MONTH) + 1;
        return c.get(Calendar.YEAR) + "-" + month + "-" + c.get(Calendar.DAY_OF_MONTH);
    }
}
