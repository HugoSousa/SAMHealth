$( document ).ready(function(){
  $("#lexical_button").prop('disabled',true);

  if ($('#primary-level-selected').val() != ""){
     // TODO 3 simple levels
     $('#global-level-btn').prop('disabled',false);
     $("#lexical_button").prop('disabled',false);

  }
  if ($('#global-level-selected').val() != "") $('#intermediate-level-btn').prop('disabled',false);
  if ($('#intermediate-level-selected').val() != "") $('#specific-level-btn').prop('disabled',false);

  /*THIS PART OF SCRIPT IS FOR LEXICAL PAGE - SHOULD BE IN SEPARATE FILE...?*/
  //PRIMARY LEVEL
  $("#primary-level-btn").click(function(){

    $("#primary-level-list").siblings().addClass("hidden");

    if($("#primary-level-list").hasClass("hidden"))
      $("#primary-level-list").removeClass("hidden");
    else
      $("#primary-level-list").addClass("hidden");   

    //se existe botao sibling not hidden, adicionar hidden

  })

  $("#primary-level-list li").click(function(){
      $("#lexical_button").prop('disabled',false);

    if("elements" in GLOBAL_CSV[$(this).text()]){
      //nao tem niveis inferiores
      $("#global-level-btn").prop('disabled', true);
      $("#intermediate-level-btn").prop('disabled', true);
      $("#specific-level-btn").prop('disabled', true);
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

    if($("#global-level-list").hasClass("hidden")){
      $("#global-level-list").removeClass("hidden");
    }
    else{
      $("#global-level-list").addClass("hidden"); 
    }

    $("#global-level-list").siblings().addClass("hidden");

    console.log($("#primary-level-selected").val());
    //só abrir se niveis superiores estão preenchidos!
    if($("#primary-level-selected").val() != ""){
      //encontrar filhos do valor do parent
      var child_object = GLOBAL_CSV[$("#primary-level-selected").val()];

      $("#global-level-list").empty();
      for (var key in child_object) {
        //adicionar key à lista
        $("#global-level-list").append('<li class="list-group-item text-center"><a href="#">' + key + '</a></li>');
      }

      console.log("HERE");

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

    
    if($("#intermediate-level-list").hasClass("hidden"))
      $("#intermediate-level-list").removeClass("hidden");
    else
      $("#intermediate-level-list").addClass("hidden"); 

    $("#intermediate-level-list").siblings().addClass("hidden");

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

    if($("#specific-level-list").hasClass("hidden"))
      $("#specific-level-list").removeClass("hidden");
    else
      $("#specific-level-list").addClass("hidden"); 

    $("#specific-level-list").siblings().addClass("hidden");

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


    }
  })

  $(document).on("click", "#specific-level-list li", function(){

    $("#specific-level-selected").val($(this).text());

    $("#specific-level-list").addClass("hidden");
  })

  $(document).on("click","#lexical_button", function() {

    var primary= $("#primary-level-selected").val();
    var global= $("#global-level-selected").val();
    var intermediate= $("#intermediate-level-selected").val();
    var specific= $("#specific-level-selected").val();

    var params = "?";
    if (primary != "")
      params += "primary=" + primary;

      if (global != "")
      params += "&global=" + global;

      if (intermediate != "")
      params += "&intermediate=" + intermediate;

      if (specific != "")
      params += "&specific=" + specific;

    if (params == "?") params = "";
    window.location.href = location.origin +'/lexical' + params;

  })

})