/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.aor.edcodisrt.endpoint;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import pt.uc.aor.edcodisrt.jsfbean.MessageProducerBean;

/**
 *
 * @author Elsa
 */
@Stateless
@ServerEndpoint(value = "/whiteboardendpoint", encoders = {FigureEncoder.class}, decoders = {FigureDecoder.class})
public class MyWhiteboard {

    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    private static ByteBuffer dataActive = ByteBuffer.allocate(1000000);

    private static int online;
    private static int aborts;

    @Inject
    private MessageProducerBean messageProducerBean;

    @OnMessage
    public void sendNumbers(String numbers, Session session) throws IOException {

        switch (numbers) {
            case "checked":
                online--;
                aborts++;

                break;
            case "notChecked":
                online++;
                aborts--;

                break;
            case "goChecked":
                aborts--;
                break;
            case "goNotChecked":
                online--;
                break;
        }
        if (aborts < 0) {
            aborts = 0;
        }
        if (online < 0) {
            online = 0;
        }

        for (Session p : peers) {
            p.getBasicRemote().sendText("{\"edit\":" + online + ",\"abort\":" + aborts + "}");
        }
        messageProducerBean.sendText(online + " " + aborts);

    }

    @OnMessage
    public void broadcastSnapshot(ByteBuffer data, Session session) throws IOException {

        System.out.println("broadcastBinary: " + data);
        for (Session peer : peers) {
            if (!peer.equals(session)) {
                peer.getBasicRemote().sendBinary(data);
            }
        }
        dataActive = data;
        messageProducerBean.sendMessage(data);
    }

    public void onJMSMessage(byte[] msg) {
        dataActive = ByteBuffer.wrap(msg);
        for (Session s : peers) {
            try {
                s.getBasicRemote().sendBinary(dataActive);
            } catch (IOException ex) {
                Logger.getLogger(MyWhiteboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void onJMSMessageText(String msg) {
        StringTokenizer div = new StringTokenizer(msg);
        String[] numbers = new String[2];

        for (int i = 0; i < 2; i++) {
            numbers[i] = div.nextToken();
        }

        online = Integer.parseInt(numbers[0]);
        aborts = Integer.parseInt(numbers[1]);

        for (Session s : peers) {
            try {
                s.getBasicRemote().sendText("{\"edit\":" + online + ",\"abort\":" + aborts + "}");
            } catch (IOException ex) {
                Logger.getLogger(MyWhiteboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @OnOpen
    public void onOpen(Session peer) throws IOException {

        if (peer.getUserPrincipal().getName() != null) {
            online++;
        }
        peers.add(peer);
        peer.getBasicRemote().sendBinary(dataActive);

        for (Session p : peers) {
            p.getBasicRemote().sendText("{\"edit\":" + online + ",\"abort\":" + aborts + "}");
        }
        messageProducerBean.sendText(online + " " + aborts);
    }

    @OnClose
    public void onClose(Session peer) {
        peers.remove(peer);
    }

    public static Set<Session> getPeers() {
        return peers;
    }

    public static void setPeers(Set<Session> peers) {
        MyWhiteboard.peers = peers;
    }

    public static ByteBuffer getDataActive() {
        return dataActive;
    }

    public static void setDataActive(ByteBuffer dataActive) {
        MyWhiteboard.dataActive = dataActive;
    }

    public MessageProducerBean getMessageProducerBean() {
        return messageProducerBean;
    }

    public void setMessageProducerBean(MessageProducerBean messageProducerBean) {
        this.messageProducerBean = messageProducerBean;
    }

}
