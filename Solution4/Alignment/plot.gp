set terminal png
set output "fpa_id xy_id2 test_zyx2.png"
set size ratio 0.5
set title "Fixed Point Alignment id xy vs. id2 test_zyx2"

set xlabel "id xy"
set ylabel "id2 test_zyx2"

set tic scale 0

set palette rgbformulae 22,13,10
set palette negative

set cbrange [-145.92518765893635:-11.399999999999995]
#unset cbtics

set xrange [0:254]
set yrange [0:257]

set view map

splot 'matrix.txt' matrix with image
