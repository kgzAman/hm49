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
        <p>Name of the Album : ${album.name}</p>
        <p>Artist : ${album.artist}</p>
        <p>Year : ${album.year}</p>
        <p>-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</p>
        <input name="candidateId" type="hidden" value=${album.id}>
    </div>
</#list>

</body>
</html>