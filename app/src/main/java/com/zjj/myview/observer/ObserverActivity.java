package com.zjj.myview.observer;

import android.os.Bundle;

import com.zjj.myview.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ObserverActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer);
        MyEditText editText = findViewById(R.id.editTextTextPersonName);
        MyTextView textView2 = findViewById(R.id.textView2);
        editText.registerObservable(textView2);
    }
}
