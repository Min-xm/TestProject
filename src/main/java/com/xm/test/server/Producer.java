package com.xm.test.server;


import com.xm.test.entity.Student;

public interface Producer {
    public void sendMail(String queue, Student student);//向队列queue发送消息
    public void getMail(String queue);//向队列queue获取数据
}
