document.addEventListener('DOMContentLoaded', function() {
    var target = document.getElementById('total-downloads');
    if (!target) return;

    fetch('downloads.json')
        .then(function(res) { return res.json(); })
        .then(function(data) {
            if (data && typeof data.total === 'number' && data.total > 0) {
                target.textContent = data.total.toLocaleString() + ' | Downloads';
            } else {
                target.textContent = 'InternalError';
            }
        })
        .catch(function() {
            target.textContent = 'InternalError';
        });
});
