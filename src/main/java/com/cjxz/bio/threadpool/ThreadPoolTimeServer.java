package com.cjxz.bio.threadpool;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.cjxz.bio.TimeServerHandler;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019/04/01
 * @Version: 1.0
 */
public class ThreadPoolTimeServer {

    private static ThreadPoolExecutor pool = null;

    public static void init(){
        pool = (ThreadPoolExecutor)Executors.newScheduledThreadPool(5);
    }

    public static void main(String[] args)throws Exception {
        if(pool == null){
            init();
        }
        int port = 8080;
        ServerSocket server = null;
        try{
            //开启一个ServerSocket服务端
            server = new ServerSocket();
            SocketAddress address = new InetSocketAddress("127.0.0.1",port);
            server.bind(address);

            System.out.println("打开端口："+port);
            Socket socket = null;
            while(true){
                //如果有客户端接入，开启一个新的线程为当前这个Socket服务
                socket = server.accept();
                ThreadPoolTimeServer.pool.execute(new TimeServerHandler(socket));

            }

        }finally {
            if(server != null){
                System.out.println("服务端关闭");
                server.close();
                server = null;
            }
        }
    }

}
