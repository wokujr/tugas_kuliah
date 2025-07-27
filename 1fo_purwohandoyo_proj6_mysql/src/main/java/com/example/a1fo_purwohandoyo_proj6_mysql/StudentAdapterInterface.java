package com.example.a1fo_purwohandoyo_proj6_mysql;

import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

public interface StudentAdapterInterface {
    public int GetItem();

    Object GetItem(int i);

    long GetItemId(int i);

    View GetView(int i, View view, ViewGroup viewGroup);
}
