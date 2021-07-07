package com.example.nguyenducphu_181201867_quanan;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class Phu_Sqlite extends SQLiteOpenHelper {
    public final String KEY_INTENT = "intent";
    public static final String TABLENAME = "Contact_181201867";
    private static final String DATABASENAME = "Phu.sqlite";

    private static final int VERSION = 1;
    public static final String COL_0 = "Id";
    public static final String COL_1 = "TenNhaHang";
    public static final String COL_2 = "DiaDiem";
    public static final String COL_3 = "DanhGia";


    public Phu_Sqlite(Context context) {
        super(context, DATABASENAME, null, VERSION);
    }

    // AUTOINCREMENT    : tự động sinh khóa
    // PRIMARY KEY      : Khóa chính
    // NVARCHAR(255)
    // MONEY    INTEGER     BOOLEAN     DATETIME       DATE     : Kiểu dữ liệu

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLENAME + " ("
                + COL_0 + " INTEGER PRIMARY KEY, "
                + COL_1 + " NVARCHAR(255), "
                + COL_2 + " NVARCHAR(255), "
                + COL_3 + " DOUBLE )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    // Truy vấn có trả kết quả: CREATE, INSERT, UPDATE, DELETE,...
    public void QueryData(String str) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(str);
    }
    // Truy vấn có trả kết quả: SELECT
    public Cursor GetData(String str) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(str, null);
    }

    public void Delete(Contact_181201867 object) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLENAME + " WHERE " + COL_0 + " = " + object.getId() + ";");
    }

    public void Insert(Contact_181201867 object) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "INSERT INTO " + TABLENAME + " VALUES(Null,?,?,?)";
        SQLiteStatement mSqLiteStatement = sqLiteDatabase.compileStatement(sql);
        mSqLiteStatement.clearBindings();
        mSqLiteStatement.bindString(1, object.getTenNhaHang());
        mSqLiteStatement.bindString(2, object.getDiaDiem());
        mSqLiteStatement.bindDouble(3, object.getDanhGia());
        mSqLiteStatement.executeInsert();
    }

    public void Update(Contact_181201867 object) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = " UPDATE " + TABLENAME + " SET "
                + COL_1 + " = ?, "
                + COL_2 + " = ?, "
                + COL_3 + " = ? "
                + " WHERE " + COL_0 + " = " + object.getId() + ";";
        SQLiteStatement mSqLiteStatement = sqLiteDatabase.compileStatement(sql);
        mSqLiteStatement.clearBindings();
        mSqLiteStatement.bindString(1, object.getTenNhaHang());
        mSqLiteStatement.bindString(2, object.getDiaDiem());
        mSqLiteStatement.bindDouble(3, object.getDanhGia());
        mSqLiteStatement.executeUpdateDelete();
    }
}
