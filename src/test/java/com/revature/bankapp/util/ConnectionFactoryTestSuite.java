package com.revature.bankapp.util;

import com.revature.bankapp.util.datasource.ConnectionFactory;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactoryTestSuite {

    @Test
    public void test_getConnection_returnsProperConnection_givenProvidedCredentials() {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            Assert.assertNotNull(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
