set terminal png
set output "fixedPointAlignment.png"
set size ratio 0.5
set title "Fixed Point Alignment"

set xlabel "Sequence 1"
set ylabel "Sequence 2"

set tic scale 0

set palette rgbformulae 22,13,10
set palette negative

set cbrange [-159.4:4.9]
#unset cbtics

set xrange [0:69]
set yrange [0:65]

set view map

splot 'matrix.txt' matrix with image
