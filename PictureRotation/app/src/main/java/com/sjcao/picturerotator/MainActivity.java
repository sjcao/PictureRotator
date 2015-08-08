package com.sjcao.picturerotator;

import android.app.Activity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.sjcao.picturerotator.volley.RequestManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private LayoutInflater inflater;
    private LinearLayout ll_board_viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflater=LayoutInflater.from(this);
        RequestManager.init(this);
        initView();
    }
    private void initView() {
            ll_board_viewpager= (LinearLayout) findViewById(R.id.ll_board_viewpager);
        List<String> datas=new ArrayList<String>();
        datas.add("http://h.hiphotos.baidu.com/image/pic/item/71cf3bc79f3df8dc668cb219ce11728b46102880.jpg");
        datas.add("http://g.hiphotos.baidu.com/image/pic/item/4bed2e738bd4b31cb966705984d6277f9f2ff8fe.jpg");
        datas.add("http://b.hiphotos.baidu.com/image/pic/item/3801213fb80e7bec85dd9ae02d2eb9389b506ba8.jpg");
        datas.add("http://b.hiphotos.baidu.com/image/pic/item/3c6d55fbb2fb4316e463e37b22a4462309f7d3b7.jpg");
            ll_board_viewpager.addView(new PictureRotator(this,inflater,3000).initView(datas));
    }

}
