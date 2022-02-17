$(document).ready(function () {
    showAll();
    $('#searchButton').click(showAll);

    function showAll() {
        $.ajax({
            type: 'GET',
            url: '/get-books',
            dataType: 'json',
            data: {
                getBy: $("#searchInput").val()
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json')
            },
            success: function (res) {
                console.log(res);
                $('#tableBook').html(search(res));
            },
            error: function (response) {
                console.log(response);
            }
        })
    }


    function search(books) {
        let res = "<table class='table table-sm'"  +
            // " class='table table-hover'" +
            ">" +
            "<thead>" +
            "<th>Isbn</th>" +
            "<th>Title</th>" +
            "<th>Author</th>" +
            "</thead>" +
            "<tbody>";

        for (let i = 0; i < books.length; i++) {
            res += "<tr>" +
                "<td>" + books[i].isbn + "</td>" +
                "<td>" + books[i].title + "</td>" +
                "<td>" + books[i].author + "</td>" +
                "</tr>";
        }
        res += "</tbody></table>";
        return res;
    }

    $('#addButton').click(function (event) {
        event.preventDefault();
        let book = {
            isbn: $("#isbn").val(),
            title: $("#title").val(),
            author: $("#author").val()
        }

        $.ajax({
            type: 'POST',
            url: '/add-book',

            data: JSON.stringify(book),
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json')
            },

            success: function (response) {
                console.log(response);
                showAll();
                $("#isbn").val("");
                $("#title").val("");
                $("#author").val("");
            },
            error: function (response) {
                alert(response.responseText);
                $("#isbn").val("");
                $("#title").val("");
                $("#author").val("");
            }
        })
    });
});