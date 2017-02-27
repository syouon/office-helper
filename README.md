office-helper
=============

Requirements
------------

- maven 3
- jdk 1.8
- docker or mysql server


How to install
--------------

Clone the repository and cd into it

    git clone git@github.com:syouon/office-helper.git
    cd office-helper

build and start the mysql server container using docker

    sh tools/docker/run.sh start

build the project with maven

    mvn package

run the application

    mvn spring-boot:run