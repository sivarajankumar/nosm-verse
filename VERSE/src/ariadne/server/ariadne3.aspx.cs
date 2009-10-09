using System;
using System.Collections;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;

using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;

using System.Xml;
using System.Xml.Linq;
using System.Xml.Serialization;
using System.Xml.Xsl;
using System.Xml.XPath;

using System.Net;
using System.IO;
using System.Text;


public partial class ariadne3 : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        string mid = String.Empty;
        string sessID = String.Empty;
        string mnodeID = String.Empty;
        string aMode = String.Empty;
        List<string> Errors = new List<string>();

        string outboundXMLText = String.Empty;

        try
        {
            // Grab the QueryString parameters
            if (Request.QueryString["sessID"] != null)
                sessID = (string)Request.QueryString["sessID"];
            if (Request.QueryString["mnodeID"] != null)
                mnodeID = (string)Request.QueryString["mnodeID"];

            if (Request.QueryString["mode"] != null)
                aMode = (string)Request.QueryString["mode"];

            if (aMode == "")
            {
                outboundXMLText = "No mode has been defined in the request";

            }
            else
            {
                if (mnodeID == "" && (aMode.ToLower() == "slplay" || aMode.ToLower() == "admin"))
                {
                    outboundXMLText = "Missing required MNodeID in the request";
                   // return;
                }

                switch (aMode.ToLower())
                {
                    case "slplay":

                        if (mnodeID != "")
                        {
                            if (Session["ol-sessid"] == null) Session["ol-sessid"] = sessID; // needed?

                            string OLResults = getOLXml(mnodeID, sessID);
                            string olXMLSess = extractNode("//labyrinth/mysession", OLResults);

                            if (sessID == "")
                                Session["ol-sessid"] = olXMLSess;
                            else
                                Session["ol-sessid"] = sessID;

                            
                            string strMNodeTitle = HttpUtility.UrlDecode(extractNode("//labyrinth/mnodetitle", OLResults));
                            string thisNodeID = extractNode("//labyrinth/mnodeid", OLResults);
                            int intMNodeID = Convert.ToInt32(thisNodeID);
                            //always add vpdtext
                            outboundXMLText = outboundXMLText + "<Asset type=\"VPDText\" name=\"nodenm\" targettype=\"PIVOTE\" value=\"" + strMNodeTitle + "\" ></Asset>";

                            DataTable aT = (DataTable)this.ExecuteQuery("SELECT Lower(AssetTypeName) as AssetTypeName, NodeAssetName,NodeAssetTarget, NodeAssetValue, AssetTypeDesc, MNodeID, AssetTypeUIPairs FROM AssetMapNode mapped, AssetType types where mapped.AssetTypeID = types.AssetTypeID and mapped.MNodeID = @param", thisNodeID, true);

                            foreach (DataRow row in aT.Rows)
                            {
                                outboundXMLText = outboundXMLText + "<Asset type=\"" + row[0] + "\" name=\"" + row[1] + "\" targettype=\"" + row[2] + "\" value=\"" + row[3] + "\" ></Asset>";
                            }

                   
                            //links
                            outboundXMLText = outboundXMLText + getLinkSetXML(extractNode("//labyrinth/linker", OLResults));
                        }
                        break;

                    case "admin":
                        /*
                            admin session? disable OL session tracking?
                            build extended XML
                            xml = getnodexml from OL
                            xml = xml + get mappedAssets, assettypes, assetTypeAtrrib xml
                            return resulting xmlhttprequest obj 
                            (requested by jQuery get() method in ariadneAdmin.html)
                         */
                        if (mnodeID != "")
                        {
                            if (Session["ol-sessid"] != null) Session["ol-sessid"] = sessID; // needed?

                            string OLResults = getOLXml(mnodeID, sessID);
//throw new Exception("OLResults: " + OLResults);
                            string olXMLSess = extractNode("//labyrinth/mysession", OLResults);
throw new Exception("olXMLSess: " + olXMLSess);
                            if (sessID == "")
                                Session["ol-sessid"] = olXMLSess;
                            else
                                Session["ol-sessid"] = sessID;

                            
                            
                            string strMNodeTitle = HttpUtility.UrlDecode(extractNode("//labyrinth/mnodetitle", OLResults));
                            string thisNodeID = extractNode("/labyrinth/mnodeid", OLResults);
                            int intMNodeID = Convert.ToInt32(thisNodeID);
                            //always add vpdtext
                            outboundXMLText = outboundXMLText + "<Asset type=\"VPDText\" name=\"nodenm\" targettype=\"PIVOTE\" value=\"" + strMNodeTitle + "\" ></Asset>";

                            DataTable aT = (DataTable)this.ExecuteQuery("SELECT Lower(AssetTypeName) as AssetTypeName, NodeAssetName,NodeAssetTarget, NodeAssetValue, AssetTypeDesc, MNodeID, AssetTypeUIPairs FROM AssetMapNode mapped, AssetType types where mapped.AssetTypeID = types.AssetTypeID and mapped.MNodeID = 8336 ", thisNodeID, true);


                            foreach (DataRow row in aT.Rows)
                            {
//throw new Exception("hey some rows!: ");
                                outboundXMLText = outboundXMLText + "<Asset type=\"" + row[0] + "\" name=\"" + row[1] + "\" targettype=\"" + row[2] + "\" value=\"" + row[3] + "\" ></Asset>";



			    }
/*
			    DataTable aT3 = (DataTable)this.ExecuteQuery("select AssetTypeID from AssetType", " ", true);
                            foreach (DataRow row3 in aT3.Rows)
                            {

                             	// add asset attrib xml, FOR EACH ASSET TYPE
                            	DataTable aT2 = (DataTable)this.ExecuteQuery("SELECT AssetTypeAttribValue, CASE  WHEN(SELECT SUM(1) FROM AssetTypeAttribs attr WHERE  attr.AssetTypeID = (select distinct AssetTypeID  from AssetType where   AssetType.AssetTypeID = @param )  ) > 1  THEN 'select' ELSE 'input' END as FROM  AssetTypeAttribs  WHERE AssetTypeAttribs.AssetTypeID = @param  ", Convert.ToInt32(row3[0].ToString()), true);

                            	foreach (DataRow row2 in aT2.Rows)
                            	{
                                    	outboundXMLText = outboundXMLText + "<assetattrib name=\"\" value=\"" + row2[0] + "\" label=\"" + (string)row2[0] + "\" ftype=\"" + row2[1] + "\"/></<assetattrib>";
                            	}

                            }
*/
//throw new Exception("OLResults string: " + OLResults);

                            //links
				string linkXML = extractNode("/labyrinth/linker", OLResults);

throw new Exception("linkXML string: " + linkXML);

                            outboundXMLText = outboundXMLText + getLinkSetXML(linkXML);

                        }
                        break;


                    case "slstats":
                        //googlecharts  asking for stats xml
                        // TODO: 
                        break;

                    case "slbuild":
                        /*
                            is builder HUD
                            insert new asset into assetTypeAttrib table
                            return (brief) success xml msg
                         */
                        break;


                    case "rpc":
                        /*
                            insert new holodeck list
                                or
                            send pause to controller (HSVO)
                                or
                            send generic msg to named in-world object (key needed?) 
                         return (brief) xml success msg 
                         */
                        break;

                    default: //sl play?
                        break;
                }
            }

        }
        catch (Exception Ex)
        {
            outboundXMLText = "General Error Trapping [" + Ex.Message + Ex.Data.Values.ToString() + Ex.GetBaseException().StackTrace.ToString()+ "]";
        }

        // run xslt?
        // outboundXMLText = transformXML(outboundXMLText, olPage);
        Response.ContentType = "text/xml";
        Response.Write(outboundXMLText);


    }



    public string extractNode(string nodePath, string xml)
    {
/*
        XmlDocument xDoc = new XmlDocument();
        xDoc.LoadXml(xml);
        XmlNode xmlNode = (XmlNode)xDoc.SelectNodes(nodePath).Item(0);
        return xmlNode.Value;


  XPathNavigator nav;
   XPathDocument docNav;
   XPathNodeIterator NodeIter;

     docNav = new XPathDocument(xml);
     nav = docNav.CreateNavigator();
     string strExpression = nav.Evaluate(nodePath).ToString();
    return strExpression ;



XmlDocument xmldoc = new XmlDocument();
xmldoc.Load(xml);
System.IO.StringReader sr = new System.IO.StringReader(xmldoc.InnerXml);
XPathDocument doc = new XPathDocument(sr);
XPathNavigator nav = doc.CreateNavigator();
XPathExpression ex = nav.Compile(nodePath);
*/

XmlTextReader reader = new XmlTextReader(xml);
XmlDocument doc = new XmlDocument(); 
doc.Load(reader);
//reader.Close();

XmlNode xmlNode ;
XmlElement root = doc.DocumentElement;
xmlNode  = root.SelectSingleNode(@nodePath);


return xmlNode.Value;




    }

    public string getX(string n, string v)
    {
        return "<" + n + ">" + v + "</" + n + ">";
    }


    public static string transformXML(string xml, string stylesheetPath)
    {
        StringBuilder stringBuilder = new StringBuilder(xml);
        try
        {
            XslCompiledTransform xslCompiledTransform = new XslCompiledTransform();
            xslCompiledTransform.Load(stylesheetPath);
            StringWriter stringWriter = new StringWriter(stringBuilder);
            XmlTextReader xmlTextReader = new XmlTextReader(xml);
            xslCompiledTransform.Transform(xmlTextReader, null, stringWriter);
        }
        catch (Exception oops2)
        {
            Console.WriteLine(oops2.Message);
        }
        return stringBuilder.ToString();

    }


    public string getLinkSetXML(string strLinker)
    {
throw new Exception("linker string: " + strLinker);

        string xmlout = "";
        strLinker = strLinker.Replace("<p>", "");
        strLinker = strLinker.Replace("</p>", "");
        strLinker = strLinker.Replace("<a href='mnode_client.asp", "");
        strLinker = strLinker.Replace("</a>", "");
        strLinker = strLinker.Replace("&mode=remote", "");

        List<string> MNodeIDs = new List<string>();
        List<string> MNodeTexts = new List<string>();

        while (strLinker != "")
        {
            // Get the MNodeID
            int intIDLoc = strLinker.IndexOf("?id=");
            int intSessLoc = strLinker.IndexOf("&sessID");
            string strMNode = strLinker.Substring(intIDLoc + 4, intSessLoc - intIDLoc - 4); //node ID
            MNodeIDs.Add(strMNode);

            // Get the Corresponding Text
            strLinker = strLinker.Substring(intSessLoc, strLinker.Length - intSessLoc);
            int intBracketLoc = strLinker.IndexOf("]]]] -");
            strLinker = strLinker.Substring(intBracketLoc + 7, strLinker.Length - intBracketLoc - 7);
            int intNextIDLoc = strLinker.IndexOf("?id=");
            if (intNextIDLoc == -1)
            {
                MNodeTexts.Add(strLinker);
                strLinker = "";
            }
            else
            {
                MNodeTexts.Add(strLinker.Substring(0, intNextIDLoc));
                strLinker = strLinker.Substring(intNextIDLoc, strLinker.Length - intNextIDLoc);
            }
        }

        if (MNodeIDs.Count > 0)
        {
            xmlout = "<Links>";
            for (int j = 0; j < MNodeIDs.Count; j++)
            {
                xmlout = xmlout + "<Link label=\"" + MNodeTexts[j] + "\" ref=\"" + MNodeIDs[j] + "\" ></Link>";
            }
            xmlout = xmlout + "<Links>";
        }

        return xmlout;
    }


    public string getOLXml(string mnodeID, string sessID)
    {
        // Make the call to OpenLabyrinth
        string OLResults = String.Empty;
        string str2append = String.Empty;
        string OLAddress = System.Configuration.ConfigurationManager.AppSettings["OLURL"].ToString();

        if (sessID == "" || sessID == null)
        {
            // First call.
            OLAddress += "mnode.asp?mode=remote&id=" + mnodeID;
//throw new Exception("OLAddress: " + OLAddress+"<-");
            OLResults = GetPageAsString(OLAddress);
//throw new Exception("OLResults: " + OLResults);
        }
        else
        {
            // Subsequent call.
            OLAddress += "mnode.asp?mode=remote&id=" + mnodeID + "&sessID=" + sessID;
//throw new Exception("OLAddress: " + OLAddress);

            OLResults = GetPageAsString(OLAddress);
//throw new Exception("OLResults: " + OLResults);
        }

        if (OLResults.Contains("<labyrinth>") && !OLResults.Contains("<labyrinth>Not a valid"))  // it's valid XML vs. error message
        {
            str2append = str2append + OLResults;
        }
        else
        {
            str2append = "Bad Call to OL [" + OLAddress + "] [" + OLResults + "]";
        }
        return str2append;
    }



    public static string GetPageAsString(string address)
    {
        string result = "";
        try
        {
            // Create the web request   
            HttpWebRequest request = WebRequest.Create(address) as HttpWebRequest;
            // Get response   
            using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
            {
                // Get the response stream   
                StreamReader reader = new StreamReader(response.GetResponseStream());
                // Read the whole contents and return as a string   
                result = reader.ReadToEnd();
            }
        }
        catch (Exception ex)
        {
            result = ex.Message;
        }
        return result;
    }

    private String UTF8ByteArrayToString(Byte[] characters)
    {
        UTF8Encoding encoding = new UTF8Encoding();

        String constructedString = encoding.GetString(characters);

        return (constructedString);
    }

    public static string getFileAsString(string fileName)
    {
        StreamReader sReader = null;
        string contents = null;
        try
        {
            FileStream fileStream = new FileStream(fileName, FileMode.Open, FileAccess.Read);
            sReader = new StreamReader(fileStream);
            contents = sReader.ReadToEnd();
        }
        finally
        {
            if (sReader != null) { sReader.Close(); }
        }
        return contents;
    }

    private DataTable ExecuteQuery(string SQLstring, object theKey, bool useNumber)
    {
        DataTable dt;
        SqlConnection conn = new SqlConnection(System.Web.Configuration.WebConfigurationManager.ConnectionStrings["AriadneConnectionString2"].ToString());
        //DataTable dt = new DataTable("AssetType");
        using (conn)
        {
            conn.Open();
            String strCmd = SQLstring;
            SqlCommand cmd = new SqlCommand();
            cmd.CommandText = strCmd;
            cmd.Connection = conn;
            SqlDataAdapter da = new SqlDataAdapter();
            da.SelectCommand = cmd;
		SqlParameter p1 = new SqlParameter("@param", theKey );
		
//if (useNumber){
//p1 = new SqlParameter("@param", thekey, SqlDbType.VarChar);

//}

            
            p1.Direction = ParameterDirection.Input;
            p1.Value = Convert.ToInt32(theKey);
            cmd.Parameters.Add(p1);

            dt = new DataTable();
            da.Fill(dt);
        }
        conn.Close();
        conn = null;
        return dt;
    }

}

