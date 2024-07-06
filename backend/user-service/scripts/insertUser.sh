#!/bin/sh

# Execute the SQL command
PGPASSWORD=123 psql -h eshop-db -U admin -d eshop -c "
INSERT INTO users (user_id, email, encrypted_password, first_name, last_name, role)
VALUES (1, 'aivazart@fel.cvut.cz', '\$2a\$04\$yzZfSAHePR/QgrphZmyULe7MEKJyF2MudVeNcPFRMQXM5vDe1oL.e', 'Artem', 'Aivazian', 'ADMIN')
ON CONFLICT (email) DO NOTHING;"
