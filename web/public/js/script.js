$( document ).ready(function() {

  //the positioning after collapsing an accordion item is not correct
  $('.collapse').on('show.bs.collapse hide.bs.collapse', function(e) {
    e.preventDefault();
    var caret_span = $(this).parent().find('.caret-span');
    if(caret_span.hasClass('glyphicon-chevron-down'))
    	caret_span.removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-up");
    else if(caret_span.hasClass('glyphicon-chevron-up'))
    	caret_span.removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
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

		var query = $("#searchbox").val();

		window.location.href = 'http://localhost/search?query=' + query;

		//?q=text:sim&fl=id,patient,therapist,session_number,session_date,score&wt=json&hl=true&hl.snippets=20&hl.fl=content&hl.usePhraseHighlighter=true'
		
		//to count the number of times a term appears on the document (a term is a single word! - if multiple terms, they can be calculated separately): 
		//add to fl: <FIELD_ALIAS>:termfreq(text,'<TERM>')
	});

});