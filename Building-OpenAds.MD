
# Setting up the build environment
September 29th, 2015

How to set up the Tellfinder build environment.

## What software you need
JDK 7
git
Apache Tomcat 7
Mysql
Apache Ant
Eclipse (optional)
NodeJS (optional)
npm (optional)
requireJS (optional)

## Import source data into Mysql
Import `docs/sampledata/sample.sql`. This should create the following tables in a schema `roxy_ui`:

	ads
	ads_attributes
	sources
	
## MySql Setup
If you are using this default mysql setup, then you don't need to do anything:

	database_type = mysql
    database_host = localhost
    database_port = 3306
    database_user = root
    database_password = admin
    database_ht_schema = roxy_ui
    database_oculus_schema = memex_uncharted
	
To change these, either edit `war\xdataht.properties` and rebuild the war, or copy the file into the `tomcat/conf`.

## Java Setup
Ensure JDK_HOME and JAVA_HOME environment variables are set and correct.

## Building the WAR into the Tomcat directory
Edit `build.properties` to point to your `<tomcat>/webapps` directory.

Alternatively, specify the destination for the WAR file at command-line:

	ant -Dbuild.path=<webapps_directory>
	
Build the default target of `build.xml` with Ant. This will put a new war file in `webapps` directory.

## Building the Uncharted MySQL views
From the command line, run the following:

	ant -buildfile process.xml
	 
This will read in the original ads and do all the necessary preprocessing steps to populate the memex_uncharted table.

## Tomcat Setup
Create a file called `<tomcat>/bin/setenv.bat` (or `setenv.sh`) containing:

	set JAVA_OPTS="-Xmx6144m"
	
Open `<tomcat>\conf\tomcat-users.xml` and add the lines:

	<role rolename="ocweb"/>
	<user username="xxxx" password="yyyy" roles="ocweb"/>

`xxx` and `yyy` will be the username and password you use to log in later.


## Starting Tomcat
Start tomcat by launching `tomcat/bin/startup.bat`

Open a browser (recommended: Google Chrome) to http://localhost:8080/openads/

## Map Server
It is possible to host your own map server by deploying a war file containing the tiles in the same Tomcat server as Tellfinder.
The format for the maps:

	%WARFILE_NAME%/1.0.0/%baseLayer.tms.options.layername%/z/x/y.png
	
Where `baseLayer.tms.options.layername` is defined in xdatahtconfig.js

## Optimizing Javascript Code (Optional)
In order run the optimizer on our Javascript code, we must install the requireJS command-line tools from npm.

1)  [Install NodeJS](https://nodejs.org/)

2)  [Install npm](https://www.npmjs.com/)

3)  Install requireJS:

    `npm install -g requirejs`

To create a production WAR file, run ant with the production target:

    `ant production`

This will combine all Javascript files into a single, obfuscated file for each app/page in Tellfinder.

