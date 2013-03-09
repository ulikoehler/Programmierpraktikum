#! /bin/bash

#echo "crossvalidate: db -> training files & test files" >> debug
#chmod 700 crossvalidateExtract.sh 2>> debug
#bash crossvalidateExtract.sh ../train/uni/test.db 2>> debug
#bash crossvalidateExtract.sh ../train/uni/CB513DSSP.db 2>> debug

#echo "train: training files -> model files" >> debug
#chmod 700 crossvalidateTrain.sh 2>> debug
#bash crossvalidateTrain.sh 2>> debug

echo "predict: model files -> predict files" >> debug
chmod 700 crossvalidatePredict.sh 2>> debug
bash crossvalidatePredict.sh 2>> debug


