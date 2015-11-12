<?php
include('base_templates/header.php');
include('base_templates/lexical_bar.php');
$origin = "lexical";
include('base_templates/results.php');
?>
	<script>var GLOBAL_CSV = <?php echo json_encode($csv); ?>;</script>
	
<?php
include('base_templates/footer.php');
?>
<script src="js/script_lexical.js"></script>