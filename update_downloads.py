import os
import requests

# 1. Fetch Modrinth downloads
mr_res = requests.get(
    'https://api.modrinth.com/v2/project/create-beyond-limits',
    headers={'User-Agent': 'CreateBeyondLimits-Bot/1.0'}
).json()
mr_count = mr_res.get('downloads', 0)

# 2. Fetch CFWidget downloads
cf_res = requests.get('https://api.cfwidget.com/1490004').json()
cf_count = cf_res.get('downloads', {}).get('total', 0)

total_downloads = mr_count + cf_count
print(f"Total downloads fetched: {total_downloads}")

# 3. MediaWiki API Config (REPLACE WITH YOUR WIKI API URL)
WIKI_API = 'https://YOUR_WIKI_DOMAIN/w/api.php'

session = requests.Session()

# Get login token
login_token = session.get(WIKI_API, params={
    'action': 'query', 'meta': 'tokens', 'type': 'login', 'format': 'json'
}).json()['query']['tokens']['logintoken']

# Login as bot
session.post(WIKI_API, data={
    'action': 'login',
    'lgname': os.environ['WIKI_USER'],
    'lgpassword': os.environ['WIKI_PASS'],
    'lgtoken': login_token,
    'format': 'json'
})

# Get CSRF token
csrf_token = session.get(WIKI_API, params={
    'action': 'query', 'meta': 'tokens', 'type': 'csrf', 'format': 'json'
}).json()['query']['tokens']['csrftoken']

# Update JSON page
res = session.post(WIKI_API, data={
    'action': 'edit',
    'title': 'MediaWiki:Custom-ModDownloads.json',
    'text': f'{{"total": {total_downloads}}}',
    'token': csrf_token,
    'summary': f'Automated update: {total_downloads} total downloads',
    'format': 'json'
})

print("Wiki response:", res.json())
