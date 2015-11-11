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

  /*THIS PART OF SCRIPT IS FOR LEXICAL PAGE - SHOULD BE IN SEPARATE FILE...?*/
  //PRIMARY LEVEL
  $("#primary-level-btn").click(function(){

    if($("#primary-level-list").hasClass("hidden"))
      $("#primary-level-list").removeClass("hidden");
    else
      $("#primary-level-list").addClass("hidden");   
  })

  $("#primary-level-list li").click(function(){

    if("elements" in GLOBAL_CSV[$(this).text()]){
      //nao tem niveis inferiores
      $("#global-level-btn").prop('disabled', true);
      $("#intermediate-level-btn").prop('disabled', true);
      $("#specific-level-btn").prop('disabled', false);
    }else{
      $("#global-level-btn").prop('disabled', false);
      $("#intermediate-level-btn").prop('disabled', true);
      $("#specific-level-btn").prop('disabled', true);
    }

    //limpar niveis inferiores
    $("#primary-level-selected").val($(this).text());
    $("#global-level-selected").val("");
    $("#intermediate-level-selected").val("");
    $("#specific-level-selected").val("");

    $("#primary-level-list").addClass("hidden");
  })


  //GLOBAL LEVEL
  $("#global-level-btn").click(function(){

    console.log($("#primary-level-selected").val());
    //só abrir se niveis superiores estão preenchidos!
    if($("#primary-level-selected").val() != ""){
      //encontrar filhos do valor do parent
      var child_object = GLOBAL_CSV[$("#primary-level-selected").val()];

      $("#global-level-list").empty();
      for (var key in child_object) {
        //adicionar key à lista
        $("#global-level-list").append('<li class="list-group-item text-center"><a href="#">' + key + '</a></li>');

        if($("#global-level-list").hasClass("hidden"))
          $("#global-level-list").removeClass("hidden");
        else
          $("#global-level-list").addClass("hidden"); 
      }
    }
  })

  $(document).on("click", "#global-level-list li", function(){

    console.log("A");
    //limpar niveis inferiores
    $("#global-level-selected").val($(this).text());
    $("#intermediate-level-selected").val("");
    $("#specific-level-selected").val("");

    $("#global-level-list").addClass("hidden");

    $("#intermediate-level-btn").prop('disabled', false);
    $("#specific-level-btn").prop('disabled', true);
  })

  //INTERMEDIATE LEVEL
  $("#intermediate-level-btn").click(function(){

    console.log($("#global-level-selected").val());
    //só abrir se niveis superiores estão preenchidos

    if($("#primary-level-selected").val() != "" && $("#global-level-selected").val() != ""){
      //encontrar filhos do valor do parent
      var child_object = GLOBAL_CSV[$("#primary-level-selected").val()][$("#global-level-selected").val()];
      console.log(child_object);

      $("#intermediate-level-list").empty();
      for (var key in child_object) {
        //adicionar key à lista
        $("#intermediate-level-list").append('<li class="list-group-item text-center"><a href="#">' + key + '</a></li>');
      }

      if($("#intermediate-level-list").hasClass("hidden"))
        $("#intermediate-level-list").removeClass("hidden");
      else
        $("#intermediate-level-list").addClass("hidden"); 
    }
  })

  $(document).on("click", "#intermediate-level-list li", function(){

    //limpar niveis inferiores
    $("#intermediate-level-selected").val($(this).text());
    $("#specific-level-selected").val("");

    $("#intermediate-level-list").addClass("hidden");

    $("#specific-level-btn").prop('disabled', false);
  })

  //SPECIFIC LEVEL
  $("#specific-level-btn").click(function(){

    console.log($("#intermediate-level-selected").val());
    //só abrir se niveis superiores estão preenchidos

    if($("#primary-level-selected").val() != "" && $("#global-level-selected").val() != "" && $("#intermediate-level-selected").val() != ""){
      //encontrar filhos do valor do parent
      var child_object = GLOBAL_CSV[$("#primary-level-selected").val()][$("#global-level-selected").val()][$("#intermediate-level-selected").val()];
      console.log(child_object);

      $("#specific-level-list").empty();
      for (var key in child_object) {
        //adicionar key à lista
        $("#specific-level-list").append('<li class="list-group-item text-center"><a href="#">' + key + '</a></li>');
      }

      if($("#specific-level-list").hasClass("hidden"))
        $("#specific-level-list").removeClass("hidden");
      else
        $("#specific-level-list").addClass("hidden"); 
    }
  })

  $(document).on("click", "#specific-level-list li", function(){

    $("#specific-level-selected").val($(this).text());

    $("#specific-level-list").addClass("hidden");
  })

});