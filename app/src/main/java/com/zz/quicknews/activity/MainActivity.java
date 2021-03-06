package com.zz.quicknews.activity;

import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.zz.quicknews.NewsUtil;
import com.zz.quicknews.R;
import com.zz.quicknews.adapter.GridViewAdapter;
import com.zz.quicknews.fragment.HomeFragment;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private GridView mGvPage;
    public static boolean isForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JPushInterface.init(this);
        System.out.println("JPushInterface.getRegistrationID");
        JPushInterface.init(getApplicationContext());
        String rid = JPushInterface.getRegistrationID(getApplicationContext());
        System.out.println("JPushInterface.getRegistrationID"+rid);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        initView();
//        registerMessageReceiver();  // used for receive msg

    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(getApplicationContext());
        isForeground = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(getApplicationContext());
        isForeground = false;
    }

    @Override
    protected void onDestroy() {
//        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    public void initView() {

        mGvPage = (GridView) findViewById(R.id.pageView);
        int images[] = {R.drawable.b_newhome_tabbar_night, R.drawable.b_newdiscover_tabbar_night,
                R.drawable.b_newcare_tabbar_night, R.drawable.b_newmine_tabbar_night};
        int newImages[] = {R.drawable.b_newhome_tabbar_press, R.drawable.b_newdiscover_tabbar_press,
                R.drawable.b_newcare_tabbar_press, R.drawable.b_newmine_tabbar_press};
        String titles[] = {"首页", "视频", "关注", "我的"};
        final GridViewAdapter adapter = new GridViewAdapter(MainActivity.this, images, titles, newImages);
        mGvPage.setAdapter(adapter);
        mGvPage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("点击的位置是" + i);
                adapter.setSelectPosition(i);
                adapter.notifyDataSetChanged();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                switch (i) {
                    case 0:
                        ft.replace(R.id.content_view, HomeFragment.newInstance(0)).commit();
                        break;
                    case 1:
                        ft.replace(R.id.content_view, HomeFragment.newInstance(1)).commit();
                        break;

                }
            }
        });
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.content_view, new HomeFragment()).commit();
        adapter.setSelectPosition(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


        }
    }

   /* //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!NewsUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
//                setCostomMsg(showMsg.toString());
            }
        }
    }
*/




}
