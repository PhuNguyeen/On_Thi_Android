package com.example.nguyenducphu_181201867_checkbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import static com.example.nguyenducphu_181201867_checkbox.Phu_Sqlite.TABLENAME;

public class Add_object extends AppCompatActivity {
    private EditText editText1, editText2;
    private CheckBox checkBox;
    private Button buttonThem,btnBack;
    private Contact_181201867 contact_181201867;
    private Phu_Sqlite database = new Phu_Sqlite(Add_object.this);
    private int id;
    private Boolean col3;
    private String col1, col2;
    private boolean isSuccessful = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_object);
        // Ánh xạ
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        checkBox = findViewById(R.id.checkBox1);
        buttonThem = findViewById(R.id.buttonAdd);
        btnBack = findViewById(R.id.btnBack);
        // Khai báo
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("intent");// dong này đổi bị sai
        id = bundle.getInt("id");
        int isEdit = bundle.getInt("isEdit");
        // Xử lý
        if (isEdit == 1) {
            buttonThem.setText("Cập nhật");
            Cursor cursor = database.GetData("SELECT * FROM " + TABLENAME + " WHERE Id = " + id + ";");
            cursor.moveToFirst();
            col1 = cursor.getString(1);
            col2 = cursor.getString(2);
            col3 = Boolean.valueOf(cursor.getString(3));
            // set Text cho EditText
            editText1.setText(col1);
            editText2.setText(col2);
            checkBox.setChecked(col3);
            checkBox.setChecked(!col3);

        } else {
            buttonThem.setText("Thêm");
        }
        buttonThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidate()) {
                    Toast.makeText(Add_object.this, "Không được để trống thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isEdit == 1) {
                    database.Update(contact_181201867);
                    isSuccessful = true;
                } else {
                    try {
                        database.Insert(contact_181201867);
                        isSuccessful = true;
                    } catch (Exception e) {
                        Toast.makeText(Add_object.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                if (isSuccessful) {
                    finish();
                    Intent intent1 = new Intent(Add_object.this, MainActivity.class);
                    startActivity(intent1);
                    isSuccessful = false;
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack  = new Intent(Add_object.this, MainActivity.class);
                startActivity(intentBack);
            }
        });
    }

    public Boolean isValidate() {
        col1 = editText1.getText().toString();
        col2 = editText2.getText().toString();
        col3 = checkBox.isChecked();


        if (col1.isEmpty() || col2.isEmpty() || col3 == null) {
            return false;
        } else {
            contact_181201867 = new Contact_181201867(id, col1, col2, col3);
            return true;
        }
    }
}