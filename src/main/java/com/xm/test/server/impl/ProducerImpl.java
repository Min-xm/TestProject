package com.xm.test.server.impl;

import com.xm.test.entity.Student;
import com.xm.test.server.Producer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Transactional
@Service("producer")
public class ProducerImpl implements Producer {
    @Resource
    RabbitTemplate rabbitTemplate;

    @Override
    public void sendMail(String queue, Student student) {
        rabbitTemplate.convertAndSend(queue,student);

    }

    @Override
    public void getMail(String queue) {
        Object o = rabbitTemplate.receiveAndConvert(queue);
        System.out.println(o.getClass());
        System.out.println(o);
    }
}
