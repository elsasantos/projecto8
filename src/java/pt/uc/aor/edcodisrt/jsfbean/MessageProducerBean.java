/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.aor.edcodisrt.jsfbean;

import java.nio.ByteBuffer;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Topic;

/**
 *
 * @author Guilherme Pereira
 */
@Named(value = "messageProducerBean")
@RequestScoped
public class MessageProducerBean {

    @Resource(lookup = "jms/myTopic")
    private Topic myTopic;
    @Inject
    @JMSConnectionFactory("jms/myTopicFactory")
    private JMSContext context;


    public MessageProducerBean() {
    }


    public void sendMessage(ByteBuffer message) {
        System.out.println("entrou");
        byte[] b = new byte[message.capacity()];
        message.get(b, 0, b.length);
        context.createProducer().send(myTopic, b);
        System.out.println("sending message " + message.toString());
    }

}


