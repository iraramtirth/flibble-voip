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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.PushSourceStream;
import javax.media.protocol.SourceTransferHandler;
import javax.media.rtp.OutputDataStream;
import javax.media.rtp.RTPConnector;

import com.sipresponse.flibblecallmgr.CallManager;
import com.sipresponse.flibblecallmgr.internal.InternalCallManager;
import com.sipresponse.flibblecallmgr.internal.media.MediaSocketManager;
import com.sipresponse.flibblecallmgr.plugin.jmf.ReceiveAdapter.SocketOutputStream;

public class SendAdapter implements RTPConnector
{
    private MediaSocketManager socketMgr;
    private String address;
    private int port;
    private SocketOutputStream rtpOutputStream;
    private SocketOutputStream rtcpOutputStream;
    private DatagramSocket rtpSocket;
    private DatagramSocket rtcpSocket;
    
    public SendAdapter(CallManager callMgr,
            String address,
            int port)
    {
        this.address = address;
        this.port = port;
        socketMgr = InternalCallManager.getInstance().getMediaSocketManager(callMgr);
    }
    
    public void close()
    {
    }

    public PushSourceStream getControlInputStream() throws IOException
    {
        return new DummyInputStream();
    }

    public OutputDataStream getControlOutputStream() throws IOException
    {
        if (rtcpOutputStream == null)
        {
            if (null == rtcpSocket)
            {
                rtcpSocket = this.socketMgr.getSocket(port+1);
            }
            rtcpOutputStream = new SocketOutputStream(rtcpSocket, InetAddress.getByName(address), port+1);
        }
        return rtcpOutputStream;
    }

    public PushSourceStream getDataInputStream() throws IOException
    {
        return new DummyInputStream();
    }

    public OutputDataStream getDataOutputStream() throws IOException
    {
        if (rtpOutputStream == null)
        {
            if (null == rtpSocket)
            {
                rtpSocket = socketMgr.getSocket(port);
            }
            rtpOutputStream = new SocketOutputStream(rtpSocket, InetAddress.getByName(address), port);
        }
        return rtpOutputStream;
    }

    public double getRTCPBandwidthFraction()
    {
        return 0;
    }

    public double getRTCPSenderBandwidthFraction()
    {
        return 0;
    }

    public int getReceiveBufferSize()
    {
        return 0;
    }

    public int getSendBufferSize()
    {
        return 0;
    }

    public void setReceiveBufferSize(int arg0) throws IOException
    {
    }

    public void setSendBufferSize(int arg0) throws IOException
    {
    }
    
    /**
     * An inner class to implement an OutputDataStream based on UDP sockets.
     */
    class SocketOutputStream implements OutputDataStream
    {

        DatagramSocket sock;

        InetAddress addr;

        int port;

        public SocketOutputStream(DatagramSocket sock, InetAddress addr, int port)
        {
            this.sock = sock;
            this.addr = addr;
            this.port = port;
        }

        public int write(byte data[], int offset, int len)
        {
            try
            {
                sock.send(new DatagramPacket(data, offset, len, addr, port));
                Thread.sleep(15);  //smooth out the inter-arrival jitter
            }
            catch (Exception e)
            {
                return -1;
            }
            return len;
        }
    }
    
    public class DummyInputStream implements PushSourceStream
    {

     public int getMinimumTransferSize()
     {
         return 0;
     }

     public int read(byte[] arg0, int arg1, int arg2) throws IOException
     {
         // TODO Auto-generated method stub
         return -1;
     }

     public void setTransferHandler(SourceTransferHandler arg0)
     {
         
     }

     public boolean endOfStream()
     {
         return true;
     }

     public ContentDescriptor getContentDescriptor()
     {
         return null;
     }

     public long getContentLength()
     {
         return 0;
     }

     public Object getControl(String arg0)
     {
         return null;
     }

     public Object[] getControls()
     {
         return null;
     }

    }    

}