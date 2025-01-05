let deleteHref;

function confirmDelete(event) {
    event.preventDefault();
    deleteHref = event.target.closest('a').href;
    openModal();
}

function openModal() {
    document.getElementById('deleteConfirmationModal').style.display = 'block';
}

function closeModal() {
    document.getElementById('deleteConfirmationModal').style.display = 'none';
}

document.getElementById('confirmDeleteBtn').addEventListener('click', function() {
    closeModal();
    window.location.href = deleteHref;
});


function toggleOptions(element) {
    var options = document.querySelector(".options")
    if (options.style.display === "none") {
        options.style.display = "block";
    } else {
        options.style.display = "none";
    }
}