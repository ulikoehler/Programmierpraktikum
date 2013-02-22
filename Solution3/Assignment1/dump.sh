#!/bin/sh
#Usage: dump.sh <target file>
mysqldump -u bioprakt4 -h mysql2-ext.bio.ifi.lmu.de -D bioprakt4 -P 3306 -pvGI5GCMg0x > $1
