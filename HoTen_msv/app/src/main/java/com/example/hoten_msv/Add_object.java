package com.example.hoten_msv;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.hoten_msv.HoTenSV_SqLite.TABLENAME;

public class Add_object extends AppCompatActivity {
    private EditText editText1, editText2, editText3;
    private Button button, btnBack;
    private Contact_TenSV object;
    private HoTenSV_SqLite database = new HoTenSV_SqLite(Add_object.this);
    private boolean isSuccessful = false;
    public Toast toast;
    public final String KEY_INTENT = "intent";
    public final String KEY_ID = "id";
    public final String KEY_IS_UPDATE = "isUpdate";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_object);
        this.AnhXa();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_INTENT);
        int id = bundle.getInt(KEY_ID);
        boolean isUpdate = bundle.getBoolean(KEY_IS_UPDATE);

        if (isUpdate) {
            button.setText("Cập nhật");
            getSupportActionBar().setTitle("Chỉnh sửa");
            Cursor cursor = database.GetData("SELECT * FROM " + TABLENAME + " WHERE Id = " + id + ";");
            cursor.moveToFirst();
            object = new Contact_TenSV(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            // set Text cho EditText
            editText1.setText(String.valueOf(object.getId()));
            editText2.setText(object.getHoTen());
            editText3.setText(object.getSDT());
        } else {
            button.setText("Thêm");
            getSupportActionBar().setTitle("Thêm mới");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object = new Contact_TenSV(
                        Integer.valueOf(editText1.getText().toString()),
                        editText2.getText().toString(),
                        editText3.getText().toString()
                );
                if (isUpdate) {
                    database.Update(object);
                    isSuccessful = true;
                    editText1.setFocusable(false);
                } else {
                    database.Insert(object);
                    isSuccessful = true;
                    editText1.setFocusable(true);

                }
                if (isSuccessful) {
                    finish();
                    Intent intent1 = new Intent(Add_object.this, MainActivity.class);
                    startActivity(intent1);
                    isSuccessful = false;
                }
            }
        });
        // back để thoát khỏi  form thêm mới
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack  = new Intent(Add_object.this, MainActivity.class);
                startActivity(intentBack);
            }
        });
    }

    public void AnhXa() {
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        button = findViewById(R.id.button);
        btnBack = findViewById(R.id.btnBack);
    }


}