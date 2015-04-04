# Introduction #

A Simple Code Snippet For Placing Calls


# Details #

```
        try
        {
            CallMgr callMgr = new CallMgr();
            callMgr.initialize(InetAddress.getLocalHost().getHostAddress(),  // address to bind to
                    5060, // port to bind to 
                    9300, // start media port range
                    9400, // end media port range
                    "192.168.0.105", // proxy address
                    5060, // proxy port
                    null, // stun server
                    true, // use sound card
                    null); // media filename
            callMgr.addListener(this);  // this class implements the FlibbleListener interface
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        
        // create a provisioned line, and place the call:
        // 
        String lineHandle = callMgr.addLine("sip:mike1@192.168.0.105", "Foo Bar", false, 0, null);
        String callHandle = callMgr.createCall(lineHandle, "sip:mike2@192.168.0.105");
        callMgr.placeCall(callHandle);

```

