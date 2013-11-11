package com.example.testpulltorefresh;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((PullToRefreshView) findViewById(R.id.listview)).getListView().setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    static String[] data = {
            "kk", "kkjkk", "kkk", "kjdkfjkdfj", "Mac book pro", "X230",
            "kk", "kkjkk", "kkk", "kjdkfjkdfj", "Mac book pro", "X230",
            "kk", "kkjkk", "kkk", "kjdkfjkdfj", "Mac book pro", "X230",
            "kk", "kkjkk", "kkk", "kjdkfjkdfj", "Mac book pro", "X230",
    };
}
