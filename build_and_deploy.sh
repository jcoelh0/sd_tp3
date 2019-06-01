export SSHPASS='qwerty'

zip -r deployment.zip SD-ProbObrig3_3_1

sshpass -e ssh -T -o StrictHostKeyChecking=no -f sd0301@l040101-ws01.ua.pt "killall java;killall rmiregistry"
sshpass -e ssh -T -o StrictHostKeyChecking=no -f sd0301@l040101-ws02.ua.pt "killall java;"
sshpass -e ssh -T -o StrictHostKeyChecking=no -f sd0301@l040101-ws03.ua.pt "killall java;"
sshpass -e ssh -T -o StrictHostKeyChecking=no -f sd0301@l040101-ws04.ua.pt "killall java;"
sshpass -e ssh -T -o StrictHostKeyChecking=no -f sd0301@l040101-ws05.ua.pt "killall java;"
sshpass -e ssh -T -o StrictHostKeyChecking=no -f sd0301@l040101-ws06.ua.pt "killall java;"
sshpass -e ssh -T -o StrictHostKeyChecking=no -f sd0301@l040101-ws07.ua.pt "killall java;"
sshpass -e ssh -T -o StrictHostKeyChecking=no -f sd0301@l040101-ws08.ua.pt "killall java;"
sshpass -e ssh -T -o StrictHostKeyChecking=no -f sd0301@l040101-ws10.ua.pt "killall java;"

sshpass -e sftp -o StrictHostKeyChecking=no sd0301@l040101-ws01.ua.pt << !
    put deployment.zip
    bye
!

sshpass -e sftp -o StrictHostKeyChecking=no sd0301@l040101-ws02.ua.pt << !
    put -r deployment.zip
    bye
!

sshpass -e sftp -o StrictHostKeyChecking=no sd0301@l040101-ws03.ua.pt << !
    put -r deployment.zip
    bye
!

sshpass -e sftp -o StrictHostKeyChecking=no sd0301@l040101-ws04.ua.pt << !
    put -r deployment.zip
    bye
!

sshpass -e sftp -o StrictHostKeyChecking=no sd0301@l040101-ws05.ua.pt << !
    put -r deployment.zip
    bye
!

sshpass -e sftp -o StrictHostKeyChecking=no sd0301@l040101-ws06.ua.pt << !
    put -r deployment.zip
    bye
!

sshpass -e sftp -o StrictHostKeyChecking=no sd0301@l040101-ws07.ua.pt << !
    put -r deployment.zip
    bye
!

sshpass -e sftp -o StrictHostKeyChecking=no sd0301@l040101-ws08.ua.pt << !
    put -r deployment.zip
    bye
!

sshpass -e sftp -o StrictHostKeyChecking=no sd0301@l040101-ws10.ua.pt << !
    put -r deployment.zip
    bye
!

rm deployment.zip

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws01.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws02.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws03.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws04.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws05.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws06.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws07.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws08.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws10.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws01.ua.pt << EOF
    cd Public/classes/registry
    nohup rmiregistry -J-Djava.rmi.server.useCodebaseOnly=true 22961 > /dev/null 2>&1 &

    sleep 5
    
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/registry/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    ServerRegisterRemoteObject > /dev/null 2>&1 &
    
    cd ..
    cd shared/Repository
    
    sleep 5

    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/registry/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    RepositoryServer > /dev/null 2>&1 &
EOF

sleep 5

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws02.ua.pt << EOF
    cd Public/classes/shared/Lounge
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/registry/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    LoungeServer > /dev/null 2>&1 &
EOF

sleep 1

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws03.ua.pt << EOF
    cd Public/classes/shared/OutsideWorld
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/registry/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    OutsideWorldServer > /dev/null 2>&1 &
EOF

sleep 1

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws04.ua.pt << EOF
    cd Public/classes/shared/RepairArea
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/registry/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    RepairAreaServer > /dev/null 2>&1 &
EOF

sleep 1

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws05.ua.pt << EOF
    cd Public/classes/shared/Park
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/registry/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    ParkServer > /dev/null 2>&1 &
EOF

sleep 1

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws06.ua.pt << EOF
    cd Public/classes/shared/SupplierSite
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/registry/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    SupplierSiteServer > /dev/null 2>&1 &
EOF

sleep 5

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws07.ua.pt << EOF
    cd Public/classes/entities/Manager
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/registry/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    ManagerClient > /dev/null 2>&1 &
EOF

sleep 1

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws08.ua.pt << EOF
    cd Public/classes/entities/Mechanic
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/registry/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    MechanicClient > /dev/null 2>&1 &
EOF

sleep 1

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws10.ua.pt << EOF
    cd Public/classes/entities/Customer
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/registry/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    CustomerClient > /dev/null 2>&1 &
EOF

sleep 1