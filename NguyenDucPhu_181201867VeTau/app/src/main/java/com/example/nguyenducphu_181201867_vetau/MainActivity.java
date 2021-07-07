package com.example.nguyenducphu_181201867_vetau;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.nguyenducphu_181201867_vetau.Phu_Sqlite.TABLENAME;

public class MainActivity extends AppCompatActivity {

    public final String KEY_INTENT = "intent";
    public final String KEY_ID = "id";
    public final String KEY_IS_UPDATE = "isUpdate";

    public Phu_Sqlite db = new Phu_Sqlite(MainActivity.this);
    public ListView listView;
    public EditText editTextFilter;
    public FloatingActionButton actionButtonAdd;
    public Phu_Adapter adapter;
    public ArrayList<Contact_181201867> arrayListObject = new ArrayList<>();
    public ArrayList<Contact_181201867> arrayListObjectDelete = new ArrayList<>();
    public long backPressTime;
    public Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Ánh xạ
        listView = findViewById(R.id.listView);
        editTextFilter = findViewById(R.id.editTextSearch);
        actionButtonAdd = findViewById(R.id.floatingActionButtonAdd);
        // Insert data
        if (db.GetData("SELECT * FROM " + TABLENAME).getCount() == 0) {
            db.Insert(new Contact_181201867(1, "Vinh", "Nam Định", 351.500, true));
            db.Insert(new Contact_181201867(2, "Nam Định", "Thanh Hóa", 237.500, true));
            db.Insert(new Contact_181201867(3, "Thanh Hóa", "Hà Nội", 170.000, false));
            db.Insert(new Contact_181201867(4, "Hà Nội", "Thanh Hóa", 170.000, false));
            db.Insert(new Contact_181201867(5, "Hà Nội", "Nam Định", 131.100, true));

        }

        // End Insert data

        arrayListObject = selectAll(TABLENAME);
        // Sort arrayList theo Giá (đảo vị trí (t1,t2) để đảo chiều sort)
        Collections.sort(arrayListObject, (t1, t2) -> t2.getDonGia().compareTo(t1.getDonGia()));
        // set Adapter
        adapter = new Phu_Adapter(MainActivity.this, arrayListObject, new Phu_Adapter.onClick() {
            @Override
            public void onClickItem(Contact_181201867 sp, Boolean isChecked) {
                if (isChecked) {
                    arrayListObjectDelete.add(sp);
                } else {
                    arrayListObjectDelete.remove(sp);
                }
            }

            @Override
            public void onClickEditItem(Contact_181201867 sp) {
                intentView(sp.getId(), Add_object.class, "intent", 1);
            }
        });
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
                intentView(0, Add_object.class, "intent", 0);
            }
        });

    }
    // menu thường
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_context, menu);
        return true;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // Khởi tạo Context Menu
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_context, menu);
    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_item_xoa: {
//                for (Contact_181201867 sp : arrayListObjectDelete) {
//                    db.Delete(sp);
//                    arrayListObject.remove(sp);
//                    adapter.notifyDataSetChanged();
//                }
//                break;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }
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
                intentView(arrayListObject.get(info.position).getId(), Add_object.class, KEY_INTENT, 1 );
                break;
        }
        return super.onContextItemSelected(item);
    }

    public ArrayList<Contact_181201867> selectAll(String tableName) {
        // Select data SQLite
        ArrayList<Contact_181201867> arrayList = new ArrayList<>();
        Cursor cursor = db.GetData("SELECT * FROM " + tableName + "");
        while (cursor.moveToNext()) {
            int col0 = Integer.parseInt(cursor.getString(0));
            String col1 = cursor.getString(1);
            String col2 = cursor.getString(2);
            Double col3 = cursor.getDouble(3);
            boolean col4 = Boolean.valueOf(cursor.getString(4));
            arrayList.add(new Contact_181201867(col0, col1, col2, col3, col4));
        }
        return arrayList;
    }

    private void intentView(int val, Class cls, String str, int isEdit) {
        // truyền dữ liệu giữa các activity
        Intent intent = new Intent(MainActivity.this, cls);
        Bundle bundle = new Bundle();
        bundle.putInt("id", val);
        bundle.putInt("isEdit", isEdit);
        intent.putExtra(str, bundle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis()) {
            toast.cancel();
            this.finishAffinity();
            return;
        } else {
            toast = Toast.makeText(MainActivity.this, "Nhấp Back 1 lần nữa để thoát", Toast.LENGTH_SHORT);
            toast.show();
        }
        backPressTime = System.currentTimeMillis();
    }
}