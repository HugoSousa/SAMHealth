
<div class="container container-search">

  <div class="row search-row">
<br><br>
	<div class="row">
		<h1 class="text-center hh1 col-md-10 col-md-offset-1">Search for documents...</h1>
	</div>
	<br>
	<div class="row">

	<div class="input-group input-group-lg col-md-6 col-md-offset-3">
	<input id="searchbox" autofocus="" type="text" class="form-control" value="<?php echo isset($_GET['query']) ? $_GET['query'] : ''; ?>">
	<span class="input-group-btn">
		<button id="sbtn" class="btn btn-primary" type="button">
		Search
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