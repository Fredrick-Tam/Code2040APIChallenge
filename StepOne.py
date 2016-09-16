import requests as api
### Stepone.py: makes API call to code2040 server to register my github repo

# Create data object to send to endpoint
data = {
	"token": "f736e65019698ece59249b39364ec100",
	"github": "https://github.com/Fredrick-Tam/Code2040APIChallenge"
}

# Make api call to code2040 server
api_call = api.post("http://challenge.code2040.org/api/register", data )

# Print out the API response
print (api_call.text)

