package com.example.a1fo_purwohandoyo_proj6_mysql;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Student> studentList = new ArrayList<>();
    StudentAdapter adapter;
    String url = "http://192.168.0.6/stekomapi/get_students.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = findViewById(R.id.btn_add);
        Button btnRefresh = findViewById(R.id.btn_refresh);

        if (btnAdd == null || btnRefresh == null) {
            Log.e("MainActivity", "Buttons not found!");
            return;
        }

        btnAdd.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Add button clicked", Toast.LENGTH_SHORT).show();
            EditStudent(null);
        });

        btnRefresh.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Refreshing list...", Toast.LENGTH_SHORT).show();
            studentList.clear();
            adapter.notifyDataSetChanged();
            FetchStudents();
        });

        listView = findViewById(R.id.student_lists);
        adapter = new StudentAdapter(this, studentList, new StudentAdapter.OnItemClickListener() {
            @Override
            public void OnEdit(Student student) {
                Toast.makeText(MainActivity.this, "Edit " + student.name, Toast.LENGTH_SHORT).show();
                EditStudent(student);
            }

            @Override
            public void OnDelete(Student student) {
                Toast.makeText(MainActivity.this, "Delete " + student.name, Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Delete" + student.name + "?")
                        .setPositiveButton("Confirm", (dialog, which) -> DeleteStudent(student.id))
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
        listView.setAdapter(adapter);
        FetchStudents();
    }

    //Fetch Function
    private void FetchStudents() {
        studentList.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject student = response.getJSONObject(i);
                            int id = student.getInt("id");
                            String name = student.getString("name");
                            String year = student.getString("kelas");
                            String npm = student.getString("npm");

                            studentList.add(new Student(id, name, npm, year));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                });

        queue.add(request);
    }

    //Edit database function
    private void EditStudent(Student studentToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(studentToEdit == null ? "Add Student" : "Edit Student");
        builder.setMessage("Are you sure you want to edit this student?");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_student_form, null);
        EditText inputName = viewInflated.findViewById(R.id.edit_name);
        EditText inputNpm = viewInflated.findViewById(R.id.edit_npm);
        EditText inputKelas = viewInflated.findViewById(R.id.edit_kelas);

        if (studentToEdit != null) {
            inputName.setText(studentToEdit.name);
            inputNpm.setText(studentToEdit.npm);
            inputKelas.setText(studentToEdit.kelas);
        }

        builder.setView(viewInflated);

        builder.setPositiveButton(studentToEdit == null ? "Add" : "Update", (dialog, which) -> {
            String name = inputName.getText().toString();
            String npm = inputNpm.getText().toString();
            String kelas = inputKelas.getText().toString();

            if (studentToEdit == null) {
                AddStudent(name, npm, kelas);
            } else {
                UpdateStudent(studentToEdit.id, name, npm, kelas);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void UpdateStudent(int id, String name, String npm, String kelas) {
        String url = "http://192.168.0.6/stekomapi/update_student.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(this, "Student Updated", Toast.LENGTH_SHORT).show();
                    FetchStudents();
                },
                error -> error.printStackTrace()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                params.put("name", name);
                params.put("npm", npm);
                params.put("kelas", kelas);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void AddStudent(String name, String npm, String kelas) {
        String url = "http://192.168.0.6/stekomapi/add_students.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(this, "Student Added", Toast.LENGTH_SHORT).show();
                    FetchStudents();
                },
                error -> {
                    Toast.makeText(this, "Add failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("npm", npm);
                params.put("kelas", kelas);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void DeleteStudent(int id) {
        String url = "http://192.168.0.6/stekomapi/delete_student.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(this, "Student Deleted", Toast.LENGTH_SHORT).show();
                    FetchStudents();
                },
                error -> error.printStackTrace()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }



}