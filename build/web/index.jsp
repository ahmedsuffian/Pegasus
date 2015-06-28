<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Pegasus</title>
        <link href="templatemo_style.css" rel="stylesheet" type="text/css" />
        <link href="css/jquery.ennui.contentslider.css" rel="stylesheet" type="text/css" media="screen,projection" />
        <script language="javascript" type="text/javascript">
            function clearText(field)
            {
                if (field.defaultValue == field.value)
                    field.value = '';
                else if (field.value == '')
                    field.value = field.defaultValue;
            }
        </script>
        
        <script>/*
            $(document).ready(function () {
            $('#loginForm').on('submit', function(e) {
                    e.preventDefault();
                    $.ajax({
                        url : $(this).attr('action') || window.location.pathname,
                        type: "GET",
                        data: $(this).serialize(),
                        success: function (data) {
                            $("#form_output").html(data);
                        },
                        error: function (jXHR, textStatus, errorThrown) {
                            alert(errorThrown);
                        }
                    });
                });
            });*/
        //}); // Click effect     
            
        </script>
    </head>
    <body>

        <div id="templatemo_wrapper">

            <div id="templatemo_header">

                <div id="site_title">
                    <h1><a href="#" target="_parent">
                            <img src="images/logo.PNG" alt="Site Title" width="200" height="50" />
                            <span>Security you need</span>
                        </a></h1>
                </div>

                <div id="search_box">
                    <form action="#" method="get">
                        <input type="text" value="Enter a keyword here..." name="q" size="10" id="searchfield" title="searchfield" onfocus="clearText(this)" onblur="clearText(this)" />
                        <input type="submit" name="Search" value="Search" alt="Search" id="searchbutton" title="Search" />
                    </form>
                </div>

                <div class="cleaner"></div>
            </div> <!-- end of header -->


            <div id="templatemo_menu">

                <ul>
                    <li><a href="index.html" class="current">Home</a></li>
                </ul>       

            </div> <!-- end of templatemo_menu -->

            <div id="templatemo_content_wrapper"><span class="top"></span><span class="bottom"></span>
                <div id="templatemo_content" style = "padding:20px">

                    <h2>Pegasus Admin's panel</h2>

                    <form action="MainServlet" method="post" id="loginForm">
                        <input type="text" value="Login ID" name="loginID" size="10" id="searchfield" title="searchfield" onfocus="clearText(this)" onblur="clearText(this)" style = "padding: 2%"/>

                        <br />

                        <input type="password" value="Password" name="loginPwd" size="10" id="searchfield" title="searchfield" onfocus="clearText(this)" onblur="clearText(this)" style = "padding: 2%" />

                        <br />

                        <br />
                        
                        <% 
                            String userName = null;
                            Cookie [] cookies =  request.getCookies ();
                            if (cookies != null) {
                                for (Cookie cookie: cookies)
                                    if (cookie.getName().equals ("user"))
                                        userName = cookie.getValue ();
                                
                            }
                        
                        %>
                        
                        <input type="submit" name="login" value="Login" alt="Login" id="loginButton" title="Search" />
                    </form>
                    <span id ="form_output"> </span>

                </div> <!-- end of templatemo_content -->

                <div id="templatemo_sidebar">

                    <div class="section_rss_twitter">

                        <div class="rss_twitter twitter">
                            <a href="http://www.templatemo.com/page/1" target="_parent">FOLLOW US <span>on Twitter</span></a>
                        </div>

                        <div class="margin_bottom_20"></div>

                        <div class="rss_twitter rss">
                            <a href="http://www.templatemo.com/page/2" target="_parent">SUBSCRIBE <span>our feed</span></a>
                        </div>

                    </div>


                    <div class="cleaner"></div>
                </div>

                <div class="cleaner"></div>
            </div> <!-- end of content_wrapper -->

            <div id="templatemo_footer">

                <ul class="footer_menu">
                    <li><a href="index.html">Home</a></li>
                </ul>

                Copyright ?? 2048 <a href="#">Your Company Name</a> | 
                <a href="http://www.iwebsitetemplate.com" target="_parent">Website Templates</a> by <a href="http://www.templatemo.com" target="_parent">Free CSS Templates</a>

            </div> <!-- end of footer -->

        </div> <!-- end of wrapper -->

        <div align=center>This template  downloaded form <a href='http://all-free-download.com/free-website-templates/'>free website templates</a></div></body>
</html>