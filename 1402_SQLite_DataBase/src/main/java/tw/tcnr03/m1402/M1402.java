package tw.tcnr03.m1402;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class M1402 extends AppCompatActivity implements View.OnClickListener {

    //資料庫名稱
    private static final String DB_File = "friends.db", DB_TABLE = "member";

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
        Cursor cursor = mFriendDb
                .rawQuery("SELECT   DISTINCT  tbl_name  from  sqlite_master where tbl_name = '"
                        + DB_TABLE
                        + "'", null);
        //select  DISTINCT  member from sqlite_master where  tbl_name='member'
        if (cursor != null) {
            if (cursor.getCount() == 0){
                // 沒有資料表，要建立一個資料表。
                mFriendDb.execSQL("CREATE    TABLE "
                        + DB_TABLE
                        + "   (id  INTEGER PRIMARY KEY, name TEXT NOT NULL,grp TEXT,address TEXT);");
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

    }
}