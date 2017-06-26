package ua.rDev.myEng.data;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pk on 19.06.2017.
 */

public interface SkyEngService {
    @GET("api/public/v1/words/search")
    Call<ArrayList<SkyEngWord>> translateWord(@Query("search") String word);
}
