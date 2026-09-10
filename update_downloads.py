import json
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

# 3. Save directly to a local JSON file
with open('downloads.json', 'w') as f:
    json.dump({'total': total_downloads}, f)

print("Saved total downloads to downloads.json")
