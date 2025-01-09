

function openModal() {
    document.getElementById('deleteConfirmationModal').style.display = 'block';
}

function closeModal() {
    document.getElementById('deleteConfirmationModal').style.display = 'none';
}

function toggleOptions(dotElement) {
    let options = dotElement.parentElement.querySelector(".options");
    if (options.style.display === "none" || options.style.display === "") {
        options.style.display = "block";
    } else {
        options.style.display = "none";
    }
}