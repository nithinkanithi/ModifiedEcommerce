<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>E-Commerce</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="Store.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
    <div class="container">
        <div class="navbar">
            <h3>E-Commerce</h3>
            <a href="Cart.jsp" id="Cart"><i class="fa-solid fa-cart-shopping"></i>Cart</a>
        </div>
        <div class="filter">
            <div class="categories">
                <label for="category">Category:</label>
                <form action="AllProducts" method="get">
                    <select id="category">
                        <option value="All">All</option>
                    </select>
                </form>
            </div>
        </div>
        <div class="items-container" id="items">
        </div>
    </div>
    <script src="Store.js"></script>
</body>
</html>