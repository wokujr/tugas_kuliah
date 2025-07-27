package com.example.a1fo_purwohandoyo_proj6_mysql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentAdapter extends BaseAdapter implements StudentAdapterInterface {
    private Context m_context;
    private ArrayList<Student> m_studentLists;
    private LayoutInflater m_inflater;

    @Override
    public int GetItem() {
        return 0;
    }

    @Override
    public Object GetItem(int i) {
        return null;
    }

    @Override
    public long GetItemId(int i) {
        return 0;
    }

    @Override
    public View GetView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    public interface OnItemClickListener {
        void OnEdit(Student student);
        void OnDelete(Student student);
    }

    private OnItemClickListener m_listener;

    public StudentAdapter(Context context, ArrayList<Student> studentLists, OnItemClickListener listener) {
        this.m_context = context;
        this.m_studentLists = studentLists;
        this.m_listener = listener;
        this.m_inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return m_studentLists.size();
    }

    @Override
    public Object getItem(int i) {
        return m_studentLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return m_studentLists.get(i).id;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = m_inflater.inflate(R.layout.student_list_item, parent, false);

        TextView info = convertView.findViewById(R.id.student_info);
        Button btn_edit = convertView.findViewById(R.id.btn_edit);
        Button btn_delete = convertView.findViewById(R.id.btn_delete);

        Student student = m_studentLists.get(i);
        info.setText(student.npm + " - " + student.name + " (" + student.kelas + ")");

        btn_edit.setOnClickListener(view -> m_listener.OnEdit(student));
        btn_delete.setOnClickListener(view -> m_listener.OnDelete(student));

        return convertView;
    }
}

