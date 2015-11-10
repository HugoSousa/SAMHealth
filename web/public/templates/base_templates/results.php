<div class="container container-results">

  <?php 
  $docs_size = count($results['response']['docs']);
  if($docs_size == 0) {
    echo '<h3 id="no-results"> No results found! </h3>';
  } 
  ?>

  <div class="panel-group" id="results-accordion">

    <?php

    $start = $results['response']['start'];

    for($i = 0; $i < $docs_size; $i++){
      $doc = $results['response']['docs'][$i];
      $hl = $results['highlighting'][$doc['id']];
      echo '
      <div class="panel panel-default" id="panel'.$i.'"> 
        <div class="panel-heading">
          <h4 class="panel-title">
            <a data-toggle="collapse" data-target="#collapse'.$i.'" href="">
              <div class="container-fluid">
                <div class="row">
                  <div class="col-md-10">
                    <div class="row">
                      <p class="col-md-4">Patient: <span class="patient">'.$doc['patient'].'</span></p>
                      <p class="col-md-4">Therapist: <span class="therapist">'.$doc['therapist'].'</span></p>
                    </div>
                    <div class="row">
                      <p class="col-md-4">Session: <span class="session">'.$doc['session_number'].'</span></p>';
                      if(isset($doc['session_date'])) {
                        echo '<p class="col-md-4 date-p">Date: <span class="date">'.$doc['session_date'].'</span></p>';
                      }

      echo '
                    </div>
                  </div>
                  <div class="col-md-2">
                    <div class="row">
                      <p class="result-index">#'.($start + $i + 1).'</p>
                    </div>
                    <div class="row">
                      <!--<p class="caret-span"></p>-->
                      <span class="glyphicon glyphicon-chevron-down caret-span" aria-hidden="true"></span>
                    </div>
                  </div>
                </div>
              </div>     
            </a>
          </h4>
        </div>

        <div id="collapse'.$i.'" class="panel-collapse collapse">
          <div class="panel-body">
            <p class="score">score: '.$doc['score'].'</p>
            <p>X ocurrences found on this file</p>
            <ul>';
            if(isset($hl['content'])) {
              for($k = 0; $k < count($hl['content']); $k++){
                echo '<li>'.$hl['content'][$k].'</li>';
              }
            }

    echo '  
            </ul>
            <a href="./transcriptions/'.$doc['patient'].'/'.$doc['file'].'" download>Download the document</a>
          </div>
        </div>
      </div>
      ';
    }

    ?>  
  </div>

  <?php 
  $docs_size = count($results['response']['docs']);
  $page_number = ($start / 10 + 1);

  if($docs_size != 0) {
    echo '<nav>
      <ul class="pager">';

      if($start == 0) {
        echo '<li class="disabled"><a>Previous</a></li>';
      } else {
        echo '<li><a href="http://localhost/search?query='.$query.'&page='.($page_number - 1).'">Previous</a></li>';
      }

      $results_found = $results['response']['numFound'];

      if( $page_number >= ceil($results_found / 10)) {
        echo '<li class="disabled"><a>Next</a></li>';
      } else {
        echo '<li><a href="http://localhost/search?query='.$query.'&page='.($page_number + 1).'">Next</a></li>';
      }

    echo '</ul>
      </nav>';
  } 
  ?>

  

</div>
