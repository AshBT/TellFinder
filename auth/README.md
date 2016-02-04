# Setting up Shiro

## Edit shiro.ini
There is an example shiro.ini given.  In order to use authorization with Tellfinder you'll beed to edit that file and fill
in your database connection parameters (under the #connection part).

## Create the user table
You will also need run the included `create_user_tables.sql` on whichever database you've configured shiro.ini to authorize with.

## Create a user
We do not provide a UI for managing users, so you'll have to do it manually.  
	
1. Hash the passwords of your users using sha-256. Use your favorite tool, like this one line:

	http://www.xorbin.com/tools/sha256-hash-calculator

Or, to hash a password using Tomcat's digest utility:

	$TOMCAT_HOME/bin/digest -a sha-256 mypassword
	
This will output your password and the hashed password:

	mypassword:89e01536ac207279409d4de1e5253e01f4a1769e696db0d6062ca9b8f56767c8
	
The part after the colon is the hashed password, for use in the next step.

2. Populate the `users` table created previously with users and their hashed passwords:

	insert into users (user_name, user_pass) values ('myuser', '89e01536ac207279409d4de1e5253e01f4a1769e696db0d6062ca9b8f56767c8');
	
3. You need to assign the role `ocweb` to users in to the `user_roles` table:

	insert into user_roles (user_name, role_name) values ('myuser', 'ocweb');