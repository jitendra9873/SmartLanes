from django.contrib import admin
from .models import appUser, vehicle, tollPlaza, tollBooth, transaction


admin.site.register(appUser)
admin.site.register(vehicle)
admin.site.register(tollPlaza)
admin.site.register(tollBooth)
admin.site.register(transaction)
