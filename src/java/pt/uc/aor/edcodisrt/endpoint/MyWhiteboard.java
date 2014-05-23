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
import pt.uc.aor.edcodisrt.jsfbean.UserBean;

/**
 *
 * @author Elsa
 */
@Stateless
@ServerEndpoint(value = "/whiteboardendpoint", encoders = {FigureEncoder.class}, decoders = {FigureDecoder.class})
public class MyWhiteboard {

    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    private static ByteBuffer dataActive = ByteBuffer.allocate(1000000);
    private byte[] dataToSave = dataActive.array();
    @Inject
    private UserBean userBean;

    @Inject
    MessageProducerBean messageProducerBean;

    @OnMessage
    public void broadcastFigure(Figure figure, Session session) throws IOException, EncodeException {

        System.out.println("broadcastFigure: " + figure);
        for (Session peer : peers) {
            if (!peer.equals(session)) {
                peer.getBasicRemote().sendObject(figure);
            }
        }
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

    @OnOpen
    public void onOpen(Session peer) throws IOException {
        peers.add(peer);
        peer.getBasicRemote().sendBinary(dataActive);

        for (Session p : peers) {
            p.getBasicRemote().sendText(String.valueOf(peers.size()));
        }
    }

    @OnClose
    public void onClose(Session peer) {
        peers.remove(peer);
    }

    public void onCreateSnapshot() {
       // userBean.saveWhiteboard();
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

    public byte[] getDataToSave() {
        return dataToSave;
    }

    public void setDataToSave(byte[] dataToSave) {
        this.dataToSave = dataToSave;
    }

    public MessageProducerBean getMessageProducerBean() {
        return messageProducerBean;
    }

    public void setMessageProducerBean(MessageProducerBean messageProducerBean) {
        this.messageProducerBean = messageProducerBean;
    }

}
