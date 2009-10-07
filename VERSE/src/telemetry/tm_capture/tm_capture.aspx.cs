using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using System.Data.SqlClient;
using System.Configuration;
using System.IO;

public partial class tm_capture : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        // Set the default output of the Session ID and Device ID to be blank to indicate an
        // error.  These will be no longer be blank after a succesful update.
        lblSID.Text = "asset updated";

        string strType = null;
        string strName = null;
        string strTarget = null;
        string strValue = null;
        string strAvatar = null;

        foreach (string key in Request.QueryString.Keys){
            if (key != null){
                switch (key.ToLower()){
                    case "t":
                        strType = Request.QueryString[key];
                        break;
                    case "n":
                        strName = Request.QueryString[key];
                        break;
                    case "tr":
                        strTarget = Request.QueryString[key];
                        break;
                    case "v":
                        strValue = Request.QueryString[key];
                        break;
                    case "av":
                        strAvatar = Request.QueryString[key];
                        break;
                }
            }
        }

	    int intAliasID = ValidUser(strUID, strDName);
	    if (intAliasID > 0) {
	        // Create a new Session ID
	        strSID = CreateSessionID();

	        // Look up the Device ID
	        int intDID = GetDeviceID(strDName);
	        strDID = Convert.ToString(intDID);

	        // Insert a new record in the Session Table
	        if (InsertSessionRecord(strSID, intAliasID, strDID))
	        {
	            // Always Log the QueryString to a file.
	            LogQueryString();

	            // Check to see if we are doing live updates.
	            string strLiveUpdates = System.Configuration.ConfigurationManager.AppSettings["liveUpdates"].ToString();
	            if (strLiveUpdates.ToUpper() == "TRUE")
	                InsertSessionLogRecord(strSID);

	            // Return the Session ID to the calling application.
	            lblSID.Text = lblSID.Text +" : " + strName;
	        }
	    }
    }




    private void InsertSessionLogRecord(string SID){
        // Iterate through all the keys in the QueryString.
        foreach (string key in Request.QueryString.Keys){
            if (key != null){
                string strParamName = key;
                string strParamValue = Request.QueryString[key];
                if (strParamValue != "&"){
                    if (strParamName.ToUpper() != "UID" && strParamName.ToUpper() != "SID" &&
                        strParamName.ToUpper() != "DNAME" && strParamName.ToUpper() != "DID")
                    {
                        SqlConnection conn = null;
                        try
                        {
                            // Open a connection to the database.
                            string strConn = ConfigurationManager.ConnectionStrings["VERSE"].ToString();
                            conn = new SqlConnection(strConn);
                            conn.Open();

                            // Look up the parameterID based on the parametername, aka the key, in the
                            // QueryString.
                            SqlCommand cmdGetParameterID = new SqlCommand("sp_tm_GetParameterID", conn);
                            cmdGetParameterID.CommandType = CommandType.StoredProcedure;

                            SqlParameter paramID = cmdGetParameterID.Parameters.Add("@intParamID", SqlDbType.Int);
                            paramID.Direction = ParameterDirection.Output;

                            cmdGetParameterID.Parameters.Add(new SqlParameter("@strParamName", strParamName));

                            cmdGetParameterID.ExecuteReader();
                            conn.Close();


                        }
                        catch (Exception ex)
                        {
                            LogError("Inserting Session Log [" + ex.Message + "] - [" + ex.InnerException + "]");
                        }
                        finally
                        {
                            if (conn != null)
                                conn.Close();
                        }
                    }
                }
            }
        }
    }
    private int ValidUser(string userID, string deviceName)
    {
        int intAliasID = -1;

        SqlConnection conn = null;
        try
        {
            string strConn = ConfigurationManager.ConnectionStrings["VERSE"].ToString();
            conn = new SqlConnection(strConn);
            conn.Open();

            // Look up the AliasID based on the UserName and DeviceName.
            SqlCommand cmdGetUser = new SqlCommand("sp_tm_ValidateUser", conn);
            cmdGetUser.CommandType = CommandType.StoredProcedure;

            cmdGetUser.Parameters.Add(new SqlParameter("@strAliasName", userID));
            cmdGetUser.Parameters.Add(new SqlParameter("@strDeviceName", deviceName));
            SqlParameter aliasID = cmdGetUser.Parameters.Add("@intAliasID", SqlDbType.Int);
            aliasID.Direction = ParameterDirection.Output;

            cmdGetUser.ExecuteReader();
            conn.Close();

            // If the device id is not found in the database, write to the log.
            if (aliasID.Value == System.DBNull.Value)
                LogError("Unknown User [" + deviceName + "]");
            else
                intAliasID = (int)aliasID.Value;
        }
        catch (Exception ex)
        {
            LogError("Validating User [" + ex.Message + "] - [" + ex.InnerException + "]");
        }
        finally
        {
            if (conn != null)
                conn.Close();

        }
        return intAliasID;
    }
    private string CreateSessionID()
    {
        return Guid.NewGuid().ToString();
    }
    private Boolean InsertSessionRecord(string sessionID, int aliasID, string deviceID)
    {
        Boolean boolResult = false;

        SqlConnection conn = null;
        try
        {
            string strConn = ConfigurationManager.ConnectionStrings["VERSE"].ToString();
            conn = new SqlConnection(strConn);
            conn.Open();

            // Insert a new record in the Session table.
            SqlCommand cmdCreateSession = new SqlCommand("sp_tm_InsertSession", conn);
            cmdCreateSession.CommandType = CommandType.StoredProcedure;

            cmdCreateSession.Parameters.Add(new SqlParameter("@strSessionID",sessionID));
            cmdCreateSession.Parameters.Add(new SqlParameter("@intDeviceID",deviceID));
            cmdCreateSession.Parameters.Add(new SqlParameter("@dtSessionStart",DateTime.Now));
            cmdCreateSession.Parameters.Add(new SqlParameter("@dtSessionEnd",DateTime.Now));
            cmdCreateSession.Parameters.Add(new SqlParameter("@strClientIP",GetIP()));
            cmdCreateSession.Parameters.Add(new SqlParameter("@intAliasID", aliasID));
            cmdCreateSession.ExecuteNonQuery();

            boolResult = true;
        }
        catch (Exception ex)
        {
            LogError("Inserting Session Record [" + ex.Message + "] - [" + ex.InnerException + "]");
        }
        finally
        {
            if (conn != null)
                conn.Close();

        }
        return boolResult;
    }
    private Boolean UpdateSessionRecord(string sessionID)
    {
        Boolean boolResult = false;

        SqlConnection conn = null;
        try
        {
            string strConn = ConfigurationManager.ConnectionStrings["VERSE"].ToString();
            conn = new SqlConnection(strConn);
            conn.Open();

            // Update the Session table.
            SqlCommand cmdUpdateSession = new SqlCommand("sp_tm_UpdateSessionEndTime", conn);
            cmdUpdateSession.CommandType = CommandType.StoredProcedure;

            cmdUpdateSession.Parameters.Add(new SqlParameter("@strSessionID", sessionID));
            cmdUpdateSession.Parameters.Add(new SqlParameter("@dtSessionEnd", DateTime.Now));
            cmdUpdateSession.ExecuteNonQuery();

            boolResult = true;
        }
        catch (Exception ex)
        {
            LogError("Updating Session Record [" + ex.Message + "] - [" + ex.InnerException + "]");
        }
        finally
        {
            if (conn != null)
                conn.Close();

        }

        return boolResult;
    }
    public string GetIP()
    {
        string strIPAddress = "";

        try
        {
            HttpRequest currentRequest = HttpContext.Current.Request;
            strIPAddress = currentRequest.ServerVariables["HTTP_X_FORWARDED_FOR"];

            if (strIPAddress == null || strIPAddress.ToLower() == "unknown")
                strIPAddress = currentRequest.ServerVariables["REMOTE_ADDR"];
        }
        catch (Exception ex)
        {
            LogError("Getting IP Address [" + ex.Message + "] - [" + ex.InnerException + "]");
        }

        return strIPAddress;
    }
    public int GetDeviceID(string deviceName)
    {
        int intDeviceID = -1;

        SqlConnection conn = null;
        try
        {
            string strConn = ConfigurationManager.ConnectionStrings["VERSE"].ToString();
            conn = new SqlConnection(strConn);
            conn.Open();

            // Look up the DeviceID based on the deviename.
            SqlCommand cmdGetDeviceID = new SqlCommand("sp_tm_GetDeviceID", conn);
            cmdGetDeviceID.CommandType = CommandType.StoredProcedure;

            SqlParameter deviceID = cmdGetDeviceID.Parameters.Add("@intDeviceID", SqlDbType.Int);
            deviceID.Direction = ParameterDirection.Output;

            cmdGetDeviceID.Parameters.Add(new SqlParameter("@strDeviceName", deviceName));

            cmdGetDeviceID.ExecuteReader();
            conn.Close();

            // If the device name is not found in the database, write to the log.
            if (deviceID.Value == System.DBNull.Value)
                LogError("Unknown Device [" + deviceName + "]");
            else
                intDeviceID = (int)deviceID.Value;
        }
        catch (Exception ex)
        {
            LogError("Looking up Device ID [" + ex.Message + "] - [" + ex.InnerException + "]");
        }
        finally
        {
            if (conn != null)
                conn.Close();

        }
        return intDeviceID;
    }
    public string GetDeviceName(string deviceID)
    {
        string strDeviceName = "";

        SqlConnection conn = null;
        try
        {
            string strConn = ConfigurationManager.ConnectionStrings["VERSE"].ToString();
            conn = new SqlConnection(strConn);
            conn.Open();

            // Look up the DeviceName based on the deviceID.
            SqlCommand cmdGetDeviceName = new SqlCommand("sp_tm_GetDeviceName", conn);
            cmdGetDeviceName.CommandType = CommandType.StoredProcedure;

            SqlParameter deviceName = cmdGetDeviceName.Parameters.Add("@strDeviceName", SqlDbType.VarChar);
            deviceName.Direction = ParameterDirection.Output;
            deviceName.Size = 256;

            cmdGetDeviceName.Parameters.Add(new SqlParameter("@intDeviceID", deviceID));

            cmdGetDeviceName.ExecuteReader();
            conn.Close();

            // If the device id is not found in the database, write to the log.
            if (deviceName.Value == System.DBNull.Value)
                LogError("Unknown Device [" + deviceName + "]");
            else
                strDeviceName = (string)deviceName.Value;
        }
        catch (Exception ex)
        {
            LogError("Looking up Device Name [" + ex.Message + "] - [" + ex.InnerException + "]");
        }
        finally
        {
            if (conn != null)
                conn.Close();

        }
        return strDeviceName;
    }
}
