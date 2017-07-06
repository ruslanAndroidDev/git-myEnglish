package ua.rDev.myEng.view;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;

import ua.rDev.myEng.R;
import ua.rDev.myEng.adapter.VocabularyPagerAdapter;
import ua.rDev.myEng.model.Word;
import ua.rDev.myEng.presenter.ChallangePresenter;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by pk on 30.05.2017.
 */

public class ChallengeActivity extends MvpAppCompatActivity implements IChallange {
    @InjectPresenter
    ChallangePresenter challangePresenter;

    FrameLayout challangeFrame;
    FragmentTransaction fragmentTransaction;
    Toolbar ch_toolbar;
    RelativeLayout challangeRelative;

    static MyCustomPager viewPager;
    VocabularyPagerAdapter vocabularyPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getDefaultSharedPreferences(this);
        String color = preferences.getString("color", "1");
        if (color.equals("1")) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.AppTheme2);
        }
        setContentView(R.layout.challenge);
        ch_toolbar = (Toolbar) findViewById(R.id.ch_toolbar);
        setSupportActionBar(ch_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Challenge");

        challangeRelative = (RelativeLayout) findViewById(R.id.challengeRelative);
        viewPager = (MyCustomPager) findViewById(R.id.viewpager);

        challangeFrame = (FrameLayout) findViewById(R.id.challengeFrame);
    }

    public void showErorAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setTitle(getResources().getString(R.string.error));
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                close();
            }
        });
        builder.setCancelable(false);
        builder.setMessage(getResources().getString(R.string.dialog_msg));
        builder.show();
    }

    @Override
    public void showStartChallengeFragment() {
        showFragment(new StartChallengeFragment(challangePresenter));
    }

    @Override
    public void showChallengeLayout(int num_of_challange_item, ArrayList<Word> arrayList) {
        challangeRelative.setVisibility(View.VISIBLE);
        challangeFrame.setVisibility(View.GONE);
        vocabularyPagerAdapter = new VocabularyPagerAdapter(getSupportFragmentManager(), num_of_challange_item, arrayList, challangePresenter);
        viewPager.setAdapter(vocabularyPagerAdapter);

        final int pageCount = vocabularyPagerAdapter.getCount();
        Log.d("tag", "pageCount" + pageCount);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("tag", "position" + position);
                if (position == (pageCount - 1)) {
                    challangePresenter.updateResult();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void showFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.challengeFrame, fragment);
        fragmentTransaction.commit();
        challangeRelative.setVisibility(View.GONE);
        challangeFrame.setVisibility(View.VISIBLE);
    }

    private void close() {
        Intent intent = new Intent(this, MainActivity.class);
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim_enter_to_main, R.anim.anim_leave_to_main);
        startActivity(intent, options.toBundle());
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            close();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        close();
    }

    public static void scrollToNext() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }
}
