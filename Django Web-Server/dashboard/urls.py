from django.conf.urls import url
from . import views

app_name = "dashboard"

urlpatterns = [
    url(r'^transact/(?P<uid>\d+)/(?P<lPlate>[\w-]+)/$', views.transact, name='transact'),
    url(r'^dash/$', views.dash, name='dash'),
    url(r'^radarRender/$', views.radarRender, name='radarRender'),
    url(r'^radarAPI/$', views.radarAPI, name='radarAPI'),
    # url(r'^details/(?P<book_id>\d+)/$', views.details, name='details'),
]
