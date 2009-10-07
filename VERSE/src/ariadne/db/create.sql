CREATE TABLE [AssetType](
	[AssetTypeID] [int] IDENTITY(1,1) NOT NULL,
	[AssetTypeName] [varchar](50) NOT NULL,
	[AssetTypeDesc] [varchar](max) NULL,
	[UIHelpMediaPath] [varchar](max) NULL,
	[UIHelpText] [varchar](max) NULL,
	[UICategory] [varchar](50) NULL,
	[isParcelMedia] [bit] NULL,
	[canLoop] [bit] NULL,
	[hasDuration] [bit] NULL,
	[isInventoryObject] [bit] NULL,
	[isObject] [bit] NULL,
	[isInventoryMedia] [bit] NULL,
	[isAction] [bit] NULL,
	[isText] [bit] NULL,
	[isHidden] [bit] NULL,
	[isSystem] [bit] NULL,
	[isMutuallyExclusive] [bit] NULL,
	[isAdmin] [bit] NULL
GO
CREATE TABLE [AssetMapNode](
	[AssetMapNodeID] [int] IDENTITY(1,1) NOT NULL,
	[MNodeID] [int] NOT NULL,
	[AssetTypeID] [int] NULL,
	[NodeAssetValue] [varchar](max) NULL,
	[NodeAssetTarget] [varchar](max) NULL,
	[NodeAssetName] [varchar](max) NULL,
	[AssetTypeAttribPairs] [varchar](max) NULL
GO

	// deprecated- ? will be stored as name/value pair in asset of type AriadneAdmin,
	//with isAdmin, isHidden, and isSystem flagged
CREATE TABLE [AssetTypeAttribs](
	[AssetTypeAttribID] [int] IDENTITY(1,1) NOT NULL,
	[AssetTypeID] [int] NOT NULL,
	[AssetTypeAttribName] [varchar](50) NULL,
	[AssetTypeAttribValue] [varchar](200) NULL
GO