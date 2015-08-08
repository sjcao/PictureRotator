package com.sjcao.picturerotator;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by senjucao on 2015/8/5.
 */
public class PictureRotator implements ViewPager.OnPageChangeListener {
    private ViewPager vp_board;
    private Context context;
    private LayoutInflater inflater;
    private int timeout;  //多长时间切换一次pager

    List<View> views; //装载图片的View列表
    private ImageView[] dots; //圆点指示器

    private int currentIndex; //当前索引

    Timer timer;
    TimerTask task;
    int count = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    int currentPage = (Integer) msg.obj;
                    setCurrentDot(currentPage);
                    vp_board.setCurrentItem(currentPage);
                    break;
            }
        }
    };

    public PictureRotator(Context context, LayoutInflater inflater, int timeout) {
        this.context = context;
        this.inflater = inflater;
        this.timeout = timeout;
    }
    public View initView(final List<String> datas){
        View view= inflater.inflate(R.layout.viewpager_board,null);
        vp_board= (ViewPager) view.findViewById(R.id.vp_board);
        vp_board.setOnPageChangeListener(this);
        views=new ArrayList<View>();
        LinearLayout ll_board_dot= (LinearLayout) view.findViewById(R.id.ll_board_dot);
        for(int i=0;i<datas.size();i++){
            views.add(inflater.inflate(R.layout.viewpager_item_picture,null));
            ll_board_dot.addView(inflater.inflate(R.layout.viewpager_board_dot,null));
        }
        initDots(view, ll_board_dot); //初始化点指示器
        ViewPagerAdapter adapter=new ViewPagerAdapter(context,views,datas);
        vp_board.setOffscreenPageLimit(3); //设置viewpager保留多少个显示界面
        vp_board.setAdapter(adapter);

        timer=new Timer();
        task=new TimerTask() {
            @Override
            public void run() {
                int currentPage=count%datas.size();
                count++;
                Message msg=Message.obtain();
                msg.what=0;
                msg.obj=currentPage;
                handler.sendMessage(msg);
            }
        };
        timer.schedule(task,0,timeout); //定时切换页面
        return  view;
    }
//初始化圆点
    private void initDots(View view, LinearLayout ll_board_dot) {
        dots=new ImageView[views.size()];
        //循环获取小圆点指示器
        for(int i=0;i<views.size();i++){
            dots[i]=(ImageView)ll_board_dot.getChildAt(i);
            dots[i].setEnabled(true); //都为灰色
        }
        currentIndex=0;
        dots[currentIndex].setEnabled(false); //设置为false 黄色

    }

//当页面切换时，圆点也切换
    private void setCurrentDot(int currentPage) {
        if(currentPage<0||currentPage>views.size()-1||currentIndex==currentPage)
            return;
        dots[currentPage].setEnabled(false);
        dots[currentIndex].setEnabled(true);
        currentIndex=currentPage;
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        count=i;
        setCurrentDot(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
