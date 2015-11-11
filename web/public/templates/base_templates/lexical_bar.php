<div class="container container-search">
  	<div class="row search-row">
		<br><br>
		
		<div class="row">
			<h1 class="text-center hh1 col-md-10 col-md-offset-1">Search for documents...</h1>
		</div>
		
		<br>
		
		<div class="row">

			<div class="col-md-6">
				<div class="row" style="margin-left: 5%">
					<div class="col-md-4 text-center">Nível Primário</div>
					<div class="col-md-8 text-center">
						<div class="input-group">
					      	<input id="primary-level-selected" type="text" class="form-control" disabled>
					      	<span class="input-group-btn">
					        	<button class="btn btn-default" id="primary-level-btn">
					        		<span class="glyphicon glyphicon-list" style="line-height: inherit"></span>
					        	</button>
					      	</span>
					    </div>
					</div>
				</div>
				
				<div class="row" style="margin-left: 5%">
					<div class="col-md-4 text-center">Nível Global</div>
					<div class="col-md-8 text-center">
						<div class="input-group">
					      	<input id="global-level-selected" type="text" class="form-control" disabled>
					      	<span class="input-group-btn">
					        	<button class="btn btn-default" id="global-level-btn">
					        		<span class="glyphicon glyphicon-list" style="line-height: inherit"></span>
					        	</button>
					      	</span>
					    </div>
					</div>
				</div>

				<div class="row" style="margin-left: 5%">
					<div class="col-md-4 text-center">Nível Intermédio</div>
					<div class="col-md-8 text-center">
						<div class="input-group">
					      	<input id="intermediate-level-selected" type="text" class="form-control" disabled>
					      	<span class="input-group-btn">
					        	<button class="btn btn-default" id="intermediate-level-btn">
					        		<span class="glyphicon glyphicon-list" style="line-height: inherit"></span>
					        	</button>
					      	</span>
					    </div>
					</div>
				</div>

				<div class="row" style="margin-left: 5%">
					<div class="col-md-4 text-center">Nível Específico</div>
					<div class="col-md-8 text-center">
						<div class="input-group">
					      	<input id="specific-level-selected" type="text" class="form-control" disabled>
					      	<span class="input-group-btn">
					        	<button class="btn btn-default" id="specific-level-btn">
					        		<span class="glyphicon glyphicon-list" style="line-height: inherit"></span>
					        	</button>
					      	</span>
					    </div>
					</div>
				</div>
			</div>

			<div class="col-md-6" style="max-height: 300px; overflow-y:auto; ">
				<ul id="primary-level-list" class="list-group hidden" style="margin:0; padding: 0; color: #000">
					<?php 
						foreach($csv as $key => $value){
							echo '<li class="list-group-item text-center"><a href="#">'.$key.'</a></li>';
						}
					?>
				</ul>
				<ul id="global-level-list" class="list-group hidden" style="margin:0; padding: 0">
				</ul>
				<ul id="intemerdiate-level-list" class="list-group hidden" style="margin:0; padding: 0"></ul>
				<ul id="specific-level-list" class="list-group hidden" style="margin:0; padding: 0"></ul>
			</div>

		</div>
		
		<br>
		
		<div class="row">
			<div class="col-md-12 text-center">
				<button class="btn btn-primary" type="button" style="width:40%">Search</button>
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