# Ptolemy - Eclipse RCP Demo Application
A Simple Cross Platform Eclipse RCP Demo application. A ready to use playground for Java OOA/D and to practices programming coding skills.

# How to use it
Eclipse RCP application development is best supported with Eclipse IDE. 
Current version target platform is set to 2022/03, you can get your version of IDE [here](https://www.eclipse.org/downloads/packages/release/2022-03/r/eclipse-ide-rcp-and-rap-developers).

If Eclipse is not an option, you can still use a basic text editor and start playing with the demo using Maven.
````sh
git clone https://github.com/nabil/ptolemy.git && cd ptolemy
cd io.github.nabil.ptolemy.rcp.mvn-dependencies && mvn clean install && cd ..
mvn clean verify
````

The maven verify lifecyle phase will build three products version (Linux, MacOSX and Windows) under the application target products folder.

````sh
ls ./io.github.nabil.ptolemy.rcp.application/target/products/io.github.nabil.ptolemy.rcp.application.product
````

Once you start the application you will be prompted with a login dialog to setup the workbench. 

This demo is provided with no encryption and is intended for eduction purpose only. The content of the accounts are stored in clear in the json database file ptolemy.db

