class CommentApp {
    constructor() {
        this.commentApi = new CommentApi(); // Reference to the API handler
        this.commentInp = document.getElementById("commentInput"); // Input field for comment
        this.postBtn = document.getElementById("postBtn"); // Button to post comment
        this.commentsBody = document.getElementById("commentBody"); // Section to display comments
    }

    async init() {
        await this.loadComments(); // Load all comments on initialization
        this.postBtn.addEventListener("click", () => {
            this.addComment(); // Attach event listener to post button
        });
    }

    async addComment() {
        const comment = {
            comment: this.commentInp.value, // Adjust this to match your CommentDto fields
        };

        const response = await fetch('/api/v1/comments/add', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(comment),
        });

        if (response.ok) {
            const addedComment = await response.json();
            this.commentsBody.innerHTML = ""; // Clear and reload comments
            this.createCommentElement(addedComment);
            this.commentInp.value = "";
        } else {
            console.error("Failed to add comment", response);
        }
    }

    async loadComments() {
        try {
            const comments = await this.commentApi.findAll(); // Fetch all comments
            this.commentsBody.innerHTML = ""; // Clear the comments section
            comments.forEach(comment => {
                this.createCommentElement(comment); // Add each comment to the UI
            });
        } catch (error) {
            console.error("Error loading comments:", error);
            alert("Failed to load comments. Please refresh the page.");
        }
    }

    createCommentElement(comment) {
        const commentElement = document.createElement("p"); // Create a new paragraph
        commentElement.classList.add("comment"); // Add styling class
        commentElement.innerHTML = `
            <p>${comment.comment}</p>
        `; // Populate with comment content
        this.commentsBody.appendChild(commentElement); // Append to comments section
    }
}

const commentApp = new CommentApp();
commentApp.init();
