#!/bin/bash
cd testqueries
ng build --base-href=/test/
cp -R dist/testqueries/browser/. ../../src/main/webapp/
cd ..