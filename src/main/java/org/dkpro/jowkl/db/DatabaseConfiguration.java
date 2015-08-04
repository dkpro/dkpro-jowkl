/*******************************************************************************
 * Copyright 2012
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.dkpro.jowkl.db;

/**
 *
 * Database configuration for accessing OmegaWiki database
 *
 * @author matuschek
 *
 */

public class DatabaseConfiguration {

	private String db_driver;
	private String db_vendor;
    private String host;
    private String database;
    private String user;
    private String password;
    private int language;

    /**
     * Empty constructor
     */
    public DatabaseConfiguration() {}

    /**
     *
     * @param host Database host url
     * @param database DB name
     * @param user
     * @param password
     * @param language language of the OW to be constructed
     */

    public DatabaseConfiguration(String host, String database,String db_driver,String db_vendor, String user, String password, int language) {
        this.db_driver = db_driver;
        this.db_vendor = db_vendor;
    	this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.language = language;
    }

    public String getDb_driver()
	{
		return db_driver;
	}

	public void setDb_driver(String dbDriver)
	{
		db_driver = dbDriver;
	}

	public String getDb_vendor()
	{
		return db_vendor;
	}

	public void setDb_vendor(String dbVendor)
	{
		db_vendor = dbVendor;
	}

	/**
     * @param database The name of the database.
     */
    public void setDatabase(String database) {
        this.database = database;
    }
    /**
     * @param host The host where the database is running. Set to "localhost", if the database is running locally.
     */
    public void setHost(String host) {
        this.host = host;
    }
    /**
     * @param password The password to access the database.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @param user The database user.
     */
    public void setUser(String user) {
        this.user = user;
    }
    /**
     * @param language The language
     */
    public void setLanguage(int language) {
        this.language = language;
    }


    /**
     * @return The name of the database.
     */
    public String getDatabase() {
        return database;
    }
    /**
     * @return The host where the database is running.
     */
    public String getHost() {
        return host;
    }
    /**
     * @return The password to access the database.
     */
    public String getPassword() {
        return password;
    }
    /**
     * @return The database user.
     */
    public String getUser() {
        return user;
    }
    /**
     * @return The language
     */
    public int getLanguage() {
        return language;
    }
}

