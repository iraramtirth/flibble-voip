**High-level Java API for easily creating SIP enabled VoIP applications.  Suitable for either a desktop (softphone, phone applet, incoming call screener) or server-side (auto attendant, ACD, voicemail) application.  Defines a generic media provider interface, and includes an implementation of that interface which uses the Java Media Framework.
Flibble-VoIP is the underlying engine of the [VoxCenter softphone](http://sipresponse.com/)**

NEWS 6/11/2008 - Flibble-VoIP Beta 2 has been released.
In addition to the addition of the roadmap items below, many fixes were made to
STUN, SIP Signaling, media, DTMF, and authentication.  Tested with Vonage, VoIP.com, Broad Voice, and IdeaSIP.

News - 5/26/2007:  I've modified the way flibble-voip is being packaged.  This should be less confusing now, and all of the source and binary packages are available (on sourceforge).  Fixed an issue with authentication challenges on outgoing calls.  This means the PlaceCall example, with two way audio, is functional with VoIP providers, such as Vonage.

News - 5/5/2007:  Tested with a Vonage softphone account.  Can receive calls, verified two way audio!

Roadmap Phase 0:
  * (completed rev-50) SIP proxy registration (tested with the [SipExchange](http://cafesip.org/) proxy)
  * (completed rev-50) Place a Call
  * (completed rev-50) End a Call
  * (completed rev-50) Blind Transfer

Roadmap BETA 1:
  * (completed rev-81) Media Integration (JMF)
  * (completed rev-81) G711 / PCMU codec
  * (completed rev-81) BYE handling
  * (completed rev-83) Answer Call
  * (completed rev-87) STUN probe for Public IP on init
  * (completed rev-100) Media File Streaming (.WAV / .MP3)
  * (completed rev-100) Allow for Media Source change during a call
  * (completed rev-106) Responding to INVITE authentication challenges
  * (completed rev-118) DTMF receiving
  * (completed rev-122) DTMF Sending

Roadmap BETA 2:
  * (completed rev-148) Cancel Call
  * (completed rev-148) Reject Call
  * (completed rev-148) Hold / Unhold
  * (completed rev-148) Echo Suppression
  * (completed rev-148) SIP Keepalives

Roadmap RELEASE 1.1:
  * Call Recording
  * Voice Recognition
  * Text-To-Speech

Roadmap RELEASE 1.2:
  * Mute local audio playout
  * Mute microphone
  * Transferee Support
  * SIP over TCP
  * GSM Audio Codec
  * Video (JPEG, H263)
  * Consultatvie Transfer
  * Presence
  * Instant Messaging (IM / SIMPLE)