
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

			<div class="col-md-6 col-md-offset-3" id="stip" style="display: block; padding:0px;">
				<i class="icon-lightbulb icon-large"></i> <b>TIP</b> - Use keywords separated by
				<b>space</b>, example: "<span class="keyword"><b>tristeza dor</b></span>"
				<!-- Button trigger modal -->
				<p class="pull-right">
					Patient Filter:
					<a data-toggle="modal" data-target="#myModal" class="patient-label" style="cursor: pointer;">
						<?php echo isset($_GET['patient']) ? $_GET['patient'] : 'None'; ?>
					</a>
				</p>
			</div>
		</div>
		<br>
		<div class="row">
			<h2 class="text-center hh1 col-md-10 col-md-offset-1">Find documents, patients &amp; feelings!</h2>
		</div>
		<br>
		<br>
	</div>
</div><!-- /.container -->

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="z-index:1500">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel">Patient List</h4>
			</div>
			<div class="modal-body" style="padding-bottom: 0px;">

				<div style="width: 100%">
					<ul class="list-group" style="max-height: 300px;overflow-y:auto; color: #000; padding-top: 1px;">
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">None</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P003</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P010</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P011</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P013</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P019</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P021</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P026</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P027</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P056</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P075</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P152</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P172</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P177</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P186</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P199</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P210</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P213</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P342</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P412</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P413</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P433</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P443</a></li>
						<li class="list-group-item text-center"><a class="patient-id" data-dismiss="modal" href="javascript:void(0)">P465</a></li>
					</ul>
				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>