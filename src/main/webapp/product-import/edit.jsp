<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 9/27/2023
  Time: 9:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/toastr@2.1.4/build/toastr.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/toastr@2.1.4/build/toastr.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <form action="/product-import?action=edit&id=${productImport.id}" method="post">
        <div class="mb-3">
            <label for="code" class="form-label">Mã import</label>
            <input type="text" class="form-control" id="code" name="code" required value="${productImport.code}">
        </div>
        <div class="mb-3">
            <label for="importDate" class="form-label">Ngày import</label>
            <input type="date" class="form-control" id="importDate" name="importDate" required value="${productImport.importDate}">
        </div>
        <%--        <div class="mb-3">--%>
        <%--            <label for="totalAmount" class="form-label">Tổng giá trị</label>--%>
        <%--            <input type="number" class="form-control" id="totalAmount" name="totalAmount" required>--%>
        <%--        </div>--%>
        <div class="row mb-3">
            <div class="col-4">
                Product
            </div>
            <div class="col-3">
                Quantity
            </div>
            <div class="col-3">
                Amount
            </div>
            <div class="col-2 d-flex justify-content-end">
                <button type="button" class="btn btn-info" onclick="addMore()">Add More</button>
            </div>
        </div>
        <div id="product-import-detail">
            <c:forEach var="piDetail" varStatus="status" items="${productImport.productImportDetails}">


            <div class="row mb-3" id="product-import-${status.index + 1}">
                <div class="col-4">
                    <select class="form-control" onchange="onChangeSelect(this)" name="productIds" id="product" required>
                        <option value="">---Please Choose---</option>
                        <c:forEach var="product" items="${products}">
                            <option value="${product.id}" ${product.id == piDetail.product.id ? 'selected' : ''}>
                                    ${product.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-3">
                    <input type="number" class="form-control" name="quantities" value="12" value="${piDetail.quantity}" required>
                </div>
                <div class="col-3">
                    <input type="number" class="form-control" name="amounts" value="${piDetail.amount}" required>
                </div>
                <div class="col-2 d-flex justify-content-end">
                    <button type="button" class="btn btn-danger" onclick="deleteRow(${status.index + 1})">Delete</button>
                </div>
            </div>
            </c:forEach>
        </div>
        <button type="submit" class="btn btn-primary">Edit import</button>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script>
    const productId = document.getElementById('product');
    const productImportDetail = document.getElementById('product-import-detail');
    const eSelect = document.getElementsByName('productIds');
    const products = ${productsJSON};
    const productsSelected = Array.from(eSelect).map(e => e.value);
    let rowProductImport = ${productImport.productImportDetails.size()};
    let rowCount = rowProductImport;
        function addMore() {
        if(rowCount === products.length) return
        rowCount++;
        let selectStr = '<select class="form-control" name="productIds" id="product" required><option value="">---Please Choose---</option>';
        for (const product of products) {
            selectStr += `<option value="\${product.id}">\${product.name}</option>`;
        }

        selectStr += '</select>';
        const strRow = `<div class="row mb-3" id="product-import-\${++rowProductImport}">
            <div class="col-4">
                \${selectStr}
            </div>
            <div class="col-3">
                <input type="number" class="form-control"  name="quantities" required>
            </div>
            <div class="col-3">
                <input type="number" class="form-control"  name="amounts" required>
            </div>
            <div class="col-2 d-flex justify-content-end">
                <button class="btn btn-danger" onclick="deleteRow(\${rowProductImport})">Delete</button>
            </div>
        </div>`
        document.querySelector('#product-import-detail').innerHTML += strRow;
    }

    function deleteRow(number) {
        rowCount--;
        const row = document.getElementById('product-import-' + number);
        productImportDetail.removeChild(row);
    }
    function onChangeSelect(e){
        if(productsSelected.find(id => +id === +e.value)){
            e.value = '';
            toastr.warn('Product has been selected');
        }
    }

</script>
</body>
</html>