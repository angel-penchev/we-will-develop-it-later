<?php
	$curl = curl_init();
	$data = array('image' => '@/home/dell/var/www/html/Image2.png');
	curl_setopt($curl, CURLOPT_URL, 'http://localhost/script.php');
	curl_setopt($ch, CURLOPT_HEADER, FALSE); 
	curl_setopt($curl, SETOPT_POSTFIELDS, $data);
	$output  = curl_exec($curl);
	echo $output;
?>