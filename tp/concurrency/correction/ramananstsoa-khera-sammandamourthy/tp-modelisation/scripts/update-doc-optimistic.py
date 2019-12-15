import requests

#Request A GET
response = requests.get("http://localhost:9090/api/docs/5dee935abda4682f900f867e")
request_1 = response.json()
print ("User A get:", request_1)

#Request B GET
response = requests.get("http://localhost:9090/api/docs/5dee935abda4682f900f867e")
request_2 = response.json()
print ("User B get:", request_2)

#Request B Update
request_2["title"] = "New awsome TV"
response = requests.put("http://localhost:9090/api/docs/5dee935abda4682f900f867e", json = request_2)
print ("User B save:", response.json())

#Request A Update (Blows up on a lock exception)
request_1["title"] = "Newer awesomer flatscreen TV"
response = requests.put("http://localhost:9090/api/docs/5dee935abda4682f900f867e", json = request_1)
print ("User A save:", response.json())