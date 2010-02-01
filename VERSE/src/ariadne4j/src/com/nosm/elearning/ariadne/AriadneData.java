package com.nosm.elearning.ariadne;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.common.resources.Resources;
import com.nosm.elearning.ariadne.constants.EC;
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
		try { // maybe shouldn't be in static init, in case it fails
			Reader reader = Resources.getResourceAsReader("com/nosm/elearning/ariadne/SqlMapConfig.xml");
			sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("<error>Ariadne: Error while building the SqlMapClient instance." + e + EC.XML_ET, e);
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

	public static int getIdByAsset(Asset asset)throws SQLException{
		return ((Integer)sqlMapper.queryForObject("getIdforAsset", asset)).intValue();
	}

	public static Asset refreshAsset(Asset asset)throws SQLException{ // gets you the id...
		return AriadneData.getAssetById(AriadneData.getIdByAsset( asset));
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
		if(Boolean.parseBoolean((String)sqlMapper.queryForObject("isUser", user ))){
			return true;
		}else{
			return false;
		}
	}

	public static boolean isAdmin(User user)throws SQLException{
		if(Boolean.parseBoolean((String)sqlMapper.queryForObject("isAdmin", user ))){
			return true;
		}else{
			return false;
		}

	}

	public static boolean isAdmin(String first, String last)throws SQLException{
		return isAdmin(new User (first, last));

	}


	public static void registerUser(User user)throws SQLException{
		if(!AriadneData.isUser(user)){
			// insertAvatarWithKey
			sqlMapper.insert("insertAvatarWithKey", user);
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
			setSeqForNode(asset, "update");
		}else{
			thisSeq = new Asset("master~id"+ Integer.toString(asset.getNodeid()),
					"SLInnerSequence",
					Integer.toString(asset.getId()),
					"",
					Integer.toString(asset.getNodeid()), // target for a seq is a node id
					asset.getNodeid()
				);
				sqlMapper.insert("insertAsset", thisSeq);
		}
	}

	public static void removeAssetFromSeq(int assetID) throws Exception{
		Asset thisAsset = AriadneData.getAssetById(assetID);
		setSeqForNode(thisAsset, "delete");
	}

	public static Asset getSeqbyNodeID(int mnodeid)throws Exception {
		return (Asset)sqlMapper.queryForObject("getSeqbyNodeID", mnodeid);
	}

	public static Asset getSeqForNode(Asset asset)throws SQLException{
		return (Asset)sqlMapper.queryForObject("getSeqForNode", asset.getNodeid());
	}

	public static void resetSeqForNode(Asset nonSeqAsset) throws Exception {
		setSeqForNode(nonSeqAsset, "update");
	}

	public static void setSeqForNode(Asset nonSeqAsset, String action) throws Exception {
		if (nonSeqAsset.getName().toLowerCase().indexOf("master~id") == -1){ // if this is not a node's main sequence asset
			Asset thisSeq = null;
			try{
				thisSeq = AriadneData.getSeqForNode(nonSeqAsset); // get seq for this asset's node


				StringBuffer newSeqBuff = new StringBuffer();
				StringTokenizer st =  new StringTokenizer(thisSeq.getValue(), ",");
				if (action.indexOf("del") > -1){
					while (st.hasMoreElements()){
						int token = Integer.parseInt((String)st.nextElement());
							if (nonSeqAsset.getId() != token ){ //will exclude current asset
								newSeqBuff.append(token + ",");
							}
					}
				}else{//add
					newSeqBuff.append(thisSeq.getValue()+","+Integer.toString(nonSeqAsset.getId()));
				}

				if (newSeqBuff.toString().equals(",")){
					newSeqBuff = new StringBuffer();
				}
				// all these insts kinda defeat the ppse of using stringbuffers...
				if (newSeqBuff.toString().length() > 0){
					// double comma trim. shouldn't happen, but...
					if (newSeqBuff.toString().indexOf(",,") > -1){
						System.out.println("Ariadne: Sequence edit alert "+newSeqBuff.toString());
						newSeqBuff = new StringBuffer(newSeqBuff.toString().replace(",,", ","));
					}

					// end trim :
					if (newSeqBuff.toString().endsWith(",")){
						System.out.println("Ariadne: Sequence edit alert "+newSeqBuff.toString());
						newSeqBuff = new StringBuffer(newSeqBuff.toString().substring(0, newSeqBuff.toString().length() -1));
					}

					// front trim :
					if (newSeqBuff.toString().startsWith(",")){
						System.out.println("Ariadne: Sequence edit alert "+newSeqBuff.toString());
						newSeqBuff = new StringBuffer(newSeqBuff.toString().substring(1, newSeqBuff.toString().length()));
					}
				}
				thisSeq.setValue(newSeqBuff.toString());


				if (thisSeq.getValue().trim().length() > 0){
					sqlMapper.update("resetSequence", thisSeq);
				}else{
					AriadneData.deleteAsset (thisSeq.getId());
				}
			}catch (NullPointerException npe) {
				if (thisSeq == null) throw new Exception("<error>: no sequence found for node: "+nonSeqAsset.getNodeid() +EC.XML_ET);
			}
		}else{
			throw new Exception("<error>Cannot add the asset: "+nonSeqAsset.getName()+" to an SLInnerSequence."+ EC.XML_ET);
		}
	}
}
