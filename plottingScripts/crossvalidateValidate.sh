#! /bin/bash

for f in results/predictions/*
do

  if [ -f $f ]
  then

    b=$(basename $f)
    db=$(echo $b | cut -c 5- | grep -o -e '^[^x]*' | sed -e 's/.$//g')

    java -jar validateGor.jar --predictions $f --dssp-file ../train/uni/$db --format txt -s results/summary/$b -d results/detailed/$b
  fi

done

exit 0

# simple

for i in $(seq 0 9)
do
java -jar validateGor.jar --predictions results/predictions/std/gor3test.db${i}x17stdf --dssp-file ../train/uni/test.db --format txt -s results/summary/std/gor3test.db${i}x17stdf -d results/detailed/gor3test.db${i}x17stdf
java -jar validateGor.jar --predictions results/predictions/avg/gor3test.db${i}x17avgf --dssp-file ../train/uni/test.db --format txt -s results/summary/avg/gor3test.db${i}x17avgf -d results/detailed/gor3test.db${i}x17avgf
done

exit(0)
# validate test.db

java -jar validateGor.jar --predictions results/predictions/gor1test.db0x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor1test.db0x17 -d results/detailed/gor1test.db0x17
java -jar validateGor.jar --predictions results/predictions/gor1test.db1x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor1test.db1x17 -d results/detailed/gor1test.db1x17
java -jar validateGor.jar --predictions results/predictions/gor1test.db2x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor1test.db2x17 -d results/detailed/gor1test.db2x17
java -jar validateGor.jar --predictions results/predictions/gor1test.db3x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor1test.db3x17 -d results/detailed/gor1test.db3x17
java -jar validateGor.jar --predictions results/predictions/gor1test.db4x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor1test.db4x17 -d results/detailed/gor1test.db4x17
java -jar validateGor.jar --predictions results/predictions/gor1test.db5x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor1test.db5x17 -d results/detailed/gor1test.db5x17
java -jar validateGor.jar --predictions results/predictions/gor1test.db6x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor1test.db6x17 -d results/detailed/gor1test.db6x17
java -jar validateGor.jar --predictions results/predictions/gor1test.db7x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor1test.db7x17 -d results/detailed/gor1test.db7x17
java -jar validateGor.jar --predictions results/predictions/gor1test.db8x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor1test.db8x17 -d results/detailed/gor1test.db8x17
java -jar validateGor.jar --predictions results/predictions/gor1test.db9x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor1test.db9x17 -d results/detailed/gor1test.db9x17

java -jar validateGor.jar --predictions results/predictions/gor3test.db0x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor3test.db0x17 -d results/detailed/gor3test.db0x17
java -jar validateGor.jar --predictions results/predictions/gor3test.db1x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor3test.db1x17 -d results/detailed/gor3test.db1x17
java -jar validateGor.jar --predictions results/predictions/gor3test.db2x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor3test.db2x17 -d results/detailed/gor3test.db2x17
java -jar validateGor.jar --predictions results/predictions/gor3test.db3x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor3test.db3x17 -d results/detailed/gor3test.db3x17
java -jar validateGor.jar --predictions results/predictions/gor3test.db4x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor3test.db4x17 -d results/detailed/gor3test.db4x17
java -jar validateGor.jar --predictions results/predictions/gor3test.db5x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor3test.db5x17 -d results/detailed/gor3test.db5x17
java -jar validateGor.jar --predictions results/predictions/gor3test.db6x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor3test.db6x17 -d results/detailed/gor3test.db6x17
java -jar validateGor.jar --predictions results/predictions/gor3test.db7x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor3test.db7x17 -d results/detailed/gor3test.db7x17
java -jar validateGor.jar --predictions results/predictions/gor3test.db8x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor3test.db8x17 -d results/detailed/gor3test.db8x17
java -jar validateGor.jar --predictions results/predictions/gor3test.db9x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor3test.db9x17 -d results/detailed/gor3test.db9x17

java -jar validateGor.jar --predictions results/predictions/gor4test.db0x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor4test.db0x17 -d results/detailed/gor4test.db0x17
java -jar validateGor.jar --predictions results/predictions/gor4test.db1x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor4test.db1x17 -d results/detailed/gor4test.db1x17
java -jar validateGor.jar --predictions results/predictions/gor4test.db2x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor4test.db2x17 -d results/detailed/gor4test.db2x17
java -jar validateGor.jar --predictions results/predictions/gor4test.db3x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor4test.db3x17 -d results/detailed/gor4test.db3x17
java -jar validateGor.jar --predictions results/predictions/gor4test.db4x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor4test.db4x17 -d results/detailed/gor4test.db4x17
java -jar validateGor.jar --predictions results/predictions/gor4test.db5x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor4test.db5x17 -d results/detailed/gor4test.db5x17
java -jar validateGor.jar --predictions results/predictions/gor4test.db6x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor4test.db6x17 -d results/detailed/gor4test.db6x17
java -jar validateGor.jar --predictions results/predictions/gor4test.db7x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor4test.db7x17 -d results/detailed/gor4test.db7x17
java -jar validateGor.jar --predictions results/predictions/gor4test.db8x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor4test.db8x17 -d results/detailed/gor4test.db8x17
java -jar validateGor.jar --predictions results/predictions/gor4test.db9x17 --dssp-file ../train/uni/test.db --format txt -s results/summary/gor4test.db9x17 -d results/detailed/gor4test.db9x17

# validate cb513
java -jar validateGor.jar --predictions results/predictions/gor1CB513DSSP.db0x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor1CB513DSSP.db0x17 -d results/detailed/gor1CB513DSSP.db0x17
java -jar validateGor.jar --predictions results/predictions/gor1CB513DSSP.db1x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor1CB513DSSP.db1x17 -d results/detailed/gor1CB513DSSP.db1x17
java -jar validateGor.jar --predictions results/predictions/gor1CB513DSSP.db2x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor1CB513DSSP.db2x17 -d results/detailed/gor1CB513DSSP.db2x17
java -jar validateGor.jar --predictions results/predictions/gor1CB513DSSP.db3x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor1CB513DSSP.db3x17 -d results/detailed/gor1CB513DSSP.db3x17
java -jar validateGor.jar --predictions results/predictions/gor1CB513DSSP.db4x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor1CB513DSSP.db4x17 -d results/detailed/gor1CB513DSSP.db4x17
java -jar validateGor.jar --predictions results/predictions/gor1CB513DSSP.db5x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor1CB513DSSP.db5x17 -d results/detailed/gor1CB513DSSP.db5x17
java -jar validateGor.jar --predictions results/predictions/gor1CB513DSSP.db6x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor1CB513DSSP.db6x17 -d results/detailed/gor1CB513DSSP.db6x17
java -jar validateGor.jar --predictions results/predictions/gor1CB513DSSP.db7x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor1CB513DSSP.db7x17 -d results/detailed/gor1CB513DSSP.db7x17
java -jar validateGor.jar --predictions results/predictions/gor1CB513DSSP.db8x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor1CB513DSSP.db8x17 -d results/detailed/gor1CB513DSSP.db8x17
java -jar validateGor.jar --predictions results/predictions/gor1CB513DSSP.db9x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor1CB513DSSP.db9x17 -d results/detailed/gor1CB513DSSP.db9x17

java -jar validateGor.jar --predictions results/predictions/gor3CB513DSSP.db0x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor3CB513DSSP.db0x17 -d results/detailed/gor3CB513DSSP.db0x17
java -jar validateGor.jar --predictions results/predictions/gor3CB513DSSP.db1x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor3CB513DSSP.db1x17 -d results/detailed/gor3CB513DSSP.db1x17
java -jar validateGor.jar --predictions results/predictions/gor3CB513DSSP.db2x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor3CB513DSSP.db2x17 -d results/detailed/gor3CB513DSSP.db2x17
java -jar validateGor.jar --predictions results/predictions/gor3CB513DSSP.db3x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor3CB513DSSP.db3x17 -d results/detailed/gor3CB513DSSP.db3x17
java -jar validateGor.jar --predictions results/predictions/gor3CB513DSSP.db4x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor3CB513DSSP.db4x17 -d results/detailed/gor3CB513DSSP.db4x17
java -jar validateGor.jar --predictions results/predictions/gor3CB513DSSP.db5x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor3CB513DSSP.db5x17 -d results/detailed/gor3CB513DSSP.db5x17
java -jar validateGor.jar --predictions results/predictions/gor3CB513DSSP.db6x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor3CB513DSSP.db6x17 -d results/detailed/gor3CB513DSSP.db6x17
java -jar validateGor.jar --predictions results/predictions/gor3CB513DSSP.db7x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor3CB513DSSP.db7x17 -d results/detailed/gor3CB513DSSP.db7x17
java -jar validateGor.jar --predictions results/predictions/gor3CB513DSSP.db8x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor3CB513DSSP.db8x17 -d results/detailed/gor3CB513DSSP.db8x17
java -jar validateGor.jar --predictions results/predictions/gor3CB513DSSP.db9x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor3CB513DSSP.db9x17 -d results/detailed/gor3CB513DSSP.db9x17

java -jar validateGor.jar --predictions results/predictions/gor4CB513DSSP.db0x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor4CB513DSSP.db0x17 -d results/detailed/gor4CB513DSSP.db0x17
java -jar validateGor.jar --predictions results/predictions/gor4CB513DSSP.db1x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor4CB513DSSP.db1x17 -d results/detailed/gor4CB513DSSP.db1x17
java -jar validateGor.jar --predictions results/predictions/gor4CB513DSSP.db2x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor4CB513DSSP.db2x17 -d results/detailed/gor4CB513DSSP.db2x17
java -jar validateGor.jar --predictions results/predictions/gor4CB513DSSP.db3x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor4CB513DSSP.db3x17 -d results/detailed/gor4CB513DSSP.db3x17
java -jar validateGor.jar --predictions results/predictions/gor4CB513DSSP.db4x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor4CB513DSSP.db4x17 -d results/detailed/gor4CB513DSSP.db4x17
java -jar validateGor.jar --predictions results/predictions/gor4CB513DSSP.db5x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor4CB513DSSP.db5x17 -d results/detailed/gor4CB513DSSP.db5x17
java -jar validateGor.jar --predictions results/predictions/gor4CB513DSSP.db6x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor4CB513DSSP.db6x17 -d results/detailed/gor4CB513DSSP.db6x17
java -jar validateGor.jar --predictions results/predictions/gor4CB513DSSP.db7x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor4CB513DSSP.db7x17 -d results/detailed/gor4CB513DSSP.db7x17
java -jar validateGor.jar --predictions results/predictions/gor4CB513DSSP.db8x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor4CB513DSSP.db8x17 -d results/detailed/gor4CB513DSSP.db8x17
java -jar validateGor.jar --predictions results/predictions/gor4CB513DSSP.db9x17 --dssp-file ../train/uni/CB513DSSP.db --format txt -s results/summary/gor4CB513DSSP.db9x17 -d results/detailed/gor4CB513DSSP.db9x17

