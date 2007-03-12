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
package com.sipresponse.flibblecallmgr.internal.actions;

import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.ResponseEvent;
import javax.sip.SipException;
import javax.sip.message.Request;

import com.sipresponse.flibblecallmgr.CallManager;
import com.sipresponse.flibblecallmgr.internal.Call;
import com.sipresponse.flibblecallmgr.internal.FlibbleSipProvider;
import com.sipresponse.flibblecallmgr.internal.InternalCallManager;

public class ByeAction extends Thread
{
    private int timeout = 4000;
    private CallManager callMgr;
    private Call call;
    
    public ByeAction(CallManager callMgr, Call call)
    {
        this.callMgr = callMgr;
        this.call = call;
    }
    
    public int getTimeout()
    {
        return timeout;
    }
    
    public void setTimeout(int timeout)
    {
        this.timeout = timeout;
    }
    
    public void run()
    {
        FlibbleSipProvider flibbleProvider = InternalCallManager.getInstance()
            .getProvider(callMgr);
        Dialog dialog = call.getDialog();
        Request bye = null;
        try
        {
            bye = dialog.createRequest(Request.BYE);
        }
        catch (SipException e)
        {
            e.printStackTrace();
        }
        if (null != bye)
        {
            ClientTransaction ct = flibbleProvider.sendRequest(bye);
            ResponseEvent responseEvent = flibbleProvider.waitForResponseEvent(ct);
            // response should be 200 ok...
        }
        
    }
}

