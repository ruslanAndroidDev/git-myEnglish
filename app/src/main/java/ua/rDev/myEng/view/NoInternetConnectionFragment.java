package ua.rDev.myEng.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ua.rDev.myEng.R;
import ua.rDev.myEng.Utill;

/**
 * Created by pk on 14.10.2016.
 */
public class NoInternetConnectionFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.no_net_connection, container, false);
        ImageView img = (ImageView) v.findViewById(R.id.imageViewRefresh);
        img.setImageBitmap(Utill.loadBitmapFromResource(getResources(), R.drawable.no_net, 100, 100));
        return v;
    }
}
