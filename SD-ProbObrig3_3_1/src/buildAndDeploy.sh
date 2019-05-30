#!/usr/bin/env bash

echo "compiling source code..."
javac -cp settings/*.java 
javac -cp ../src interfaces/*.java 
javac -cp .. registry/*.java 
javac -cp ../:genclass.jar shared/Repository/*.java 
javac -cp .. shared/Lounge/*.java 
javac -cp .. shared/OutsideWorld/*.java 
javac -cp .. shared/Park/*.java 
javac -cp .. shared/RepairArea/*.java 
javac -cp .. shared/SupplierSite/*.java 
javac -cp .. entities/Customer/*.java 
javac -cp .. entities/Customer/States/*.java
javac -cp .. entities/Customer/Interfaces/*.java
javac -cp .. entities/Manager/*.java
javac -cp .. entities/Manager/States/*.java
javac -cp .. entities/Manager/Interfaces/*.java
javac -cp .. entities/Mechanic/*.java
javac -cp .. entities/Mechanic/States/*.java
javac -cp .. entities/Mechanic/Interfaces/*.java

echo "Copying Interfaces .class files ..."
cp interfaces/*.class deploy/interfaces/

echo "Copying Registry .class files ..."
cp registry/*.class deploy/registry/

echo "Copying Monitors .class files ... "
cp shared/Lounge/*.class deploy/shared/Lounge/
cp shared/OutsideWorld/*.class deploy/shared/OutsideWorld/
cp shared/Park/*.class deploy/shared/Park/
cp shared/RepairArea/*.class deploy/shared/RepairArea/
cp shared/SupplierSite/*.class deploy/shared/SupplierSite/

echo "Copying Entities .class files ..."
cp Entities/Broker/*.class deploy/Entities/Broker/
cp Entities/Horses/*.class deploy/Entities/Horses/
cp Entities/Spectators/*.class deploy/Entities/Spectators/

echo "Copying Entities Interfaces .class files ..."
cp Entities/Broker/BrokerInterfaces/*.class deploy/Entities/Broker/BrokerInterfaces/
cp Entities/Horses/HorsesInterfaces/*.class deploy/Entities/Horses/HorsesInterfaces/
cp Entities/Spectators/SpectatorsInterfaces/*.class deploy/Entities/Spectators/SpectatorsInterfaces/

echo "Copying Entities Enumerates .class files ..."
cp Entities/Broker/BrokerEnum/*.class deploy/Entities/Broker/BrokerEnum/
cp Entities/Horses/HorsesEnum/*.class deploy/Entities/Horses/HorsesEnum/
cp Entities/Spectators/SpectatorsEnum/*.class deploy/Entities/Spectators/SpectatorsEnum/

echo "Copying Constants .class files ..."
cp Constants/*.class deploy/Constants/

echo "done build and deploy!!!"