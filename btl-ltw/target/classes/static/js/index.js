function manager(selector, add, edit) {
    var selectorElement = document.querySelector(selector);
    var list = selectorElement.querySelectorAll('.button-item');
    list[0].onclick = addProduct;
    list[1].onclick = editProduct;
    list[2].onclick = removeProduct;

    function addProduct(event) {
        if (event.target.style.backgroundColor == 'var(--primary-color)') {
            var list = selectorElement.querySelectorAll('.button-item');

            for (var item of list) {
                if (item != event.target) {
                    item.style.backgroundColor = '#999';
                }
            }
            document.getElementById("search").style.display = 'none';
            document.querySelector(".editEmployeeModal").style.display = 'block';
            document.querySelector('.cart-detail').style.display = 'none';
            document.querySelector('.modal-title').innerHTML = add;
            document.querySelector('.btn-product').value = "Add";
        }

        var cancel = document.querySelector('.close');
        cancel.onclick = function() {
            document.querySelector(".editEmployeeModal").style.display = 'none';
            remove(".editEmployeeModal")
            var list = selectorElement.querySelectorAll('.button-item');
            for (var item of list) {
                item.style.backgroundColor = 'var(--primary-color)';
            }
        }
    }

    function editProduct(event) {
        if (event.target.style.backgroundColor == 'var(--primary-color)') {
            var list = selectorElement.querySelectorAll('.button-item');

            for (var item of list) {
                if (item != event.target) {
                    item.style.backgroundColor = '#999';
                }
            }
            document.getElementById("search").style.display = 'block';
            document.querySelector('.cart-detail').style.display = 'block';
            document.querySelector(".cart-buttton-update").innerHTML = "Hoàn tất";
            document.querySelector('.cart-button-update-nth').style = 'display:flex; justify-content: flex-end';
            var list = document.querySelectorAll('.checkbox');
            for (var item of list) {
                item.style.display = 'none';
            }
            list = document.querySelectorAll('.edit');
            for (var item of list) {
                item.style.display = 'block';
            }
            document.querySelector('.modal-title').innerHTML = edit;
            document.querySelector('.btn-product').value = "Edit";
        }

       

        var cancel = document.querySelector('.close');
        cancel.onclick = function() {
            document.querySelector(".editEmployeeModal").style.display = 'none';
             remove(".editEmployeeModal")
        }


    }

    function removeProduct(event) {
        if (event.target.style.backgroundColor == 'var(--primary-color)') {
            var list = selectorElement.querySelectorAll('.button-item');

            for (var item of list) {
                if (item != event.target) {
                    item.style.backgroundColor = '#999';
                }
            }
            document.getElementById("search").style.display = 'block';
            document.querySelector('.cart-detail').style.display = 'block';
            document.querySelector(".cart-buttton-update").innerHTML = "Xóa tất cả";
            document.querySelector('.cart-button-update-nth').style = 'display:flex; justify-content: flex-end';
            var list = document.querySelectorAll('.edit');
            for (var item of list) {
                item.style.display = 'none';
            }
            list = document.querySelectorAll('.checkbox');
            for (var item of list) {
                item.style.display = 'block';
            }
           
        }
    }
}

function thongke(selector) {
    var selectorElement = document.querySelector(selector);
    var list = selectorElement.querySelectorAll('.button-item');
    list[1].onclick = view;
    list[2].onclick = comment;


    function view(event) {
        if (event.target.style.backgroundColor == 'var(--primary-color)') {
            var list = selectorElement.querySelectorAll('.button-item');

            for (var item of list) {
                if (item != event.target) {
                    item.style.backgroundColor = '#999';
                }
            }
            document.getElementById("search").style.display = 'block';
            document.querySelector('.cart-detail').style.display = 'block';

            var button = document.querySelector('.cart-buttton-update');
            button.onclick = close;

            function close() {
                document.getElementById("search").style.display = 'none';
                document.querySelector('.cart-detail').style.display = 'none';
                var list = selectorElement.querySelectorAll('.button-item');
                for (var item of list) {
                    item.style.backgroundColor = 'var(--primary-color)';
                }
            }
            var items = document.querySelectorAll("#type");
            for (var item of items) {
                item.innerHTML = "Lượt xem";
            }
        }


    }

    function comment(event) {
        if (event.target.style.backgroundColor == 'var(--primary-color)') {
            var list = selectorElement.querySelectorAll('.button-item');

            for (var item of list) {
                if (item != event.target) {
                    item.style.backgroundColor = '#999';
                }
            }
            document.getElementById("search").style.display = 'block';
            document.querySelector('.cart-detail').style.display = 'block';
            var button = document.querySelector('.cart-buttton-update');
            button.onclick = close;

            function close() {
                document.getElementById("search").style.display = 'none';
                document.querySelector('.cart-detail').style.display = 'none';
                var list = selectorElement.querySelectorAll('.button-item');
                for (var item of list) {
                    item.style.backgroundColor = 'var(--primary-color)';
                }
            }
            var items = document.querySelectorAll("#type");
            for (var item of items) {
                item.innerHTML = "Số bình luận";
            }
        }
    }
}

function remove(selector){
	var form = document.querySelector(selector);
	form.style = "display:none";
	form.querySelector("#id").value ="0";
	form.querySelector("#ten").value ="";
	form.querySelector("#username").value ="";
	form.querySelector("#password").value ="";
	form.querySelector("#email").value ="";
	form.querySelector("#gioithieu").value ="";
	form.querySelector("#message").innerHTML = "";
}


function Cancel(selector){
	var btn = document.querySelectorAll(selector);
	for (var bt of btn){
		bt.onclick = huy;
	}
	
	function huy(event){
		if(confirm("Bạn muốn xóa Post")==false){
			
			event.preventDefault();
		}
		console.log(1);
	}
}

function xoapost(selector){
	var list = document.querySelectorAll(selector);
	for(var item of list){
		item.onclick = removePost;
	}
	
	function removePost(event){
		if(!confirm("Bạn muốn xóa Post")){
			event.preventDefault();
		}
	}
}