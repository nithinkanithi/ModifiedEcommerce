<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style>
     body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: radial-gradient(circle, #ff7e5f, #feb47b);
        }

        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .container h3 {
            text-align: center;
            color: #333;
        }

        .cart-items {
            margin-top: 20px;
        }

        .item-card {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
            padding: 15px;
            border-radius: 8px;
            background-color: #f9f9f9;
        }

        .item-image img {
            max-width: 100px;
            height: auto;
            margin-right: 20px;
        }

        .item-details {
            flex: 1;
        }

        .item-details h4 {
            margin: 0 0 10px 0;
            color: #333;
        }

        .item-details p {
            margin: 0;
            color: #666;
        }

        .quantity-controls {
            display: flex;
            align-items: center;
            margin-top: 10px;
        }

        .quantity-controls button {
            padding: 5px 10px;
            border: none;
            background-color: #ddd;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .quantity-controls button:hover {
            background-color: #ccc;
        }

        .quantity {
            margin: 0 10px;
            font-size: 18px;
            font-weight: bold;
            color: #333;
        }

        .checkout-btn {
                display: block;
                width: fit-content;
                padding: 15px;
                margin: 20px auto 0;
                border: none;
                background: linear-gradient(to right, #4e73df, #224abe); /* Default background gradient */
                color: #fff;
                text-align: center;
                text-decoration: none;
                border-radius: 8px;
                cursor: pointer;
                transition: background-color 0.3s ease;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                outline: none; /* Remove button outline */
            }

            .checkout-btn:hover {
                background-color: #0056b3; /* Change hover background color */
                text-decoration: none; /* Remove underline on hover */
            }

            .checkout-btn a {
                color: inherit; /* Inherit text color from parent */
                text-decoration: none; /* Remove underline */
            }


        @media (max-width: 600px) {
            .container {
                padding: 10px;
            }

            .item-image img {
                max-width: 80px;
            }
        }
</style>
</head>
<body onload="loadCartItems()">
	<div class="container">
        <h3>Shopping Cart</h3>
        <div class="cart-items" id="cartItems">
           
        </div>
    </div>
    <div></div>
    <div><button class="checkout-btn"><a href="CheckOut.jsp">check out</a></button></div>
<script src="Cart.js"></script>
</body>

</html>