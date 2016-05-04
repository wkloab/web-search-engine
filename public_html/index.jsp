<html>
    <head>
  <title id="title"></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=10"/>
  <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico" />
  <meta name="Content-Language" content="English, Englisch" />
  <meta name="keywords" content="YaCy HTTP search engine spider indexer java network open free download Mac Windows Linux Software development" />
  <meta name="description" content="Software HTTP Freeware Home Page" />
  <meta name="copyright" content="Michael Christen et al." />

  <link href=" bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <script src=" bootstrap/js/jquery.min.js"></script>
  <script src=" bootstrap/js/bootstrap.min.js"></script>
  <script src=" bootstrap/js/docs.min.js"></script>

  <script src="navigation.js" type="text/javascript"></script>
  <script src=" js/lib/underscore-min.js"></script>
  <script src=" js/lib/backbone-min.js"></script>
  <script src=" js/setup.js" type="text/javascript"></script> <!-- customization -->
  <script src=" js/yacysearch.js" type="text/javascript"></script>


  <link href=" css/bootstrap-base.css" rel="stylesheet">


  <link rel="stylesheet" type="text/css" media="all" href=" css/base.css" />
  <link rel="stylesheet" type="text/css" media="screen" href=" css/style.css" />


  <script type="text/javascript">
  function handleArrowKeys(evt) {
    evt = (evt) ? evt : ((window.event) ? event : null);
    if (evt) {
      switch (evt.keyCode) {
        case 9:
        case 33:
          window.location.href = document.getElementById("nextpage").href;
          break;
        case 34:
          window.location.href = document.getElementById("prevpage").href;
          break;
        case 40:
      }
    }
  }
  document.onkeydown = handleArrowKeys;
  </script>
  <script type="text/javascript" src=" bootstrap/js/typeahead.jquery.min.js"></script>
  <script type="text/javascript">
     var suggestMatcher = function() {
       return function opensearch(q, cb) {
            $.getJSON(suggestUrl + "&q="+ q, function(data) {
               var parsed = [];
            for (var i = 0; i < data[0][1].length; i++) {
                var row = data[0][1][i];
                if (row) {
                    parsed[parsed.length] = {
                        data: [row],
                        value: row,
                        result: row
                    };
                };
            };
            cb(parsed);
          });
        };
      };
    $(document).ready(function() {
      $('#query').typeahead({hint:false,highlight:true,minLength:1}, {
        name: 'states',
        displayKey: 'value',
        source: suggestMatcher()
      });
    });
  </script>
  <style type="text/css">.twitter-typeahead {margin: 0px;padding: 0px;top:2px;}</style> <!-- fix for input window -->
</head>
<body id="yacysearch" onLoad="document.searchform.query.focus();">

  <!-- top navigation -->
  <div class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container-fluid">
      <div class="navbar-header">
<!--        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>-->
        <a id="homepage" class="navbar-brand" href="index.html" style="position:absolute;top:-6px;display:inline;white-space:nowrap;">
          <img id="logo" class="yacylogo" src=" images/YaCyLogo2011_60.png" style="height:auto; width:auto; max-width:200px; max-height:32px;vertical-align:middle">
        </a>
        <span id="topmenu" style="position:absolute;top:12px;left:80px;display:inline;white-space:nowrap;font-size:2em;"></span>
      </div>
    </div>
  </div>
  
    <div class="container-fluid">
    <div class="starter-template">
      <!-- body -->
      <%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@page import="IRUtilities.*" %>
<%@page import="java.util.*" %>
<%
    if(request.getParameter("query")!=null)
    {
            out.println("<h4>Results:</<h4><hr/>");
            String string1 = request.getParameter("query");

        String[] str1 = string1.split(" ");
    //        out.println(Arrays.toString(str1));
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
    </div> <!-- close the starter-template, to open a row -->

    <div class="row">
      <div class="col-sm-8 col-sm-offset-4 col-md-9 col-md-offset-3 main" id="main">
        <form class="search small" name="searchform" action="index.jsp" method="post" accept-charset="UTF-8" style="position:fixed;top:8px;z-index:1052;max-width:500px;">
          <div class="input-group">
            <input name="query" id="query" type="text" class="form-control searchinput typeahead" size="40" maxlength="200" value="" autofocus="autofocus" onFocus="this.select()" onclick="document.getElementById('Enter').innerHTML = 'search'"/>
            <div class="input-group-btn">
              <button id="Enter" name="Enter" class="btn btn-default" type="submit">search</button>
            </div>
          </div>
          <input type="hidden" name="nav" value="filetype,protocol,hosts,language,authors,namespace,topics" />
          <input type="hidden" name="maximumRecords" id="maximumRecords" value="10" />
          <input type="hidden" name="startRecord" id="startRecord" value="0" />
          <input type="hidden" name="layout" id="layout" value="paragraph" />
          <input type="hidden" name="contentdom" id="contentdom" value="text" />
          <input id="timezoneOffset" type="hidden" name="timezoneOffset" value=""><script>document.getElementById("timezoneOffset").value = new Date().getTimezoneOffset();</script>
        </form>

        <!-- type the number of results and navigation bar -->
<!--        <div id="results"></div>
        <div class="progress" id="progress">
          <div class="progress-bar progress-bar-info" id="progressbar" role="progressbar" style="width:0%;">
            <span style="position:absolute;padding-left:10px;display:block;text-align:left;width:100%;color:black;" id="progressbar_text"></span>
          </div>
        </div>-->
        <script>
        if (document.getElementById("progressbar").getAttribute('class') != "progress-bar progress-bar-success") {
          document.getElementById("progressbar").setAttribute('style',"width:100%");
          document.getElementById("progressbar").setAttribute('style',"transition:transform 0s;-webkit-transition:-webkit-transform 0s;");
          document.getElementById("progressbar").setAttribute('class',"progress-bar progress-bar-success");
          window.setTimeout(fadeOutBar, 500);
        }
        </script>

        <!-- linklist begin -->
        <div id="downloadscript" style="clear:both;"></div>
        <div id="searchresults" style="clear:both;"></div>
        <!-- linklist end -->

        <span id="resNav" class="col-sm-12  col-md-12" style="display: inline;"></span>
      </div> <!-- close main -->

<!--      <div class="col-sm-4 col-md-3 sidebar" id="sidebar" id="sidebar">
         navigation begin 
        <p class="navbutton">
        </p>

        <div id="searchnavigation"></div>
        <div id="downloadbutton"></div>

         navigation end 

      </div>  close sidebar -->
    </div> <!-- close row -->
  </div> <!-- close content container -->
  
  

</body>
</html>