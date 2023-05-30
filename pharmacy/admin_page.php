<?php
session_start();

if ((!isset($_SESSION['username'])) && $_SESSION['is_logged_in'] === false) {
    header('location:login_form.php');
}

                                        // Add button
if (isset($_POST['addPharmacy'])) {
    $inputPharmacyName = $_POST['pharmacyName'];
    $inputPharmacyAddress = $_POST['pharmacyAddress'];

    if (!empty($inputPharmacyName) && !empty($inputPharmacyAddress)) {

        $url = 'http://localhost:8080/api/pharmacies/';

        $data = array(
            'name' => $inputPharmacyName,
            'address' => $inputPharmacyAddress
        );

        $jsonPayload = json_encode($data);

        $options = array(
            'http' => array(
                'method' => 'POST',
                'header' => "Content-Type: application/json\r\n" .
                "Content-Length: " . strlen($jsonPayload) . "\r\n",
                'content' => $jsonPayload
            )
        );

        $context = stream_context_create($options);

        $response = file_get_contents($url, false, $context);
        //echo "response = " . $response;

        if ($response === false) {
            echo "Error fetching contents of URL";
        } else {
            $decoded_response = json_decode($response);

            //print_r("decoded response = " . $decoded_response ."<br>");
        }

    } else {
        // echo"pharmacy name and pharmacy address must be filled";
        echo "<script>alert(`Pharmacy Name and Pharmacy Address must be filled`);</script>";

    }
}

if (isset($_POST['updatePharmacy'])) {
    $intMax = 2147483647;
    $id = $_POST['ID'];
    $inputPharmacyName = $_POST['pharmacyName'];
    $inputPharmacyAddress = $_POST['pharmacyAddress'];

    if (!empty($inputPharmacyName) && !empty($inputPharmacyAddress) && !empty($id)) {

        if ($id > 0 && $id < $intMax) {
            $url = 'http://localhost:8080/api/pharmacies/update/' . $id;

            $data = array(
                'name' => $_POST['pharmacyName'],
                'address' => $_POST['pharmacyAddress']
            );

            $jsonPayload = json_encode($data);
            $options = array(
                'http' => array(
                    'method' => 'POST',
                    'header' => "Content-Type: application/json\r\n" .
                    "Content-Length: " . strlen($jsonPayload) . "\r\n",
                    'content' => $jsonPayload
                )
            );
            $context = stream_context_create($options);
            $response = file_get_contents($url, false, $context);
        } else {
            echo "<script>alert(`Enter a valid ID`);</script>";
        }

    } else {
        echo "<script>alert(`Text fields must not be empty`);</script>";
    }
}


if (isset($_POST["deletePharmacy"])) {
    $url = 'http://localhost:8080/api/pharmacies/' . $_POST["deletePharmacy"];

    //$response = file_get_contents($url);
    $options = array(
        'http' => array(
            'method' => 'POST',
            'header' => 'Content-type: application/x-www-form-urlencoded',
            'content' => http_build_query(array('deletePharmacy' => $_POST["deletePharmacy"]))
        )
    );
    $context = stream_context_create($options);
    $response = file_get_contents($url, false, $context);
}
                                            // Listing...

$table = "";

if (isset($_POST["updateList"])) {
    $url = 'http://localhost:8080/api/pharmacies/getall/';

    $idNo = $_SESSION['username'];
    $password = "empty";
    $is_admin = true;

    $jsonObject = array(
        'trIdNumber' => $idNo,
        'password' => $password,
        'isAdmin' => $is_admin
    );

    $jsonString = json_encode($jsonObject);

    // istek ayarlari
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
    $table .= "<tr><th>ID</th><th>Name</th><th>Address</th><th>Action</th></tr>";
    foreach ($dataArray as $item) {
        $table .= "<tr><td>" . $item['id'] . "</td><td>" . $item['name'] . "</td><td>" . $item['address'] . "</td><td><form method='POST' action=''><button type='submit' name='deletePharmacy' value='" . $item['id'] . "'>Delete</button></form></td></tr>";
    }
    $table .= "</table>";
}



$logs = "";


if (isset($_POST["updateLogs"])) {
    $url = 'http://localhost:8080/api/logs/getall';

    // Get the JSON data from the URL
    $jsonData = file_get_contents($url);

    // Convert the JSON data to an array
    $dataArray = json_decode($jsonData, true);

    $logs = "<table>";
    $logs .= "<tr><th>TrIdNo</th><th>Log</th><th>TimeStamp</th></tr>";
    foreach ($dataArray as $item) {
        $temp = explode('T', $item['timeStamp']);
        $temp2 = explode('.', $temp[1]);
        // $logs .= "<tr><td>" . $item['trIdNo'] . "</td><td>" . $item['logMessage'] . "</td><td>" . $item['timeStamp'] . "</td><td>";
        $logs .= "<tr><td>" . $item['trIdNo'] . "</td><td>" . $item['logMessage'] . "</td><td>" . $temp[0] . " " . $temp2[0] . "</td><td>";
    }
    $logs .= "</table>";
}

?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>admin page</title>

    <!-- <link rel = "stylesheet" href = "css/style.css"> -->
    <link rel="stylesheet" href="./css/main2.css">

</head>

<body>
    <div class="container">
        <div class="content">
            <div class="welcome">
                <div>
                    <h3>Hi, <span>Admin</span> </h3>
                    <h1>Welcome <span>
                            <?php echo $_SESSION['username'] ?>
                        </span></h1>
                </div>
                <h2>This is an admin page</h2>
            </div>

            <form action="" method="POST">
                <input type="text" name="ID" placeholder="ID" maxlength="11"
                    onkeypress="return event.charCode >= 48 && event.charCode <= 57 ">
                <input type="text" name="pharmacyName" placeholder="Pharmacy Name" maxlength="20">
                <input type="text" name="pharmacyAddress" placeholder="Pharmacy address" maxlength="250">


                <input type="submit" name="addPharmacy" value="Add Pharmacy" class="btn">
                <input type="submit" name="updatePharmacy" value="Update Pharmacy" class="btn">
                <input type="submit" name="updateList" value="Show Pharmacies" class="btn">
                <input type="submit" name="updateLogs" value="Show Logs" class="btn">
                <div class="logout">
                    <a href="logout.php" class="btn">logout</a>
                </div>
            </form>


        </div>
        <section class="table" id="table">
            <?php
            echo $table;
            ?>
            <?php
            echo $logs;
            ?>
        </section>


    </div>


</body>

</html>