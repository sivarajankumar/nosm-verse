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

/// <summary>
/// Summary description for XSLRequestConfig
/// </summary>
namespace verse.xsl
{
    public class XSLRequestConfig
    {

        private string baseURL;
        private string URL;
        private NameValueCollection queryString;
        private Encoding encoding;
        private string stylesheetPath;
        private string approot;
        private string appDir;
        private string pivoteAppDir;
        private string pivoteCGIDir;

        /*
        <appSettings>
            <add key="ChartImageHandler" value="storage=file;timeout=20;dir=c:\TempImageFiles\;" />
            <add key="logFileDir" value="C:\"/>
            <add key="baseURL" value="http://142.51.75.111"/>
            <add key="URL" value="http://142.51.75.111/cgi-bin/pivote/pivoteplayer.pl"/>
            <add key="stylesheetPath" value="ol2pivote.xsl"/>
            <add key="approot" value="C:\Inetpub\wwwroot\VERSE"/>
            <add key="appDir" value="SLDataCollection"/>
            <add key="pivoteAppDir" value="wrkpivote"/>
            <add key="pivoteCGIDir" value="wrk\cgi-bin"/>
        </appSettings>
         * */



        public XSLRequestConfig()
        {
            setBaseURL(System.Configuration.ConfigurationManager.AppSettings["baseURL"].ToString());
            setURL(System.Configuration.ConfigurationManager.AppSettings["URL"].ToString());
            setEncoding(Encoding.ASCII);
            setStylesheetPath(System.Configuration.ConfigurationManager.AppSettings["stylesheetPath"].ToString());
            setApproot(System.Configuration.ConfigurationManager.AppSettings["approot"].ToString());
            setAppDir(System.Configuration.ConfigurationManager.AppSettings["appDir"].ToString());
            setPivoteAppDir(System.Configuration.ConfigurationManager.AppSettings["pivoteAppDir"].ToString());
            setPivoteCGIDir(System.Configuration.ConfigurationManager.AppSettings["pivoteCGIDir"].ToString());

            setQueryString(null);
        }

        public XSLRequestConfig(NameValueCollection inQS)
        {

            //again?!? call no param constructor above instead?
            setBaseURL(System.Configuration.ConfigurationManager.AppSettings["baseURL"].ToString());
            setURL(System.Configuration.ConfigurationManager.AppSettings["URL"].ToString());
            setEncoding(Encoding.ASCII);
            setStylesheetPath(System.Configuration.ConfigurationManager.AppSettings["stylesheetPath"].ToString());
            setApproot(System.Configuration.ConfigurationManager.AppSettings["approot"].ToString());
            setAppDir(System.Configuration.ConfigurationManager.AppSettings["appDir"].ToString());
            setPivoteAppDir(System.Configuration.ConfigurationManager.AppSettings["pivoteAppDir"].ToString());
            setPivoteCGIDir(System.Configuration.ConfigurationManager.AppSettings["pivoteCGIDir"].ToString());

            setQueryString(inQS);
        }

        // getter & settters

        public string getURL() { return this.URL; }
        public void setURL(string newurl) { this.URL = newurl; }

        public string getBaseURL() { return this.baseURL; }
        public void setBaseURL(string newBaseUrl) { this.baseURL = newBaseUrl; }

        public NameValueCollection getQueryString() { return this.queryString; }
        public void setQueryString(NameValueCollection newQS) { this.queryString = newQS; }

        public Encoding getEncoding() { return this.encoding; }
        public void setEncoding(Encoding newEncoding) { this.encoding = newEncoding; }

        public string getStylesheetPath() { return this.stylesheetPath; }
        public void setStylesheetPath(string newPath) { this.stylesheetPath = newPath; }

        public string getApproot() { return this.approot; }
        public void setApproot(string newAppRoot) { this.approot = newAppRoot; }

        public string getAppDir() { return this.appDir; }
        public void setAppDir(string newAppDir) { this.appDir = newAppDir; }

        public string getPivoteAppDir() { return this.pivoteAppDir; }
        public void setPivoteAppDir(string newPivoteAppDir) { this.pivoteAppDir = newPivoteAppDir; }

        public string getPivoteCGIDir() { return this.pivoteCGIDir; }
        public void setPivoteCGIDir(string newPivoteCGIDir) { this.pivoteCGIDir = newPivoteCGIDir; }
    }
}
