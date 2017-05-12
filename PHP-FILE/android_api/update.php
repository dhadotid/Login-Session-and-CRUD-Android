<?php
include "Connection.php";

	$id 	= $_POST['id'];
  $userID = $_POST['userID'];
	$judul_curhat 	= $_POST['judul_curhat'];
	$isi_curhat = $_POST['isi_curhat'];

	class emp{}

	if (empty($id) || empty($userID) || empty($judul_curhat) || empty($isi_curhat)) {
		$response = new emp();
		$response->success = 0;
		$response->message = "Please fill in the data";
		die(json_encode($response));
	} else {
		$query = mysql_query("UPDATE curhat SET judul_curhat='".$judul_curhat."', isi_curhat='".$isi_curhat."' WHERE id='".$id."'");

		if ($query) {
			$response = new emp();
			$response->success = 1;
			$response->message = "Data successfully update";
			die(json_encode($response));
		} else{
			$response = new emp();
			$response->success = 0;
			$response->message = "Can't update the data";
			die(json_encode($response));
		}
	}
?>
