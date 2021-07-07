package com.example.nguyenducphu_181201867_thietbi;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.nguyenducphu_181201867_thietbi.Phu_Sqlite.TABLENAME;

public class MainActivity extends AppCompatActivity {
    public final String KEY_INTENT = "intent";
    public final String KEY_ID = "id";
    public final String KEY_IS_UPDATE = "isUpdate";

    public Phu_Sqlite db = new Phu_Sqlite(MainActivity.this);
    public ListView listView;
    public EditText editTextFilter;
    public Button   buttonAdd, buttonDelete;
    public Phu_Adapter adapter;
    public ArrayList<Contact_181201867> arrayListObject = new ArrayList<>();
    public ArrayList<Contact_181201867> arrayListObjectSelect = new ArrayList<>();
    public long backPressTime;
    public Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Ánh xạ
        listView = findViewById(R.id.listView);
        editTextFilter = findViewById(R.id.editTextSearch);
        buttonAdd = findViewById(R.id.btnAdd);
        buttonDelete = findViewById(R.id.btnDelete);
        // Insert data
        if (db.GetData("SELECT * FROM " + TABLENAME).getCount() == 0) {
            db.Insert(new Contact_181201867(0, "Bóng đèn", "220V", "a", false));
            db.Insert(new Contact_181201867(0, "Máy bơm", "220V", "a", false));
            db.Insert(new Contact_181201867(0, "Quạt điện", "220V", "a", false));
            db.Insert(new Contact_181201867(0, "Tủ lạnh", "220V", "a", false));
            db.Insert(new Contact_181201867(0, "Điều hòa", "220V", "a", false));
            db.Insert(new Contact_181201867(0, "Tivi", "220V", "a", false));
            db.Insert(new Contact_181201867(0, "Nồi cơm điện", "220V", "a", false));

        }

        // End Insert data
        arrayListObject = selectAll(TABLENAME);
        // Sort arrayList theo Giá (đảo vị trí (t1,t2) để đảo chiều sort)
        Collections.sort(arrayListObject, (t1, t2) -> t2.getTenThietBi().compareTo(t1.getTenThietBi()));
        // set Adapter
        adapter = new Phu_Adapter(MainActivity.this, arrayListObject, new Phu_Adapter.onClick() {
            @Override
            public void onClickItem(Contact_181201867 sp, Boolean isChecked) {
                if (isChecked) {
                    arrayListObjectSelect.add(sp);
                } else {
                    arrayListObjectSelect.remove(sp);
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

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentView(0, Add_object.class, "intent", 0);
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Nguyễn Đức Phú wants to detele?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Sự kiện click nút 'Có'
                        for (Contact_181201867 sp : arrayListObjectSelect) {
                            db.Delete(sp);
                            arrayListObject.remove(sp);
                            adapter.notifyDataSetChanged();
                        }
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

            }
        });
    }
    //ko dùng được context menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // Khởi tạo Context Menu
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_xoa: {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Nguyễn Đức Phú wants to detele?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Sự kiện click nút 'Có'
                        for (Contact_181201867 sp : arrayListObjectSelect) {
                            db.Delete(sp);
                            arrayListObject.remove(sp);
                            adapter.notifyDataSetChanged();
                        }
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
                break;
            }
            case R.id.menu_item_sua:
                // chọn item 'Sửa'
                intentView(arrayListObjectSelect.get(0).getId(), Add_object.class, KEY_INTENT, 1 );
                break;
        }
        return super.onOptionsItemSelected(item);
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
                        for (Contact_181201867 sp : arrayListObjectSelect) {
                            db.Delete(sp);
                            arrayListObject.remove(sp);
                            adapter.notifyDataSetChanged();
                        }
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
            String col3 = cursor.getString(3);
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