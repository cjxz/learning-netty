package com.cjxz.bio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019/04/01
 * @Version: 1.0
 */
public class TimeServer {

    public static void main(String[] args)throws Exception {
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
                new Thread(new TimeServerHandler(socket)).start();
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
