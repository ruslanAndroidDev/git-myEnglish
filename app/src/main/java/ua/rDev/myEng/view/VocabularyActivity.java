package ua.rDev.myEng.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.mancj.slideup.SlideUp;

import java.util.ArrayList;

import ua.rDev.myEng.R;
import ua.rDev.myEng.Utill;
import ua.rDev.myEng.adapter.MyListRecyclerAdapter;
import ua.rDev.myEng.adapter.PanelAdapter;
import ua.rDev.myEng.adapter.WrapContentLinearLayoutManager;
import ua.rDev.myEng.data.MyDataBaseHelper;
import ua.rDev.myEng.model.Word;
import ua.rDev.myEng.model.WordPack;
import ua.rDev.myEng.presenter.VocabularyPresenter;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
public class VocabularyActivity extends MvpAppCompatActivity implements View.OnClickListener, IVocabulary {
    @InjectPresenter
    VocabularyPresenter presenter;
    static RecyclerView recyclerView;
    static MyListRecyclerAdapter adapter;
    FloatingActionButton fab;
    View slideView;
    SlideUp slideUp;
    RecyclerView panel_rv;

    ImageView vocab_iv;
    TextView vocab_tv;
    Toolbar vocab_toolbar;

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
        setContentView(R.layout.my_vocabulary_frag);
        vocab_toolbar = (Toolbar) findViewById(R.id.vocab_toolbar);
        setSupportActionBar(vocab_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(getString(R.string.my_dictionary));

        slideView = findViewById(R.id.slideView);
        slideUp = new SlideUp.Builder(slideView)
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.TOP)
                .build();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        panel_rv = (RecyclerView) findViewById(R.id.panelRecyclerView);
        panel_rv.setLayoutManager(new LinearLayoutManager(this));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerv);

        vocab_iv = (ImageView) findViewById(R.id.vocab_iv);
        vocab_tv = (TextView) findViewById(R.id.vocab_tv);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof MyListRecyclerAdapter.HeaderHolder || viewHolder instanceof MyListRecyclerAdapter.BannerHolder)
                    return 0;
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                adapter.data.remove(position - 2);
                MyDataBaseHelper.deleteItem(position - 2, getApplicationContext());
                adapter.notifyItemRemoved(position);
                if (MyListRecyclerAdapter.data.size() == 0) {
                    vocab_iv.setVisibility(View.VISIBLE);
                    vocab_iv.setImageBitmap(Utill.loadBitmapFromResource(getResources(), R.drawable.clear_book, 250, 250));
                    vocab_tv.setVisibility(View.VISIBLE);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void showVocabDialog() {
        if (alertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
            View dialogView = View.inflate(this, R.layout.word_status_dialog, null);
            builder.setView(dialogView);
            alertDialog = builder.create();
        }
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (isPanelShow()) {
            hidePanel();
        } else {
            close();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            close();
        } else {
            showVocabDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void close() {
        Intent intent = new Intent(this, MainActivity.class);
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim_enter_to_main, R.anim.anim_leave_to_main);
        startActivity(intent, options.toBundle());
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_button_ok) {
            if (dialog_et_native.getText().toString().equals("") || dialog_et_translate.getText().toString().equals("")) {
                alertDialog.dismiss();
            } else {
                presenter.writeWord(dialog_et_translate.getText().toString(), dialog_et_native.getText().toString());
                alertDialog.dismiss();
            }
        } else if (v.getId() == R.id.dialog_button_cancel) {
            alertDialog.dismiss();
        }

    }

    AlertDialog alertDialog;
    EditText dialog_et_native;
    EditText dialog_et_translate;

    @Override
    public void showDialog() {
        if (alertDialog == null) {
            createDialog();
        }
        try {
            dialog_et_native.setText("");
            dialog_et_translate.setText("");
        } catch (NullPointerException e) {
            createDialog();

            dialog_et_native.setText("");
            dialog_et_translate.setText("");
        }

        alertDialog.show();
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = View.inflate(this, R.layout.dialogmaket, null);
        builder.setView(dialogView);

        Button dialog_btn_ok = (Button) dialogView.findViewById(R.id.dialog_button_ok);
        Button dialog_btn_cancel = (Button) dialogView.findViewById(R.id.dialog_button_cancel);
        dialog_et_native = (EditText) dialogView.findViewById(R.id.dialog_et_original);
        dialog_et_translate = (EditText) dialogView.findViewById(R.id.dialog_et_translate);

        dialog_btn_cancel.setOnClickListener(this);
        dialog_btn_ok.setOnClickListener(this);
        alertDialog = builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.loadData(this);
    }

    @Override
    public void showData(ArrayList<Word> data) {
        adapter = new MyListRecyclerAdapter(data, presenter);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        if (data.size() == 0) {
            vocab_iv.setImageBitmap(Utill.loadBitmapFromResource(getResources(), R.drawable.clear_book, 250, 250));
            vocab_iv.setVisibility(View.VISIBLE);
            vocab_tv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void insertWord(String translate, String original, int status) {
        MyListRecyclerAdapter.data.add(0, new Word(translate, original, status));
        adapter.notifyItemInserted(0);
        vocab_iv.setImageBitmap(Utill.loadBitmapFromResource(getResources(), R.drawable.clear_book, 250, 250));
        vocab_iv.setVisibility(View.GONE);
        vocab_tv.setVisibility(View.GONE);
    }

    @Override
    public void showPanel(WordPack wordPack) {
        panel_rv.setAdapter(new PanelAdapter(wordPack, this, presenter));
        slideUp.show();
        fab.hide();
    }

    @Override
    public void hidePanel() {
        slideUp.hide();
        fab.show();
        panel_rv.removeAllViews();
    }

    public boolean isPanelShow() {
        return slideUp.isVisible();
    }
}
