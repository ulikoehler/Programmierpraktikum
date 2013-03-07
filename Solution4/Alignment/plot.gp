set terminal png
set output "fpa_id xy_id2 test_zyx2.png"
set size ratio 1.0
set title "Fixed Point Alignment id xy vs. id2 test_zyx2"

set xlabel "id xy"
set ylabel "id2 test_zyx2"

set tic scale 0

set palette rgbformulae 22,13,10
set palette negative

set cbrange [-236.60196617853506:-15.399999999999977]
#unset cbtics

set xrange [0:498]
set yrange [427:0]

set view map

splot 'fpa_id xy_id2 test_zyx2' matrix with image
