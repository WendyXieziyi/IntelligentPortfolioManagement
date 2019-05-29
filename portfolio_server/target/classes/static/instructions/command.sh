#!/bin/sh
cd $1
nohup python3 main.py --mode=train --processes=1