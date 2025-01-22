document.addEventListener("DOMContentLoaded", function () {
    const postId = document.querySelector("[data-post-id]").getAttribute("data-post-id");
    const commentsContainer = document.getElementById("comments-container");
    const addCommentForm = document.getElementById("add-comment-form");
    const editCommentForm = document.getElementById("edit-comment-form");
    const deleteCommentModal = document.getElementById("delete-comment-modal");
    const confirmDeleteBtn = document.getElementById("confirm-delete-btn");

    let currentCommentId = null; // Track the comment being edited or deleted

    // Load comments when the page loads
    loadComments();

    // Add comment form submission
    addCommentForm.addEventListener("submit", function (event) {
        event.preventDefault();
        const commentText = document.getElementById("comment-input").value;
        addComment(postId, commentText);
    });

    // Edit comment form submission
    editCommentForm.addEventListener("submit", function (event) {
        event.preventDefault();
        const updatedText = document.getElementById("edit-comment-input").value;
        updateComment(currentCommentId, updatedText);
    });

    // Delete comment confirmation
    confirmDeleteBtn.addEventListener("click", function () {
        deleteComment(currentCommentId);
    });

    // Load all comments
    function loadComments() {
        fetch("/api/v1/comments")
            .then(response => response.json())
            .then(comments => {
                commentsContainer.innerHTML = ""; // Clear existing comments
                comments.forEach(comment => {
                    if (comment.postId === postId) { // Filter comments by postId
                        commentsContainer.appendChild(createCommentElement(comment));
                    }
                });
            })
            .catch(error => console.error("Error loading comments:", error));
    }

    // Add a new comment
    function addComment(postId, text) {
        fetch("/api/v1/comments/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                postId: postId, // Include postId in the request body
                text: text
            })
        })
            .then(response => response.json())
            .then(comment => {
                commentsContainer.appendChild(createCommentElement(comment));
                document.getElementById("comment-input").value = ""; // Clear the input field
            })
            .catch(error => console.error("Error adding comment:", error));
    }

    // Update a comment
    function updateComment(commentId, text) {
        fetch(`/api/v1/comments/update/${commentId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                text: text
            })
        })
            .then(response => response.json())
            .then(updatedComment => {
                const commentElement = document.getElementById(`comment-${commentId}`);
                commentElement.querySelector(".comment-text").textContent = updatedComment.text;
                bootstrap.Modal.getInstance(document.getElementById("edit-comment-modal")).hide(); // Close the modal
            })
            .catch(error => console.error("Error updating comment:", error));
    }

    // Delete a comment
    function deleteComment(commentId) {
        fetch(`/api/v1/comments/delete/${commentId}`, {
            method: "DELETE"
        })
            .then(() => {
                document.getElementById(`comment-${commentId}`).remove();
                bootstrap.Modal.getInstance(deleteCommentModal).hide(); // Close the modal
            })
            .catch(error => console.error("Error deleting comment:", error));
    }

    // Create a comment element for the DOM
    function createCommentElement(comment) {
        const commentElement = document.createElement("div");
        commentElement.id = `comment-${comment.id}`;
        commentElement.className = "comment";

        const userInfo = document.createElement("div");
        userInfo.className = "user-info";
        userInfo.innerHTML = `
            <img alt="" src="./assets/header.png" height="30px" width="30px" style="border-radius: 50%; margin-top: 6px">
            <p>Anda Rexhepi</p>
            <p>|</p>
            <p>${comment.postedAt}</p>
            <div class="btn-group dropstart">
                <button type="button" class="btn btn-secondary" data-bs-toggle="dropdown" aria-expanded="false"
                        style="background-color: transparent; border: none; font-size: 25px; width: fit-content; height: fit-content; color: #333333; margin:0;">
                    <span>&#x22EE;</span>
                </button>
                <ul class="dropdown-menu">
                    <li><button class="dropdown-item" onclick="openEditModal(${comment.id}, '${comment.text}')">Edit</button></li>
                    <li><button class="dropdown-item" onclick="openDeleteModal(${comment.id})">Delete</button></li>
                </ul>
            </div>
        `;
        commentElement.appendChild(userInfo);

        const commentText = document.createElement("p");
        commentText.className = "comment-text";
        commentText.textContent = comment.text;
        commentElement.appendChild(commentText);

        return commentElement;
    }

    // Open edit modal
    window.openEditModal = function (commentId, commentText) {
        currentCommentId = commentId;
        document.getElementById("edit-comment-input").value = commentText;
        new bootstrap.Modal(document.getElementById("edit-comment-modal")).show();
    };

    // Open delete modal
    window.openDeleteModal = function (commentId) {
        currentCommentId = commentId;
        new bootstrap.Modal(deleteCommentModal).show();
    };
});