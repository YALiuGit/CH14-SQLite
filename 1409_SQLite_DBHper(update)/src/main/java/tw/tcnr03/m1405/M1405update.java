package tw.tcnr03.m1405;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

import android.app.Service;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class M1405update extends AppCompatActivity implements View.OnClickListener {
    private TextView count_t;
    private Button b001, b002, b003, b004;
    private EditText e000,e001, e002, e003, e004;

    private FriendDbHelper dbHper;
    private static final String DB_FILE = "friends.db";
    private static final String DB_TABLE = "member";
    private static final int DBversion = 1;
    private TextView t001;
    private TextView tvTitle;
    private Button btNext;
    private Button btPrev;
    private Button btTop;
    private Button btEnd;
    private int index =0;
    private ArrayList<String> recSet = null;
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private float range =350; //設定touchEvent的距離
    private int ran =50;
    private Vibrator myVibrator;
    private Button btUpdate;
    private Button btDelete;
    private String tname;
    private String tgrp;
    private String taddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1405update);
        setupViewComponent();
    }

    private void setupViewComponent() {
        tvTitle = (TextView)findViewById(R.id.tvIdTitle);
        e000 = (EditText) findViewById(R.id.edtId);
        e001 = (EditText) findViewById(R.id.edtName);
        e002 = (EditText) findViewById(R.id.edtGrp);
        e003 = (EditText) findViewById(R.id.edtAddr);
        count_t =(TextView)findViewById(R.id.count_t) ;

        btNext = (Button) findViewById(R.id.btIdNext);
        btPrev = (Button) findViewById(R.id.btIdPrev);
        btTop = (Button) findViewById(R.id.btIdtop);
        btEnd = (Button) findViewById(R.id.btIdend);
        btUpdate = (Button) findViewById(R.id.btIdUpdate);
        btDelete = (Button) findViewById(R.id.btIdDelete);

        e000.setKeyListener(null);  //設定ID 不能修改
        btNext.setOnClickListener(this);
        btPrev.setOnClickListener(this);
        btTop.setOnClickListener(this);
        btEnd.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        btDelete.setOnClickListener(this);

        //如果放在onCreate會與setupViewComponent()同時執行造成錯誤
        initDB();
        count_t.setText("共計:" + Integer.toString(dbHper.RecCount()) + "筆");

        showRec(index);

        // 宣告震動
        myVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);

    }

    private void showRec(int index) {
        int b=0;
        if (recSet.size() != 0)   {
            String stHead = "顯示資料：第 " + (index + 1) + " 筆 / 共 " + recSet.size() + " 筆";
            tvTitle.setTextColor(ContextCompat.getColor(this, R.color.Blue));
            tvTitle.setText(stHead);
            //--------------------------
//            fld為field欄位的簡寫
            String[] fld = recSet.get(index).split("#");
            e000.setTextColor(ContextCompat.getColor(this, R.color.Red));
            e000.setBackgroundColor(ContextCompat.getColor(this, R.color.Yellow));
            e000.setText(fld[0]);
            e001.setText(fld[1]);
            e002.setText(fld[2]);
            e003.setText(fld[3]);

            //---------------------
        } else        {
            String stHead = "顯示資料：0 筆";
            tvTitle.setTextColor(ContextCompat.getColor(this, R.color.Blue));
            tvTitle.setText(stHead);
            e000.setText("");
            e001.setText("");
            e002.setText("");
            e003.setText("");
        }
    }

    private void initDB() {
        if(dbHper == null){
            dbHper = new FriendDbHelper(this, DB_FILE, null, DBversion);
        }
        recSet = dbHper.getRecSet();
        int a =0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btIdtop:
                ctlFirst();
                index=0;
                break;
            case R.id.btIdNext:
                ctlNext();
                break;
            case R.id.btIdPrev:
                ctlPrev();
                break;
            case R.id.btIdend:
                ctlLast();
                index=recSet.size()-1;
                break;
            case R.id.btIdUpdate:
                // 資料更新
                tid = e000.getText().toString().trim();
                tname = e001.getText().toString().trim();
                tgrp = e002.getText().toString().trim();
                taddr = e003.getText().toString().trim();
//
                rowsAffected = dbHper.updateRec(tid, tname, tgrp, taddr);//傳回修改筆數

                if (rowsAffected == -1) {
                    msg = "資料表已空, 無法修改 !";
                } else if (rowsAffected == 0) {
                    msg = "找不到欲修改的記錄, 無法修改 !";
                } else {
                    msg = "第 " + (index + 1) + " 筆記錄  已修改 ! \n" + "共 " + rowsAffected + " 筆記錄   被修改 !";
                    recSet = dbHper.getRecSet();
                    showRec(index);
                }
                Toast.makeText(M1405update.this, msg, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btIdDelete:
                MyAlertDialog aldDial = new MyAlertDialog(M1405update.this);
                aldDial.setTitle(getString(R.string.m_clear));
                aldDial.setMessage(getString(R.string.m_message));
                aldDial.setIcon(android.R.drawable.ic_dialog_info);
                aldDial.setCancelable(false); //返回鍵關閉
                aldDial.setButton(BUTTON_POSITIVE, getString(R.string.m_btn_ok), aldBtListener);
                aldDial.setButton(BUTTON_NEGATIVE, getString(R.string.m_btn_cancel), aldBtListener);
                aldDial.show();
                break;
        }
    }

    private String msg;
    private String tid;
    private int rowsAffected;

    private DialogInterface.OnClickListener aldBtListener = new DialogInterface.OnClickListener() {


        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case BUTTON_POSITIVE:
                    tid = e000.getText().toString().trim();
                    rowsAffected = dbHper.deleteRec(tid);  // delete record
                    if (rowsAffected == -1) {
                        msg = "資料表已空, 無法刪除 !";
                    } else if (rowsAffected == 0) {
                        msg = "找不到欲刪除的記錄, 無法刪除 !";
                    } else {
                        msg = "第 " + (index + 1) + " 筆記錄  已刪除 ! \n" + "共 " + rowsAffected + " 筆記錄   被刪除 !";
                        recSet = dbHper.getRecSet();

                        if (index == dbHper.RecCount()) {
                            index--; //
                        }
                        showRec(index); //重構
                    }
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                    break;
                case BUTTON_NEGATIVE:
                    msg = getString(R.string.msg2);
                    break;
            }
            Toast.makeText(M1405update.this, msg, Toast.LENGTH_SHORT).show();
        }
    };
    private void ctlFirst() {
        showRec(0);
    }

    private void ctlPrev() {
        index--;
        if (index < 0)
            index = recSet.size() - 1;
        showRec(index);
    }

    private void ctlNext() {
        index++;
        if (index >= recSet.size())
            index = 0;
        showRec(index);
    }

    private void ctlLast() {
        showRec(recSet.size()-1);
    }

//    ----------------------------------------------------------

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: //按下
                x1 = event.getX(); //按下去取得的x
                y1 = event.getY(); //按下去取得的y
                break;
            case MotionEvent.ACTION_MOVE: //移動
                myVibrator.vibrate(10000); // 震動五秒
                break;
            case MotionEvent.ACTION_UP: //放開
                x2 = event.getX(); //按下去取得的x
                y2 = event.getY(); //按下去取得的y
                //                ========================================
                // 判斷左右的方法，因為屏幕的左上角是：0，0 點右下角是max,max
                // 並且移動距離需大於 > range
                float xbar = Math.abs(x2 - x1);
                float ybar = Math.abs(y2 - y1);
                double z = Math.sqrt(xbar * xbar + ybar * ybar);
                int angle = Math.round((float) (Math.asin(ybar / z) / Math.PI * 180));// 角度
                if (x1 != 0 && y1 != 0) {
                    if (x1 - x2 > range) { // 向左滑動
                        ctlPrev();
                    }
                    if (x1 - x2 <  range) { // 向右滑動
                        ctlNext();
                        // t001.setText("向右滑動\n" + "滑動參值x1=" + x1 + " x2=" + x2 + "
                        // r=" + (x2 - x1)+"\n"+"ang="+angle);
                    }
                    if (y2 - y1 > range && angle > ran) { // 向下滑動
                        // 往下角度需大於50
                        // 最後一筆
                        ctlLast();
                        index=recSet.size()-1;
                    }
                    if (y1 - y2 > range && angle > ran) { // 向上滑動
                        // 往上角度需大於50
                        ctlFirst();// 第一筆
                        index=0;
                    }
                }
                myVibrator.cancel();
                break;

        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),getString(R.string.noback),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dbHper != null)
        {
            dbHper.close();
            dbHper = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dbHper == null)
            dbHper = new FriendDbHelper(this, DB_FILE, null, DBversion);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dbHper != null) {
            dbHper.close();
            dbHper = null;
        }
        if (myVibrator != null)
            myVibrator.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1405sub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.m_return:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
