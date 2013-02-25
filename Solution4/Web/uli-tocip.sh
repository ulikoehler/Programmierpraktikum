#!/bin/sh
rsync --delete -ravze ssh * koehleru@remote.cip.ifi.lmu.de:public_html/propra/
