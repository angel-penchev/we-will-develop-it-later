<?php 
	print("Executing...");
	$image = $_POST['image'];
	echo shell_exec("python /home/dell/var/www/html/text_recognition.py --east /home/dell/var/www/html/frozen_east_text_detection.pb --image $image 2>&1");
?>
