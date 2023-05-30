<?php
session_start();

if ((!isset($_SESSION['username'])) && $_SESSION['is_logged_in'] === false) {
   header('location:login_form.php');
}

                                                        // Listing the All Pharmacies
$table = "";

if (isset($_POST["updateList"])) {
    $url = 'http://localhost:8080/api/pharmacies/getall/';

    $idNo = $_SESSION['username'];
    $password = "empty";
    $is_admin = false;

    $jsonObject = array(
        'trIdNumber' => $idNo,
        'password' => $password,
        'isAdmin' => $is_admin
    );

    $jsonString = json_encode($jsonObject);

    $options = array(
        'http' => array(
            'method' => 'GET',
            'header' => "Content-Type: application/json\r\n" .
            "Content-Length: " . strlen($jsonString) . "\r\n",
            'content' => $jsonString
        )
    );

    $context = stream_context_create($options);

    // Get the JSON data from the URL
    $jsonData = file_get_contents($url, false, $context);

    // Convert the JSON data to an array
    $dataArray = json_decode($jsonData, true);

    $table = "<table>";
    $table .= "<tr><th>Name</th><th>Address</th><th>Action</th></tr>";
    foreach ($dataArray as $item) {
        $table .= "<tr><td>" . $item['name'] . "</td><td>" . $item['address'] . "</td><td><form method='POST' action=''><button type='submit' name='selectPharmacy' value='" . $item['id'] . "'>Select</button></form></td></tr>";
    }
    $table .= "</table>";
}

                                                // Selection operation 
if (isset($_POST["selectPharmacy"])) {
    $url = 'http://localhost:8080/api/pharmacies/select/' . $_SESSION['username']  .'/'. $_POST["selectPharmacy"];

    //$response = file_get_contents($url);
    $options = array(
        'http' => array(
            'method' => 'POST',
            'header' => 'Content-type: application/x-www-form-urlencoded',
            'content' => http_build_query(array('selectPharmacy' => $_POST["selectPharmacy"]))
        )
    );
    $context = stream_context_create($options);
    $response = file_get_contents($url, false, $context);

    $decoded_response = json_decode($response, true);
    $pharmacyName = $decoded_response['name'];

    echo "<script>alert(`Selected pharmacy is = " .$pharmacyName . "`);</script>";
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>user page</title>
    
    <!-- custom css file link -->
    <link rel = "stylesheet" href = "css/main2.css">

</head>
<body>
    <div class="container">
        <div class="content">
            <div class="welcome">
                <div>
                    <h3>hi, <span>user</span> </h3>
                    <h1>welcome <span><?php echo $_SESSION['username'] ?></span> </h1>
                </div>
                <h2>this is a user page</h2>
            </div>
            <form action="" method="POST" class="user-form">
                <input type="submit" name="updateList" value="Show Pharmacies" class="btn">
                <div class="logout">
                    <a href="logout.php" class="btn">logout</a>
                </div>
            </form>
        </div>
        <section class="table" id="table">
            <?php
            echo $table;
            ?>
        </section>
    </div>

    </div>
</body>
</html>