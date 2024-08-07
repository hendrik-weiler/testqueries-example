window.onload = () => {
    var table = document.getElementById('table');
    for (var i = 0; i < data.length; i++) {
        var item = data[i];
        var div = document.createElement('tr');
        div.innerHTML = '<td>' + item.id + '</td>' +
            '<td>' + item.username + '</td>' +
            '<td>' + item.password + '</td>';
        table.appendChild(div);
    }
}