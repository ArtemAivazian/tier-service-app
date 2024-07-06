CREATE EXTENSION dblink;

DO
$$
    BEGIN
        IF NOT EXISTS (
                SELECT FROM pg_catalog.pg_database
                WHERE datname = 'eshop'
            ) THEN
            PERFORM dblink_exec('dbname=postgres user=admin password=123', 'CREATE DATABASE eshop');
        END IF;
    END
$$;
