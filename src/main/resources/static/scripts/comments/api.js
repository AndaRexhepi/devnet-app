fetch("api/v1/comments")
    .then((response) => response.json())
    .then((json) => console.log(json));

