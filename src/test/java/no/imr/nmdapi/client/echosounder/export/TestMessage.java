/*
 */
package no.imr.nmdapi.client.echosounder.export;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author sjurl
 */
public class TestMessage {

    /**
     * This test regenerates all datasets on the server so use with caution as
     * this will take a very long time.
     *
     * @throws JMSException
     */
    @Ignore
    public void test() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://tomcat7-test.imr.no:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("echosounderRefresh");
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        Message message = session.createMessage();
        message.setStringProperty("imr:refresh", "all");
        producer.send(message);
        session.close();
        connection.close();
    }

}
