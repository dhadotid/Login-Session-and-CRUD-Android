<?php
include "Connection.php";
/*
$userIDFIX 	= $_POST['userID'];
//$userIDFIX	= 2;


class emp{}

	if(empty($userIDFIX)){
		$response = new emp();
		$response->success = 0;
		$response->message = "Data not found";
		die(json_encode($response));
	}else {
		$query = mysql_query("SELECT u.id, c.id, c.userID, c.judul_curhat, c.isi_curhat FROM curhat c join users u on u.id = c.userID WHERE c.userID = '$userIDFIX' ORDER BY judul_curhat ASC");

		if($query){
			$json = array();

			while($row = mysql_fetch_assoc($query)){
				$json[] = $row;
			}

			echo json_encode($json);

			mysql_close($connect);
			$response = new emp();
			$response->success = 1;
			$response->message = "Data found";
			die(json_encode($response));
		}else {
			$response = new emp();
			$response->success = 0;
			$response->message = "Data not found!";
			die(json_encode($response));
		}


}
*/

//$userID	= 2;
//$userIDFIX	= $_POST['userID'];
$query = mysql_query("SELECT * FROM curhat ORDER BY judul_curhat ASC");
$json = array();

while($row = mysql_fetch_assoc($query)){
	$json[] = $row;
}

echo json_encode($json);

mysql_close($connect);

?>
