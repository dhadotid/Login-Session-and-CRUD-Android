<?php
include "Connection.php";

	$id 	= $_POST['id'];

	class emp{}

	if (empty($id)) {
		$response = new emp();
		$response->success = 0;
		$response->message = "Error Retrive the Data";
		die(json_encode($response));
	} else {
		$query 	= mysql_query("SELECT u.id, c.id, c.userID, c.judul_curhat, c.isi_curhat FROM curhat c join users u on u.id = c.userID WHERE c.id='".$id."'");
		$row 	= mysql_fetch_array($query);

		if (!empty($row)) {
			$response = new emp();
			$response->success = 1;
			$response->id = $row["id"];
      $response->userID = $row["userID"];
			$response->judul_curhat = $row["judul_curhat"];
			$response->isi_curhat = $row["isi_curhat"];
			die(json_encode($response));
		} else{
			$response = new emp();
			$response->success = 0;
			$response->message = "Error Retrive the Data";
			die(json_encode($response));
		}
	}
?>
