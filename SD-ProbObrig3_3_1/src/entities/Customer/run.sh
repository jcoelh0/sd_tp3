#!/bin/bash
rm -r classes;
mkdir classes;
javac -d "classes" -cp "../../../genclass.jar" ../../entities/Customer/*.java ../../entities/Customer/Interfaces/*.java  ../../entities/Customer/States/*.java ../../entities/Manager/*.java ../../entities/Manager/Interfaces/*.java ../../entities/Manager/States/*.java ../../entities/Mechanic/*.java ../../entities/Mechanic/Interfaces/*.java ../../entities/Mechanic/States/*.java ../../communication/*.java ../../messages/LoungeMessage/*.java ../../messages/OutsideWorldMessage/*.java ../../messages/ParkMessage/*.java ../../messages/RepairAreaMessage/*.java ../../messages/RepositoryMessage/*.java ../../messages/SupplierSiteMessage/*.java ../../settings/*.java ../../shared/Lounge/*.java ../../shared/OutsideWorld/*.java ../../shared/Park/*.java ../../shared/RepairArea/*.java ../../shared/Repository/*.java ../../shared/SupplierSite/*.java;
java -classpath "classes" entities.Customer.CustomerRun;
