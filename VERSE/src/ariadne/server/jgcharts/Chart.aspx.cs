using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Xml.Linq;

public partial class Chart : System.Web.UI.Page
{
    private DataTable GetSampleData()
    {
        DataTable dt = new DataTable();
        dt.Columns.Add("Month");
        dt.Columns.Add("Income");
        dt.Columns.Add("Expense");

        dt.Rows.Add("01/2009", "1000", "3003.55");
        dt.Rows.Add("02/2009", "1000", "72.65");
        dt.Rows.Add("03/2009", "1000", "760.89");
        dt.Rows.Add("04/2009", "1000", "354.55");
        dt.Rows.Add("05/2009", "1000", "180.52");
        dt.Rows.Add("06/2009", "1000", "408.54");
        dt.Rows.Add("07/2009", "0", "0");
        dt.Rows.Add("08/2009", "0", "0");
        dt.Rows.Add("09/2009", "0", "0");
        dt.Rows.Add("10/2009", "0", "0");
        dt.Rows.Add("11/2009", "0", "0");
        dt.Rows.Add("12/2009", "0", "0");

        return dt;

    }
    protected void Page_Load(object sender, EventArgs e)
    {
        DataTable dTable = GetSampleData();
        grvIncomeExp.DataSource = dTable;
        grvIncomeExp.DataBind();

        ArrayList chartData = new ArrayList();
        foreach (DataRow dr in dTable.Rows)
        {
            chartData.Add(String.Format("[{0}, {1}]", dr[1], dr[2].ToString().Replace("-", "")));
        }

        //Convert .NET Array to JS Array
        string ReturnValue = String.Empty;
        for (int i = 0; i < chartData.Count; i++)
        {
            ReturnValue += chartData[i];
            if (i != chartData.Count - 1)
                ReturnValue += ",";
        }

        //Data is returned in the following format: 
        //[[1000, 3003.55],[1000, 72.65],[1000, 760.89],[1000, 354.55],[1000, 180.52],[1000, 408.54],[0, 0],[0, 0],[0, 0],[0, 0],[0, 0],[0, 0]]

        hidChartData.Value = String.Format("[{0}]", ReturnValue);
    }
}
