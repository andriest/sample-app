# Simple App by Scala

Requirement:
- sbt 0.3.15
- scala 2.10.4
- java jdk8

Run web:
See example config file at etc/example.conf

$ cp etc/example.conf app.conf
$ ./etc/script/run-service.sh

Open browser and type http://localhost:8080

Make test:

$ cp etc/example-test.conf test.conf
$ sbt "project app-core" "testOnly com.andrie.simple.dao.BeratDaoSpec"

