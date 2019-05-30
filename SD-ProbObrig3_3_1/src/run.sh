export SSHPASS='qwerty'

echo -e "\n${bold}* Execução do código em cada nó *${normal}"


echo -e "\n${bold}->${normal} A iniciar e executar Registry e executar Repository na máquina ${bold}1${normal}"
sshpass -e ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws01.ua.pt << EOF
    cd Public/classes
    nohup rmiregistry -J-Djava.rmi.server.useCodebaseOnly=true 22427 > /dev/null 2>&1 &

    sleep 5
    
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    MainPackage.ServerRegisterRemoteObject > /dev/null 2>&1 &
    cd ../..
    cd Public/classes/shared/Repository
    
    sleep 5

    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    MainPackage.MainProgram > /dev/null 2>&1 &
    exit
EOF

sleep 5

echo -e "\n${bold}->${normal} A executar Lounge na máquina ${bold}2${normal}"
sshpass -e ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws02.ua.pt << EOF
    cd Public/classes
    nohup rmiregistry -J-Djava.rmi.server.useCodebaseOnly=true 22427 > /dev/null 2>&1 &

    sleep 5
    
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    MainPackage.ServerRegisterRemoteObject > /dev/null 2>&1 
    exit
EOF




sleep 5 



echo -e "\n${bold}->${normal} A executar OutsideWorld na máquina ${bold}3${normal}"
sshpass -e ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws03.ua.pt << EOF
    cd Public/classes/
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    MainPackage.MainProgram > /dev/null 2>&1 &
    exit
EOF


sleep 1

echo -e "\n${bold}->${normal} A executar RepairArea na máquina ${bold}4${normal}"
sshpass -e ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws04.ua.pt << EOF
    cd Public/classes/
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    MainPackage.MainProgram > /dev/null 2>&1 &
    exit
EOF

sleep 1

echo -e "\n${bold}->${normal} A executar Park na máquina ${bold}5${normal}"
sshpass -e ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws05.ua.pt << EOF
    cd Public/classes/
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    MainPackage.MainProgram > /dev/null 2>&1 &
    exit
EOF

sleep 1

echo -e "\n${bold}->${normal} A executar SupplierSite na máquina ${bold}6${normal}"
sshpass -e ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws06.ua.pt << EOF
    cd Public/classes/
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    MainPackage.MainProgram > /dev/null 2>&1 &
    exit
EOF

sleep 1



# Wait for the shared regions to be launched before lanching the intervening enities

sleep 5

echo -e "\n${bold}->${normal} A executar Manager na máquina ${bold}8${normal}"
sshpass -e ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws08.ua.pt << EOF
    cd Public/classes/
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    MainPackage.MainProgram > /dev/null 2>&1 &
    exit
EOF

sleep 1

echo -e "\n${bold}->${normal} A executar Mechanics na máquina ${bold}9${normal}"
sshpass -e ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws09.ua.pt << EOF
    cd Public/classes/
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    MainPackage.MainProgram > /dev/null 2>&1 &
    exit
EOF

sleep 1

echo -e "\n${bold}->${normal} A executar Customers na máquina ${bold}10${normal}"
sshpass -e ssh -tt -o StrictHostKeyChecking=no sd0301@l040101-ws10.ua.pt << EOF
    cd Public/classes/
    nohup java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0301/classes/"\
    -Djava.rmi.server.useCodebaseOnly=true\
    -Djava.security.policy=java.policy\
    MainPackage.MainProgram > /dev/null 2>&1 &
    exit
EOF
