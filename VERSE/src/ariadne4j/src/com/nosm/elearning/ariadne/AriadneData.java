package com.nosm.elearning.ariadne;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.common.resources.Resources;
import com.nosm.elearning.ariadne.model.Asset;
import com.nosm.elearning.ariadne.model.AssetType;
import com.nosm.elearning.ariadne.model.User;

import java.io.Reader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AriadneData {

	private static SqlMapClient sqlMapper;

	static {
		try {
			Reader reader = Resources.getResourceAsReader("com/nosm/elearning/ariadne/SqlMapConfig.xml");
			sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("<error>Ariadne: Error while building the SqlMapClient instance." + e + "</error>", e);
		}
	}

//	 ------ assets ------
	public static List selectAllAssets () throws SQLException {
		return sqlMapper.queryForList("selectAllAssets");
	}

	public static List selectAssetsByMNodeId  (int id, boolean isAdmin) throws Exception {
		if (isAdmin){
			return sqlMapper.queryForList("selectAssetsAdminByMNodeId", id);
		}else{
			return sqlMapper.queryForList("selectAssetsSLByMNodeId", id);
		}
	}

	public static Asset getAssetById(int id)throws SQLException{
		return (Asset)sqlMapper.queryForObject("getAssetById", id);
	}

	public static void insertAsset (Asset asset) throws SQLException {
		sqlMapper.insert("insertAsset", asset);
	}

	public static void updateAsset (Asset asset) throws SQLException {
		sqlMapper.update("updateAsset", asset);
	}

	public static void deleteAsset (int id) throws SQLException {
		sqlMapper.delete("deleteAssetById", id);
	}

	public static void deleteAsset (String name) throws SQLException {
		sqlMapper.delete("deleteAssetByName", name);
	}

	public static boolean doesAssetExist (int id) throws SQLException {
		return Boolean.parseBoolean(sqlMapper.queryForObject("doesAssetExist").toString());
	}




//	 ------ asset class/attrib/type ------
	public static void setAssetType(String type, String name, String value) throws SQLException{

		sqlMapper.insert("insertAssetAttrib", new AssetType(type,name,value));
	}

	public static AssetType getAssetType(AssetType attribs) throws SQLException{

		return (AssetType)sqlMapper.queryForObject("selectAttribByNameAndType", attribs);
	}

	public static AssetType getAssetTypeByName(String name) throws SQLException{
		return (AssetType)sqlMapper.queryForObject("selectAttribByName", name);
	}

	public static void addAssetType(AssetType AssetType)throws SQLException{
		// TODO: CHECK IF IT'S THERE
		sqlMapper.insert("insertInventoryAttrib", AssetType);
		//sqlMapper.update("updateUser", user);
	}

	public static List getAllAssetTypes() throws SQLException{
		return sqlMapper.queryForList("selectAllAttribs");
	}

	public static AssetType getAssetTypeBySlType(int slInvtype)throws SQLException{
		return (AssetType)sqlMapper.queryForObject("getAssetTypeBySlType", slInvtype);

	}


	// ------ users ------
	public static List getAllUsers()throws SQLException{
		return sqlMapper.queryForList("selectAllUsers");
	}

	public static boolean isUser(String first, String last)throws SQLException{
		return isUser(new User (first, last));

	}

	public static boolean isUser(User user)throws SQLException{
		return  Boolean.parseBoolean((String)sqlMapper.queryForObject("isUser", user ));

	}

	public static boolean isAdmin(User user)throws SQLException{
		return  Boolean.parseBoolean((String)sqlMapper.queryForObject("isAdmin", user ));

	}

	public static boolean isAdmin(String first, String last)throws SQLException{
		return isAdmin(new User (first, last));

	}


	public static void registerUser(User user)throws SQLException{
		if(!AriadneData.isUser(user)){
			// insertAvatarWithKey
			sqlMapper.insert("insertKeylessAvatar", user);
		}else{
			sqlMapper.update("updateUserByKey", user);
		}
	}

	public static void removeUser(User user)throws SQLException{
		sqlMapper.delete("deleteUserByAvatarName", user);
	}


//	 ------ sequence ------
	public static void addNode2Seq(Asset asset) throws Exception{
		Asset thisSeq = AriadneData.getSeqForNode(asset);
		if (thisSeq != null){
			thisSeq.setValue(thisSeq.getValue()+ ","+asset.getName());
			sqlMapper.update("resetSequence", thisSeq);
		}else{
			thisSeq = new Asset("master",
							"SLInnerSequence",
							asset.getName(),
							"",
							Integer.toString(asset.getNodeid()),
							asset.getNodeid()
						);
			sqlMapper.insert("insertAsset", thisSeq);
		}
	}


	public static void removeAssetFromSeq(int assetID) throws Exception{

		Asset thisAsset = AriadneData.getAssetById(assetID);
		Asset thisSeq = AriadneData.getSeqbyNodeID(thisAsset.getNodeid());
		if (thisSeq == null || thisAsset == null){
			throw new Exception("NoAssetOrSeqException: tried with id: " + Integer.toString(assetID)+ ", gets this node ID: " +thisAsset.getNodeid() + ", seq:" + Integer.toString(thisSeq.getId()));
		}

		String assetName = thisAsset.getName();

		StringBuffer newSeqBuff = new StringBuffer();
        StringTokenizer st =  new StringTokenizer(thisSeq.getValue(), ",");
        int cnt = 0;
        String delim = ",";
	    while (st.hasMoreElements()){
	    	cnt++;
	        String token = st.nextElement().toString();
	        if (!token.equals(assetName)){ //will exclude current asset
	        	if (new Integer(cnt).equals(new Integer(st.countTokens()))){
	        		delim = "";
	        	}
	        	newSeqBuff.append(token +delim);
	        }
	    }

	    if (newSeqBuff.toString().length() > 1){
		    // end trim hack:
		    if (newSeqBuff.toString().lastIndexOf(",") == newSeqBuff.toString().length() - 1){
		    	newSeqBuff = new StringBuffer(newSeqBuff.toString().substring(0, newSeqBuff.toString().length() -1));
		    }

		    // front trim hack:
		    if (newSeqBuff.toString().indexOf(",") == 0){
		    	newSeqBuff = new StringBuffer(newSeqBuff.toString().substring(1, newSeqBuff.toString().length()));
		    }
	    }
		thisSeq.setValue(newSeqBuff.toString());
		sqlMapper.update("resetSequence", thisSeq);
	}

	public static Asset getSeqbyNodeID(int mnodeid)throws Exception {
		return (Asset)sqlMapper.queryForObject("getSeqbyNodeID", mnodeid);
	}


	public static Asset getSeqForNode(Asset asset)throws SQLException{
		return (Asset)sqlMapper.queryForObject("getSeqForNode", asset.getNodeid());
	}

	public static void resetSeqForNode(Asset nonSeqAsset, String oldAssetName) throws Exception {
		if (nonSeqAsset.getName().toLowerCase().indexOf("master") == -1){ // if this is not a node's  main sequence  asset
			Asset thisSeq = null;
			try{
				thisSeq = AriadneData.getSeqForNode(nonSeqAsset); // get seq for this asset's node
				if (thisSeq.getValue().indexOf(oldAssetName) > -1){
					// just runs simple find/replace for now
					// resequencing later
					thisSeq.setValue(thisSeq.getValue().replace(oldAssetName, nonSeqAsset.getName()));
				}else{
					//append
					thisSeq.setValue(thisSeq.getValue() + ","+nonSeqAsset.getName());
				}
				sqlMapper.update("resetSequence", thisSeq);
			}catch (NullPointerException npe) {
				if (thisSeq == null) throw new Exception("<error>: no sequence found for node: "+nonSeqAsset.getNodeid() +".</error>");
			}
		}else{
			throw new Exception("<error>Cannot add an SLInnerSequence to this sequence: "+nonSeqAsset.getName()+"</error>");
		}
	}
}
