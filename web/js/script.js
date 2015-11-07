$( document ).ready(function() {
    console.log( "ready!" );

    //the positioning after collapsing an accordion item is not correct
    $('.collapse').on('show.bs.collapse hide.bs.collapse', function(e) {
        e.preventDefault();
    });
    $('[data-toggle="collapse"]').on('click', function(e) {
        e.preventDefault();
        $($(this).data('target')).toggleClass('in');
    });

    //if searchbox is focused and 'Enter' is clicked, simulate button click
    $('#searchbox').keypress(function(event){
		if(event.keyCode == 13){
    		$('#sbtn').click();
	  	}
	});

    $("#sbtn").click(function() {
  		console.log("clicked button. send get request");

  		var query_term = $("#searchbox").val();
  		var results_per_page = 10;
  		var page = 1;
  		var query = 'text:' + query_term;
  		//?q=text:sim&fl=id,patient,therapist,session_number,session_date,score&wt=json&hl=true&hl.snippets=20&hl.fl=content&hl.usePhraseHighlighter=true'
  		
  		//to count the number of times a term appears on the document (a term is a single word! - if multiple terms, they can be calculated separately): 
  		//add to fl: <FIELD_ALIAS>:termfreq(text,'<TERM>')

  		$.ajax({
	    	'url' : 'http://localhost:8983/solr/samh/select',
		    'type' : 'GET',
		    'data' : {
		    	'q' : query_term,
		    	'fl' : 'id,patient,therapist,session_number,session_date',
		    	'wt' : 'json',
		    	'hl' : 'true',
		    	'hl.snippets': 20,
		    	'hl.fl' : 'content',
		    	'hl.usePhraseHighlighter' : 'true',
		    	'rows' : results_per_page,
		    	'start' : (page - 1) * results_per_page
		    },
		    'dataType' : 'jsonp',
		    'jsonp' : 'json.wrf',
		    'success' : function(data) {
		    	console.log('success');
		    	//console.log(JSON.stringify(data));
		    	var results_found = data.response.numFound;
		    	var pages = Math.ceil(results_found / results_per_page); 
		    	console.log("FOUND: " + results_found);
		    	console.log("PAGES: "+ pages);
		    	console.log(data);

		    	//if no documents returned, add "no documents returned"
		    	if(results_found == 0){
		    		$('#no-results').removeClass("hidden");
		    	}else{
		    		$('#no-results').addClass("hidden");
			    	$('#results-accordion').children('.panel').each(function (index, element) {

			    		$(element).addClass("hidden");
			    		if(data.response.docs[index] != undefined){
			    			$(element).removeClass("hidden");
						
			    			//if term, say number of ocurrences
			    			//show score

				    		$(element).find('.patient').text(data.response.docs[index].patient);
				    		$(element).find('.therapist').text(data.response.docs[index].therapist);
				    		$(element).find('.session').text(data.response.docs[index].session_number);
				    		var result_index = (page - 1) * results_per_page + index + 1;
				    		$(element).find('.result-index').text('#' + result_index);
				    		if(data.response.docs[index].session_date != undefined){
				    			$(element).find('.date').text(data.response.docs[index].session_date);
				    		}else{
				    			$(element).find('.date-p').addClass('hidden');
				    		}
				    	}

			    		//$('.panel-title a').text("AAA");
			    		//$('.panel-body').text("BBB");

					});
			    }
	    	},
	    	'error' : function(data) {
		    	console.log('error');
	    	}
	  	});
		
	});
});