


using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.IO;
using System.Net;
using System.Text;
using System.Xml;
using System.Xml.Xsl;
using System.Collections.Specialized;
using CustomExtensions;

namespace verse.xsl
{

    /// <summary>
    /// Summary description for XSLTransformer
    /// </summary>

    public class XSLTransformer
    {
        //read an xml file, xslt, and write to string (no error checking)
        public static string transformBETA(string xmlFile, string xslFile)
        {
            XmlDocument xmlDocument = new XmlDocument();
            xmlDocument.Load(xmlFile);

            XslTransform xslTransform = new XslTransform();
            xslTransform.Load(xslFile);

            StringWriter stringWriter = new StringWriter();
            XmlTextWriter xmlTextWriter = new XmlTextWriter(stringWriter);
            xmlTextWriter.Formatting = Formatting.Indented;
            xslTransform.Transform(xmlDocument, null, xmlTextWriter);

            xmlTextWriter.Flush();
            return stringWriter.ToString();
        }


        public static string extractNode(string nodePath, string xml)
        {
            XmlDocument xDoc = new XmlDocument();
            xDoc.LoadXml(xml);

            //temp
            //nodePath = "//labyrinthservice/labyrinthmap/labyrinthmaproot";

            XmlNode xmlNode = (XmlNode)xDoc.SelectNodes(nodePath).Item(0);
            return xmlNode.Value;
        }



        public static string relay(XSLRequestConfig xslConf, bool usePivote)
        {
            HttpWebRequest request;
            HttpWebResponse response = null;
            Stream resStream = null;
            StreamReader streamReader = null;
            StringBuilder stringBuilder = new StringBuilder();
            try
            {
                string appendChar = "?";
                if (xslConf.getURL().IndexOf(appendChar) > -1)
                {
                    appendChar = "&";
                }

                //send HTTP request
                request = (HttpWebRequest)WebRequest.Create(xslConf.getURL()
                    + appendChar + xslConf.getQueryString().ConstructQueryString());

                //another way...
                //request = (HttpWebRequest)WebRequest.Create(
                //    xslConf.getQueryString().WriteLocalPathWithQuery(new Uri(xslConf.getURL())));


                if (request != null)
                {
                    response = (HttpWebResponse)request.GetResponse();
                    resStream = response.GetResponseStream();
                    streamReader = new StreamReader(resStream, xslConf.getEncoding());

                    if (usePivote)
                    {
                        //is a call to pivote: do nothing to response text
                        stringBuilder = new StringBuilder(streamReader.ReadToEnd());
                    }
                    else
                    {
                        //is openLabyrinth: must XSLT
                        XslCompiledTransform xslCompiledTransform = new XslCompiledTransform();

                        if (System.Environment.MachineName.ToLower().IndexOf("vptest") == -1)
                        {
                            xslConf.setApproot(@"C:\dev\nosm-verse\src");
                            xslConf.setAppDir(@"sl_dispatch");
                        }

                        xslCompiledTransform.Load(xslConf.getApproot() + "\\" + xslConf.getAppDir() + "\\" + xslConf.getStylesheetPath());
                        StringWriter stringWriter = new StringWriter(stringBuilder);
                        XmlTextReader xmlTextReader = new XmlTextReader(streamReader);

                        if (System.Environment.MachineName.ToLower().IndexOf("vptest") > -1)
                        {
                            xslCompiledTransform.Transform(xmlTextReader, null, stringWriter);
                        }
                        else
                        {
                            xslCompiledTransform.Transform(@"C:\dev\nosm-verse\src\sl_dispatch\ol-mnode1.xml", null, stringWriter);
                        }
                        stringWriter.Close();
                    }
                }
            }
            catch (WebException exception)
            {
                if ((exception.Status == WebExceptionStatus.ProtocolError) || (response.StatusCode != System.Net.HttpStatusCode.OK))
                { //get the response object from the WebException
                    response = exception.Response as HttpWebResponse;
                    if (response == null) { return "<Error>" + exception.StackTrace + "</Error>"; }
                }
            }

            finally
            {
                if (response != null) response.Close();
                if (resStream != null) resStream.Close();
                if (streamReader != null) streamReader.Close();
            }

            return stringBuilder.ToString();

        }

    }
}
namespace CustomExtensions
{
    public static class Extensions
    {
        public static string WriteLocalPathWithQuery(
            this NameValueCollection collection,
            Uri Url)
        {
            if (collection.Count == 0)
                return Url.LocalPath;

            System.Text.StringBuilder sb = new System.Text.StringBuilder(Url.LocalPath);
            sb.Append("?");

            for (int i = 0; i < collection.Keys.Count; i++)
            {
                if (i != 0)
                    sb.Append("&");
                sb.Append(
                    String.Format("{0}={1}",
                    collection.Keys[i], collection[i])
                    );
            }
            return sb.ToString();
        }

        public static NameValueCollection ChangeField(this NameValueCollection collection,
            string Key, string Value)
        {
            return ChangeField(collection, Key, Value, true);
        }

        public static NameValueCollection ChangeField(this NameValueCollection collection,
            string Key, string Value, bool Allow)
        {
            if (Allow)
            {
                if (!String.IsNullOrEmpty(collection[Key]))
                    collection[Key] = Value;
                else
                    collection.Add(Key, Value);
            }
            else //remove the value all together
            {
                if (!String.IsNullOrEmpty(collection[Key]))
                    collection.Remove(Key);
            }
            return collection;
        }

        public static NameValueCollection Duplicate(this NameValueCollection source)
        {
            System.Collections.Specialized.NameValueCollection collection = new System.Collections.Specialized.NameValueCollection();
            foreach (string key in source)
                collection.Add(key, source[key]);
            return collection;
        }

        public static string ConstructQueryString(this NameValueCollection collection)
        {
            if (collection.Count == 0)
                return "";

            List<String> items = new List<String>();

            foreach (String name in collection)
                items.Add(String.Concat(name, "=", System.Web.HttpUtility.UrlEncode(collection[name])));

            return String.Join("&", items.ToArray());

        }


    }
}