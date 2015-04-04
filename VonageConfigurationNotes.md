# Introduction #

It is very easy to configure a flibble based client for use with a Vonage softphone account.




# Details #

A registered line can be created like so, if your phone number is 781-555-1212:

```
String lineHandle = callMgr.addLine("sip:17815551212@sphone.vopr.vonage.net",
                "Your Name",
                true,
                20,
                "YourPassword" );
```

Surprisingly, STUN is _not_ needed.

Vonage does need clients configured with a very short register period though.  I suggest a value of 20 seconds for the register period.