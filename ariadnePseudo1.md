<b>Ariadne XML service:  ariadne.aspx</b><br />
On page load:<br />
Grab the QueryString parameters<br />
if (Request.QueryString[sessID](sessID.md) is not null )<br />
<br />
sessID = Request.QueryString[sessID](sessID.md);<br />
if (Request.QueryString[mnodeID](mnodeID.md) != null)<br />
mnodeID = (string)Request.QueryString[mnodeID](mnodeID.md);<br />
if (Request.QueryString[mode](mode.md) != null)<br />
mnodeID = Request.QueryString[mode](mode.md);<br />
if MODE=SLBuild (subtypes: build, play, stats?)<br />
is builder HUD<br />
insert new asset into assetTypeAttrib table<br />
<br />
return (brief) success xml msg<br />
> else<br />
> is MODE=SLPlay<br />
> > start/keep session for this player<br />

> build basic XML(no need to get assettypes, assetTypeAtrrib xml)<br />
> xml = get OL node xml <br />
> > xml = xml + get session id, mappedAssets, links xml for this mnode<br />

> return basic xml to SL<br />
> > else<br />
> > > is MODE=SLStats  (googlecharts asking for stats xml) what do we do<br />

> else <br />
> is MODE=admin<br />
> if isPost, update-insert-delete<br />
> is admin session, do we disable OL<br />
> do we care about session tracking<br />
> build extended XML<br />
> xml = get OL node xml <br />

xml = xml + get mappedAssets,<br />
> assettypes, assetTypeAtrrib xml<br />
> return resulting xmlhttprequest<br />
> obj (from jQuery get() method in ariadneAdmin.html)<br />
> is MODE=RPC<br />
> insert new holodeck list<br />
> or<br />
> send pause to controller (HSVO)<br />
> or<br />
> send generic msg to named in-world object (key needed?)<br />
> return (brief) xml success<br />
> msg<br />
> else<br />
> refuse access, or do we have other clients?<br />
> <br />
> basic data schema<br />
> <br />
> <br />
> > Tables:<br />
> > <br />
CREATE TABLE AssetType<br />
> > AssetTypeID <br />
> > AssetTypeName  <br />
> > AssetTypeDesc <br />
> > UIHelpMediaPath<br />
> > UIHelpText<br />
> > UICategory<br />
> > isParcelMedia<br />
> > canLoop<br />
> > hasDuration<br />
> > isInventoryObject<br />
> > isObject<br />
> > isInventoryMedia<br />
> > isAction<br />
> > isText<br />
> > isHidden<br />
> > isSystem<br />
> > isMutuallyExclusive<br />
> > isAdmin<br />
> > <br />
> > <br />

> CREATE TABLE AssetMapNode<br />
> > AssetMapNodeID<br />
> > MNodeID<br />
> > AssetTypeID<br />
> > NodeAssetValue<br />
> > NodeAssetTarget<br />
> > NodeAssetName<br />
> > AssetTypeAttribPairs<br />
> > <br />
> > <br />
> > this now deprecated- now stored as name/value pair in asset of type AriadneAdmin,
> > with isAdmin, isHidden, and isSystem flagged
<br />
<br />
CREATE TABLE AssetTypeAttribs<br />
AssetTypeAttribID<br />
AssetTypeID<br />
AssetTypeAttribName<br />
AssetTypeAttribValue
<br />