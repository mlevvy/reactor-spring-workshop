#!/bin/sh
find . -name '*.java' -type f -print0 |  xargs -0 sed -i '/DELETE ME/d'
find . -name '*.java' -type f -print0 |  xargs -0 sed -i '/\/\/ return null/c\\t\treturn null;'
find . -name '*.java' -type f -print0 |  xargs -0 sed -i '/\/\/ return/c\\t\treturn;'

