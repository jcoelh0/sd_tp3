#!/usr/bin/env bash

rmiregistry -J-Djava.rmi.server.codebase="http://l040101-ws09.ua.pt/sd0301/src/"\
            -J-Djava.rmi.server.useCodebaseOnly=true $1