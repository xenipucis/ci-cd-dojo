#!/bin/bash
set -m

if [ ! -f /data/db/.mongodb_password_set ]; then
    /set_mongodb_password.sh
fi

$cmd &