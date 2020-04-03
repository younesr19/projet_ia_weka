#!/bin/bash

if [ "$#" -eq 1 ]; then
    java -jar projet_ia_weka_youyou.jar $1

elif [ "$#" -eq 3 ]; then
    java -jar projet_ia_weka_youyou.jar $1 $2 $3

else
    echo "Wrong argument number !"
    echo "Please use one of these two options : "
    echo "     ./execute.sh weather.nominal.arff"
    echo "or"
    echo "     ./execute.sh weather.nominal.arff 0.55 0.005"

fi