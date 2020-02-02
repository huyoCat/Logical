package com.example.logical_01.book;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.logical_01.MainActivity;
import com.example.logical_01.R;

import java.util.zip.Inflater;

public class book_Activity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_top_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Tolead_b:
                Toast.makeText(this,"还不支持噢",Toast.LENGTH_SHORT).show();
                break;

            case R.id.share_item:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "要分享的内容");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "选择要分享的方式"));
                break;

            case R.id.help_item:
                Toast.makeText(this,"QQ：1064534251",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
    //右上角菜单

    private String[] book={
            "《某某》","《C语言修仙》","《全星际都知道他是我前男友》",
            "《某某》","《C语言修仙》","《全星际都知道他是我前男友》",
            "《某某》","《C语言修仙》","《全星际都知道他是我前男友》",
            "《某某》","《C语言修仙》","《全星际都知道他是我前男友》",
            "《某某》","《C语言修仙》","《全星际都知道他是我前男友》",
            "《某某》","《C语言修仙》","《全星际都知道他是我前男友》",
            "《某某》","《C语言修仙》","《全星际都知道他是我前男友》",
            "《某某》","《C语言修仙》","《全星际都知道他是我前男友》",
            "《某某》","《C语言修仙》","《全星际都知道他是我前男友》",};

    @Nullable
    public View onCreateView(@Nullable LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_book_, container, false);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                book_Activity.this,android.R.layout.simple_list_item_1,book);
        ListView listView=(ListView)findViewById(R.id.book_ListView);
        listView.setAdapter(adapter);
        //book页面ListView
    }
}
