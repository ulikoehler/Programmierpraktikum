df1 <- read.table(header=T, con <- textConnection('
Größe Algorithmus Laufzeit
1	Dynamisch	0.364
1	Rekursiv	0.356
2	Dynamisch	0.336
2	Rekursiv	0.348
3	Dynamisch	0.332
3	Rekursiv	0.328
4	Dynamisch	0.352
4	Rekursiv	0.340
5	Dynamisch	0.364
5	Rekursiv	0.344
6	Dynamisch	0.344
6	Rekursiv	0.372
7	Dynamisch	0.328
7	Rekursiv	0.584
8	Dynamisch	0.360
8	Rekursiv	0.696
9	Dynamisch	0.364
9	Rekursiv	0.732
10	Dynamisch	0.328
10	Rekursiv	1.188
11	Dynamisch	0.327
11	Rekursiv	3.596
12	Dynamisch	0.340
12	Rekursiv	17.189
13	Dynamisch	0.340
13	Rekursiv	93.582
14	Dynamisch	0.380
'))
close(con)

#Original size

#df1 <- transform(df1, Algorithm = reorder(Algorithm, Index))
library(ggplot2)

png(file="rec.png",height=300,width=500)


  print(ggplot(data=df1, aes(x=Größe, y=Laufzeit, group=Algorithmus, colour=Algorithmus)) +
      geom_line() + # Thinner lines
    scale_fill_hue(name="Algorithmus") +      # Set legend title
    xlab("Inputgröße (n*n)") + ylab("Laufzeit (s)") + # Set axis labels
    ggtitle("Rekursiver vs. Dynamischer Algorithmus") +  # Set title
      theme())
