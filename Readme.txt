Library Application - Instructions

Application was developed using Maven build tool.
In order to build it one need to install and configure it.

1. Building the application

Open console in the directory of the Library application.

Type in: mvn clean install

It will clean the previous build (if any) and build your application along with running tests.

If you want to omit running tests, just type: mvn clean install -Dmaven.test.skip=true

2. Running tests

While having opened console in the directory of the application please type in:
mvn test