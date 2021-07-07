package com.example.nguyenducphu_181201867;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.nguyenducphu_181201867.Phu_Sqlite.TABLENAME;

public class MainActivity extends AppCompatActivity {
    public Phu_Sqlite db = new Phu_Sqlite(MainActivity.this);
    public ListView listView;
    public EditText editTextFilter;
    public FloatingActionButton actionButtonAdd;
    public Phu_Adapter adapter;
    public ArrayList<Contact_181201867> arrayListObject = new ArrayList<>();
    public long backPressTime;
    public Toast toast;

    public final String KEY_INTENT = "intent";
    public final String KEY_ID = "id";
    public final String KEY_IS_UPDATE = "isUpdate";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Trang chủ");
        // Ánh xạ
        listView = findViewById(R.id.listView);
        editTextFilter = findViewById(R.id.editTextSearch);
        actionButtonAdd = findViewById(R.id.floatingActionButtonAdd);
        // Insert data
        if (db.GetData("SELECT * FROM " + db.TABLENAME).getCount() == 0) {
            db.Insert(new Contact_181201867(1, "Nam", "098876533"));
            db.Insert(new Contact_181201867(2, "Hữu Thắng", "098876533"));
            db.Insert(new Contact_181201867(3, "Toàn", "098876533"));
            db.Insert(new Contact_181201867(4, "Phú", "098876533"));
            db.Insert(new Contact_181201867(5, "Minh HIếu", "098876533"));
            db.Insert(new Contact_181201867(6, "Hoàng Anh", "098876533"));
            db.Insert(new Contact_181201867(7, "Thành Mới", "098876533"));
        }
        // End Insert data
        arrayListObject = selectAll(TABLENAME);
        // Sort arrayList theo Giá (đảo vị trí (t1,t2) để đảo chiều sort)
        Collections.sort(arrayListObject, (t1, t2) -> t1.getHoTen().compareTo(t2.getHoTen()));
        // set Adapter
        adapter = new Phu_Adapter(MainActivity.this, arrayListObject);
        listView.setAdapter(adapter);
        // Đăng ký contextMenu
        registerForContextMenu(listView);
        // Filter
        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        actionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentView(0, Add_object.class, KEY_INTENT, false);
            }
        });

    }

    public ArrayList<Contact_181201867> selectAll(String tableName) {
        // Select data SQLite
        ArrayList<Contact_181201867> arrayList = new ArrayList<>();
        Cursor cursor = db.GetData("SELECT * FROM " + tableName + "");
        while (cursor.moveToNext()) {
            String col1 = cursor.getString(1);
            arrayList.add(new Contact_181201867(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            ));
        }
        return arrayList;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // Khởi tạo Context Menu
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        // Sự kiện click Item Contect Menu
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_item_xoa:
                // chọn item 'Xóa'
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirm");
                builder.setMessage("Nguyễn Đức Phú wants to detele?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Sự kiện click nút 'Có'
                        db.Delete(new Contact_181201867(arrayListObject.get(info.position).getId()));
                        arrayListObject.remove(info.position);
                        adapter.updateResults(arrayListObject);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // sự kiện nút 'Không'
                    }
                });
                builder.create();// tạo dialog
                builder.show(); // show dialog
                return true;
            case R.id.menu_item_sua:
                // chọn item 'Sửa'
                intentView(arrayListObject.get(info.position).getId(), Add_object.class, KEY_INTENT, true);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void intentView(int val, Class cls, String str, Boolean isUpdate) {
        // truyền dữ liệu giữa các activity
        Intent intent = new Intent(MainActivity.this, cls);
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ID, val);
        bundle.putBoolean(KEY_IS_UPDATE, isUpdate);
        intent.putExtra(str, bundle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // click 'Back'
        if (backPressTime + 2000 > System.currentTimeMillis()) {
            toast.cancel();
            this.finishAffinity();
//            super.onBackPressed();
            return;
        } else {
            toast = Toast.makeText(MainActivity.this, "Nhấp Back 1 lần nữa để thoát", Toast.LENGTH_SHORT);
            toast.show();
        }
        backPressTime = System.currentTimeMillis();
    }
}