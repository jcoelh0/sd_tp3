#!/usr/bin/env bash
url=http://l040101-ws09.ua.pt/sd0301/
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

echo "Building..."

bash build.sh

echo "Compressing..."

tar -czf deploy.tar.gz SD-ProbObrig3_3_1/src

sleep 5
   
echo "Sending compressed project..."

sshpass -p $password scp deploy.tar.gz $username@$registryHostName:~/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$registryHostName "tar -xmzf deploy.tar.gz" &

sshpass -p $password scp deploy.tar.gz $username@$RepositoryHostName:~/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RepositoryHostName "tar -xmzf deploy.tar.gz" &

sshpass -p $password scp deploy.tar.gz $username@$LoungeHostName:~/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$LoungeHostName "tar -xmzf deploy.tar.gz" &

sshpass -p $password scp deploy.tar.gz $username@$OutsideWorldHostName:~/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$OutsideWorldHostName "tar -xmzf deploy.tar.gz" &

sshpass -p $password scp deploy.tar.gz $username@$RepairAreaHostName:~/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RepairAreaHostName "tar -xmzf deploy.tar.gz" &

sshpass -p $password scp deploy.tar.gz $username@$ParkHostName:~/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ParkHostName "tar -xmzf deploy.tar.gz" &

sshpass -p $password scp deploy.tar.gz $username@$SupplierSiteHostName:~/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$SupplierSiteHostName "tar -xmzf deploy.tar.gz" &

sshpass -p $password scp deploy.tar.gz $username@$ManagerHostName:~/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ManagerHostName "tar -xmzf deploy.tar.gz" &

sshpass -p $password scp deploy.tar.gz $username@$MechanicHostName:~/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$MechanicHostName "tar -xmzf deploy.tar.gz" &

sshpass -p $password scp deploy.tar.gz $username@$CustomerHostName:~/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$CustomerHostName "tar -xmzf deploy.tar.gz" &

sleep 1

echo "Running project..."

echo "Setting rmi"

sshpass -p $password scp set_registry.sh $username@$registryHostName:~/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$registryHostName "sh set_registry.sh $registryPortNum" &
sleep 5

echo "Setting service register"

sshpass -p $password scp registry.sh $username@$registryHostName:~/SD-ProbObrig3_3_1/src/deploy
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$registryHostName "cd ~/SD-ProbObrig3_3_1/src/deploy ; bash registry.sh $registryHostName $registryPortNum $url" &
sleep 5

echo "Setting repository"

sshpass -p $password scp repository.sh $username@$RepositoryHostName:~/SD-ProbObrig3_3_1/src/deploy
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RepositoryHostName "cd ~/SD-ProbObrig3_3_1/src/deploy ; bash repository.sh $registryHostName $registryPortNum $url" &
sleep 5

echo "Setting lounge"

sshpass -p $password scp lounge.sh $username@$LoungeHostName:~/SD-ProbObrig3_3_1/src/deploy
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$LoungeHostName "cd ~/SD-ProbObrig3_3_1/src/deploy ; bash lounge.sh $registryHostName $registryPortNum $url" &
sleep 5

echo "Setting outsideworld"

sshpass -p $password scp outsideworld.sh $username@$OutsideWorldHostName:~/SD-ProbObrig3_3_1/src/deploy
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$OutsideWorldHostName "cd ~/SD-ProbObrig3_3_1/src/deploy ; bash outsideworld.sh $registryHostName $registryPortNum $url" &
sleep 5

echo "Setting repairarea"

sshpass -p $password scp repairarea.sh $username@$RepairAreaHostName:~/SD-ProbObrig3_3_1/src/deploy
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RepairAreaHostName "cd ~/SD-ProbObrig3_3_1/src/deploy ; bash repairarea.sh $registryHostName $registryPortNum $url" &
sleep 5

echo "Setting park"

sshpass -p $password scp park.sh $username@$ParkHostName:~/SD-ProbObrig3_3_1/src/deploy
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ParkHostName "cd ~/SD-ProbObrig3_3_1/src/deploy ; bash park.sh $registryHostName $registryPortNum $url" &
sleep 5

echo "Setting suppliersite"

sshpass -p $password scp suppliersite.sh $username@$SupplierSiteHostName:~/SD-ProbObrig3_3_1/src/deploy
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$SupplierSiteHostName "cd ~/SD-ProbObrig3_3_1/src/deploy ; bash suppliersite.sh $registryHostName $registryPortNum $url" &
sleep 5

echo "Setting manager"

sshpass -p $password scp manager.sh $username@$ManagerHostName:~/SD-ProbObrig3_3_1/src/deploy
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ManagerHostName "cd ~/SD-ProbObrig3_3_1/src/deploy ; bash manager.sh $registryHostName $registryPortNum $url" &
sleep 5

echo "Setting mechanic"

sshpass -p $password scp mechanic.sh $username@$MechanicHostName:~/SD-ProbObrig3_3_1/src/deploy
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$MechanicHostName "cd ~/SD-ProbObrig3_3_1/src/deploy ; bash mechanic.sh $registryHostName $registryPortNum $url" &
sleep 5

echo "Setting customer"

sshpass -p $password scp customer.sh $username@$CustomerHostName:~/SD-ProbObrig3_3_1/src/deploy
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$CustomerHostName "cd ~/SD-ProbObrig3_3_1/src/deploy ; bash customer.sh $registryHostName $registryPortNum $url" &
sleep 5

echo "Stopping rmi processes"

bash kill_rmi.sh

echo "Cleaning local classes"

bash cleanclasses.sh

echo "Cleaning remote machines"

#bash remoteclean.sh