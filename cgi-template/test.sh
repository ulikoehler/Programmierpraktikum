#!/bin/sh
#Start a local server on port 1500 with CGI
export GEM_PATH=`pwd`/../gems
../bin-linux-$(uname -p)/thttpd -d . -nor -p 1500 -c \*.cgi
