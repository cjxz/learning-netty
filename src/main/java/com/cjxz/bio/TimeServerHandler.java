package com.cjxz.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019/04/01
 * @Version: 1.0
 */
public class TimeServerHandler implements Runnable{
    private Socket socket;
    public TimeServerHandler(Socket socket){
        this.socket = socket;
    }

    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            //从socket中获得输入和输出流
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(),true);
            String currentTime = null;
            String body = null;
            while (true){
                //得到客户端发送到服务端的数据
                body = in.readLine();
                if(body == null){
                    break;
                }
                System.out.println("接收内容："+body);
                currentTime = "服务器时间："+new Date(System.currentTimeMillis());
                //服务端向客户端写入数据
                out.println(currentTime);
            }
        }catch (Exception e){
            if(in != null){
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if(out != null){
                out.close();
            }
            if(this.socket != null){
                try {
                    this.socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
