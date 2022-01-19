package tw.tcnr03.m1401;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class M1401 extends AppCompatActivity implements
        ViewSwitcher.ViewFactory,
        View.OnClickListener{


    private TextView s001;
    private TextView f000;
    private String u_select;
    private String ans;
    private ImageButton b001;
    private ImageButton b002;
    private ImageButton b003;
    private ImageSwitcher c001;
    private MediaPlayer startmusic;
    private MediaPlayer win;
    private MediaPlayer lose;
    private MediaPlayer draw;
    private MediaPlayer endmusic;
    private ImageSwitcher imgSwi_comp;
    private int miCountSet = 0,
            miCountPlayerWin = 0,
            miCountComWin = 0,
            miCountDraw = 0;
    //   miCountSet總局數 miCountPlayerWin玩家贏
    //miCountComWin電腦贏 miCountDraw平手


    private View.OnClickListener b001ON = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            miCountSet++;
            int iComp = (int) (Math.random() * 3 + 1);
// 1 - scissors, 2 - stone, 3 - net.
            switch (v.getId()) {
                case R.id.m1401_b001:
                    u_select = getString(R.string.m1401_s001) + getString(R.string.m1401_b001);
                    u_setalpha();
                    b001.getBackground().setAlpha(255);
                    switch (iComp) {
                        case 1:
                            c001.setImageResource(R.drawable.scissors);
//                        ans=getString(R.string.m1401_f000)+getString(R.string.m1401_f003);
//                        f000.setTextColor(Color.YELLOW);
                            music(2);
                            break;
                        case 2:
                            c001.setImageResource(R.drawable.stone);
//                        ans=getString(R.string.m1401_f000)+getString(R.string.m1401_f002);
//                        f000.setTextColor(Color.RED);
                            music(3);
                            break;
                        case 3:
                            c001.setImageResource(R.drawable.net);
//                        ans=getString(R.string.m1401_f000)+getString(R.string.m1401_f001);
//                        f000.setTextColor(Color.GREEN);
                            music(1);
                            break;
                    }
                    break;

//-------------------------------------------------------
                case R.id.m1401_b002:
                    u_select = getString(R.string.m1401_s001) + getString(R.string.m1401_b002);
                    u_setalpha();
                    b002.getBackground().setAlpha(255);
                    switch (iComp) {
                        case 1:
                            c001.setImageResource(R.drawable.stone);
//                        ans=getString(R.string.m1401_f000)+getString(R.string.m1401_f003);
//                        f000.setTextColor(Color.YELLOW);
                            music(2);
                            break;
                        case 2:
                            c001.setImageResource(R.drawable.net);
//                        ans=getString(R.string.m1401_f000)+getString(R.string.m1401_f002);
//                        f000.setTextColor(Color.RED);
                            music(3);
                            break;
                        case 3:
                            c001.setImageResource(R.drawable.scissors);
//                        ans=getString(R.string.m1401_f000)+getString(R.string.m1401_f001);
//                        f000.setTextColor(Color.GREEN);
                            music(1);
                            break;
                    }
                    break;

//-------------------------------------------------------
                case R.id.m1401_b003:
                    u_select = getString(R.string.m1401_s001) + getString(R.string.m1401_b003);
                    u_setalpha();
                    b003.getBackground().setAlpha(255);
                    switch (iComp) {
                        case 1:
                            c001.setImageResource(R.drawable.net);
//                        ans=getString(R.string.m1401_f000)+getString(R.string.m1401_f003);
//                        f000.setTextColor(Color.YELLOW);
                            music(2);
                            break;
                        case 2:
                            c001.setImageResource(R.drawable.scissors);
//                        ans=getString(R.string.m1401_f000)+getString(R.string.m1401_f002);
//                        f000.setTextColor(Color.RED);
                            music(3);
                            break;
                        case 3:
                            c001.setImageResource(R.drawable.stone);
//                        ans=getString(R.string.m1401_f000)+getString(R.string.m1401_f001);
//                        f000.setTextColor(Color.GREEN);
                            music(1);
                            break;
                    }
                    break;
            }
//           -------------電腦出拳增加動畫---------------
            imgSwi_comp.clearAnimation();
            Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_trans_in);//down
            anim.setInterpolator(new BounceInterpolator());//jump
            imgSwi_comp.setAnimation(anim);
//            -----------------------------------------------

            s001.setText(u_select);
            f000.setText(ans);
        }
    };
    private Button btn_show;
    private Button btn_ok;
    private Button btn_can;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1401);
        setupViewComponent();
        u_loaddata();
    }

    private void setupViewComponent() {
        b001 = (ImageButton) findViewById(R.id.m1401_b001);
//    startmusic = MediaPlayer.create(getApplication(), R.raw.guess);
//    startmusic.start();
        b002 = (ImageButton) findViewById(R.id.m1401_b002);
        b003 = (ImageButton) findViewById(R.id.m1401_b003);
        //--設定imageutton初始值為全透明B
        u_setalpha();
        c001 = (ImageSwitcher) findViewById(R.id.m1401_c001);
        s001 = (TextView) findViewById(R.id.m1401_s001);
        f000 = (TextView) findViewById(R.id.m1401_f000);
        //---電腦出拳---
        imgSwi_comp = (ImageSwitcher) findViewById(R.id.m1401_c001);
        imgSwi_comp.setFactory(this);
        // ---開機動畫---
//        r_layout = (ConstraintLayout) findViewById(R.id.m1401_r001);
//        r_layout.setBackgroundResource(R.drawable.back01);
//        r_layout.setAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_scale_rotate_out));
//        r_layout.setAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_scale_rotate_in));
//        r_layout.setBackgroundResource(R.drawable.back01);
//
//        startmusic = MediaPlayer.create(getApplication(), R.raw.guess);
//        startmusic.start();
//        endmusic = MediaPlayer.create(getApplication(), R.raw.goodnight);
        win = MediaPlayer.create(getApplication(), R.raw.vctory);
        draw = MediaPlayer.create(getApplication(), R.raw.haha);
        lose = MediaPlayer.create(getApplication(), R.raw.lose);
        //GONE的按鈕
        btn_show = (Button)findViewById(R.id.m1401_btnResult);
        btn_ok =  (Button)findViewById(R.id.m1401_btnOK);
        btn_can = (Button)findViewById(R.id.m1401_btnCancel);

        b001.setOnClickListener(b001ON);
        b002.setOnClickListener(b001ON);
        b003.setOnClickListener(b001ON);
    }

    private void u_setalpha() {
//        imageButton 背景為灰色且為全透明
//        setBackgroundColor(Color.XXX)為新方法，講義為舊方法。
        b001.setBackgroundResource(R.drawable.circle_shape);
        b001.getBackground().setAlpha(0); //0-255
        b002.setBackgroundResource(R.drawable.circle_shape);
        b002.getBackground().setAlpha(0);
        b003.setBackgroundResource(R.drawable.circle_shape);
        b003.getBackground().setAlpha(0);
    }


    private void music(int i) { //自定義方法
        if (win.isPlaying()) win.pause();
        if (draw.isPlaying()) draw.pause();
        if (lose.isPlaying()) lose.pause();

        switch (i) {
            case 1:
                win.start();
                miCountPlayerWin++;
                ans = getString(R.string.m1401_f000) + getString(R.string.m1401_f001);
                f000.setTextColor(Color.GREEN);
                Toast.makeText(getApplicationContext(), R.string.m1401_f001,
                        Toast.LENGTH_LONG).show(); // 贏
                break;
            case 2:
                draw.start();
                miCountDraw++;
                ans = getString(R.string.m1401_f000) + getString(R.string.m1401_f003);
                f000.setTextColor(Color.YELLOW);
                Toast.makeText(getApplicationContext(), R.string.m1401_f003,
                        Toast.LENGTH_LONG).show(); // 平手
                break;
            case 3:
                lose.start();
                miCountComWin++;
                ans = getString(R.string.m1401_f000) + getString(R.string.m1401_f002);
                f000.setTextColor(Color.RED);
                Toast.makeText(getApplicationContext(), R.string.m1401_f002,
                        Toast.LENGTH_LONG).show(); // 贏
                break;
//            case 4:
//                endmusic.start();
//                break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        music(4);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public View makeView() {
        ImageView v = new ImageView(this);
        // v.setBackgroundColor(0xFF000000);
        v.setScaleType(ImageView.ScaleType.FIT_CENTER);
        v.setLayoutParams(new
                ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return v;
    }

    public void btnShowResultLis(View view) {
        Intent it = new Intent();
        it.setClass(M1401.this, GameResult.class);
        Bundle bundle = new Bundle();
        bundle.putInt("KEY_COUNT_SET", miCountSet);
        bundle.putInt("KEY_COUNT_PLAYER_WIN", miCountPlayerWin);
        bundle.putInt("KEY_COUNT_COM_WIN", miCountComWin);
        bundle.putInt("KEY_COUNT_DRAW", miCountDraw);
        it.putExtras(bundle);
        startActivity(it);
        setResult(RESULT_OK, it);
        finish();
    }

    public void onOK(View view) {
        Intent it = new Intent();

        Bundle bundle = new Bundle();
        bundle.putInt("KEY_COUNT_SET", miCountSet);
        bundle.putInt("KEY_COUNT_PLAYER_WIN", miCountPlayerWin);
        bundle.putInt("KEY_COUNT_COM_WIN", miCountComWin);
        bundle.putInt("KEY_COUNT_DRAW", miCountDraw);
        it.putExtras(bundle);

        setResult(RESULT_OK, it);
        finish();
    }

    public void onCancel(View view) {
        //            Toast.makeText(getApplicationContext(), "333", Toast.LENGTH_SHORT).show();
//            setResult(RESULT_CANCELED);
//            finish();
        setResult(RESULT_CANCELED);
        finish();
    }

    private void u_savedata(){
        // 儲存SharedPreferences資料
        SharedPreferences gameResultData = getSharedPreferences("GAME_RESULT",0);

        gameResultData
                .edit()
                .putInt("KEY_COUNT_SET",miCountSet)
                .putInt("KEY_COUNT_PLAYER_WIN", miCountPlayerWin)
                .putInt("KEY_COUNT_COM_WIN", miCountComWin)
                .putInt("KEY_COUNT_DRAW", miCountDraw)
                .commit();
        Toast.makeText(M1401.this, "儲存完成", Toast.LENGTH_LONG).show();
    }

    private void u_loaddata() {
        SharedPreferences gameResultData = getSharedPreferences("GAME_RESULT",0);

        miCountSet = gameResultData.getInt("KEY_COUNT_SET",0);
        miCountPlayerWin = gameResultData.getInt("KEY_COUNT_PLAYER_WIN",0);
        miCountComWin = gameResultData.getInt("KEY_COUNT_COM_WIN",0);
        miCountDraw = gameResultData.getInt("KEY_COUNT_DRAW",0);
        Toast.makeText(M1401.this, "讀取完成", Toast.LENGTH_LONG).show();
    }

    private void u_cleardata() {
        SharedPreferences gameResultData = getSharedPreferences("GAME_RESULT",0);

        gameResultData
                .edit()
                .clear()
                .commit();
        miCountSet = 0;
        miCountPlayerWin = 0;
        miCountComWin = 0;
        miCountDraw = 0;
        Toast.makeText(M1401.this, "清除完成", Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1401, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnShowResult:
                btn_show.performClick();
                u_savedata();
                break;
            case R.id.btnSaveResult:
                u_savedata();
                break;
            case R.id.btnLoadResult:
                u_loaddata();
                break;
            case R.id.btnClearResult:
                u_cleardata();
                break;
            case R.id.btnaboutme:
                new AlertDialog.Builder(M1401.this)
                        .setTitle("Preferences範例程式")
                        .setMessage("TCNR雲端製作")
                        .setCancelable(false)
                        .setIcon(android.R.drawable.star_big_on)
                        .setPositiveButton("確定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                        .show();
                break;
            case R.id.action_settings:
                u_savedata();
                btn_ok.performClick();
                break;
        }
        return true;
    }
}