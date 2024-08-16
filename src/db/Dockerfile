FROM mysql:8.4

WORKDIR /docker-entrypoint-initdb.d

ENV MYSQL_DATABASE=personalFinance
ENV MYSQL_USER=${MYSQL_USER}
ENV MYSQL_PASSWORD=${MYSQL_PASSWORD}

COPY ./schema.sql /docker-entrypoint-initdb.d/

EXPOSE 3306