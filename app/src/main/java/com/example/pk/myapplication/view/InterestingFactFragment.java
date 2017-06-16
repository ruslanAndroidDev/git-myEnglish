package com.example.pk.myapplication.view;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.pk.myapplication.R;
import com.example.pk.myapplication.data.NasaService;
import com.example.pk.myapplication.databinding.MainFragmentBinding;
import com.example.pk.myapplication.model.NasaArticle;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
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
    TextView textView;
    ProgressDialog pd;

    TextView copyright_tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
        textView = (TextView) binding.getRoot().findViewById(R.id.interesting_article_tv);
        copyright_tv = (TextView) binding.getRoot().findViewById(R.id.copyright_tv);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getNewHttpClient())
                .build();

        service = retrofit.create(NasaService.class);
        c = Calendar.getInstance();

        pd = new ProgressDialog(getContext());
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.show();
        updateData(getCurrendDate());
        return binding.getRoot();
    }
    public static OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder client) {
        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 22) {
            try {
                SSLContext sc = SSLContext.getInstance("TLSv1.2");
                sc.init(null, null, null);
                client.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()));

                ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build();

                List<ConnectionSpec> specs = new ArrayList<>();
                specs.add(cs);
                specs.add(ConnectionSpec.COMPATIBLE_TLS);
                specs.add(ConnectionSpec.CLEARTEXT);

                client.connectionSpecs(specs);
            } catch (Exception exc) {
                Log.d("tag", "Error while setting TLS 1.2", exc);
            }
        }

        return client;
    }

    private OkHttpClient getNewHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .cache(null)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS);

        return enableTls12OnPreLollipop(client).build();
    }

    public void updateData(String date) {
        pd.show();
        Call<NasaArticle> call = service.loadArticle(getResources().getString(R.string.nasa_key), date);
        call.enqueue(new Callback<NasaArticle>() {
            @Override
            public void onResponse(Call<NasaArticle> call, Response<NasaArticle> response) {
                pd.hide();
                binding.setNasaArticle(response.body());
                if (response.body().getCopyright() != null) {
                    copyright_tv.setText("Copyright: " + response.body().getCopyright());
                }
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
