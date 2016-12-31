package com.example.shana.thread;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.sql.Time;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView tv1;
    private int seconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.tv1);
        long LastDay=new Date(117,5,23).getTime();
        long today=new Date().getTime();
        seconds= (int) (LastDay-today);
    }

    public void doclick(View V) {
        switch (V.getId()) {
            case R.id.tv_anr:
                anrDemo();
                break;
            case R.id.tv_handler:
                handlerDemo();
                break;
            case R.id.tv_runnabel:
                runnableDemo();
                break;
            case R.id.tv_thread:
                threadDemo();
                break;
            case R.id.tv_timer_task:
                timertaskDemo();
                break;
            case R.id.tv_asynctask:
                asynctaskDemo();
                break;
        }
    }

    private void asynctaskDemo() {
        class MyAsyncTask extends AsyncTask<Long,String,String>{
            Context context;
            int duration=10,count=0;
            public  MyAsyncTask(Activity context){
                this.context=context;
            }
            @Override
            protected String doInBackground(Long... params) {
                long num=params[0].longValue();
                while (count<duration){
                    num--;
                    count++;
                    String status="离毕业还有" +num+
                            "秒"+"努力学习" +count+
                            "秒";
                    publishProgress(status);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return "这一秒"+duration+"没有虚度";
            }

            @Override
            protected void onPostExecute(String s) {
                showMsg(s);
                super.onPostExecute(s);
            }

            @Override
            protected void onProgressUpdate(String... values) {
                tv1.setText(values[0]);
                super.onProgressUpdate(values);
            }
        }
        MyAsyncTask myAsyncTask=new MyAsyncTask(this);
        myAsyncTask.execute((long)seconds);

    }

    private void handlerDemo() {
        final Handler handler =new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                showMsg(message.toString()+"\n"+message.getData().getString("attach"));
                return false;
            }
        });
        class MyMsgTask extends TimerTask{
            int count;
            double fighting1=1,fighting2=1;
            public MyMsgTask(int seconds){
                this.count=seconds;
            }
            @Override
            public void run() {
                Message message=Message.obtain(handler);
                message.what=1;
                message.arg1=count--;
                fighting1=fighting1*1.01;
                fighting2=fighting2*1.02;
                Bundle data=new Bundle();
                data.putString("attach","努力多1%"+fighting1+"\t努力多2%"+fighting2);
                message.setData(data);
                message.sendToTarget();
            }
        }
            Timer timer=new Timer();
            timer.schedule(new MyMsgTask(seconds),1,1000);

    }

    private void showMsg(String msg) {
        tv1.setText(msg);
    }

    private void runnableDemo() {
        class MyRunnable implements Runnable{
            String name;
            Random random;
            MyRunnable (String name){
                this.name=name;
                random=new Random();
            }
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    System.out.println(name+"\t"+i);
                    try {
                        Thread.sleep(random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(name+"\t完成");
            }
        }
        Thread thread1=new Thread(new MyRunnable("线程一"));
        Thread thread2=new Thread(new MyRunnable("线程二"));
        thread1.start();
        thread2.start();
    }

    private void anrDemo() {
        for(int i=0;i<10000;i++){
            BitmapFactory.decodeResource(getResources(),R.drawable.android);
        }
    }

    private void threadDemo() {
        class MyThread extends Thread {
            Random random;

            public MyThread(String name) {
                super(name);
                random = new Random();
            }

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(getName() + "\t" + i);
                    try {
                        Thread.sleep(random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println(getName() + "\t完成");
            }
        }
        MyThread MyThread = new MyThread("线程一");
        MyThread.start();
        MyThread MyThread1 = new MyThread("线程二");
        MyThread1.start();
    }


    private void timertaskDemo() {
        class MyWorkTask extends TimerTask {
            Random random;
            String name;

            public MyWorkTask(String name) {
                this.name = name;
                random = new Random();
            }

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(name + "\t" + i);
                    try {
                        Thread.sleep(random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println(name + "\t完成");
            }
        }
        Timer timer = new Timer();
        Timer timer1 = new Timer();
        timer.schedule(new MyWorkTask("线程一"), 0);
        timer.schedule(new MyWorkTask("线程二"), 0);
    }


}
