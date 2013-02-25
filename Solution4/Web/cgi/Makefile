pdbids:
	wget -qO- http://www.rcsb.org/pdb/rest/getCurrent | grep structureId | cut -d\" -f2 > pdbids
	
all: pdbids