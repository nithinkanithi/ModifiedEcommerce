let a=5;


$(document).ready(function(){
        $.ajax({
            url: 'http://localhost:8080/Ecommerce/AllProducts',
            method: 'GET',
            success: function(data) {
                $('#items').empty();
                data=JSON.parse(data);
                $.each(data.AllProducts, function(ind, ele) {
                    var item = '<div class="item-card" id="' + ele[3] + '">' +
                    '<img src="images/' + ele[0] + '" alt="Item">' +
                    '<h4>' + ele[1] + '</h4>' +
                    '<p>' + ele[2] + '</p>' +
                    '<input type="text" class="pincode-input" placeholder="Enter Pin Code">'+
                    '<button>Add to Cart</button>' +
                    '</div>';
                    $('#items').append(item);
                });
                $.each(data.Catigories, function(ind, ele1) {
                    var item1 = '<option value="'+(a++).toString()+'">'+ele1+'</option>';
                    $('#category').append(item1);
                });
            },
            error: function(xhr, status, error) {
                console.log("Error: " + error);
            }
 	       });
})

$(document).ready(function(){
    $('#category').change(function(){
        var selectedVal=$(this).val();
        $.ajax({
            url:"http://localhost:8080/Ecommerce/CategoryServlet?cat="+selectedVal,
            method:'GET',
            success: function(data) {
                $('#items').empty();
                try {
                    data = JSON.parse(data);
                    $.each(data.AllProducts, function(ind, ele) {
                        var item = '<div class="item-card" id="' + ele[3] + '">' +
                                        '<img src="images/' + ele[0] + '" alt="Item">' +
                                        '<h4>' + ele[1] + '</h4>' +
                                        '<p>' + ele[2] + '</p>' +
                                        '<button>Add to Cart</button>' +
                                        '</div>';
                        $('#items').append(item);
                    });
                } catch (e) {
                    console.log("Error parsing JSON: " + e);
                }

            },
            error: function(xhr, status, error) {
                console.log("Error: " + error);
            }
        })
    })
})

function createProductCard(imgSrc, title, price, id) {
    var imageSrc = imgSrc;
    var itemName = title;
    var itemDescription = price;
    var itemId = id;
    // console.log(itemId)


        var itemData = {
            itemId: itemId,
            imageSrc: imageSrc,
            itemName: itemName,
            itemDescription: itemDescription,
            quantity: 1 
        };

        var existingItemDataJSON = localStorage.getItem(itemName);
        if (existingItemDataJSON) {
            var existingItemData = JSON.parse(existingItemDataJSON);
            existingItemData.quantity++;
            itemData = existingItemData;
        }

        var itemDataJSON = JSON.stringify(itemData);

        localStorage.setItem(itemName, itemDataJSON);

        alert('Item added to cart successfully!');
    }







$(document).ready(function() {
    $('#items').on('click', '.item-card button', function() {
        var itemCard = $(this).closest('.item-card');
        var itemId = itemCard.attr('id');
        var pinCode = itemCard.find('.pincode-input').val();
        let det={};
        det[itemId]=pinCode;
        console.log(det);
        $.ajax({
            url:"http://localhost:8080/Ecommerce/CartServlet",
            method:'GET',
            data:{
                "product_id":JSON.stringify(det)
            },
            success: function(data) {
                data=JSON.parse(data)
                createProductCard(data.Products[0][0], data.Products[0][1], data.Products[0][2], data.Products[0][3]);
            },
            error: function(xhr, status, error) {
                // console.log("Error: " + error);
                alert("The Product u selected is not deliverable at ur location");
            }
        })
    });
});









