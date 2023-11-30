$(document).ready(function() {
    $('.emoji').click(function() {
        $('.emoji').removeClass('highlight');

        $(this).addClass('highlight');

        var emojiAlt = $(this).attr('alt');
        $('#moodRatingInput').val(emojiAlt);
        event.preventDefault();
    });
});