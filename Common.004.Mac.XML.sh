#!/bin/sh

cd bin


rm -R ./org/hy/common/xml/junit


jar cvfm xjava.jar MANIFEST.MF LICENSE org

cp xjava.jar ..
rm xjava.jar
cd ..

