package com.xm.test;



import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.*;
import com.xm.test.entity.Student;
import com.xm.test.server.Producer;
import com.xm.test.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


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

    @Resource
    private Producer producer;

    @Test
    void contextLoads() {

        RedisUtil redisUtil = new RedisUtil(template);
        Student student = new Student();
        student.setName("123");
        student.setSex("男");
        template.opsForValue().set("student",student);
        redisUtil.hPut("hash","student",student);

        Type type = Type.SUCCESS;
        System.out.println(type.value);

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


//        Thread thread = new Thread(()-> System.out.println("aaa"));
//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i=0;i<5;i++) {
//                    System.out.println("bbb");
//                }
//            }
//        });
//        Thread thread3 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i=0;i<5;i++) {
//                    System.out.println("ccc");
//                }
//            }
//        });
//        thread2.setPriority(10);
//        thread.start();
//        thread2.start();
//        thread3.start();



    }


//    public static void main(String[] args) {
//        MyThread thread = new MyThread();
//        Thread thread1 = new Thread(thread);
//        Thread thread2 = new Thread(thread);
//        Thread thread3 = new Thread(thread);
//        thread1.start();
//        thread2.start();
//        thread3.start();
//    }

//}

//class MyThread implements Runnable{
//    private int ticket = 5 ;    // 假设一共有5张票
//    public void run(){
//        for(int i=0;i<100;i++){
//            synchronized(this){ // 要对当前对象进行同步
//                if(ticket>0){   // 还有票
//                    try{
//                        Thread.sleep(300) ; // 加入延迟
//                    }catch(InterruptedException e){
//                        e.printStackTrace() ;
//                    }
//                    System.out.println("卖票：ticket = " + ticket-- );
//                }
//            }
//        }
//    }
};

/**
 * 实验结果:在run方法如果不加上synchronized，会产生线程抢占，
 * 如果加上就确保了线程运行的原子性，先执行的执行，后执行的等待。
 */
class Te1 implements Runnable {

    private int count = 100;

    @Override
    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void main(String[] args) {
        Te1 t = new Te1();
        for (int i = 0; i < 100; i++){
            new Thread(t, "Thread" + i).start();
        }
    }
}


/**
 * 实验结果:此案例说明加synchronized修饰后的线程对象，
 * 和不加synchronized修饰，两者同时运行并不冲突。
 */
class Te2 {
    public synchronized void m1(){
        System.out.println(Thread.currentThread().getName() + " m1 start...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m1 end...");
    }

    public void m2(){
        System.out.println(Thread.currentThread().getName() + " m2 start...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m2 end...");
    }

    public static void main(String[] args) {
        Te2 t = new Te2();
        //语法有点叼
//        new Thread(()->t.m1(),"t1").start();
//        new Thread(()->t.m2(),"t2").start();

        //和上面结果一样
        new Thread(t::m1, "t1").start();
        new Thread(t::m2, "t2").start();
    }
}


/**
 * 脏读
 * 实验结果:实验结果为0.0。
 * 解决方法:确保读写都加上synchronized，保证原子性。
 */
class Account {
    String name;
    double balance;

    public synchronized void set(String name, double balance){
        this.name = name;
        //这里加上间隔时间的意义在于模拟线程被其他线程进行调用
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public synchronized double getBalance(String name){
        return this.balance;
    }

    public static void main(String[] args) {
        Account account = new Account();
        new Thread(()->account.set("xipiker", 21.0)).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(account.getBalance("xipiker"));

    }
}

/**
 * 一个同步方法可以调用另一个同步方法，一个线程已经拥有某个对象的锁，
 * 再次申请的时候仍然会得到该兑现的锁。也就是说synchronized获得的
 * 锁是可重入的。
 */
class Te3 {
    synchronized void m1(){
        System.out.println("m1 start...");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
    }

    synchronized void m2(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2 start...");
    }

    public static void main(String[] args) {
        Te3 t = new Te3();
        new Thread(()->t.m1(), "t1").start();
    }
}


/**
 * 子类调用父类的同步方法
 */
class Te4 {
    synchronized void m(){
        System.out.println("m start");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        new TT().m();
    }
}

class TT extends Te4{
    synchronized void m(){
        System.out.println("child m start");
        super.m();
        System.out.println("child m end");
    }
}


/**
 * 程序在执行过程中，如果出现异常，默认情况下锁会被释放。
 * 所以，在并发处理过程中，有异常要多加小心，不然可能会发生不一致情况。
 * 比如，在一个web app处理过程中，多个servlet线程共同访问同一个资源，
 * 这时候如果异常处理不合适，在第一个线程中抛出异常，其他线程就会进入同步代码区，
 * 有可能会访问到异常产生时的数据。因此要非常小心的处理同步业务逻辑中的异常。
 * 实验结果:锁被释放了，原子性不存在了，第二个线程接着第一个线程继续执行。
 */
class Te5 {
    int count = 0;
    synchronized void m(){
        System.out.println(Thread.currentThread().getName() + " start...");
        while (true){
            count++;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //这里我们模拟异常
            //此处抛出异常，锁将释放，要想不被释放，可以在这里进行catch，然后让循环继续
            if (count == 5){
                int i = 1/0;
            }
        }
    }

    public static void main(String[] args) {
        Te5 t = new Te5();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                t.m();
            }
        };

        new Thread(r, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(r, "t2").start();
    }
}



/**
 * volatile关键字，使一个变量在多个线程间可见。
 * 例子: A B线程都用到一个变量，java默认是A线程中保留一份copy，这样如果B线程修改了该变量，则A线程未必知道
 * 使用volatile关键字，会让所有线程都会读到变量的赋值。
 *
 * 在下面代码中running是存在于堆内存的t对象中，当线程t1开始运行的时候，会把running值从内存中读到t1线程的工作区，
 * 在运行过程中直接使用这个copy，并不会每次都去读取堆内存，这样，当主线程修改running的值之后，t1线程感知不到，所以不会停止运行。
 * 使用volatile，将会强制所有线程都去堆内存中读取running的值。
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，也就是说volatile不能替代synchronized。
 */
class Te6 {
    volatile boolean running = true;
    void m(){
        System.out.println("m start...");
        while (running){

        }
        System.out.println("m end!");
    }

    public static void main(String[] args) {
        Te6 t = new Te6();
        new Thread(t::m, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t.running = false;
    }
}


/**
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，也就是说volatile不能代替synchronized。
 * 实验结果:多个线程间比如A线程执行中的count为100，B线程计算的A的count在加一为101，然后轮到A执行时他的count值时应该count结果为102，
 * 但是执行了为101，所以原本count的102值又被清回101。
 */
class Te7 {
    volatile int count = 5;
    void m(){
        for(int i = 0; i < 1000; i++)
            count++;
    }

    public static void main(String[] args) {
        Te7 t = new Te7();
        List<Thread> threads = new ArrayList<Thread>();

        for (int i = 0; i < 10; i++){
            threads.add(new Thread(t::m, "Thread-" + i));
        }

        threads.forEach((o)->o.start());

        threads.forEach((o)->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(t.count);
    }
}




