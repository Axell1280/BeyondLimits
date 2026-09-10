document.addEventListener('DOMContentLoaded', function() {
    var target = document.getElementById('total-downloads');
    if (!target) return;

    fetch('downloads.json')
        .then(function(res) {
            if (!res.ok) throw new Error('HTTP ' + res.status);
            return res.json();
        })
        .then(function(data) {
            if (data && typeof data.total === 'number' && data.total > 0) {
                target.textContent = data.total.toLocaleString() + ' | Downloads';
            } else {
                target.textContent = 'InternalError';
            }
        })
        .catch(function(err) {
            console.error('Failed to load downloads.json:', err);
            target.textContent = 'InternalError';
        });
});
