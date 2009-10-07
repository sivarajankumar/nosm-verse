using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

/// <summary>
/// Summary description for Class1
/// </summary>

namespace verse.rules
{
    public class Engine
    {
        public enum EngineTypes { Cumulative, BreakOnFirst }
        protected EngineTypes engineType = EngineTypes.Cumulative;
        public EngineTypes EngineType { get { return engineType; } set { engineType = value; } }
        System.Collections.Hashtable rules = new System.Collections.Hashtable();

        public Engine()
        {

            //load up rules from web.config
            System.Collections.Specialized.NameValueCollection SectionIndex =
                (System.Collections.Specialized.NameValueCollection)
                System.Configuration.ConfigurationSettings.GetConfig("RulesEngine/Index");

            if (SectionIndex != null)
            {
                for (int x = 0; x < SectionIndex.Count; x++)
                {
                    string sectionname = "RulesEngine/" + SectionIndex.Keys[x];
                    string sectiondata = SectionIndex[x];
                    string[] asmdata = sectiondata.Split(',');
                    if (asmdata.Length == 2)
                    {
                        string progid = asmdata[0].Trim();
                        string asmname = asmdata[1].Trim();
                        if (progid != "" && asmname != "")
                        {
                            System.Reflection.Assembly asm = System.Reflection.Assembly.GetExecutingAssembly();
                            verse.rules.IRules rule = (verse.rules.IRules)asm.CreateInstance(progid, true);
                            if (rule != null)
                            {
                                this.AddRule(sectionname, rule);
                            }
                        }
                    }
                }
            }

        }
        public string Execute(string inPath)
        {
            string r = "";
            string newresult = "";
            //tear off the keylist for easy access
            string[] keylist = new string[rules.Keys.Count];
            rules.Keys.CopyTo(keylist, 0);
            verse.rules.IRules rule = null;
            if (rules != null && rules.Count > 0)
            {
                //lets process the first off of the top, in case we only have one item
                rule = (verse.rules.IRules)rules[keylist[0]];
                if (rule != null)
                {
                    //execute it, and return if we need too
                    r = rule.Execute(inPath, (string)keylist[0]);
                    if (engineType == EngineTypes.BreakOnFirst && r != "")
                        return r;
                    else
                    {
                        for (int x = 1; x < rules.Count; x++)
                        {
                            //each rule will simply take the Application and modify the path
                            //it will return the new path to be used for successive rules
                            //this allows you to chain together successive rules
                            rule = (verse.rules.IRules)rules[keylist[x]];
                            //if r is "" then lets set it back to the path again.
                            //each rule can optionally use its value, but it is needed for the simple rule
                            //it is merely used to carry forward the last transformation that was done to the path
                            if (r == "") r = inPath;
                            newresult = rule.Execute(r, (string)keylist[x]);
                            //should we return because we want to break on the first item?
                            if (engineType == EngineTypes.BreakOnFirst && newresult != "")
                            {
                                r = newresult;
                                break;
                            }
                            //make sure r has the most recent value
                            r = (newresult == "") ? r : newresult;
                        }
                    }
                }
                else
                {
                    return r;
                }
            }
            return r;
        }
        /// <summary>
        /// Add a new rule to our engine, its key is the settings section in the web.config
        /// </summary>
        /// <param name="SettingsSection"></param>
        /// <param name="NewRule"></param>
        public void AddRule(string SettingsSection, IRules NewRule)
        {
            if (!rules.ContainsKey(SettingsSection)) rules.Add(SettingsSection, NewRule);
        }
    }
}


