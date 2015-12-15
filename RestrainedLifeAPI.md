# Most of the available Restrained Life API, which most SL clients (other than LL's) support #

<br /><br />Render an object detachable/nondetachable <br /> detach, type, yes-no
<br /><br />Unlock/Lock an attachment point <br /> detach, attach-point, type, yes-no
<br /><br />Unlock/Lock an attachment point empty <br /> addattach, attach-point, type, yes-no
<br /><br />Unlock/Lock an attachment point full <br /> remattach, attach-point, type, yes-no
<br /><br />Allow/deny the Wear contextual menu <br /> defaultwear, type, yes-no
<br /><br />Start/stop notifications on a private channel <br /> notify channel word , type add/rem
<br /><br />Allow/prevent sending chat messages <br /> sendchat, type, yes-no
<br /><br />Remove/add an exception to the emote truncation above <br /> emote, type remove-add
<br /><br />Allow/prevent shouting <br /> chatshout, type, yes-no
<br /><br />Allow/prevent chatting at normal volume <br /> chatnormal, type, yes-no
<br /><br />Allow/prevent whispering <br /> chatwhisper, type, yes-no
<br /><br />Redirect public chat to private channels <br /> redirchat channel, type remove-add
<br /><br />Redirect public emotes to private channels <br /> rediremote channel, type remove-add
<br /><br />Allow/prevent sending instant messages <br /> sendim, type, yes-no
<br /><br />Allow/prevent sending instant messages, secure way <br /> sendim\_sec, type, yes-no
<br /><br />Remove/add exceptions to the instant message sending prevention <br /> sendim, player-key, type remove-add
<br /><br />Allow/prevent receiving chat messages <br /> recvchat, type, yes-no
<br /><br />Allow/prevent receiving chat messages, secure way <br /> recvchat\_sec, type, yes-no
<br /><br />Remove/add exceptions to the chat message receiving prevention <br /> recvchat, player-key, type remove-add
<br /><br />Allow/prevent seeing emotes <br /> recvemote, type, yes-no
<br /><br />Allow/prevent seeing emotes, secure way <br /> recvemote\_sec, type, yes-no
<br /><br />Remove/add exceptions to the emote seeing prevention <br /> recvemote, player-key, type remove-add
<br /><br />Allow/prevent receiving instant messages <br /> recvim, type, yes-no
<br /><br />Allow/prevent receiving instant messages, secure way <br /> recvim\_sec, type, yes-no
<br /><br />Remove/add exceptions to the chat message receiving prevention <br /> recvim, player-key, type remove-add
<br /><br />Allow/prevent teleporting to a landmark <br /> tplm, type, yes-no
<br /><br />Allow/prevent teleporting to a location <br /> tploc, type, yes-no
<br /><br />Allow/prevent teleporting by a friend <br /> tplure, type, yes-no
<br /><br />Allow/prevent teleporting by a friend, secure way <br /> tplure\_sec, type, yes-no
<br /><br />Remove/add exceptions to the friend teleport prevention <br /> tplure, player-key, type remove-add
<br /><br />Unlimit/limit sit-tp sittp, type, yes-no
<br /><br />Allow/deny permissive exceptions <br /> permissive, type, yes-no
<br /><br />Clear all the rules tied to an object <br /> clear
<br /><br />Clear a subset of the rules tied to an object <br /> clear, type str
<br /><br />Allow/prevent editing objects <br /> edit, type, yes-no
<br /><br />Allow/prevent rezzing inventory <br /> rez, type, yes-no
<br /><br />Allow/prevent wearing clothes <br /> addoutfit-part, type, yes-no
<br /><br />Allow/prevent removing clothes <br /> remoutfit-part , type, yes-no
<br /><br />Force removing clothes <br /> remoutfit-part-, type force  (teens can't be forced to remove underpants and undershirt)
<br /><br />Force removing attachments <br /> detach-attachpt-, type force
<br /><br />Force removing attachments (alias) <br /> remattach-attachpt-, type force
<br /><br />Get the list of worn clothes <br /> getoutfit-part, type channel gloves,jacket,pants,shirt,shoes,skirt,socks,underpants,undershirt,skin,eyes,hair,shape
<br /><br />Get the list of worn attachments <br /> getattach-attachpt, type channel
<br /><br />Force the viewer to automatically accept attach and take control permission requests <br /> acceptpermission, type remove-add
<br /><br />Allow/prevent accepting attach and take control permissions <br /> denypermission, type remove-add
<br /><br />Allow/prevent using inventory <br /> showinv, type, yes-no
<br /><br />Allow/prevent reading notecards <br /> viewnote, type, yes-no
<br /><br />Allow/prevent opening scripts <br /> viewscript, type, yes-no
<br /><br />Allow/prevent opening textures <br /> viewtexture, type, yes-no
<br /><br />Allow/prevent standing up <br /> unsit, type, yes-no
<br /><br />Force sit on an object <br /> sit, player-key, type force
<br /><br />Force unsit <br /> unsit, type force
<br /><br />Allow/prevent sitting down <br /> sit, type, yes-no
<br /><br />Allow/prevent using any chat channel but certain channels <br /> sendchannel-channel, type, yes-no
<br /><br />Allow/prevent using any chat channel but certain channels, secure way sendchannel\_sec, channel, type, yes-no
<br /><br />Get the list of restrictions the avatar is currently submitted to <br /> getstatus[rulePart](rulePart.md), type channel
<br /><br />Get the list of all the restrictions the avatar is currently submitted to <br /> getstatusall[rulePart](rulePart.md), type channel
<br /><br />Get the list of shared folders in the avatar's inventory <br /> getinv folderNames, type channel
<br /><br />Get the list of shared folders in the avatar's inventory, with information about worn items <br /> getinvworn folder names, type channel
<br /><br />Get the path to a shared folder by giving a search criterion <br /> findfolder parts, type channel
<br /><br />Force attach items contained inside a shared folder <br /> attach folderNames, type force
<br /><br />Force attach items contained inside a shared folder, and its children recursively <br /> attachall folderNames type force
<br /><br />Force detach items contained inside a shared folder <br /> detach<br />folderName, type force
<br /><br />Force detach items contained inside a shared folder, and its children recursively <br /> detachall folderNames, type force
<br /><br />Get the path to the shared folder containing a particular object/clothing <br /> getpath-attachpt or  clothing-layer , type channel
<br /><br />Force attach items contained into a shared folder that contains a particular object/clothing <br /> attachthis- attachpt or clothing\_layer, type force
<br /><br />Force attach items contained into a shared folder that contains a particular object/clothing, and its children folders <br /> attachallthis attachpt  or  clothing\_layer, type force
<br /><br />Force detach items contained into a shared folder that contains a particular object/clothing <br /> detachthis-attachpt or clothing\_layer, type force
<br /><br />Force detach items contained into a shared folder that contains a particular object/clothing, and its children folders <br /> detachallthis attachpt  or  clothing\_layer, type force
<br /><br />Force detach an item detachme, type force
<br /><br />Allow/prevent touching objects located further than 1.5 meters away from the avatar <br /> fartouch, type, yes-no
<br /><br />Allow/prevent viewing the world map <br /> showworldmap, type, yes-no
<br /><br />Allow/prevent viewing the mini map <br /> showminimap, type, yes-no
<br /><br />Allow/prevent knowing the current location <br /> showloc, type, yes-no
<br /><br />Force-Teleport the user <br /> tpto X-Y-Z, type force
<br /><br />Remove/add auto-accept teleport offers from a particular avatar <br /> accepttp-player-key, type remove-add
<br /><br />Allow/prevent seeing the names of the people around <br /> shownames, type, yes-no
<br /><br />Allow/prevent seeing all the hovertexts <br /> showhovertextall, type, yes-no
<br /><br />Allow/prevent seeing one hovertext in particular <br /> showhovertext, player-key, type, yes-no
<br /><br />Allow/prevent seeing the hovertexts on the HUD of the user <br /> showhovertexthud, type, yes-no
<br /><br />Allow/prevent seeing the hovertexts in-world <br /> showhovertextworld, type, yes-no
<br /><br />Allow/prevent flying <br /> fly, type, yes-no
<br /><br />Get the UUID of the object the avatar is sitting on <br /> getsitid, type channel
<br /><br />Force rotate the avatar to a set direction <br /> setrot angle\_in\_radians , type force
<br /><br />Allow/prevent changing some debug settings <br /> setdebug, type, yes-no
<br /><br />Get gender of the avatar at creation.
<br /><br />creates blurriness of the screen. Combined to clever setenv commands, can simulate nice effects. (Note: renderresolutiondivisor is a Windlight only option )
<br /><br />Force change a debug setting <br /> setdebug\_setting = value, type force
<br /><br />Get the value of a debug setting <br /> getdebug setting, type channel
<br /><br />Allow/prevent changing the environment settings <br /> setenv, type, yes-no
<br /><br />
<br /><br />Force change an environment setting , type force : time of day or Windlight) and set it to the new value
<br /><br /><br /><br />
| ambientr 		| 0.0-1.0 |	Ambient light, Red channel |
|:-----------|:--------|:---------------------------|
| ambientg 		| 0.0-1.0 |	Ambient light, Green channel |
| ambientb 		| 0.0-1.0 |	Ambient light, Blue channel |
| ambienti 		| 0.0-1.0 |	Ambient light, Intensity   |
| bluedensityr 		| 0.0-1.0 |	Blue Density, Red channel  |
| bluedensityg 		| 0.0-1.0 |	Blue Density, Green channel |
| bluedensityb 		| 0.0-1.0 |	Blue Density, Blue channel |
| bluedensityi 		| 0.0-1.0 |	Blue Density, Intensity    |
| bluehorizonr 		| 0.0-1.0 |	Blue Horizon, Red channel  |
| bluehorizong 		| 0.0-1.0 |	Blue Horizon, Green channel |
| bluehorizonb 		| 0.0-1.0 |	Blue Horizon, Blue channel |
| bluehorizoni 		| 0.0-1.0 |	Blue Horizon, Intensity    |
| cloudcolorr 		| 0.0-1.0 |	Cloud color, Red channel   |
| cloudcolorg 		| 0.0-1.0 |	Cloud color, Green channel |
| cloudcolorb 		| 0.0-1.0 |	Cloud color, Blue channel  |
| cloudcolori 		| 0.0-1.0 |	Cloud color, Intensity     |
| cloudcoverage 		| 0.0-1.0 |	Cloud coverage             |
| cloudx 			 | 0.0-1.0 |	Cloud offset X             |
| cloudy 			 | 0.0-1.0 |	Cloud offset Y             |
| cloudd 			 | 0.0-1.0 |	Cloud density              |
| clouddetailx 		| 0.0-1.0 |	Cloud detail X             |
| clouddetaily 		| 0.0-1.0 |	Cloud detail Y             |
| clouddetaild 		| 0.0-1.0 |	Cloud detail density       |
| cloudscale 		| 0.0-1.0 |	Cloud scale                |
| cloudscrollx 		| 0.0-1.0 |	Cloud scroll X             |
|  cloudscrolly 		| 0.0-1.0 |	Cloud scroll Y             |
| densitymultiplier 	| 0.0-0.9 | 	Density multiplier of the fog |
| distancemultiplier 	| 0.0-10 0.0|	Distance multiplier of the fog |
| eastangle 		| 0.0-1.0 |	Position of the east,  0.0 is normal |
| hazedensity 		| 0.0-1.0 |	Density of the haze        |
| hazehorizon 		| 0.0-1.0 |	Haze at the horizon        |
| maxaltitude 		| 0.0-400 0.0|	Maximum altitude of the fog |
| scenegamma 		| 0.0-1 0.0|	Overall gamma, 1.0 is normal |
| starbrightness		| 0.0-2.0 |	Brightness of the stars    |
| sunglowfocus 		| 0.0-0.5 | 	Focus of the glow of the sun |
| sunglowsize 		|1.0-2.0  |	Size of the glow of the sun |
| sunmooncolorr 		| 0.0-1.0 |	Sun and moon, Red channel  |
| sunmooncolorg 		| 0.0-1.0 |	Sun and moon, Green channel |
| sunmooncolorb 		| 0.0-1.0 |	Sun and moon, Blue channel |
|  sunmooncolori 		| 0.0-1.0 |	Sun and moon, Intensity    |
| sunmoonposition 	| 0.0-1.0 |	Position of the sun/moon, different from daytime, use this to set the apparent sunlight after loading a Preset |
<br /><br />
Note<br /> from the above settings, only the daytime one is supported by v1.19.0||(or older) viewers implementing RestrainedLife v1.14 and later. The other settings are ignored. This is because these viewers do not implement the Windlight renderer.
<br /><br />
Get the value of an environment setting <br /> getenv setting, type channel
<br /><br />