#!/bin/sh

# Start the user service in the background
java -jar /user-service.jar &

# Wait for 60 seconds
sleep 150

# Execute the user insertion script
/usr/local/bin/insertUser.sh

# Wait for the user service to exit
wait
