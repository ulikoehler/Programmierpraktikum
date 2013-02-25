data/pdbids:
	wget -qO- http://www.rcsb.org/pdb/rest/getCurrent | grep structureId | cut -d\" -f2 > data/pdbids

data/swissprotids:
	bash -c 'wget -qO- "http://www.uniprot.org/uniprot/?query=reviewed%3ayes&force=yes&format=tab&columns=id" > data/swissprotids'

all: data/pdbids data/swissprotids