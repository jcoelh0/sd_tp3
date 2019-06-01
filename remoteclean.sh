#!/usr/bin/env bash
url=l040101-ws09.ua.pt/sd0301/
username=sd0301
password=qwerty
registryHostName=l040101-ws09.ua.pt
RepositoryHostName=l040101-ws01.ua.pt
LoungeHostName=l040101-ws02.ua.pt
OutsideWorldHostName=l040101-ws03.ua.pt
RepairAreaHostName=l040101-ws04.ua.pt
ParkHostName=l040101-ws05.ua.pt
SupplierSiteHostName=l040101-ws06.ua.pt
ManagerHostName=l040101-ws07.ua.pt
MechanicHostName=l040101-ws08.ua.pt
CustomerHostName=l040101-ws10.ua.pt
registryPortNum=22970

sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$registryHostName "cd Public ; rm -rf src ; rm deploy.tar.gz "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RepositoryHostName "cd Public ; rm -rf src ; rm deploy.tar.gz "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$LoungeHostName "cd Public ; rm -rf src ; rm deploy.tar.gz  "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$OutsideWorldHostName "cd Public ; rm -rf src ; rm deploy.tar.gz  "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RepairAreaHostName "cd Public ; rm -rf src ; rm deploy.tar.gz "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ParkHostName "cd Public ; rm -rf src ; rm deploy.tar.gz "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$SupplierSiteHostName "cd Public ; rm -rf src ; rm deploy.tar.gz "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ManagerHostName "cd Public ; rm -rf src ; rm deploy.tar.gz  "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$MechanicHostName "cd Public ; rm -rf src ; rm deploy.tar.gz  "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$CustomerHostName "cd Public ; rm -rf src ; rm deploy.tar.gz "
