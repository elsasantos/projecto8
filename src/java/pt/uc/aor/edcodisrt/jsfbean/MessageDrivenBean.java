/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.uc.aor.edcodisrt.jsfbean;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author Guilherme Pereira
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/myTopic"),
})
public class MessageDrivenBean implements MessageListener {
    
    public MessageDrivenBean() {
    }
    
    @Override
    public void onMessage(Message message) {
                try {
            System.out.println("Recebido no Receiver: " + message.getBody(byte[].class));
        } catch (JMSException ex) {
            Logger.getLogger(MessageDrivenBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
