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
import javax.inject.Named;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Elsa
 */
@Named
@ServerEndpoint(value = "/whiteboardendpoint", encoders = {FigureEncoder.class}, decoders = {FigureDecoder.class})
public class MyWhiteboard {

    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    private ByteBuffer bb = ByteBuffer.allocate(1000000);

    @OnMessage
    public void broadcastFigure(Figure figure, Session session) throws IOException, EncodeException {
        System.out.println("broadcastFigure: " + figure);
        for (Session peer : peers) {
            if (!peer.equals(session)) {
                peer.getBasicRemote().sendObject(figure);//msg enviada ao servidor
            }
        }
    }

    @OnMessage
    public void broadcastSnapshot(ByteBuffer data, Session session) throws IOException {
        bb = data;
        System.out.println("broadcastBinary: " + data);
        for (Session peer : peers) {
            if (!peer.equals(session)) {
                peer.getBasicRemote().sendBinary(data);//msg enviada ao servidor
            }
        }
    }

    @OnOpen
    public void onOpen(Session peer) {
        try {
            peers.add(peer);//qdo se acede ao endpoint
            peer.getBasicRemote().sendBinary(bb);
        } catch (IOException ex) {
            Logger.getLogger(MyWhiteboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @OnClose
    public void onClose(Session peer) {
        peers.remove(peer);
    }

    public String nPeer() {
        String p = "" + peers.size();
        System.out.println("peers: " + p);
        return p;

    }

}
