package com.example.logical_01;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.logical_01.ui.book.Book;
import com.example.logical_01.ui.book.BookAdapter;
import com.example.logical_01.ui.home.HomeFragment;
import com.example.logical_01.ui.msg.MsgFragment;
import com.example.logical_01.ui.write.WriteFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

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

    private BottomNavigationView bottomNavigationView;
    private Fragment home_fragment;
    private Fragment message_fragment;
    private Fragment book_fragment;
    private Fragment write_fragment;
    private Fragment[] fragments;
    private int lastfragment;//用于记录上个选择的Fragment

    private FrameLayout contentLayout;//容器
    private BottomNavigationView mainBottomView;//底部导航
    private List<Fragment> fragmentList = new ArrayList<>();

    private static final String HOME_FRAGMENT_KEY = "home_Fragment";
    private static final String MSG_FRAGMENT_KEY = "message_Fragment";
    private static final String BOOK_FRAGMENT_KEY = "book_Fragment";
    private static final String WRITE_FRAGMENT_KEY = "write_Fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_msg, R.id.navigation_book,R.id.navigation_write)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        //底部导航

        //if (savedInstanceState != null) {

            /*获取保存的fragment  没有的话返回null*/
            /*home_fragment = (HomeFragment) getSupportFragmentManager().getFragment(savedInstanceState, HOME_FRAGMENT_KEY);
            message_fragment = (MsgFragment) getSupportFragmentManager().getFragment(savedInstanceState, MSG_FRAGMENT_KEY);
            book_fragment = (BookFragment) getSupportFragmentManager().getFragment(savedInstanceState, BOOK_FRAGMENT_KEY);
            write_fragment = (WriteFragment) getSupportFragmentManager().getFragment(savedInstanceState, WRITE_FRAGMENT_KEY);

            addToList(home_fragment);
            addToList(message_fragment);
            addToList(book_fragment);
            addToList(write_fragment);
        } else {
            initFragment();
        }*/
            //initFragment();
    }

    /*private void addToList(Fragment fragment) {
        if (fragment != null) {
            fragmentList.add(fragment);
        }
    }

    private void initView() {
        contentLayout = (FrameLayout) findViewById(R.id.nav_host_fragment);
        mainBottomView = (BottomNavigationView) findViewById(R.id.nav_view);
        mainBottomView.setOnNavigationItemSelectedListener(this);
    }

    private void initFragment() {
        /* 默认显示home  fragment*/
        /*home_fragment = new HomeFragment();
        addFragment(home_fragment);
        showFragment(home_fragment);
    }

    /*添加fragment*/
    /*private void addFragment(Fragment fragment) {
        /*判断该fragment是否已经被添加过  如果没有被添加  则添加*/
        /*if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, fragment).commit();
            /*添加到 fragmentList*/
            /*fragmentList.add(fragment);
        }
    }

    /*显示fragment*/
    /*private void showFragment(Fragment fragment) {
        for (Fragment frag : fragmentList) {
            if (frag != fragment) {
                /*先隐藏其他fragment*/
                /*getSupportFragmentManager().beginTransaction().hide(frag).commit();
            }
        }
        getSupportFragmentManager().beginTransaction().show(fragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                if (home_fragment == null) {
                    home_fragment = new HomeFragment();
                }
                addFragment(home_fragment);
                showFragment(home_fragment);
                break;

            case R.id.navigation_msg:
                if (message_fragment == null) {
                    message_fragment = new MsgFragment();
                }
                addFragment(message_fragment);
                showFragment(message_fragment);
                break;

            case R.id.navigation_book:
                if (book_fragment == null) {
                    book_fragment = new BookFragment();
                }
                addFragment(book_fragment);
                showFragment(book_fragment);
                break;

            case R.id.navigation_write:
                if (write_fragment==null){
                    write_fragment=new WriteFragment();
                }
                addFragment(write_fragment);
                showFragment(write_fragment);
                break;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        /*fragment不为空时 保存*/
        /*if (home_fragment != null) {
            getSupportFragmentManager().putFragment(outState, HOME_FRAGMENT_KEY, home_fragment);
        }
        if (message_fragment != null) {
            getSupportFragmentManager().putFragment(outState, MSG_FRAGMENT_KEY, message_fragment);
        }
        if (book_fragment != null) {
            getSupportFragmentManager().putFragment(outState, BOOK_FRAGMENT_KEY, book_fragment);
        }
        if (write_fragment != null) {
            getSupportFragmentManager().putFragment(outState, WRITE_FRAGMENT_KEY, write_fragment);
        }
        super.onSaveInstanceState(outState);
    }*/

    private void initFragment() {
        home_fragment=new HomeFragment();
        message_fragment = new MsgFragment();
        //book_fragment = new BookFragment();
        write_fragment = new WriteFragment();
        fragments = new Fragment[]{home_fragment, message_fragment, book_fragment,write_fragment};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction().replace(
                R.id.nav_host_fragment, home_fragment).show(home_fragment).commit();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(changeFragment);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    if (lastfragment != 0) {
                        switchFragment(lastfragment, 0);
                        lastfragment = 0;
                    }
                    return true;
                }
                case R.id.navigation_msg: {
                    if (lastfragment != 1) {
                        switchFragment(lastfragment, 1);
                        lastfragment = 1;
                    }
                    return true;
                }
                case R.id.navigation_book: {
                    if (lastfragment != 2) {
                        switchFragment(lastfragment, 2);
                        lastfragment = 2;
                    }
                    initBook();
                    RecyclerView recyclerView=(RecyclerView)findViewById(R.id.text_book);
                    StaggeredGridLayoutManager layoutManager = new
                            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    BookAdapter adapter=new BookAdapter(bookList);
                    recyclerView.setAdapter(adapter);
                    return true;
                }
                case R.id.navigation_write: {
                    if (lastfragment != 3) {
                        switchFragment(lastfragment, 3);
                        lastfragment = 3;
                    }
                    return true;
                }
            }
            return false;
        }
    };

    //切换Fragment
    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragments[lastfragment].setMenuVisibility(true);
        transaction.hide(fragments[lastfragment]);//隐藏上个Fragment
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.nav_host_fragment, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

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
