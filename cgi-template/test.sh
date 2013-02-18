#!/bin/sh
#Start a local server on port 1500 with CGI
../bin/thttpd -d . -nor -p 1500 -c \*.cgi
