# Introduction #

Because of the nascent nature of flibble-voip, the following are required to run the unit tests:
A SIP proxy server.
Two user agent clients (hard or soft phones)


# Details #

The location of the proxy, and the SIP URIs of the user-agents can be specified in the flibble-test.properties file.
```
                proxyAddress=your-proxy-host-name-or-ip

                proxyPort=5060

                uriA=sip:transferee@192.168.0.201

                uriB=sip:target@192.168.0.202

                localIp=192.168.0.42

```

  * Create a properties file like the one above (replace all of the ip addresses with the correct ones for your environment).
  * Before running unit tests, copy this file to your "user.home" directory.
```
    ( in Windows, C:\Documents and Settings\YourUsername\, and in Linux /home/YourUsername/)
```
