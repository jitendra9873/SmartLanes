from twilio.rest import Client
# from twilio.rest import TwilioRestClient
# Your Account SID from twilio.com/console
account_sid = "AC08422376f2d9a1f896bdf932b11dfa74"
# Your Auth Token from twilio.com/console
auth_token = "a319a22248f3581f07482198458195d8"

# client = TwilioRestClient(account_sid, auth_token)
client = Client(account_sid, auth_token)

message = client.messages.create(
    to="+917756048862",
    from_="+17208975434",
    body="Heya")

print(message.sid)
