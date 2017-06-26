package ua.rDev.myEng.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import ua.rDev.myEng.R;
import ua.rDev.myEng.adapter.VocabularyPagerAdapter;
import ua.rDev.myEng.model.Word;
import ua.rDev.myEng.presenter.ChallangePresenter;

import java.util.ArrayList;

/**
 * Created by pk on 30.05.2017.
 */

public class ChallengeActivity extends MvpAppCompatActivity implements View.OnClickListener, IChallange {
    @InjectPresenter
    ChallangePresenter challangePresenter;

    FrameLayout challangeFrame;
    FragmentTransaction fragmentTransaction;
    Toolbar ch_toolbar;
    RelativeLayout challangeRelative;

    static MyCustomPager viewPager;
    VocabularyPagerAdapter vocabularyPagerAdapter;
    Button finishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge);
        ch_toolbar = (Toolbar) findViewById(R.id.ch_toolbar);
        setSupportActionBar(ch_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Challange");

        challangeRelative = (RelativeLayout) findViewById(R.id.challengeRelative);

        finishBtn = (Button) findViewById(R.id.finish_button);
        finishBtn.setOnClickListener(this);
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
                finish();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finish_button:
                challangePresenter.clearData();
                finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void scrollToNext() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }
}
