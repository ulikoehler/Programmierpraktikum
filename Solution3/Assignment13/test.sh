#!/bin/sh
#Start a local server on port 1500 with CGI
killall -9 thttpd 2>/dev/null
../../bin-linux-$(uname -p)/thttpd -d . -nor -p 1500 -c \*.cgi -l `pwd`/thttpd.log
