package com.cjxz.bio.threadpool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019/04/02
 * @Version: 1.0
 */
public class ThreadPoolTimeClient {
    public static void main(String[] args) {
        ThreadPoolTimeClient client = new ThreadPoolTimeClient();
        client.sendMsgThread();
    }
    //串行执行100次请求，客户端会顺序打印100个请求，每次阻塞1S原因是in.readLine是阻塞读取客户端数据
    public static void sendMsg(){
        int port = 8080;
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        for(int i = 0 ; i < 100 ; i++){
            try{
                socket = new Socket("127.0.0.1",port);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(),true);
                out.println("查询时间");
                String resp = in.readLine();
                System.out.println("当前服务器时间："+resp);
            }catch (Exception e){

            }finally {
                if(out != null){
                    out.close();
                }
                if(in != null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void sendMsgThread(){
        ThreadPoolExecutor pool = (ThreadPoolExecutor)Executors.newScheduledThreadPool(30);
        for(int i = 0 ; i < 300 ; i++){
            pool.execute(new Worker());
        }

    }

    class Worker implements Runnable{
        public void run() {
            int port = 8080;
            Socket socket = null;
            BufferedReader in = null;
            PrintWriter out = null;
            try{
                socket = new Socket("127.0.0.1",port);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(),true);
                out.println("查询时间");
                String resp = in.readLine();
                System.out.println("当前服务器时间："+resp);
            }catch (Exception e){

            }finally {
                if(out != null){
                    out.close();
                }
                if(in != null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
