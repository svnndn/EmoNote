document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.non-more-button').forEach(button => {
        button.addEventListener('click', function(event) {
            const currentButton = event.currentTarget;
            const noteId = currentButton.closest('.note').getAttribute('data-note-id');
            window.location.replace(`/EmoNote/comments?moodJournalId=${noteId}`);
        });
    });
});