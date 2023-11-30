document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.more-button').forEach(button => {
        button.addEventListener('click', function(event) {
            const currentButton = event.currentTarget;
            const listContainer = currentButton.closest('.note').querySelector('.list-container');

            listContainer.classList.toggle('active');
        });
    });

    document.querySelectorAll('.edit-button').forEach(button => {
        button.addEventListener('click', function(event) {
            const currentButton = event.currentTarget;
            const noteId = currentButton.closest('.note').getAttribute('data-note-id');
            window.location.replace(`/EmoNote/editnote?moodJournalId=${noteId}`);
        });
    });

    document.querySelectorAll('.delete-button').forEach(button => {
        button.addEventListener('click', function(event) {
            const currentButton = event.currentTarget;
            const noteId = currentButton.closest('.note').getAttribute('data-note-id');
            window.location.replace(`/EmoNote/deletenote?moodJournalId=${noteId}`);
        });
    });
});