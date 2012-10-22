/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2008, Red Hat Middleware LLC or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Middleware LLC.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 *
 */
package org.hibernate.engine.jdbc.dialect.internal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.SQLServer2005Dialect;
import org.hibernate.dialect.SQLServer2008Dialect;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.service.jdbc.dialect.internal.StandardDialectResolver;
import org.hibernate.testing.junit4.BaseUnitTestCase;
import org.junit.Test;

/**
 * Unit test of the {@link StandardDialectResolver} class.
 *
 * @author Bryan Turner
 */
public class StandardDialectResolverTest extends BaseUnitTestCase {

    @Test
    public void testResolveDialectInternalForSQLServer2000() throws SQLException {
        runSQLServerDialectTest(8, SQLServerDialect.class);
    }

    @Test
    public void testResolveDialectInternalForSQLServer2005() throws SQLException {
        runSQLServerDialectTest(9, SQLServer2005Dialect.class);
    }

    @Test
    public void testResolveDialectInternalForSQLServer2008() throws SQLException {
        runSQLServerDialectTest(10, SQLServer2008Dialect.class);
    }

    @Test
    public void testResolveDialectInternalForSQLServer2012() throws SQLException {
        runSQLServerDialectTest(11, SQLServer2008Dialect.class);
    }

    @Test
    public void testResolveDialectInternalForUnknownSQLServerVersion() throws SQLException {
        runSQLServerDialectTest(7, SQLServerDialect.class);
    }

    private static void runSQLServerDialectTest(int version, Class<? extends SQLServerDialect> expectedDialect)
            throws SQLException {
        DatabaseMetaData metaData = mock(DatabaseMetaData.class);
        when(metaData.getDatabaseProductName()).thenReturn("Microsoft SQL Server");
        when(metaData.getDatabaseMajorVersion()).thenReturn(version);

        Dialect dialect = new StandardDialectResolver().resolveDialect(metaData);
        assertNotNull("Dialect for SQL Server version " + version + " should not be null", dialect);
        assertTrue("Dialect for SQL Server version " + version + " should be " + expectedDialect.getSimpleName(),
                expectedDialect.isInstance(dialect));
    }
}