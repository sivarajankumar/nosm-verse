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

public partial class _Default : System.Web.UI.Page
{


    protected void Page_Load(object sender, EventArgs e)
        {
            string xmlfile = "";
            foreach (string key in Request.QueryString.Keys)
            {
                if (key != null)
                {
                    string strKey = key.ToLower();
                    if (strKey == "node")
                    {
                        xmlFile = Request.QueryString[key].ToString();
                    }
                }//end if
            }//end for


            if (xmlFile != "")
            {
                mvpXML.Text = getFileAsString(@"C:\Inetpub\wwwroot\VERSE\SLDataCollection\xml\" + xmlfile + ".xml");
            }
        }

protected string getFileAsString(string fileName){
        StreamReader sReader = null;
        string contents = null;
        try{
            FileStream fileStream = new FileStream(fileName, FileMode.Open, FileAccess.Read);
            sReader = new StreamReader(fileStream);
            contents = sReader.ReadToEnd();
        }
        finally{
            if (sReader != null){sReader.Close(); }
        }
        return contents;
    }

    }


}
