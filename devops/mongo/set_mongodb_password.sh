#!/bin/bash

ADMIN_USER=${MONGODB_ADMIN_USER:-"admin"}
ADMIN_PASS=${MONGODB_ADMIN_PASS:-"ninja"}

# Application Database User
APPLICATION_DATABASE=${APPLICATION_DATABASE:-"ninja"}
APPLICATION_USER=${APPLICATION_USER:-"ninja"}
APPLICATION_PASS=${APPLICATION_PASS:-"ninja"}

RET=1
while [[ RET -ne 0 ]]; do
    echo "=> Waiting mongodb to startup..."
    sleep 6
    mongo admin --eval "help" >/dev/null 2>&1
    RET=$?
done

echo "=> Creating the admin user..."
mongo admin --eval "db.createUser({user: '$ADMIN_USER', pwd: '$ADMIN_PASS', roles:[{role:'root',db:'admin'}]});"

sleep 4

echo "=> Creating the ninja database..."
mongo admin -u $ADMIN_USER -p $ADMIN_PASS << EOF
use $APPLICATION_DATABASE
db.createUser({user: '$APPLICATION_USER', pwd: '$APPLICATION_PASS', roles:[{role:'dbOwner', db:'$APPLICATION_DATABASE'}]})

EOF