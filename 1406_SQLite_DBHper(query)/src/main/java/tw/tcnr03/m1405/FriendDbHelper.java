package tw.tcnr03.m1405;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** * Created by vpoint88 on 2017/7/6. */
////----------------------------------------------------------
//建構式參數說明：
//context
//可以操作資料庫的內容本文，一般可直接傳入Activity物件。
//name
//要操作資料庫名稱，如果資料庫不存在，會自動被建立出來並呼叫onCreate()方法。
//factory
//用來做深入查詢用，入門時用不到。
//version
//版本號碼。
////-----------------------
public class FriendDbHelper extends SQLiteOpenHelper
{
    String TAG = "tcnr03=>";
    public String sCreateTableCommand;    // 資料庫名稱
    private static final String DB_FILE = "friends.db";
    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    public static final int VERSION = 1;    // 資料表名稱
    private static final String DB_TABLE = "member";    // 資料庫物件，固定的欄位變數

    //此為sql語法
    private static final String crTBsql = "CREATE TABLE " + DB_TABLE + " ( "
            + "id INTEGER PRIMARY KEY," + "name TEXT NOT NULL," + "grp TEXT,"
            + "address TEXT);";

    private static SQLiteDatabase database;

    //----------------------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(crTBsql);
    }
    //----------------------------------------------
    // 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
    public static SQLiteDatabase getDatabase(Context context){
        if (database == null || !database.isOpen())
        {
            database = new FriendDbHelper(context, DB_FILE, null, VERSION)
                    .getWritableDatabase();
        }
        return database;
    }

    public FriendDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
//        super(context, name, factory, version);
        super(context, "friends.db", null, 1);
        sCreateTableCommand = "";
    }



    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.d(TAG, "onUpgrade()");
//資料結構改變
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        onCreate(db);
    }

    public long insertRec(String b_name, String b_grp, String b_address) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues rec = new ContentValues();
        rec.put("name", b_name);
        rec.put("grp", b_grp);
        rec.put("address", b_address);
        long rowID = db.insert(DB_TABLE, null, rec);
        db.close();
        return rowID;
    }


    public int RecCount() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = " SELECT  *  FROM " + DB_TABLE ;
        Cursor recSet = db.rawQuery(sql, null);
        return recSet.getCount();
    }

    public String FindRec(String tname) {
        SQLiteDatabase db = getReadableDatabase();
        String fldSet = "ans=";
        String sql = "SELECT * FROM "+  DB_TABLE  + " WHERE  name  LIKE  ?  ORDER BY id ASC ";
        String[] args  = {"%" + tname + "%"};
        Cursor recSet = db.rawQuery(sql, args);
        int columnCount = recSet.getColumnCount();
        Log.d(TAG,"ans:"+recSet.getCount());
        //==================================
        if (recSet.getCount() != 0) {
            recSet.moveToFirst();
            fldSet = recSet.getString(0) + " "
                    + recSet.getString(1) + " "
                    + recSet.getString(2) + " "
                    + recSet.getString(3) + "\n";

            while (recSet.moveToNext()) {
                for (int i = 0; i < columnCount; i++) {
                    fldSet += recSet.getString(i) + " ";
                }
                fldSet += "\n";
            }
        }
        recSet.close();
        db.close();
        return fldSet;
    }
// ---------

}
