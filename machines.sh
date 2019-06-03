#!/usr/bin/env bash
url=http://l040101-ws09.ua.pt/sd0301/src/
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

echo " "
echo "Compressing..."

cd SD-ProbObrig3_3_1/

tar -czf deploy.tar.gz src/

cd ..

sleep 5
   
echo " "
echo "Sending compressed project to the remote machines..."

sshpass -p $password scp SD-ProbObrig3_3_1/deploy.tar.gz $username@$registryHostName:~/Public
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$registryHostName "cd ~/Public ; tar -xmzf deploy.tar.gz" &

sshpass -p $password scp SD-ProbObrig3_3_1/deploy.tar.gz $username@$RepositoryHostName:~/Public
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RepositoryHostName "cd ~/Public ; tar -xmzf deploy.tar.gz" &

sshpass -p $password scp SD-ProbObrig3_3_1/deploy.tar.gz $username@$LoungeHostName:~/Public
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$LoungeHostName "cd ~/Public ; tar -xmzf deploy.tar.gz" &

sshpass -p $password scp SD-ProbObrig3_3_1/deploy.tar.gz $username@$OutsideWorldHostName:~/Public
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$OutsideWorldHostName "cd ~/Public ; tar -xmzf deploy.tar.gz" &

sshpass -p $password scp SD-ProbObrig3_3_1/deploy.tar.gz $username@$RepairAreaHostName:~/Public
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RepairAreaHostName "cd ~/Public ; tar -xmzf deploy.tar.gz" &

sshpass -p $password scp SD-ProbObrig3_3_1/deploy.tar.gz $username@$ParkHostName:~/Public
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ParkHostName "cd ~/Public ; tar -xmzf deploy.tar.gz" &

sshpass -p $password scp SD-ProbObrig3_3_1/deploy.tar.gz $username@$SupplierSiteHostName:~/Public
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$SupplierSiteHostName "cd ~/Public ; tar -xmzf deploy.tar.gz" &

sshpass -p $password scp SD-ProbObrig3_3_1/deploy.tar.gz $username@$ManagerHostName:~/Public
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ManagerHostName "cd ~/Public ; tar -xmzf deploy.tar.gz" &

sshpass -p $password scp SD-ProbObrig3_3_1/deploy.tar.gz $username@$MechanicHostName:~/Public
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$MechanicHostName "cd ~/Public ; tar -xmzf deploy.tar.gz" &

sshpass -p $password scp SD-ProbObrig3_3_1/deploy.tar.gz $username@$CustomerHostName:~/Public
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$CustomerHostName "cd ~/Public ; tar -xmzf deploy.tar.gz" &

sleep 1

echo " "
echo "Running project on remote machines:"

echo "- Setting RMI..."

sshpass -p $password scp set_registry.sh $username@$registryHostName:~/Public/src
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$registryHostName "cd ~/Public/src ; sh set_registry.sh $registryPortNum" &
sleep 5

echo " "
echo "- Starting Service Register..."

sshpass -p $password scp registry.sh $username@$registryHostName:~/Public/src
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$registryHostName "cd ~/Public/src ; bash registry.sh $registryHostName $registryPortNum $url" &
sleep 5

echo " "
echo "- Starting Repository..."

sshpass -p $password scp repository.sh $username@$RepositoryHostName:~/Public/src
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RepositoryHostName "cd ~/Public/src ; bash repository.sh $registryHostName $registryPortNum $url" &
sleep 5

echo " "
echo "- Starting Lounge..."

sshpass -p $password scp lounge.sh $username@$LoungeHostName:~/Public/src
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$LoungeHostName "cd ~/Public/src ; bash lounge.sh $registryHostName $registryPortNum $url" &
sleep 5

echo " "
echo "- Starting OutsideWorld..."

sshpass -p $password scp outsideworld.sh $username@$OutsideWorldHostName:~/Public/src
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$OutsideWorldHostName "cd ~/Public/src ; bash outsideworld.sh $registryHostName $registryPortNum $url" &
sleep 5

echo " "
echo "- Starting RepairArea..."

sshpass -p $password scp repairarea.sh $username@$RepairAreaHostName:~/Public/src
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RepairAreaHostName "cd ~/Public/src ; bash repairarea.sh $registryHostName $registryPortNum $url" &
sleep 5

echo " "
echo "- Starting Park..."

sshpass -p $password scp park.sh $username@$ParkHostName:~/Public/src
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ParkHostName "cd ~/Public/src ; bash park.sh $registryHostName $registryPortNum $url" &
sleep 5

echo " "
echo "- Starting SupplierSite..."

sshpass -p $password scp suppliersite.sh $username@$SupplierSiteHostName:~/Public/src
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$SupplierSiteHostName "cd ~/Public/src ; bash suppliersite.sh $registryHostName $registryPortNum $url" &
sleep 5

echo " "
echo "- Starting Manager..."

sshpass -p $password scp manager.sh $username@$ManagerHostName:~/Public/src
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ManagerHostName "cd ~/Public/src ; bash manager.sh $registryHostName $registryPortNum $url" &
sleep 5

echo " "
echo "- Starting Mechanics..."

sshpass -p $password scp mechanic.sh $username@$MechanicHostName:~/Public/src
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$MechanicHostName "cd ~/Public/src ; bash mechanic.sh $registryHostName $registryPortNum $url" &
sleep 5

echo " "
echo "- Starting Customers..."

sshpass -p $password scp customer.sh $username@$CustomerHostName:~/Public/src
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$CustomerHostName "cd ~/Public/src ; bash customer.sh $registryHostName $registryPortNum $url" &
sleep 5

echo " "
echo "- Retrieving Log..."
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RepositoryHostName "cat Public/src/log.txt" > Log.txt
sleep 2

echo " "
echo "- Stopping RMI Processes..."
bash kill_rmi.sh
bash kill_ports.sh

echo " "
echo "Cleaning Local Classes..."

bash cleanclasses.sh

echo " "
echo "Cleaning Remote Machines..."

bash remoteclean.sh

echo " "
echo "Done!"
