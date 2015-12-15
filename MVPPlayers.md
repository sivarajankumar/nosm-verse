> <br />SL MVP Players
> <br />There are 2 candidate applications forplaying MVP cases on-line: openLabyrinth and Pivote.  They address much of the same needs, though like any functionality-set, having their own strengths and weaknesses. Both are open-source, and are at the leading edge of using the Medbiquitous Virtual Patient standard in a practical, usable environment.
> <br />OpenLabyrinth is a database-driven application, and therefore lends itself to being able to capture real-time data as the scenarios are being completed by users. It uses an elegant visual mapping tool (Vue), which makes (at least partial) case creation quite intuitive. A user can view user telemetry once exercises are complete, and can import/export MVP data, including in XML format.
> <br />Pivote was originally designed by Daden as part of the Preview project, and was conceived specifically for in-world (ie. Second Life) interactivity. It is lightweight, with a very small footprint, and executes faster because of this (also due to the fact that it is written in Perl). Perl-based applications are also inherently more cross-platform than say, ASP.NET. Also, Pivotes cases can currently be played on mobile devices like an iPhone.
> <br />
> In addressing the requirements of this phase of the VERSE project, which, as we understand them, is to run MVP cases in Second Life and capture user telemetry for analysis, we must consider how these two projects can meet the our needs. This has lead us to one simple question: Is it harder to replicate the in-world functionality of Pivote in openLabyrinth, or capturing current openLabyrinth telemetry in Pivote...
> <br />

> Possible Solutions:
> <br />clone/recreate in-world objects (controller, media relay, mannequin, etc) and SL-specific functionality (object assignment in nodes) for openLabyrinth
> <br />use both players, and modify openLabyrinth and PIVOTE to share case data in real-time
> <br />import/export MVP data from/to openLabyrinth and PIVOTE, and capture avatar's MVP data from Second Life by using llHTTPRequest technique (see Telemetrics HUD OneNote in this section) in SL Pivote Controller/HUD to recreate/extend telemetrics reporting/charts found in openLabyrinth.

> <br />Solution Analysis:

> <br />Solution 1
> <br />rewrite pivoteplayer.pl and supporting perl routines for openLabyrinth
> <br />this allows us to leverage existing SL objects and LSL scripts created for the pivote project
> <br />single application, no redundant functionality across multiple solutions
> <br />largest in scope, therefore high-risk of not meeting other VERSE project deliverables

> <br />Solution 2
> <br />re-writing the PIVOTE manager and player to use OL's data in real-time
> <br />much redundant overlap of functionality (case creation, running exercises in browser), that will need to be disabled and/or streamlined
> <br />learning curve for PERL isn't as steep as other languages, but will be a factor, and still needs to be accounted for
> <br />still fairly large in scope, with same inherent risks
> <br />tightly couples OL and Pivote

> <br />Solution 3
> <br />would leverage SL object assignment (ie. paramedic's toolkit) and manipulation (mannequin framework) that comes with PIVOTE, and extending it to capture relative avatar-related MVP telemetry (ie. what the user is doing with/to that mannequin at that node in the exercise)
> <br />since both conform to MVP spec, sharing xml files across both applications is feasible.
> <br />however, there are significant functions the Pivote player does not address:
> <br />no multi-user support on exercises deviates from MVP spec: doesn’t use binary logic for activity node rules
> <br />only uses text VPD data type (no link to diagnoses, interview, analysis, etc)
> <br />uses custom activity & DAM level rules
> <br />uses custom global counter rules
> <br />no graphical editor (ie VUE)
> <br />uses text based XML handling (vs. trees)… should use parser, ie. XML:Parser
> <br />needs better error/exception handling
> <br />PERL vs ASP: different lang = no code re-use across applications
> <br />telemetry data in a DB is much easier to retrieve, manipulate, and display, as opposed to parsing daden's session log
> <br />unlike solution 2, it implies asynchronous case updating, as import/export functions for both openLabyrinth and PIVOTE would create new cases, and not updating existing active ones
> <br />for Pivote export/import of XML can be mimicked at the file system level, but current pivotemgr GUI does not supports it (work in progress)
> <br />loose coupling

> <br />Solution 4
> <br />Create an interceptor page which implements a Façade/Decorator pattern, where:
> <br />the (Pivote-specific) parameters of the incoming HTTP query string from the in-world Pivote controller get transformed into OpenLabyrinth remote service calls (see figure no. 20 of OL User Guide)
> <br />Corresponding OpenLabyrinth remote service (remote.asp, mnode.asp, etc)) gets invoked by the interceptor
> <br />resulting xml gets transformed (ie XSLT) to HTML to be viewed in-world (Pivote Controller)
> <br />Recommendations:
> <br />largely contingent on the standard we want to push here at NOSM
> <br />scope and time being limited, large re-writes should be avoided at all costs
> <br />solution 3 addresses this, and uses a technique we have already tested and implemented.