/*******************************************************************************
 *   Copyright 2007 SIP Response
 *   Copyright 2007 Michael D. Cohen
 *
 *      mike _AT_ sipresponse.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 ******************************************************************************/
package com.sipresponse.flibblecallmgr.plugin.jmf;

import java.io.IOException;

import javax.media.ControllerErrorEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Player;
import javax.media.RealizeCompleteEvent;
import javax.media.control.BufferControl;
import javax.media.rtp.Participant;
import javax.media.rtp.RTPControl;
import javax.media.rtp.RTPManager;
import javax.media.rtp.ReceiveStream;
import javax.media.rtp.ReceiveStreamListener;
import javax.media.rtp.SessionListener;
import javax.media.rtp.event.ByeEvent;
import javax.media.rtp.event.NewParticipantEvent;
import javax.media.rtp.event.NewReceiveStreamEvent;
import javax.media.rtp.event.ReceiveStreamEvent;
import javax.media.rtp.event.RemotePayloadChangeEvent;
import javax.media.rtp.event.SessionEvent;
import javax.media.rtp.event.StreamMappedEvent;
import javax.media.protocol.DataSource;
import com.sipresponse.flibblecallmgr.CallManager;

public class Receiver implements ReceiveStreamListener, SessionListener, 
    ControllerListener
{
    private RTPManager rtpMgr;
    private String callHandle;
    private Object dataSync = new Object();
    private Player p;
    private ReceiveStream stream;
    
    public Receiver(CallManager callMgr, String lineHandle, String callHandle, String address, int port)
    {
        rtpMgr = RTPManager.newInstance();
        rtpMgr.addSessionListener(this);
        rtpMgr.addReceiveStreamListener(this);
        this.callHandle = callHandle;
        // Initialize the RTPManager with the RTPSocketAdapter
        rtpMgr.initialize(new ReceiveAdapter(
                    callMgr,
                    address, 
                    port,
                    lineHandle,
                    callHandle));
        
        BufferControl bc = (BufferControl)rtpMgr.getControl("javax.media.control.BufferControl");
        if (bc != null)
        {
            bc.setBufferLength(20);
        }
    }
    
    public void stop()
    {
        if (stream != null)
        {
            try
            {
                stream.getDataSource().stop();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if (p != null)
        {
            p.stop();
            p.close();
        }
        rtpMgr.dispose();
    }
    /**
     * SessionListener.
     */
    public synchronized void update(SessionEvent evt)
    {
        if (evt instanceof NewParticipantEvent)
        {
        }
    }


    /**
     * ReceiveStreamListener
     */
    public synchronized void update( ReceiveStreamEvent evt)
    {

        RTPManager mgr = (RTPManager)evt.getSource();
        Participant participant = evt.getParticipant(); // could be null.
        stream = evt.getReceiveStream();  // could be null.

        if (evt instanceof RemotePayloadChangeEvent)
        {
            System.err.println("  - Received an RTP PayloadChangeEvent.");
        }
        else if (evt instanceof NewReceiveStreamEvent)
        {
            try
            {
                stream = ((NewReceiveStreamEvent)evt).getReceiveStream();
                DataSource ds = stream.getDataSource();
        
                // Find out the formats.
                RTPControl ctl = (RTPControl)ds.getControl("javax.media.rtp.RTPControl");
        
                // create a player by passing datasource to the Media Manager
                p = javax.media.Manager.createPlayer(ds);
                if (p == null)
                    return;
        
                p.addControllerListener(this);
                p.realize();
        
                // Notify intialize() that a new stream had arrived.
                synchronized (dataSync)
                {
                    dataSync.notifyAll();
                }
        
            }
            catch (Exception e)
            {
                System.err.println("NewReceiveStreamEvent exception " + e.getMessage());
                return;
            }
        }
        else if (evt instanceof StreamMappedEvent)
        {

            if (stream != null && stream.getDataSource() != null)
            {
            }
        }
        else if (evt instanceof ByeEvent)
        {

        }

    }


    /**
     * ControllerListener for the Players.
     */
    public synchronized void controllerUpdate(ControllerEvent ce)
    {

        Player p = (Player) ce.getSourceController();

        if (p == null)
            return;

        // Get this when the internal players are realized.
        if (ce instanceof RealizeCompleteEvent)
        {
            p.start();
        }

        if (ce instanceof ControllerErrorEvent)
        {
            p.removeControllerListener(this);
            System.err.println("AVReceive3 internal error: " + ce);
        }

    }
}
