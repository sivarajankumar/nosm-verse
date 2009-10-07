using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Net;
using System.IO;
using System.Xml.Serialization;
using System.Xml;
using System.Text;
using System.Data;
using System.Data.SqlClient;

namespace Ariadne
{
    public partial class _Default : System.Web.UI.Page
    {
        string mid = String.Empty;
        string sessID = String.Empty;
        string mnodeID = String.Empty;
        string aMode = String.Empty;
        List<string> Errors = new List<string>();

        protected void Page_Load(object sender, EventArgs e)
        {
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
                    if (mnodeID == "" && (aMode.ToLower == "slplay" || aMode.ToLower == "admin"))
                    {
                        outboundXMLText = "Missing required MNodeID in the request";
                    }
                }
                if (aMode != "")
                {
                    switch (aMode.ToLower())
                    {
                        case "slbuild":
                            /*
                                is builder HUD
                                insert new asset into assetTypeAttrib table
                                return (brief) success xml msg
                             */
                            break;
                        case "slplay":

                            if (mnodeID != "")
                            {
                                string OLResults = getOLXml(mnodeID, sessID);
                                string olXMLSess = extractNode("//labyrinth/mysession", OLResults);

                                if (sessID == "")
                                    thisSessID = olXMLSess;
                                else
                                    thisSessID = sessID;

                                int intMNodeID = Convert.ToInt32(thisNodeID);
                                string strMNodeTitle = HttpUtility.UrlDecode(extractNode("//labyrinth/mnodetitle", OLResults));
                                string thisNodeID = extractNode("//labyrinth/mnodeid", OLResults);

                                DataTable aT = getDataTable4SimpleSQL("SELECT Assets.AssetID, Assets.AssetTypeID, Assets.AssetTargetType, Assets.AssetName, AssetMapNode.MNodeID, Assets.AssetValue, AssetType.AssetTypeName FROM AssetMapNode", "????????", thisNodeID);
                                if (aT.Rows.Count > 0)
                                {
                                    for (int i = 0; i < aT.Rows.Count; i++)
                                    {
                                        outboundXMLText = outboundXMLText + "<Asset type=\"" + aT[i][0] + "\" name=\"" + aT[i][1] + "\" targettype=\"" + aT[i][2] + "\" value=\"" + aT[i][3] + "\" ></Asset>";
                                    }
                                    //always add vpdtext
                                    outboundXMLText = outboundXMLText + "<Asset type=\"VPDText\" name=\"nodenm\" targettype=\"PIVOTE\" value=\"" + strMNodeTitle + "\" ></Asset>";
                                }
                                //links
                                outboundXMLText = outboundXMLText + getLinkSetXML(extractNode("//labyrinth/linker", OLResults));
                            }
                            break;

                        case "slstats":
                            //googlecharts  asking for stats xml
                            // TODO: 
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
                                string OLResults = getOLXml(mnodeID, sessID);
                                string olXMLSess = extractNode("//labyrinth/mysession", OLResults);

                                if (sessID == "")
                                    thisSessID = olXMLSess;
                                else
                                    thisSessID = sessID;

                                int intMNodeID = Convert.ToInt32(thisNodeID);
                                string strMNodeTitle = HttpUtility.UrlDecode(extractNode("//labyrinth/mnodetitle", OLResults));
                                string thisNodeID = extractNode("//labyrinth/mnodeid", OLResults);

                                DataTable aT = getDataTable4SimpleSQL("SELECT Assets.AssetID, Assets.AssetTypeID, Assets.AssetTargetType, Assets.AssetName, AssetMapNode.MNodeID, Assets.AssetValue, AssetType.AssetTypeName FROM AssetMapNode", "????????", thisNodeID);
                                if (aT.Rows.Count > 0)
                                {
                                    for (int i = 0; i < aT.Rows.Count; i++)
                                    {
                                        outboundXMLText = outboundXMLText + "<Asset type=\"" + aT[i][0] + "\" name=\"" + aT[i][1] + "\" targettype=\"" + aT[i][2] + "\" value=\"" + aT[i][3] + "\" ></Asset>";
                                    }
                                    //always add vpdtext
                                    outboundXMLText = outboundXMLText + "<Asset type=\"VPDText\" name=\"nodenm\" targettype=\"PIVOTE\" value=\"" + strMNodeTitle + "\" ></Asset>";
                                }


                                DataTable aT = getDataTable4SimpleSQL("SELECT Assets.AssetID, Assets.AssetTypeID, Assets.AssetTargetType, Assets.AssetName, AssetMapNode.MNodeID, Assets.AssetValue, AssetType.AssetTypeName FROM AssetMapNode", "????????", thisNodeID);
                                if (aT.Rows.Count > 0)
                                {
                                    for (int i = 0; i < aT.Rows.Count; i++)
                                    {
                                        outboundXMLText = outboundXMLText + "<Asset type=\"" + aT[i][0] + "\" name=\"" + aT[i][1] + "\" targettype=\"" + aT[i][2] + "\" value=\"" + aT[i][3] + "\" ></Asset>";
                                    }
                                    //always add vpdtext
                                    outboundXMLText = outboundXMLText + "<Asset type=\"VPDText\" name=\"nodenm\" targettype=\"PIVOTE\" value=\"" + strMNodeTitle + "\" ></Asset>";
                                }
                                //links
                                outboundXMLText = outboundXMLText + getLinkSetXML(extractNode("//labyrinth/linker", OLResults));
                            }

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

                    // run xslt?
                    // outboundXMLText = transformXML(outboundXMLText, olPage);
                    Response.ContentType = "text/xml";
                    Response.Write(outboundXMLText);

                }
            }
            catch (Exception Ex)
            {
                outboundXMLText = "General Error Trapping [" + Ex.Message + "]";
            }

        }

        public string extractNode(string nodePath, string xml)
        {
            XmlDocument xDoc = new XmlDocument();
            xDoc.LoadXml(xml);
            XmlNode xmlNode = (XmlNode)xDoc.SelectNodes(nodePath).Item(0);
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
            finally
            {
                if (response != null) response.Close();
                if (resStream != null) resStream.Close();
                if (streamReader != null) streamReader.Close();
            }

            return stringBuilder.ToString();

        }


        public string getLinkSetXML(string strLinker)
        {
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
                OLResults = GetPageAsString(OLAddress);
            }
            else
            {
                // Subsequent call.
                OLAddress += "mnode.asp?mode=remote&id=" + mnodeID + "&sessID=" + sessID;
                OLResults = GetPageAsString(OLAddress);
            }

            if (OLResults.Contains("<labyrinth>"))  // it's valid XML vs. error message
            {
                str2append = str2append + OLResults;
            }
            else
            {
                Errors.Add("Bad Call to OL [" + OLAddress + "] [" + OLResults + "]");
            }
        }

        public static string getXML4SimpleJoinSQL(string outerquery, string innerquery, string joiningkey)
        {
            DataTable rt = this.ExecuteQuery(outerquery);
            foreach (DataRow dr in rt.Rows)
            {
                string curAType = (string)dr[joiningkey].ToString();
                DataTable rtI = this.ExecuteQuery(innerquery + "'" + curAType + "'");
                foreach (DataRow drI in rtI.Rows)
                {
                    string curAType = (string)drI["AssetTypeName"].ToString();
                }

            }
            return rt;
        }

        private void getAssetTypePairXML(string dataValueField, string dataTextField, DataTable dataTbl)
        {
            if (dataTbl.Rows.Count > 0)
            {
                dropDownList.DataTextField = dataTextField;
                dropDownList.DataValueField = dataValueField;
                dropDownList.DataSource = dataTbl;
                dropDownList.DataBind();
            }
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
    }
}
