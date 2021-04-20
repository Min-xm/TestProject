package com.xm.test;



import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.*;
import com.xm.test.entity.Student;
import com.xm.test.server.Producer;
import org.junit.jupiter.api.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;


@SpringBootTest
public class MyTest {

    public enum Type{
        /** 成功 */
        SUCCESS(0),
        /** 警告 */
        WARN(301),
        /** 错误 */
        ERROR(500);
        private final int value;

        Type(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }

    @Autowired
    private RedisTemplate<String, Student> template;

//    @Resource
//    private Producer producer;

    @Test
    void contextLoads() {
//        RedisUtil redisUtil = new RedisUtil(template);
        Student student = new Student();
        student.setName("123");
        student.setSex("男");
//        template.opsForValue().set("student",student);
//        redisUtil.hPut("hash","student",student);

//        Type type = Type.SUCCESS;
//        System.out.println(type.value);

//        producer.sendMail("XM",student);
//        producer.getMail("XM");

        //浅克隆
//        Student student2 = student.clone();

        //深克隆（与浅克隆的区别：是否复制引入类型的成员变量）
//        Student student3 = ObjectUtil.cloneByStream(student);
//
//        System.out.println("student1: "+student);
//        System.out.println("student2: "+student2);
//        System.out.println("student3: "+student3);


        //当前时间
//        Date date = DateUtil.date();
        //当前时间
//        Date date2 = DateUtil.date(Calendar.getInstance());
        //当前时间
//        Date date3 = DateUtil.date(System.currentTimeMillis());
        //当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
//        String now = DateUtil.now();
        //当前日期字符串，格式：yyyy-MM-dd
//        String today= DateUtil.today();

//        System.out.println(date);
//        System.out.println(date2);
//        System.out.println(date3);
//        System.out.println(now);
//        System.out.println(today);

        //获得年的部分
//        System.out.println(DateUtil.year(date));
        //获得月份，从0开始计数
//        System.out.println(DateUtil.month(date));
        //获得月份枚举
//        System.out.println(DateUtil.monthEnum(date));
//
//        String s = "{}aaa{}";
//        System.out.println(StrUtil.format(s,"b","c"));
//
//        System.out.println("Thomas Wang的算法，整数hash: "+HashUtil.intHash(684721343));
//        System.out.println("Thomas Wang的算法，整数hash: "+HashUtil.intHash(123545));
//
//        int[] rainbow = PageUtil.rainbow(5, 20, 7);
//
//        for (int e:rainbow) System.out.print(e+" ");


        Thread thread = new Thread(()-> System.out.println("aaa"));
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<5;i++) {
                    System.out.println("bbb");
                }
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<5;i++) {
                    System.out.println("ccc");
                }
            }
        });
//        thread2.setPriority(10);
//        thread.start();
        thread2.start();
        thread3.start();



    }


    public static void main(String[] args) {
        MyThread thread = new MyThread();
        Thread thread1 = new Thread(thread);
        Thread thread2 = new Thread(thread);
        Thread thread3 = new Thread(thread);
        thread1.start();
        thread2.start();
        thread3.start();
    }

}

class MyThread implements Runnable{
    private int ticket = 5 ;    // 假设一共有5张票
    public void run(){
        for(int i=0;i<100;i++){
            synchronized(this){ // 要对当前对象进行同步
                if(ticket>0){   // 还有票
                    try{
                        Thread.sleep(300) ; // 加入延迟
                    }catch(InterruptedException e){
                        e.printStackTrace() ;
                    }
                    System.out.println("卖票：ticket = " + ticket-- );
                }
            }
        }
    }
};
