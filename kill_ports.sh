#!/bin/bash
sshpass -p qwerty ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws07.ua.pt "/usr/sbin/fuser -n tcp -k 22316"
sshpass -p qwerty ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws08.ua.pt "/usr/sbin/fuser -n tcp -k 22317"
sshpass -p qwerty ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws10.ua.pt "/usr/sbin/fuser -n tcp -k 22318"


sshpass -p qwerty ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws01.ua.pt "/usr/sbin/fuser -n tcp -k 22310"

sshpass -p qwerty ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws02.ua.pt "/usr/sbin/fuser -n tcp -k 22311"
sshpass -p qwerty ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws03.ua.pt "/usr/sbin/fuser -n tcp -k 22312"
sshpass -p qwerty ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws04.ua.pt "/usr/sbin/fuser -n tcp -k 22313"
sshpass -p qwerty ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws05.ua.pt "/usr/sbin/fuser -n tcp -k 22314"
sshpass -p qwerty ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws06.ua.pt "/usr/sbin/fuser -n tcp -k 22315"

"exit"
