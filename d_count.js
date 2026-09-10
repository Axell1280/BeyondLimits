$(document).ready(function() {
    var $target = $('#total-downloads');
    if (!$target.length) return;

    var jsonUrl = 'https://cdn.jsdelivr.net/gh/Axell1280/BeyondLimits@main/downloads.json';

    fetch(jsonUrl)
        .then(function(res) { return res.json(); })
        .then(function(data) {
            if (data && typeof data.total === 'number' && data.total > 0) {
                $target.text(data.total.toLocaleString()+'|Downloads');
            } else {
                $target.text('InternalError');
            }
        })
        .catch(function() {
            $target.text('InternalError');
        });
});
