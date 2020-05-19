# Java Selenium Framework for use with TestModeller.io and Perfecto
Open source repository of Java based selenium tests. For use with [TestModeller.io](http://www.testmodeller.io) and [Perfecto](http://www.Perfecto.io)

**NOTE:** Targets Java 8 only out of the box.

# Getting started
1. Clone this repository.

2. Install the Java Development Kit
3. Install ChromeDriver (this needs to match the version of chrome you will be executing your tests on) and make sure you add it to your system PATH.
4. Install Maven - this is used to manage the build process of our automation framework.

5. Register the code templates to Test Modeller.
6. Copy generated page objects into the 'src\main\java\pages' directory. 
7. Copy generated tests into the 'src\test\java\tests' directory.

8. To execute all tests run 'mvn test'. To execute a specific test run 'mvn -Dtest=[filename] test' where [filename] is the name of the test file to execute.
