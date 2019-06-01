export SSHPASS='qwerty'

sshpass -e ssh -T -o StrictHostKeyChecking=no sd0301@l040101-ws09.ua.pt << EOF
   bash -c "ps -ef | grep sd0301 | grep java | awk '{print \\\$2}' | xargs kill" &
EOF