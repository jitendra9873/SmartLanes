from django.db import models


# Create your models here.
class appUser(models.Model):
    aadharID = models.CharField(max_length=12, primary_key=True)
    userName = models.CharField(max_length=40)
    mobileNo = models.CharField(max_length=10)
    key = models.CharField(max_length=20)
    balance = models.IntegerField()
    licenceID = models.CharField(max_length=16)
    licenceExpiry = models.CharField(max_length=10)
    age = models.IntegerField()

    def __str__(self):
        return self.userName


class vehicle(models.Model):
    user = models.ForeignKey(appUser, on_delete=models.CASCADE)
    licencePlate = models.CharField(max_length=10, primary_key=True)
    vehicleType = models.CharField(max_length=10)
    vehicleModel = models.CharField(max_length=20)
    vehicleState = models.CharField(max_length=10)

    def __str__(self):
        return self.licencePlate


class tollPlaza(models.Model):
    plazaName = models.CharField(max_length=20, primary_key=True)
    latitude = models.CharField(max_length=20)
    longitude = models.CharField(max_length=20)

    def __str__(self):
        return self.plazaName


class tollBooth(models.Model):
    belongsTo = models.ForeignKey(tollPlaza, on_delete=models.CASCADE)
    direction = models.CharField(max_length=10)
    tollBoothID = models.CharField(max_length=20, primary_key=True)
    tollBoothType = models.CharField(max_length=10)

    def __str__(self):
        return self.belongsTo.plazaName + self.tollBoothID


class transaction(models.Model):
    tVehicle = models.ForeignKey(vehicle, on_delete=models.CASCADE)
    tBooth = models.ForeignKey(tollBooth, on_delete=models.CASCADE)
    tID = models.CharField(max_length=30)
    amount = models.CharField(max_length=10)
    timestamp = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return self.tID


class points(models.Model):
    jsonData = models.CharField(max_length=10000)
