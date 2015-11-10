<?php
include('base_templates/header.php');
include('base_templates/search_bar.php');
include('base_templates/results.php');

?>
<script>
	var GLOBAL_QUERY = "<?php echo $_GET['query']; ?>";
</script>
<?php
include('base_templates/footer.php');
?>