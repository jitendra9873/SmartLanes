from django.shortcuts import render
from django.http import HttpResponse
from .models import appUser, transaction, vehicle, tollBooth, points
from django.utils.crypto import get_random_string
from django.views.decorators.csrf import csrf_exempt
from django.http import JsonResponse

import json

from twilio.rest import Client


def transact(request, uid, lPlate):
    amount = 20
    userObj = appUser.objects.filter(aadharID=uid)[0]
    userObj.balance = userObj.balance-amount
    number = '+91' + userObj.mobileNo
    userObj.save()
    vehicleObj = vehicle.objects.filter(user=userObj).filter(licencePlate=lPlate)
    boothObj = tollBooth.objects.all()[0]
    transactionObj = transaction.objects.create(tVehicle=vehicleObj[0], tBooth=boothObj, tID=get_random_string(length=30), amount=20)

    # from twilio.rest import TwilioRestClient
    # Your Account SID from twilio.com/console
    account_sid = "AC08422376f2d9a1f896bdf932b11dfa74"
    # Your Auth Token from twilio.com/console
    auth_token = "a319a22248f3581f07482198458195d8"

    # client = TwilioRestClient(account_sid, auth_token)
    client = Client(account_sid, auth_token)

    message = client.messages.create(
        to=number,
        from_="+17208975434",
        body="Transaction " + transactionObj.tID + " successful. Balance: Rs." + str(userObj.balance))
    print("Message sent to: " + userObj.userName + "\nLog: " + str(message.sid))

    return HttpResponse(
        '<h1>' +
        str(transactionObj.tVehicle) +
        '&nbsp&nbsp' +
        str(transactionObj.tID) +
        '&nbsp&nbsp' +
        str(transactionObj.tVehicle.user.userName) +
        '&nbsp&nbsp' +
        str(transactionObj.tVehicle.user.balance)
        + '</h1>'
    )


def dash(request):
    transactionList = list(transaction.objects.all().order_by('-timestamp'))[:3]

    htmlResponse = '<p>'
    for transactionObj in transactionList:
        htmlResponse += '<h1>' + str(transactionObj.tVehicle) + '&nbsp&nbsp' + str(transactionObj.tID) + '&nbsp&nbsp' + str(transactionObj.tVehicle.user.userName) + '&nbsp&nbsp' + str(transactionObj.tVehicle.user.balance) + '</h1>'
    htmlResponse += '</p>'
    return HttpResponse(htmlResponse)


@csrf_exempt
def radarAPI(request):
    if request.method == "POST":
        points.objects.all().delete()
        jsonData = json.loads(request.body.decode("utf-8"))
        pointObj = points.objects.create(jsonData=json.dumps(jsonData))
        print(jsonData)
    else:
        jsonData = json.loads(list(points.objects.all())[0].jsonData)
    return JsonResponse(jsonData)


def radarRender(request):
    return render(request, "radar.html")
