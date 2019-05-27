package com.example.newsservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class newsService extends Service {
    boolean quit;
    public newsService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(this, "Service End", Toast.LENGTH_SHORT).show();
        quit = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        quit = false;
        NewsThread thread = new NewsThread(this,handler);
        thread.start();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class NewsThread extends Thread{
        newsService parent;
        Handler handler;
        String[] arNews = {
                "일본, 독도는 한국땅으로 인정",
                "번데기 맛 쵸코파이 출시",
                "춘천 지역에 초거대 유전 발견",
        };
        public NewsThread(newsService parent, Handler handler){
            this.parent = parent;
            this.handler = handler;
        }
        public void run(){
            for(int i = 0; quit==false; i++){
                Message msg = new Message();
                msg.what = 0;
                msg.obj = arNews[i % arNews.length];
                handler.sendMessage(msg);
                try{
                    Thread.sleep(5000);
                }catch (Exception e){;}
            }
        }
    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == 0){
                String news = (String)msg.obj;
                Toast.makeText(newsService.this, news, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
