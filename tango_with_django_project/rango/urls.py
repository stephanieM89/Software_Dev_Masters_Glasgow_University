from django.conf.urls import url
from rango import views

urlpatterns = [
    url(r'^$', views.index, name='index'),  # Maps the basic URL to the index view in the rango app
    url(r'about/$', views.about, name='about'),  # Maps the URL to the about view in the rango app
    url(r'^add_category/$', views.add_category, name='add_category'),
    url(r'^category/(?P<category_name_slug>[\w\-]+)/$', views.show_category, name='show_category'),
    url(r'^category/(?P<category_name_slug>[\w\-]+)/add_page/$', views.add_page, name='add_page'),
    url(r'^register/$', views.register, name='register'),
    url(r'^login/$', views.user_login, name='login'),
    url(r'^restricted/', views.restricted, name='restricted'),
    url(r'^logout/$', views.user_logout, name='logout'),
]