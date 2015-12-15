System Roles:<br />
a.Builder<br />
b.Actor<br />
c.Patient<br />
d.Audience<br />

Steps to building a OL-enabled SL scene:<br />
<br />
1.Builder adds package to controller (equip, animation, parts, etc)… controller may need to copy these to each badge<br />
<br />
2.Builder adds objects to scene, runs
AVRS\_Scene script on each (will now run 2nd script, one that sends UUID/name over
HTTP to OL catalog capture aspx)
<br />
3.For a givenl abyrinth:
Builder assigns
<br />
Player<br />
> Role:<br />
> > doctor<br />
> > Nurse<br />
> > Midwife<br />
> > Student<br />
> > Paramedic<br />
> > Surgeon<br />
> > Observer<br />
> > Anesthesiologist<br />
> > etc<br />

DelegationMechanism:<br />

> Named (avatarName)<br />
> Prioritylist (sequential list of avatar Names, those present<br />
> in that order, get assigned first)<br />
> Closest to Holodeck Button<br />
> Notecard configured<br />
<br />
Patient<br />
> State:<br />
> > Gown<br />
> > Accident<br />
> > Naked(will still be wearing attach point 'suit')<br />
> > etc<br />
> > <br />
DelegationMechanism:<br />

> Named(avatarName)<br />
> Prioritylist(sequential list of avatarNames, those present<br />
> in that order get assigned first)<br />
> 2nd Closest to Holodeck Button<br />
> Notecard configured<br />
<br />
4.For a given Node:<br />
> Rule definition for assigning &quot; dynamicobjects &quot; <br />
(packages in the controller) to scene objects.<br />
Options (links) for a given node will determine the possible<br />
objects to use.<br />
Assign an anchor/attach point to:<br />
An object:<br />
> Scene object<br />
> Another dynamic object<br />
> Coordinates on the ground<br />
<b>OR</b><br />
An avatar:<br />
Body Parts<br />
LSL non-HUD attachment points<br />
Mannequin suit attach-points<br />
MultiplayerMode:<br />
> PlayType:<br />
> Representative (avatar Name)<br />
> VotingMachine<br />
> GameShowMillionaire (can delegate node, filter options, voting machine)<br />
<br />
XML:<br />
> Session<br />
> Id<br />
> History<br />
> Node<br />
> Name<br />
> ServerTime<br />
> Assets<br />
> Type (SL, Media, Text)<br />
> Value (UUID, URL, String)<br />
> Counters<br />
> Name<br />
> Value<br />

Synching avatars (doctor-patient, doctor-audience, etc)<br />
> Transparent<br />
phantom pose balls, laid down by builder with correct rotation and position, also<br />
with avrs\_scene script dragged to it, when you select that act, llDialog() will<br />
have you sit on this invisible poseball (on the correct channel, some one can<br />
say show and the balls will show themselves)<br />
<br />
We can sequence animations in one poseball, each having a play duration. Since all of <br />these params are in a notecard, these can be dispatched at runtime (itself returned<br />
from llhttprequest()<br />
<br />
Timing, scheduling and syncing activity for a given node-state, animations triggering <br />next animationsin other avatars, bodyparts rezzing before an animation starts, or a <br />soundmust be played before the patient's facial expression turns to dead gaze,etc<br />
<br />
bought:<br />
:m:MultiPoseball
ZHAO-II MB2 BOX<br />
MoHax's<br />
MoPoseBalls<br />
PROBLEM:Cannot force an avatar to sit!!!! Conclusion: poseballs are useless in timed sequnces.<br />
<b>Therefore,must now send position, rotation of desired animation at build time!!</b><br />