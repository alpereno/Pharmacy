<?php
if (isset($_GET['submit'])) {
   if ($_SERVER["REQUEST_METHOD"] === "GET") {
      // istek yapilacak URL
      $url = 'http://localhost:8080/api/appUsers/';

      // istek icin kullanilacak veri
      $data = array(
         'trIdNumber' => $_GET["ID"],
         'password' => $_GET["password"]
      );

      // JSON formatina donusturme
      $jsonPayload = json_encode($data);

      // istek ayarlari
      $options = array(
         'http' => array(
            'method' => 'GET',
            'header' => "Content-Type: application/json\r\n" .
            "Content-Length: " . strlen($jsonPayload) . "\r\n",
            'content' => $jsonPayload
         )
      );

      // context olusturma
      $context = stream_context_create($options);

      // istek gonderme
      $response = file_get_contents($url, false, $context);

      if($response === false){
         echo "Error fetching contents of URL";
      }
      else{
         $decoded_response = json_decode($response,true);
      }
     
      if (isset($_GET['ID']) && isset($_GET['password'])) {
         if($decoded_response != null){
            $isAdmin = $decoded_response['isAdmin'];

            session_start();
            $_SESSION['username'] = $_GET['ID'];
            $_SESSION['is_logged_in'] = true;

            if((bool)$isAdmin){
               header('location:admin_page.php');
            }
            else{
               header('location:user_page.php');
            }
         }
         else{
            $error[] = 'Incorrect TRID or password!';
         }
      }
   }
}
?>

<!DOCTYPE html>
<html lang="en">

<head>
   <meta charset="UTF-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>login form</title>

   <link rel="stylesheet" href="css/style.css">

</head>

<body>

   <div class="form-container">

      <form action="" method="GET">
         <h3>login now</h3>

      <?php
      if(isset($error)){
         foreach($error as $error){
            echo '<span class="error-msg">'.$error.'</span>';
         };
      };
      ?>

         <input type="text" name="ID" required maxlength="11" placeholder="enter your TR ID" onkeypress="return event.charCode >= 48 && event.charCode <= 57" >
         <input type="password" name="password" required placeholder="enter your password" maxlength="10">
         <input type="submit" name="submit" value="login" class="form-btn">
         <p>don't you have an account? <a href="register_form.php">register now</a></p>
      </form>

   </div>

</body>

</html>