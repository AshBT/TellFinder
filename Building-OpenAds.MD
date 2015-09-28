
# Setting up the build environment
September 19, 2014

How to set up the OpenAds build environment.

## What additional files you need
memex_small_with_raw.sql.gz
memex_small_openads.zip
openads-maps-9-19-2014.zip

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

	database_host = localhost
	database_port = 3306
	database_user = root
	database_password = admin
	database_name = xdataht
	
To change these, either edit `war\xdata-ht.properties` and rebuild the war, or copy the file into the `tomcat/conf`.

## Java Setup
Ensure JDK_HOME and JAVA_HOME environment variables are set and correct.

## Building the WAR into the Tomcat directory
Edit `build.properties` to point to your `<tomcat>/webapps` directory.

Alternatively, specify the destination for the WAR file at command-line:

	ant -Dbuild.path=<webapps_directory>
	
Build the default target of `build.xml` with Ant. This will put a new war file in `webapps` directory.

## Building the Uncharted MySQL views
From the command line, run the following:

	ant process.xml
	 
This will read in the original ads and do all the necessary preprocessing steps to populate the memex_uncharted table.

## Tomcat Setup
Create a file called `<tomcat>/bin/setenv.bat` (or `setenv.sh`) containing:

	set JAVA_OPTS="-Xmx6144m"
	
Open `<tomcat>\conf\tomcat-users.xml` and add the lines:

	<role rolename="ocweb"/>
	<user username="xxxx" password="yyyy" roles="ocweb"/>

`xxx` and `yyy` will be the username and password you use to log in later.

Unzip `openads-maps-9-19-2014.zip` to `<tomcat>/webapps/maps`.

## Starting Tomcat
Start tomcat by launching `tomcat/bin/startup.bat`

Open a browser (recommended: Google Chrome) to http://localhost:8080/openads/

## Optimizing Javascript Code (Optional)
In order run the optimizer on our Javascript code, we must install the requireJS command-line tools from npm.

1)  [Install NodeJS](https://nodejs.org/)

2)  [Install npm](https://www.npmjs.com/)

3)  Install requireJS:

    `npm install -g requirejs`

To create a production WAR file, run ant with the production target:

    `ant production`

This will combine all Javascript files into a single, obfuscated file for each app/page in Tellfinder.

