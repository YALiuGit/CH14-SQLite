package tw.tcnr03.m1402;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class M1402 extends AppCompatActivity implements View.OnClickListener {

    //資料庫名稱
    private static final String DB_File = "friends.db" , DB_TABLE  = "member";
    private SQLiteDatabase mFriendDb;

    private EditText mEdtName;
    private EditText mEdtGrp;
    private EditText mEdtAddr;
    private EditText mEdtList;
    private Button mBtnAdd;
    private Button mBtnQuery;
    private Button mBtnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1402);
        //============================================================================
        FriendDbHelper friendDbHelper = new FriendDbHelper(getApplicationContext(), DB_File, null, 1);
        mFriendDb = friendDbHelper.getWritableDatabase();
// 檢查資料表是否已經存在，如果不存在，就建立一個。
//        Cursor代表資料庫
//        Cursor cursor = mFriendDb
//                .rawQuery("SELECT   DISTINCT  tbl_name  from  sqlite_master where tbl_name = '"
//                        + DB_TABLE
//                        + "'", null);
        Cursor cursor = mFriendDb.
                rawQuery("SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name ='"
                        +DB_TABLE
                        + "'",null );

        //select  DISTINCT  member from sqlite_master where  tbl_name='member'
        if (cursor != null) {
            if(cursor.getCount() == 0){
                // 沒有資料表，要建立一個資料表。
                mFriendDb.execSQL("CREATE TABLE "
                        + DB_TABLE
                        + " (id INTEGER PRIMARY KEY, name TEXT NOT NULL, grp TEXT, address TEXT);");
                //結束連線
                cursor.close();
            }
            //==========================

        }
        //===========================
        setupViewComponent();
    }

    private void setupViewComponent() {
        mEdtName = (EditText) findViewById(R.id.edtName);
        mEdtGrp = (EditText) findViewById(R.id.edtGrp);
        mEdtAddr = (EditText) findViewById(R.id.edtAddr);
        mEdtList = (EditText) findViewById(R.id.edtList);

        mBtnAdd = (Button) findViewById(R.id.btnAdd);
        mBtnQuery = (Button) findViewById(R.id.btnQuery);
        mBtnList = (Button) findViewById(R.id.btnList);

        mBtnAdd.setOnClickListener(this);
        mBtnQuery.setOnClickListener(this);
        mBtnList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdd:
                u_dbadd();
                break;
            case R.id.btnQuery:
                u_dbquery();
                break;
            case R.id.btnList:
                u_dblist();
                break;
        }
    }

    private void u_dbquery() {
        Cursor cur_query = null;
        if (!mEdtName.getText().toString().equals("")) {
            // mFriendDb.query(distinct, table, columns, selection,
            // selectionArgs, groupBy, having, orderBy, limit,
            // cancellationSignal)
            cur_query = mFriendDb.query(true,
                    DB_TABLE,
                    new String[] { "id","name", "grp", "address" },
                    "name=" + "\"" + mEdtName.getText().toString() + "\"",
                    //"name=" + "\"" + mEdtName.getText().toString() + "\"",
                    null,
                    null,
                    null,
                    null,
                    null);
        } else if (!mEdtGrp.getText().toString().equals("")) {
            cur_query = mFriendDb.query(true,
                    DB_TABLE, new String[] { "id","name", "grp", "address" },
                    "grp=" + "\"" + mEdtGrp.getText().toString() + "\"",
                    null,
                    null,
                    null,
                    null,
                    null);
        } else if (!mEdtAddr.getText().toString().equals("")) {
            cur_query = mFriendDb.query(true,
                    DB_TABLE,
                    new String[] { "id","name", "grp", "address" },
                    "address=" + "\"" + mEdtAddr.getText().toString() + "\"",
                    null,
                    null,
                    null,
                    null,
                    null);
        }

        if (cur_query == null)
            return;

        if (cur_query.getCount() == 0) {
            mEdtList.setText("");
            Toast.makeText(getApplicationContext(), "沒有這筆資料", Toast.LENGTH_LONG).show();
        } else {
            cur_query.moveToFirst();
            mEdtList.setText(cur_query.getString(0) + ", " + cur_query.getString(1) + ", " + cur_query.getString(2)+ ", " + cur_query.getString(3));

            while (cur_query.moveToNext())
                mEdtList.append(
                        "\n" + cur_query.getString(0) + ", " + cur_query.getString(1) + ", "+ cur_query.getString(2)+ ", " + cur_query.getString(3));

        }
    }

    private void u_dblist() {
        Cursor cur_list = mFriendDb.query(
                true,
                DB_TABLE,
                new String[]{"id","name","grp","address"},
                null,
                null,
                null,
                null,
                null,
                null);

        // 依據 SQLite 的查詢 (select) 語法
        // 在 SQLiteDatabase 類別中定義了多種可以接收不同參數的查詢方法
        // 這些方法大致可以分為 query 開頭與 rawQuery 開頭兩類。
        // query 開頭的方法可以接受多個參數
        // 每一個參數可以對應到 SQLite select 語法中的某些值

        // 消除所有重複的記錄，並只獲取唯一一次記錄(distinct) true false
        // 表格名稱 (table)
        // 欄位名稱 (columns)
        // 查詢條件 (selection)
        // 查詢條件的值 (selectionArgs)
        // 欄位群組 (groupBy)
        // 排序方式 (orderBy)
        // 回傳資料的筆數限制 (limit) 等。
        // (cancellationSignal)

        if (cur_list ==    null)     return;
        if (cur_list.getCount() == 0) {
            mEdtList.setText("");
            Toast.makeText(M1402.this, "沒有資料", Toast.LENGTH_LONG).show();
        } else {
            cur_list.moveToFirst();
            mEdtList.setText(cur_list.getString(0) + ", "
                    + cur_list.getString(1) + ", "
                    + cur_list.getString(2) + ", "
                    + cur_list.getString(3));

            while (cur_list.moveToNext())
                mEdtList.append("\n" + cur_list.getString(0) + ", "
                        + cur_list.getString(1) + ", "
                        + cur_list.getString(2) + ", "
                        + cur_list.getString(3));
        }
        cur_list.close();
    }

    private void u_dbadd() {
        if(!mEdtName.getText().toString().equals("")){//姓名不可為空白
            ContentValues newRow = new ContentValues();
            newRow.put("name",mEdtName.getText().toString());
            newRow.put("grp",mEdtGrp.getText().toString());
            newRow.put("address",mEdtAddr.getText().toString());
            mFriendDb.insert(DB_TABLE, null, newRow);

            // 清空欄位
            mEdtName.setText("");
            mEdtGrp.setText("");
            mEdtAddr.setText("");
            mEdtName.setHint(R.string.Hint);
            u_dblist();
        }else{
            Toast.makeText(getApplicationContext(), "姓名不可為空白",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFriendDb.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFriendDb.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),getString(R.string.noback),Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.m_batch:
                u_dbbatchadd();
                break;

            case R.id.m_clear:
                mFriendDb.delete(DB_TABLE, null, null);
                // int android.database.sqlite.SQLiteDatabase.delete(String table,
                // String whereClause, String[] whereArgs)
                Toast.makeText(getApplicationContext(), "資料已全部清除", Toast.LENGTH_SHORT).show();
                u_dblist();
                break;

            case R.id.action_settings:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void u_dbbatchadd() {
        for(int i=0; i<100; i++){
            ContentValues newRow = new ContentValues();
//            newRow.put("name","路人" + u_chinano(i+1));
            newRow.put("name","路人" + u_chinayear(i));
            newRow.put("grp", "第" + u_chinano(i)+ "組");
            newRow.put("address","工業一路" + (100 + i) + "號");
            mFriendDb.insert(DB_TABLE, null, newRow);
        }
        Toast.makeText(getApplicationContext(), "批次新增100完成",Toast.LENGTH_SHORT).show();
        u_dblist();
    }

    private String u_chinayear(int input_i) {
        String c_number = "";
        String china_year[] = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
        c_number = china_year[input_i % 10];

        return c_number;
    }

    private String u_chinano(int input_i) {
        String c_number = "";
        String china_no[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九","十"};
        c_number = china_no[input_i % 10];

        return c_number;
    }
}