export SSHPASS='qwerty'



zip -r deployment.zip SD-ProbObrig3_3_1

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

sshpass -e ssh -o StrictHostKeyChecking=no sd0301@l040101-ws01.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF

sshpass -e ssh -o StrictHostKeyChecking=no sd0301@l040101-ws02.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF

sshpass -e ssh -o StrictHostKeyChecking=no sd0301@l040101-ws03.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF

sshpass -e ssh -o StrictHostKeyChecking=no sd0301@l040101-ws04.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF

sshpass -e ssh -o StrictHostKeyChecking=no sd0301@l040101-ws05.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF

sshpass -e ssh -o StrictHostKeyChecking=no sd0301@l040101-ws06.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF

sshpass -e ssh -o StrictHostKeyChecking=no sd0301@l040101-ws07.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF

sshpass -e ssh -o StrictHostKeyChecking=no sd0301@l040101-ws08.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF

sshpass -e ssh -o StrictHostKeyChecking=no sd0301@l040101-ws10.ua.pt << EOF
    rm -r Public/classes;
    mkdir Public/classes;

	unzip -o deployment.zip
	rm deployment.zip

    javac -d "Public/classes" -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java 
EOF


