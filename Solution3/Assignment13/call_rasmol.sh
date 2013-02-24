#!/bin/bash
if [ $# -eq 0 ]
  then
    echo "Usage: call_rasmol <PDB ID>"
    exit
fi
#Use a different workdir. Can't use mktemp because the directory must be mounted via NFS)
cd /home/k/koehleru/public_html/bioprakt/cgi/
export workdir=/home/k/koehleru/public_html/bioprakt/cgi/tmp
#Download
wget -qO $workdir/$1.pdb http://www.rcsb.org/pdb/files/$1.pdb
#Create the rasmol script
echo "load $1.pdb;" > $workdir/$1.pml
echo "bg_color white;" >> $workdir/$1.pml
echo "set ray_opaque_background, on;" >> $workdir/$1.pml
echo "show cartoon;" >> $workdir/$1.pml
echo "color purple, ss h;" >> $workdir/$1.pml
echo "color yellow, ss s;" >> $workdir/$1.pml
echo "color gree, ss "";" >> $workdir/$1.pml
echo "ray 1000,1000;" >> $workdir/$1.pml
echo "png $1.png;" >> $workdir/$1.pml
#echo "quit;" >> $1.pml
#Execute RASMOL
ssh -i ~/.ssh/id_dsa koehleru@remote.cip.ifi.lmu.de "cd $workdir && pymol -qxci $1.pml"
sleep 0.2 #OH DUDE WE RLY HAVE TO WAIT FOR THE FILE TO SHOW UP ON NFS
#Move the file
mv $workdir/$1.png pdbcache/
#Remove tmp files
rm -rf $workdir/$1.pml $workdir/$1.pdb
