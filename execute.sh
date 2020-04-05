#!/bin/bash

# Created by BOURIAUD Thomas & RAFIKI Younes

FILE=projet_ia_weka.jar

# test if file exists
if [ ! -f "$FILE" ]; then
    echo "Cannot find the jar : $FILE."
    echo "Please download the project again."
    exit 1
fi

# use execution with 1 argument
if [ "$#" -eq 1 ]; then
    java -jar $FILE $1

# use execution with 3 arguments
elif [ "$#" -eq 3 ]; then
    java -jar $FILE $1 $2 $3

# error, please follow execution instructions
else
    echo "Wrong argument number !"
    echo "Please use one of these two options : "
    echo "     ./execute.sh weather.nominal.arff"
    echo "or"
    echo "     ./execute.sh weather.nominal.arff 0.55 0.005"
    exit 1
fi
