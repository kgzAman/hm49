<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

    <#list albums as album>
        <div class="card">
            <img src="image/${album.img}">
                <p>Name : ${album.name}</p>
                <p>Album: ${album.artist}</p>
                <p>Year: ${album.year}</p>
                <p>-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</p>

        </div>
    </#list>
    <input>

</body>
</html>