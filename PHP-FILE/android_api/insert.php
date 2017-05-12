<?php
include "Connection.php";

  $userIDFIX = $_POST['userID'];
	$judul_curhat = $_POST['judul_curhat'];
	$isi_curhat = $_POST['isi_curhat'];

	class emp{}

	if (empty($judul_curhat) || empty($isi_curhat) || empty($userIDFIX)) {
		$response = new emp();
		$response->success = 0;
		$response->message = "Please fill in the data";
		die(json_encode($response));
	} else {
		$query = mysql_query("INSERT INTO curhat (id, userID, judul_curhat, isi_curhat) VALUES(0,'".$userIDFIX."','".$judul_curhat."','".$isi_curhat."')");

		if ($query) {
			$response = new emp();
			$response->success = 1;
			$response->message = "Data has been saved";
			die(json_encode($response));
		} else{
			$response = new emp();
			$response->success = 0;
			$response->message = "Can't save the data";
			die(json_encode($response));
		}
	}
?>
