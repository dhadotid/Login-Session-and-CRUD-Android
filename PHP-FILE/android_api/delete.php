<?php
include "Connection.php";

	$id 	= $_POST['id'];

	class emp{}

	if (empty($id)) {
		$response = new emp();
		$response->success = 0;
		$response->message = "Error delete Data";
		die(json_encode($response));
	} else {
		$query = mysql_query("DELETE FROM curhat WHERE id='".$id."'");

		if ($query) {
			$response = new emp();
			$response->success = 1;
			$response->message = "Data has been delete";
			die(json_encode($response));
		} else{
			$response = new emp();
			$response->success = 0;
			$response->message = "Can't delete the data";
			die(json_encode($response));
		}
	}
?>
