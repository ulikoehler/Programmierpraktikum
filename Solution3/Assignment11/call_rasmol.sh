#!/bin/bash
if [ $# -eq 0 ]
  then
    echo "Usage: call_rasmol <PDB ID>"
    exit
fi
#Change working 
cd /home/k/koehleru/public_html/bioprakt/cgi/
#Download
wget -qO $1.pdb http://www.rcsb.org/pdb/files/$1.pdb
#Create the rasmol script
echo "load $1.pdb;" > $1.pml
echo "bg_color white;" >> $1.pml
echo "set ray_opaque_background, on;" >> $1.pml
echo "show cartoon;" >> $1.pml
echo "color purple, ss h;" >> $1.pml
echo "color yellow, ss s;" >> $1.pml
echo "color gree, ss "";" >> $1.pml
echo "ray 1000,1000;" >> $1.pml
echo "png $1.png;" >> $1.pml
#echo "quit;" >> $1.pml
#Execute RASMOL
export wd=`pwd`
sh -i ~/.ssh/id_dsa koehleru@remote.cip.ifi.lmu.de "cd /home/k/koehleru/public_html/bioprakt/cgi/ && pymol -qxci 1ULI.pml"
mv $1.png pdbcache/
#Remove tmp files
#rm -rf $1.pml $1.pdb

