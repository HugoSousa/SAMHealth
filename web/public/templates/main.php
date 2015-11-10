<!-- 
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	<html>
<head><meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>SAMHealth</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/custom.css" rel="stylesheet">

    <!-- Just for debugging purposes. Dont actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <!--<script src="../../assets/js/ie-emulation-modes-warning.js"></script>-->

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
<body>

    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" 
aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#"><span style="color:#008cba"><b>SAM</span>H</b>ealth</a>
        </div>
		
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

    <div class="container container-search">

	  <div class="row search-row">
    <br><br>
		<div class="row">
			<h1 class="text-center hh1 col-md-10 col-md-offset-1">Search for documents...</h1>
		</div>
		<br>
		<div class="row">

		<div class="input-group input-group-lg col-md-6 col-md-offset-3">
		<input id="searchbox" autofocus="" type="text" class="form-control">
		<span class="input-group-btn">
			<button id="sbtn" class="btn btn-primary" type="button">
			Locate
			</button> 
			</span>
		</div>
				<p class="col-md-6 col-md-offset-3" id="stip" style="display: block;">
		      <i class="icon-lightbulb icon-large"></i> <b>TIP</b> - Use keywords separated by
		      <b>space</b>, example: "<span class="keyword"><b>tristeza dor</b></span>"
		    </p>
		</div>
		<br>
		<div class="row">
		<h2 class="text-center hh1 col-md-10 col-md-offset-1">Find documents, patients &amp; feelings!</h2>
		</div>
		<br>
		<br>
		</div>

    </div><!-- /.container -->

    
    <div class="container container-results">

      <h3 id="no-results" class="hidden"> No results found! </h3>

      <div class="panel-group" id="results-accordion">

        <!-- result 1 -->
        <div class="panel panel-default hidden" id="panel1">
          <div class="panel-heading">
            <h4 class="panel-title">
              <a data-toggle="collapse" data-target="#collapseOne" href="#collapseOne">
                <div class="container-fluid">
                  <div class="row">
                    <div class="col-md-10">
                      <div class="row">
                        <p class="col-md-4">Patient: <span class="patient"></span></p>
                        <p class="col-md-4">Therapist: <span class="therapist"></span></p>
                      </div>
                      <div class="row">
                        <p class="col-md-4">Session: <span class="session"></span></p>
                        <p class="col-md-4 date-p">Date: <span class="date"></span></p>
                      </div>
                    </div>
                    <div class="col-md-2">
                      <div class="row">
                        <p class="result-index"></p>
                      </div>
                      <div class="row">
                        <p class="caret-span"></p>
                      </div>
                    </div>
                  </div>
                </div>     
              </a>
            </h4>
          </div>

          <div id="collapseOne" class="panel-collapse collapse">
            <div class="panel-body">
              <p class="score">score: X</p>
              <p>X ocurrences found on this file</p>
              <ul>
                <li>exemplo <em>highlight</em> 1</li>
                <li>exemplo highlight 2</li>
              </ul>
              <a href="#">Download the document</a>
            </div>
          </div>
        </div>

        <!-- result 2 -->
        <div class="panel panel-default hidden" id="panel2">
          <div class="panel-heading">
            <h4 class="panel-title">
              <a data-toggle="collapse" data-target="#collapseTwo" href="#collapseTwo">
                <div class="container-fluid">
                  <div class="row">
                    <div class="col-md-10">
                      <div class="row">
                        <p class="col-md-4">Patient: <span class="patient"></span></p>
                        <p class="col-md-4">Therapist: <span class="therapist"></span></p>
                      </div>
                      <div class="row">
                        <p class="col-md-4">Session: <span class="session"></span></p>
                        <p class="col-md-4 date-p">Date: <span class="date"></span></p>
                      </div>
                    </div>
                    <div class="col-md-2">
                      <div class="row">
                        <p class="result-index"></p>
                      </div>
                      <div class="row">
                        <p class="caret-span"></p>
                      </div>
                    </div>
                  </div>
                </div>     
              </a>
            </h4>
          </div>

          <div id="collapseTwo" class="panel-collapse collapse">
            <div class="panel-body">
              <p class="score">score: X</p>
              <p>X ocurrences found on this file</p>
              <ul>
                <li>exemplo highlight 1</li>
                <li>exemplo highlight 2</li>
              </ul>
              <a href="#">Download the document</a>
            </div>
        </div>


        <!-- result 3 -->
        <div class="panel panel-default hidden" id="panel3">
          <div class="panel-heading">
            <h4 class="panel-title">
              <a data-toggle="collapse" data-target="#collapseThree" href="#collapseThree">
                <div class="container-fluid">
                  <div class="row">
                    <div class="col-md-10">
                      <div class="row">
                        <p class="col-md-4">Patient: <span class="patient"></span></p>
                        <p class="col-md-4">Therapist: <span class="therapist"></span></p>
                      </div>
                      <div class="row">
                        <p class="col-md-4">Session: <span class="session"></span></p>
                        <p class="col-md-4 date-p">Date: <span class="date"></span></p>
                      </div>
                    </div>
                    <div class="col-md-2">
                      <div class="row">
                        <p class="result-index"></p>
                      </div>
                      <div class="row">
                        <p class="caret-span"></p>
                      </div>
                    </div>
                  </div>
                </div>     
              </a>
            </h4>
          </div>

          <div id="collapseThree" class="panel-collapse collapse">
            <div class="panel-body">
              <p class="score">score: X</p>
              <p>X ocurrences found on this file</p>
              <ul>
                <li>exemplo highlight 1</li>
                <li>exemplo highlight 2</li>
              </ul>
              <a href="#">Download the document</a>
            </div>
        </div>

        <!-- result 4 -->
        <div class="panel panel-default hidden" id="panel4">
          <div class="panel-heading">
            <h4 class="panel-title">
              <a data-toggle="collapse" data-target="#collapseFour" href="#collapseFour">
                <div class="container-fluid">
                  <div class="row">
                    <div class="col-md-10">
                      <div class="row">
                        <p class="col-md-4">Patient: <span class="patient"></span></p>
                        <p class="col-md-4">Therapist: <span class="therapist"></span></p>
                      </div>
                      <div class="row">
                        <p class="col-md-4">Session: <span class="session"></span></p>
                        <p class="col-md-4 date-p">Date: <span class="date"></span></p>
                      </div>
                    </div>
                    <div class="col-md-2">
                      <div class="row">
                        <p class="result-index"></p>
                      </div>
                      <div class="row">
                        <p class="caret-span"></p>
                      </div>
                    </div>
                  </div>
                </div>     
              </a>
            </h4>
          </div>

          <div id="collapseFour" class="panel-collapse collapse">
            <div class="panel-body">
              <p class="score">score: X</p>
              <p>X ocurrences found on this file</p>
              <ul>
                <li>exemplo highlight 1</li>
                <li>exemplo highlight 2</li>
              </ul>
              <a href="#">Download the document</a>
            </div>
        </div>

        <!-- result 5 -->
        <div class="panel panel-default hidden" id="panel5">
          <div class="panel-heading">
            <h4 class="panel-title">
              <a data-toggle="collapse" data-target="#collapseFive" href="#collapseFive">
                <div class="container-fluid">
                  <div class="row">
                    <div class="col-md-10">
                      <div class="row">
                        <p class="col-md-4">Patient: <span class="patient"></span></p>
                        <p class="col-md-4">Therapist: <span class="therapist"></span></p>
                      </div>
                      <div class="row">
                        <p class="col-md-4">Session: <span class="session"></span></p>
                        <p class="col-md-4 date-p">Date: <span class="date"></span></p>
                      </div>
                    </div>
                    <div class="col-md-2">
                      <div class="row">
                        <p class="result-index"></p>
                      </div>
                      <div class="row">
                        <p class="caret-span"></p>
                      </div>
                    </div>
                  </div>
                </div>     
              </a>
            </h4>
          </div>

          <div id="collapseFive" class="panel-collapse collapse">
            <div class="panel-body">
              <p class="score">score: X</p>
              <p>X ocurrences found on this file</p>
              <ul>
                <li>exemplo highlight 1</li>
                <li>exemplo highlight 2</li>
              </ul>
              <a href="#">Download the document</a>
            </div>
        </div>

        <!-- result 6 -->
        <div class="panel panel-default hidden" id="panel6">
          <div class="panel-heading">
            <h4 class="panel-title">
              <a data-toggle="collapse" data-target="#collapseSix" href="#collapseSix">
                <div class="container-fluid">
                  <div class="row">
                    <div class="col-md-10">
                      <div class="row">
                        <p class="col-md-4">Patient: <span class="patient"></span></p>
                        <p class="col-md-4">Therapist: <span class="therapist"></span></p>
                      </div>
                      <div class="row">
                        <p class="col-md-4">Session: <span class="session"></span></p>
                        <p class="col-md-4 date-p">Date: <span class="date"></span></p>
                      </div>
                    </div>
                    <div class="col-md-2">
                      <div class="row">
                        <p class="result-index"></p>
                      </div>
                      <div class="row">
                        <p class="caret-span"></p>
                      </div>
                    </div>
                  </div>
                </div>     
              </a>
            </h4>
          </div>

          <div id="collapseSix" class="panel-collapse collapse">
            <div class="panel-body">
              <p class="score">score: X</p>
              <p>X ocurrences found on this file</p>
              <ul>
                <li>exemplo highlight 1</li>
                <li>exemplo highlight 2</li>
              </ul>
              <a href="#">Download the document</a>
            </div>
        </div>

        <!-- result 7 -->
        <div class="panel panel-default hidden" id="panel7">
          <div class="panel-heading">
            <h4 class="panel-title">
              <a data-toggle="collapse" data-target="#collapseSeven" href="#collapseSeven">
                <div class="container-fluid">
                  <div class="row">
                    <div class="col-md-10">
                      <div class="row">
                        <p class="col-md-4">Patient: <span class="patient"></span></p>
                        <p class="col-md-4">Therapist: <span class="therapist"></span></p>
                      </div>
                      <div class="row">
                        <p class="col-md-4">Session: <span class="session"></span></p>
                        <p class="col-md-4 date-p">Date: <span class="date"></span></p>
                      </div>
                    </div>
                    <div class="col-md-2">
                      <div class="row">
                        <p class="result-index"></p>
                      </div>
                      <div class="row">
                        <p class="caret-span"></p>
                      </div>
                    </div>
                  </div>
                </div>     
              </a>
            </h4>
          </div>

          <div id="collapseSeven" class="panel-collapse collapse">
            <div class="panel-body">
              <p class="score">score: X</p>
              <p>X ocurrences found on this file</p>
              <ul>
                <li>exemplo highlight 1</li>
                <li>exemplo highlight 2</li>
              </ul>
              <a href="#">Download the document</a>
            </div>
        </div>

        <!-- result 8 -->
        <div class="panel panel-default hidden" id="panel8">
          <div class="panel-heading">
            <h4 class="panel-title">
              <a data-toggle="collapse" data-target="#collapseEight" href="#collapseEight">
                <div class="container-fluid">
                  <div class="row">
                    <div class="col-md-10">
                      <div class="row">
                        <p class="col-md-4">Patient: <span class="patient"></span></p>
                        <p class="col-md-4">Therapist: <span class="therapist"></span></p>
                      </div>
                      <div class="row">
                        <p class="col-md-4">Session: <span class="session"></span></p>
                        <p class="col-md-4 date-p">Date: <span class="date"></span></p>
                      </div>
                    </div>
                    <div class="col-md-2">
                      <div class="row">
                        <p class="result-index"></p>
                      </div>
                      <div class="row">
                        <p class="caret-span"></p>
                      </div>
                    </div>
                  </div>
                </div>     
              </a>
            </h4>
          </div>

          <div id="collapseEight" class="panel-collapse collapse">
            <div class="panel-body">
              <p class="score">score: X</p>
              <p>X ocurrences found on this file</p>
              <ul>
                <li>exemplo highlight 1</li>
                <li>exemplo highlight 2</li>
              </ul>
              <a href="#">Download the document</a>
            </div>
        </div>

        <!-- result 9 -->
        <div class="panel panel-default hidden" id="panel9">
          <div class="panel-heading">
            <h4 class="panel-title">
              <a data-toggle="collapse" data-target="#collapseNine" href="#collapseNine">
                <div class="container-fluid">
                  <div class="row">
                    <div class="col-md-10">
                      <div class="row">
                        <p class="col-md-4">Patient: <span class="patient"></span></p>
                        <p class="col-md-4">Therapist: <span class="therapist"></span></p>
                      </div>
                      <div class="row">
                        <p class="col-md-4">Session: <span class="session"></span></p>
                        <p class="col-md-4 date-p">Date: <span class="date"></span></p>
                      </div>
                    </div>
                    <div class="col-md-2">
                      <div class="row">
                        <p class="result-index"></p>
                      </div>
                      <div class="row">
                        <p class="caret-span"></p>
                      </div>
                    </div>
                  </div>
                </div>     
              </a>
            </h4>
          </div>

          <div id="collapseNine" class="panel-collapse collapse">
            <div class="panel-body">
              <p class="score">score: X</p>
              <p>X ocurrences found on this file</p>
              <ul>
                <li>exemplo highlight 1</li>
                <li>exemplo highlight 2</li>
              </ul>
              <a href="#">Download the document</a>
            </div>
        </div>

        <!-- result 10 -->
        <div class="panel panel-default hidden" id="panel10">
          <div class="panel-heading">
            <h4 class="panel-title">
              <a data-toggle="collapse" data-target="#collapseTen" href="#collapseTen">
                <div class="container-fluid">
                  <div class="row">
                    <div class="col-md-10">
                      <div class="row">
                        <p class="col-md-4">Patient: <span class="patient"></span></p>
                        <p class="col-md-4">Therapist: <span class="therapist"></span></p>
                      </div>
                      <div class="row">
                        <p class="col-md-4">Session: <span class="session"></span></p>
                        <p class="col-md-4 date-p">Date: <span class="date"></span></p>
                      </div>
                    </div>
                    <div class="col-md-2">
                      <div class="row">
                        <p class="result-index"></p>
                      </div>
                      <div class="row">
                        <p class="caret-span"></p>
                      </div>
                    </div>
                  </div>
                </div>     
              </a>
            </h4>
          </div>

          <div id="collapseTen" class="panel-collapse collapse">
            <div class="panel-body">
              <p class="score">score: X</p>
              <p>X ocurrences found on this file</p>
              <ul>
                <li>exemplo highlight 1</li>
                <li>exemplo highlight 2</li>
              </ul>
              <a href="#">Download the document</a>
            </div>
        </div>


      </div>
    </div>


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/script.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <!--<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>-->
  

</body>
 
 </html>