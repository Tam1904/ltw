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
            document.querySelectorAll(".cart-buttton-update")[0].style.display = 'flex';
            document.querySelectorAll(".cart-buttton-update")[1].innerHTML = "Cập nhật";
            document.querySelector('.cart-button-update-nth').style = 'display:flex; justify-content: space-between';
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

        var listB = document.querySelectorAll("#icon");
        for (var item of listB) {
            item.onclick = append;
        }

        function getParent(element, selector) {

            while (element.parentElement) {
                if (element.parentElement.matches(selector)) {
                    return element.parentElement;
                }
                element = element.parentElement;
            }
        }

        function append(event) {
            var parent = getParent(event.target, ".cart-table-tr");
            document.querySelector(".editEmployeeModal").style.display = 'block';
            var form = document.querySelector(".editEmployeeModal");
            form.querySelector("#name").value = parent.querySelector("#name").innerHTML;
            form.querySelector("#username").value = parent.querySelector("#username").innerHTML;
            form.querySelector("#password").value = parent.querySelector("#password").innerHTML;
            form.querySelector("#gioithieu").value = parent.querySelector("#gioithieu").innerHTML;
            form.querySelector("#phanquyen").value = parent.querySelector("#phanquyen").innerHTML;
        }

        var cancel = document.querySelector('.close');
        cancel.onclick = function() {
            document.querySelector(".editEmployeeModal").style.display = 'none';
        }

        var listButton = document.querySelectorAll('.cart-buttton-update');
        listButton[0].onclick = Cancel;
        listButton[1].onclick = Update;

        function Cancel() {
            if (confirm("Bạn muốn hủy cập nhật")) {
                document.getElementById("search").style.display = 'none';
                document.querySelector('.cart-detail').style.display = 'none';
                var list = selectorElement.querySelectorAll('.button-item');
                for (var item of list) {
                    item.style.backgroundColor = 'var(--primary-color)';
                }
            }
        }

        function Update() {
            alert("Cập nhật thành công");
            document.getElementById("search").style.display = 'none';
            document.querySelector('.cart-detail').style.display = 'none';
            var list = selectorElement.querySelectorAll('.button-item');
            for (var item of list) {
                item.style.backgroundColor = 'var(--primary-color)';
            }
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
            document.querySelectorAll(".cart-buttton-update")[1].innerHTML = "Xóa tất cả";
            document.querySelectorAll(".cart-buttton-update")[0].style.display = 'none';
            document.querySelector('.cart-button-update-nth').style = 'display:flex; justify-content: flex-end';
            var list = document.querySelectorAll('.edit');
            for (var item of list) {
                item.style.display = 'none';
            }
            list = document.querySelectorAll('.checkbox');
            for (var item of list) {
                item.style.display = 'block';
            }
            var listButton = document.querySelectorAll('.cart-buttton-update');
            listButton[1].onclick = Delete;

            function Delete() {
                if (confirm("Bạn chắc chắn xóa sản phẩm")) {
                    alert("Xóa thành công");
                    document.getElementById("search").style.display = 'none';
                    document.querySelector('.cart-detail').style.display = 'none';
                    var list = selectorElement.querySelectorAll('.button-item');
                    for (var item of list) {
                        item.style.backgroundColor = 'var(--primary-color)';
                    }
                }
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