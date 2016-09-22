<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title id="titleElement">IBM Bluemix - Next-Generation Cloud App Development Platform</title>
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  
    <style type="text/css">

      /* Sticky footer styles
      -------------------------------------------------- */

      html,
      body {
        height: 100%;
        /* The html and body elements cannot have any padding or margin. */
      }

      /* Wrapper for page content to push down footer */
      #wrap {
        min-height: 100%;
        height: auto !important;
        height: 100%;
		width: 100%;
        /* Negative indent footer by it's height */
        margin: 0px 0px -60px 0px;
      }

      /* Set the fixed height of the footer here */
      #push,
      #footer {
        height: 60px;
      }
      #footer {
        background-color: #f5f5f5;
      }



      /* Custom page CSS
      -------------------------------------------------- */
      /* Not required for template or sticky footer method. */

      #wrap > .container {
        padding-top: 60px;
      }

      code {
        font-size: 80%;
      }
      
    .button1 {
    background-color: #008080; /* Teal */
    border: none;
    color: white;
    padding: 8px 16px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    -webkit-transition-duration: 0.4s; /* Safari */
    transition-duration: 0.4s;
    cursor: pointer;
	}

	.button1:hover {
    background-color: #00cccc;
	}
	
	.assetbox {
		 -webkit-transition-duration: 0.4s; /* Safari */
    	transition-duration: 0.4s;
    	background: linear-gradient(to right, #008080, #00cccc); 
    	height:80px;
    	width:900px
	}
	.assetbox > div > p {
		font-size:110%;
		font-weight:bold;
	}
	.assetbox:hover {
		 background: linear-gradient(to left, #008080, #00cccc); 
	}
	
	.assetbox:hover > div > p {
		 font-size:130%;
	}

    </style>
</head>

<body bgcolor="#FFF34" >
<div id="wrap">

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<table width="100%" height="40">
				<tr width="100%" height="40">
					<td width="100%" height="50" bgcolor="142633">
					<img src="images/bluemix.png" height="50" ></img>
					<font color="white"> - Monitoring Utility </font>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="container">
		
		<h2 align="center"> Welcome to Bluemix Monitor Utilty</h2>
		<h5 align="center">A service which sends daily updates around Bluemix</h5>
		<br/>
	
		<div class="container assetbox" >
			<div style="float:left;padding-top:5px;padding-bottom:5px;" >
		  		<img src="images/IBMBlueMix.jpg" align="left" height="70px" width="250px"> </img>
		  	</div>
		  	<div style="text-align:center;padding-top:30px;">
		  		<p  >It reads changes happening to IBM Bluemix Catalog like Boilerplates, Runtimes and Services</p>
		  	</div>
		</div>
		<br/> 
		<div class="container assetbox" >
			<div style="float:left;padding-top:5px;padding-bottom:5px;" >
		  		<img src="images/announce.jpg" align="left" height="70px" width="250px"> </img>
		  	</div>
		  	<div style="text-align:center;padding-top:30px;">
		  		<p  >It reads announcements made on IBM Bluemix Changes</p>
		  	</div>
		</div>
		<br/>
		
		<div class="container assetbox" >
			<div style="float:left;padding-top:5px;padding-bottom:5px;" >
		  		<img src="images/ibm_dW1.jpg" align="left" height="70px" width="250px"> </img>
		  	</div>
		  	<div style="text-align:center;padding-top:30px;">
		  		<p  >Most recent articles published on IBM DeveloperWorks targeted for IBM Bluemix Users</p>
		  	</div>
		</div>
		<br/>
		
		<div class="container assetbox" >
			<div style="float:left;padding-top:5px;padding-bottom:5px;" >
		  		<img src="images/stackoverflow_logo.jpg" align="left" height="70px" width="250px"> </img>
		  	</div>
		  	<div style="text-align:center;padding-top:30px;">
		  		<p  >Most recent discussions created on StackOverflow community by IBM Bluemix Users</p>
		  	</div>
		</div>		
	</div>
	
		
	<div class="container" style="margin-top:-20px;padding-left:130px">
		<form method="post" action="/monitorservlet">
		  <div style="color: #000000;">${message}</div>
		  
		  <input type="submit" class="button1" name="button" value="Subscribe">
		  <input type="submit" class="button1" name="button" value="Unsubscribe">
	  	</form>
	</div>

</div>
<div id="footer">
	<table width="100%" height="40">
		<tr width="100%" valign="top">
		<tr width="100%" height="40">
			<td align="right" width="100%" height="50" bgcolor="142633">
			<font color="white">Powered by </font>
			<img src="images/bluemix.png" height="50" ></img>
			</td>
		</tr>
	</table>
</div>
</body>

</html>