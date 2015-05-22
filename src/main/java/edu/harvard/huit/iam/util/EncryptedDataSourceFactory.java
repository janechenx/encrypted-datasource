package edu.harvard.huit.iam.util;

import java.util.Properties;
import javax.naming.Context;
import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;


public class EncryptedDataSourceFactory extends DataSourceFactory  {
    
    private EncryptorUtil encryptor = null;

    public EncryptedDataSourceFactory() {
        encryptor = new EncryptorUtil();
    }
    
    @Override
    public DataSource createDataSource(Properties properties, Context context, boolean XA) throws Exception {

        PoolConfiguration poolProperties = EncryptedDataSourceFactory.parsePoolProperties(properties);
        poolProperties.setPassword(encryptor.decrypt(poolProperties.getPassword()));

        if (poolProperties.getDataSourceJNDI()!=null && poolProperties.getDataSource()==null) {
            performJNDILookup(context, poolProperties);
        }
        org.apache.tomcat.jdbc.pool.DataSource dataSource = XA?
                new org.apache.tomcat.jdbc.pool.XADataSource(poolProperties) :
                new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
        //initialise the pool itself
        dataSource.createPool();
        // Return the configured DataSource instance
        return dataSource;

    }

}
