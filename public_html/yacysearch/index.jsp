<html>
<body>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@page import="IRUtilities.*" %>
<%@page import="java.util.*" %>
  <div class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container-fluid">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a id="homepage" class="navbar-brand" href="http://yacy.net" style="position:absolute;top:-6px;display:inline;white-space:nowrap;">
          <img id="logo" class="yacylogo" src="../images/YaCyLogo2011_60.png" style="height:auto; width:auto; max-width:200px; max-height:32px;vertical-align:middle">
        </a>
        <span id="topmenu" style="position:absolute;top:12px;left:80px;display:inline;white-space:nowrap;font-size:2em;"></span>
      </div>
      <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav navbar-right">
          <li id="header_help" class="dropdown">
            <a href="#" data-toggle="dropdown" class="dropdown-toggle"><span class="glyphicon glyphicon-question-sign"></span></a>
            <ul class="dropdown-menu">
              <li id="header_profile"><a href="index.html?">Search Page</a></li>
              <li id="header_profile"><a href="about.html?">About This Page</a></li>
              <li id="header_profile"><a href="usage.html?">Usage of this page templates</a></li>
              <li id="header_tutorial"><a href="http://yacy.net/tutorials/">YaCy Tutorials</a></li>
              <li class="divider"></li>
              <li id="header_tutorial"><a href="http://yacy.net" target="_blank"><i>external</i>&nbsp;&nbsp;&nbsp;Download YaCy</a></li>
              <li id="header_tutorial"><a href="http://forum.yacy.de" target="_blank"><i>external</i>&nbsp;&nbsp;&nbsp;Community (Web Forums)</a></li>
              <li id="header_tutorial"><a href="http://wiki.yacy.de" target="_blank"><i>external</i>&nbsp;&nbsp;&nbsp;Project Wiki</a></li>
              <li id="header_tutorial"><a href="https://github.com/yacy/yacy_search_server/commits/master" target="_blank"><i>external</i>&nbsp;&nbsp;&nbsp;Git Repository</a></li>
              <li id="header_tutorial"><a href="http://bugs.yacy.net" target="_blank"><i>external</i>&nbsp;&nbsp;&nbsp;Bugtracker</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </div>
<%

if(request.getParameter("query")!=null)
{
	out.println("The results are:<hr/>");
	String string1 = request.getParameter("query");

    String[] str1 = string1.split(" ");
	List<String> list = Arrays.asList(str1);
    Vector<String> vector = new Vector<String>(list);

    SearchEngine se = new SearchEngine();
	Vector<Link> result = se.search(vector);
	if(result.size() > 0){
		out.println("<table>");
		
		for(int i = 0; i < result.size(); i++){
			Link temp = result.elementAt(i);
			out.println("<tr><td valign=\"top\">"+temp.getScore()+"</td>");
			out.println("<td>");
			out.println("<a href=\""+temp.getUrl()+"\"> "+temp.getTitle()+"</a><br/>");
			out.println("<a href=\""+temp.getUrl()+"\"> "+temp.getUrl()+"</a><br/>");
			out.println(temp.getLastUpdate()+", "+temp.getPageSize()+"<br/>");
			Vector<Vocab> v1 = temp.getKeywords();
			for(int j = 0; j < v1.size(); j++){
				out.print(v1.elementAt(j).getText()+" "+v1.elementAt(j).getFreq()+"; ");
				if(j==4){
					out.println("<br/>");
					break;
				}
			}
			Vector<String> p = temp.getParentLink();
			for(int j = 0; j< p.size(); j++){
				out.println(p.elementAt(j)+"<br/>");
			}
			Vector<String> c = temp.getChildLink();
			for(int j = 0; j< c.size(); j++){
                out.println(c.elementAt(j)+"<br/>");
            }
			out.println("<br/></td></tr>");
			if(i >= 30)break;
		}
		out.println("</table>");
	}else{
		out.println("No match result");
	}
}
else
{
	out.println("You input nothing");
}

%>
</body>
</html>