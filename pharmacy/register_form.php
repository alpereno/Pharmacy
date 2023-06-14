<?php
if (isset($_POST['submit'])) {
   if ($_SERVER["REQUEST_METHOD"] === "POST") {
      $url = 'http://localhost:8080/api/appUsers/addUser';

      $selectedusertype = $_POST['user_type'];
      $is_admin = false;

      if($selectedusertype === "admin"){
         $is_admin = true;
      }

      $jsonObject = array(
         'trIdNumber' => $_POST["ID"],
         'password' => $_POST["password"],
         'isAdmin' => $is_admin
      );

      $jsonString = json_encode($jsonObject);

      // istek ayarlari
      $options = array(
         'http' => array(
            'method' => 'POST',
            'header' => "Content-Type: application/json\r\n" .
            "Content-Length: " . strlen($jsonString) . "\r\n",
            'content' => $jsonString
         )
      );

      // context olusturma
      $context = stream_context_create($options);

      // istek gonderme
      $response = @file_get_contents($url, false, $context);
      if(filter_var($response, FILTER_VALIDATE_BOOLEAN)){
         header('location:login_form.php');
      }
      else{
         $error[] = "invalid TR id";
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
   <title>register form</title>

   <link rel="stylesheet" href="css/style.css">

</head>

<body>

   <div class="form-container">

      <form action="" method="post">
         <h3>register now</h3>
        
      <?php
      if(isset($error)){
         foreach($error as $error){
            echo '<span class="error-msg">'.$error.'</span>';
         };
      };
      ?>

         <input type="text" name="ID" required maxlength="11" placeholder="enter your TR ID" onkeypress="return event.charCode >= 48 && event.charCode <= 57" >
         <input type="password" name="password" required placeholder="enter your password" maxlength="10">
         <select name="user_type">
            <option value="user">user</option>
            <option value="admin">admin</option>
         </select>
         <input type="submit" name="submit" value="register now" class="form-btn">
         <p>already have an account? <a href="login_form.php">Login Now</a></p>
      </form>

   </div>

</body>

</html>