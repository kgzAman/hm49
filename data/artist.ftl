<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

    <#list artists as artist>
        <div class="card">
                <img src="img/${artist.img}">
                <p>Name : ${artist.name}</p>
                <p>Information: ${artist.information}</p>
                <p>-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</p>
                <input name="candidateId" type="hidden" value=${artist.id}>
        </div>
    </#list>

</body>
</html>