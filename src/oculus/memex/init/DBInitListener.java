/**
Copyright 2016 Uncharted Software Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package oculus.memex.init;

import java.lang.reflect.Method;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import oculus.memex.casebuilder.CaseBuilder;
import oculus.memex.db.MemexHTDB;
import oculus.memex.db.MemexOculusDB;
import oculus.memex.db.ScriptDBInit;
import oculus.memex.image.AdImages;
import oculus.memex.image.ImageHistogramHash;
import oculus.memex.tags.Tags;
import oculus.memex.training.InvalidateAttribute;

import org.apache.log4j.Logger;


public class DBInitListener implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(DBInitListener.class.getName());
	
	public void contextInitialized(ServletContextEvent sce) {
		PropertyManager spm = PropertyManager.getInstance();
		String type = spm.getProperty(PropertyManager.DATABASE_TYPE, ScriptDBInit._type);
		String hostname = spm.getProperty(PropertyManager.DATABASE_HOSTNAME, ScriptDBInit._hostname);
		String port = spm.getProperty(PropertyManager.DATABASE_PORT, ScriptDBInit._port);
		String user = spm.getProperty(PropertyManager.DATABASE_USER, ScriptDBInit._user);
		String pass = spm.getProperty(PropertyManager.DATABASE_PASSWORD, ScriptDBInit._pass);
		String schema = spm.getProperty(PropertyManager.DATABASE_OCULUSSCHEMA,ScriptDBInit._oculusSchema);
		ScriptDBInit._oculusSchema = schema;
		MemexOculusDB oculusdb = MemexOculusDB.getInstance(schema, type, hostname, port, user, pass);
		if (oculusdb!=null) {
			logger.info("INITIALIZED OCULUS DATABASE: " + user + "@" + hostname + ":" + port + "");
		} else {
			logger.error("FAILED TO INITIALIZE OCULUS DATABASE");
		}
		CaseBuilder.initTable(oculusdb);
		Tags.initTable(oculusdb);
		AdImages.initTable(oculusdb);
		ImageHistogramHash.initTable(oculusdb);
		InvalidateAttribute.initTable(oculusdb);
		schema = spm.getProperty(PropertyManager.DATABASE_HTSCHEMA,ScriptDBInit._htSchema);
		ScriptDBInit._htSchema = schema;
		MemexHTDB htdb = MemexHTDB.getInstance(schema, type, hostname, port, user, pass);
		if (htdb!=null) {
			logger.info("INITIALIZED HT DATABASE: " + user + "@" + hostname + ":" + port + "");
		} else {
			logger.error("FAILED TO INITIALIZE HT DATABASE");
		}
	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		// Manually deregister our JDBC driver
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.info(String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
            	logger.error(String.format("Error deregistering driver %s", 
            			driver), e);
            }

        }
        
		// Workaround a MySQL bug - IF we're using it. Use reflection to avoid
		// dependency.
		try {
			Class<?> cls = Class.forName("com.mysql.jdbc.AbandonedConnectionCleanupThread");
			Method mth = (cls == null ? null : cls.getMethod("shutdown"));
			if (mth != null) {
				logger.info("MySQL connection cleanup thread shutdown");
				mth.invoke(null);
				logger.info("MySQL connection cleanup thread shutdown successful");
			}
		} catch (Throwable thr) {
			logger.error("[ER] Failed to shutdown SQL connection cleanup thread: ", thr);
		}
	}
}
