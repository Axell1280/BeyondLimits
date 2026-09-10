document.addEventListener('DOMContentLoaded', function() {
    var target = document.getElementById('total-downloads');
    if (!target) return;

    // Relative fetch works automatically when index.html and downloads.json are in the same GitHub repo
    fetch('downloads.json')
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
