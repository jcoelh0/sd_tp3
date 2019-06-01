#!/usr/bin/env bash

javac -cp "SD-ProbObrig3_3_1/src/genclass.jar" SD-ProbObrig3_3_1/src/interfaces/*.java SD-ProbObrig3_3_1/src/settings/*.java SD-ProbObrig3_3_1/src/registry/*.java SD-ProbObrig3_3_1/src/entities/Customer/*.java SD-ProbObrig3_3_1/src/entities/Customer/Interfaces/*.java  SD-ProbObrig3_3_1/src/entities/Customer/States/*.java SD-ProbObrig3_3_1/src/entities/Manager/*.java SD-ProbObrig3_3_1/src/entities/Manager/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Manager/States/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/Interfaces/*.java SD-ProbObrig3_3_1/src/entities/Mechanic/States/*.java SD-ProbObrig3_3_1/src/shared/Lounge/*.java SD-ProbObrig3_3_1/src/shared/OutsideWorld/*.java SD-ProbObrig3_3_1/src/shared/Park/*.java SD-ProbObrig3_3_1/src/shared/RepairArea/*.java SD-ProbObrig3_3_1/src/shared/Repository/*.java SD-ProbObrig3_3_1/src/shared/SupplierSite/*.java

cd SD-ProbObrig3_3_1/src

mkdir deploy
mkdir deploy/interfaces
mkdir deploy/registry
mkdir deploy/shared
mkdir deploy/settings
mkdir deploy/shared/Repository
mkdir deploy/shared/Lounge
mkdir deploy/shared/OutsideWorld
mkdir deploy/shared/RepairArea
mkdir deploy/shared/Park
mkdir deploy/shared/SupplierSite
mkdir deploy/entities/
mkdir deploy/entities/Manager
mkdir deploy/entities/Mechanic
mkdir deploy/entities/Customer
mkdir deploy/entities/Manager/Interfaces
mkdir deploy/entities/Manager/States
mkdir deploy/entities/Mechanic/Interfaces
mkdir deploy/entities/Mechanic/States
mkdir deploy/entities/Customer/Interfaces
mkdir deploy/entities/Customer/States

cp interfaces/*.class deploy/interfaces/

cp registry/*.class deploy/registry/

cp shared/Repository/*.class deploy/shared/Repository/
cp shared/Lounge/*.class deploy/shared/Lounge/
cp shared/OutsideWorld/*.class deploy/shared/OutsideWorld/
cp shared/RepairArea/*.class deploy/shared/RepairArea/
cp shared/Park/*.class deploy/shared/Park/
cp shared/SupplierSite/*.class deploy/shared/SupplierSite/

cp entities/Manager/*.class deploy/entities/Manager/
cp entities/Mechanic/*.class deploy/entities/Mechanic/
cp entities/Customer/*.class deploy/entities/Customer/

cp entities/Manager/Interfaces/*.class deploy/entities/Manager/Interfaces/
cp entities/Mechanic/Interfaces/*.class deploy/entities/Mechanic/Interfaces/
cp entities/Customer/Interfaces/*.class deploy/entities/Customer/Interfaces/

cp entities/Manager/States/*.class deploy/entities/Manager/States/
cp entities/Mechanic/States/*.class deploy/entities/Mechanic/States/
cp entities/Customer/States/*.class deploy/entities/Customer/States/

cp settings/*.class deploy/settings/