package com.example.logical_01;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logical_01.ui.book.Book;
import com.example.logical_01.ui.book.BookAdapter;
import com.example.logical_01.ui.book.BookFragment;
import com.example.logical_01.ui.home.HomeFragment;
import com.example.logical_01.ui.msg.MsgFragment;
import com.example.logical_01.ui.write.WriteFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

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
                Intent intent=new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:1064534251@qq.com"));
                intent.putExtra(Intent.EXTRA_EMAIL, "请输入内容");
                intent.putExtra(Intent.EXTRA_SUBJECT, "请输入标题");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
                default:
        }
        return true;
    }

    private List<Book> bookList	= new ArrayList<>();

    //底部菜单栏3个TextView
    @BindView(R.id.home_bottom) TextView mhome;
    @BindView(R.id.message_bottom) TextView mmsg;
    @BindView(R.id.book_bottom) TextView mbook;
    @BindView(R.id.write_bottom) TextView mwrite;

    private Fragment home_fragment;
    private Fragment message_fragment;
    private Fragment book_fragment;
    private Fragment write_fragment;
    private int lastfragment=0;//用于记录上个选择的Fragment


    private static final int HOME_FRAGMENT_KEY = 0;
    private static final int MSG_FRAGMENT_KEY = 1;
    private static final int BOOK_FRAGMENT_KEY = 2;
    private static final int WRITE_FRAGMENT_KEY = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_msg, R.id.navigation_book,R.id.navigation_write)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/

        ButterKnife.bind(this);
        //根据传入的Bundle对象判断Activity是正常启动还是销毁重建
        if(savedInstanceState == null){
            //设置第一个Fragment默认选中
            setFragment(HOME_FRAGMENT_KEY);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //通过onSaveInstanceState方法保存当前显示的fragment
        outState.putInt("fragment_id", lastfragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
        FragmentManager mFragmentManager = getSupportFragmentManager();
        //通过FragmentManager获取保存在FragmentTransaction中的Fragment实例
        home_fragment = mFragmentManager.findFragmentByTag("home_fragment");
        message_fragment = mFragmentManager.findFragmentByTag("message_fragment");
        book_fragment = mFragmentManager.findFragmentByTag("book_fragment");
        write_fragment = mFragmentManager.findFragmentByTag("write_fragment");
        //恢复销毁前显示的Fragment
        setFragment(savedInstanceState.getInt("fragment_id"));
    }

    @OnClick({R.id.home_bottom, R.id.message_bottom,R.id.book_bottom, R.id.write_bottom})
    public void onClick(View v) {
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycle_book);
        switch (v.getId()) {
            default:
                break;
            case R.id.home_bottom:
                recyclerView.setVisibility(View.GONE);
                setFragment(HOME_FRAGMENT_KEY);
                break;
            case R.id.message_bottom:
                recyclerView.setVisibility(View.GONE);
                setFragment(MSG_FRAGMENT_KEY);
                break;
            case R.id.book_bottom:
                setFragment(BOOK_FRAGMENT_KEY);
                initBook();
                recyclerView.setVisibility(View.VISIBLE);
                StaggeredGridLayoutManager layoutManager = new
                        StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                BookAdapter adapter=new BookAdapter(bookList);
                recyclerView.setAdapter(adapter);
                break;
            case R.id.write_bottom:
                recyclerView.setVisibility(View.GONE);
                setFragment(WRITE_FRAGMENT_KEY);
                break;
        }
    }

    private void setFragment(int index){
        //获取Fragment管理器
        FragmentManager mFragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        //隐藏所有Fragment
        hideFragments(mTransaction);
        switch (index){
            default:
                break;
            case HOME_FRAGMENT_KEY:
                lastfragment = HOME_FRAGMENT_KEY;
                //设置菜单栏为选中状态（修改文字和图片颜色）
                mhome.setTextColor(getResources().getColor(R.color.colorPrimary));
                mhome.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.home_later,0,0);
                //显示对应Fragment
                if(home_fragment == null){
                    home_fragment = new HomeFragment();
                    mTransaction.add(R.id.container, home_fragment, "home_fragment");
                }else {
                    mTransaction.show(home_fragment);
                }
                break;
            case MSG_FRAGMENT_KEY:
                lastfragment = MSG_FRAGMENT_KEY;
                //设置菜单栏为选中状态（修改文字和图片颜色）
                mmsg.setTextColor(getResources().getColor(R.color.colorPrimary));
                mmsg.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.message_later,0,0);
                //显示对应Fragment
                if(message_fragment == null){
                    message_fragment = new MsgFragment();
                    mTransaction.add(R.id.container, message_fragment, "message_fragment");
                }else {
                    mTransaction.show(message_fragment);
                }
                break;
            case BOOK_FRAGMENT_KEY:
                lastfragment = BOOK_FRAGMENT_KEY;
                //设置菜单栏为选中状态（修改文字和图片颜色）
                mbook.setTextColor(getResources().getColor(R.color.colorPrimary));
                mbook.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.book_later,0,0);
                //显示对应Fragment
                if(book_fragment == null){
                    book_fragment = new BookFragment();
                    mTransaction.add(R.id.container, book_fragment, "book_fragment");
                }else {
                    mTransaction.show(book_fragment);
                }
                break;
            case WRITE_FRAGMENT_KEY:
                lastfragment = WRITE_FRAGMENT_KEY;
                //设置菜单栏为选中状态（修改文字和图片颜色）
                mwrite.setTextColor(getResources().getColor(R.color.colorPrimary));
                mwrite.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.write_later,0,0);
                //显示对应Fragment
                if(write_fragment == null){
                    write_fragment = new WriteFragment();
                    mTransaction.add(R.id.container, write_fragment, "write_fragment");
                }else {
                    mTransaction.show(write_fragment);
                }
                break;
        }
        //提交事务
        mTransaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction){
        if(home_fragment != null){
            //隐藏Fragment
            transaction.hide(home_fragment);
            //将对应菜单栏设置为默认状态
            mhome.setTextColor(getResources().getColor(R.color.colorText));
            mhome.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.mipmap.home_before,0,0);
        }
        if(message_fragment != null){
            transaction.hide(message_fragment);
            //将对应菜单栏设置为默认状态
            mmsg.setTextColor(getResources().getColor(R.color.colorText));
            mmsg.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.mipmap.message_before,0,0);
        }
        if(book_fragment != null){
            transaction.hide(book_fragment);
            //将对应菜单栏设置为默认状态
            mbook.setTextColor(getResources().getColor(R.color.colorText));
            mbook.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.mipmap.book_before,0,0);
        }
        if(write_fragment != null){
            transaction.hide(write_fragment);
            //将对应菜单栏设置为默认状态
            mwrite.setTextColor(getResources().getColor(R.color.colorText));
            mwrite.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.mipmap.write_before,0,0);
        }
    }


                   /* initBook();
                    RecyclerView recyclerView=(RecyclerView)findViewById(R.id.text_book);
                    StaggeredGridLayoutManager layoutManager = new
                            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    BookAdapter adapter=new BookAdapter(bookList);
                    recyclerView.setAdapter(adapter);*/


    private void initBook(){
        Book book1 = new Book("《某某》");
        bookList.add(book1);
        Book book2 = new Book("《某某》");
        bookList.add(book2);
        Book book3 = new Book("《某某》");
        bookList.add(book3);
        Book book4 = new Book("《某某》");
        bookList.add(book4);
        Book book5 = new Book("《某某》");
        bookList.add(book5);
        Book book6 = new Book("《某某》");
        bookList.add(book6);
        Book book7 = new Book("《某某》");
        bookList.add(book7);
        Book book8 = new Book("《某某》");
        bookList.add(book8);
        Book book9 = new Book("《某某》");
        bookList.add(book9);
        Book book10 = new Book("《某某》");
        bookList.add(book10);
        Book book11 = new Book("《某某》");
        bookList.add(book11);
        Book book12 = new Book("《某某》");
        bookList.add(book12);
        Book book13 = new Book("《某某》");
        bookList.add(book13);
        Book book14 = new Book("《某某》");
        bookList.add(book14);
        Book book15 = new Book("《某某》");
        bookList.add(book15);
        Book book16 = new Book("《某某》");
        bookList.add(book16);
        Book book17 = new Book("《某某》");
        bookList.add(book17);
        Book book18 = new Book("《某某》");
        bookList.add(book18);
        Book book19 = new Book("《某某》");
        bookList.add(book19);
        Book book20 = new Book("《某某》");
        bookList.add(book20);
    }
}
