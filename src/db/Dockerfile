FROM mysql:8.4

WORKDIR /docker-entrypoint-initdb.d

COPY ./schema.sql /docker-entrypoint-initdb.d/

EXPOSE 3306