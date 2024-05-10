<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Order Details</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style>
    table {
        width: 100%;
        border-collapse: collapse;
    }

    th, td {
        border: 1px solid #dddddd;
        text-align: left;
        padding: 8px;
    }

    th {
        background-color: #f2f2f2;
    }
</style>
</head>
<body onload="loadOrderItems()">
    <div class="card">
        <h2>Order Details</h2>
        <table>
            <thead>
                <tr>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Final Price</th>
                    <th>Breakdown</th>
                </tr>
            </thead>
            <tbody id="orderTableBody">
                <!-- Order items will be dynamically added here -->
            </tbody>
        </table>

        <div id="totalShippingCharges"></div>
        <div id="orderTotal"></div>

    </div>
    <script>
        function loadOrderItems() {
            let dl={};
            for (var i = 0; i < localStorage.length; i++) {
                var key = localStorage.key(i);
                var value = localStorage.getItem(key);
                var item = JSON.parse(value);
                dl[item.itemId]=item.quantity;
            }
            console.log(dl);
            $.ajax({
                url:"http://localhost:8080/Ecommerce/CheckOutServlet",
                method:'GET',
                data:{
                    "product_details":JSON.stringify(dl)
                },
                success: function(data) {
                    data=JSON.parse(data);
                    console.log(data);
                    const orderTableBody = $('#orderTableBody');
                    const orderTotalElement = $('#orderTotal');
                    $.each(data.products, function(index, product) {
                        const breakdownHtml = `
                            <div>
                                Quantity: ${product[2]}<br>
                                GST: ${product[3]}<br>
                                Discount: ${product[4]}<br>
                                Shipment Charges: ${product[5]}
                            </div>
                        `;
                        const row = `
                            <tr>
                                <td>${product[0]}</td>
                                <td>${product[1]}</td>
                                <td>${product[6]}</td>
                                <td>${breakdownHtml}</td>
                            </tr>
                        `;
                        orderTableBody.append(row);

                    });
                    $('#totalShippingCharges').text(data.shipment);
                    $('#orderTotal').text(data.order);

                    // totalFare += parseFloat(data.order);
                    // $('#totalFare').text(totalFare.toFixed(2));
                    // $('#shippingCharges').text(data.shipment);

                    // Calculate and display total order
                    // let totalOrder = totalFare + shippingCharges;
                    // $('#totalOrder').text(totalOrder.toFixed(2));
                },
                error: function(xhr, status, error) {
                    console.log("Error: " + error);
                }
            })
        }

    </script>
</body>
</html>
