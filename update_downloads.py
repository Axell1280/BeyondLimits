import os
import requests

# 1. Fetch Modrinth downloads
mr_res = requests.get(
    'https://api.modrinth.com/v2/project/create-beyond-limits',
    headers={'User-Agent': 'CreateBeyondLimits-Bot/1.0'}
).json()
mr_count = mr_res.get('downloads', 0)

# 2. Fetch CFWidget downloads
cf_res = requests.get(
    'https://api.cfwidget.com/1490004',
    headers={'User-Agent': 'CreateBeyondLimits-Bot/1.0'}
).json()
cf_count = cf_res.get('downloads', {}).get('total', 0)

total_downloads = mr_count + cf_count
print(f"Total downloads fetched: {total_downloads}")

# 3. MediaWiki API Config
WIKI_API = 'https://beyondlimits.miraheze.org/w/api.php'

session = requests.Session()
# Set a custom User-Agent to prevent Miraheze/Cloudflare 403 blocks
session.headers.update({
    'User-Agent': 'CreateBeyondLimits-Bot/1.0 (https://beyondlimits.miraheze.org)'
})

# Get login token
res = session.get(WIKI_API, params={
    'action': 'query', 'meta': 'tokens', 'type': 'login', 'format': 'json'
})

# Safety check in case the wiki returns non-JSON content
if not res.headers.get('content-type', '').startswith('application/json'):
    print(f"[ERROR] Received non-JSON response from {WIKI_API} (Status Code: {res.status_code})")
    print(res.text[:300])
    exit(1)

login_token = res.json()['query']['tokens']['logintoken']

# Login as bot
login_res = session.post(WIKI_API, data={
    'action': 'login',
    'lgname': os.environ['WIKI_USER'],
    'lgpassword': os.environ['WIKI_PASS'],
    'lgtoken': login_token,
    'format': 'json'
}).json()

if login_res.get('login', {}).get('result') != 'Success':
    print("[ERROR] Login failed:", login_res)
    exit(1)

# Get CSRF token
csrf_token = session.get(WIKI_API, params={
    'action': 'query', 'meta': 'tokens', 'type': 'csrf', 'format': 'json'
}).json()['query']['tokens']['csrftoken']

# Update JSON page
edit_res = session.post(WIKI_API, data={
    'action': 'edit',
    'title': 'MediaWiki:Custom-ModDownloads.json',
    'text': f'{{"total": {total_downloads}}}',
    'token': csrf_token,
    'summary': f'Automated update: {total_downloads} total downloads',
    'format': 'json'
}).json()

print("Wiki edit response:", edit_res)
