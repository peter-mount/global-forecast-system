/*
 * Copyright 2015 peter.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package onl.area51.gfs.grib2.job;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import org.antlr.v4.runtime.ANTLRInputStream;
import uk.trainwatch.job.Job;
import uk.trainwatch.job.Scope;
import uk.trainwatch.job.lang.Compiler;
import uk.trainwatch.job.util.LogHandler;

/**
 *
 * @author peter
 */
public class AbstractCompilerTest
{

    protected InputStream getScript( String t )
    {
        InputStream is = AbstractCompilerTest.class.getResourceAsStream( t );
        assertNotNull( "Failed to find " + t, is );
        return is;
    }

    protected final void runTest( String n )
            throws Throwable
    {
        System.out.println( "Starting " + n );
        try {
            Job job = compile( n );
            execute( job );
            System.out.println( n + " Passed" );
        }
        catch( Throwable ex ) {
            System.out.println( n + " Failed: " + ex );
            throw ex;
        }
    }

    /**
     * Compile a job
     * <p>
     * @param n
     *          <p>
     * @return <p>
     * @throws IOException
     */
    protected final Job compileJob( String n )
            throws IOException
    {
        try( InputStream is = getScript( n + ".job" ) ) {
            return Compiler.compile( new ANTLRInputStream( is ) );
        }
    }

    /**
     * Compile a job and ensure the top Job object is correct
     * <p>
     * @param n <p>
     * @return <p>
     * @throws IOException
     */
    protected final Job compile( String n )
            throws IOException
    {
        Job job = compileJob( n );
        assertNotNull( "No Job from compilation", job );
        assertEquals( "Job id", n, job.getId() );
        return job;
    }

    protected final List<String> getLines( String n )
            throws IOException
    {
        try( BufferedReader r = new BufferedReader( new InputStreamReader( getClass().getResourceAsStream( n ) ) ) ) {
            return r.lines().collect( Collectors.toList() );
        }
        catch( NullPointerException ex ) {
            return Collections.emptyList();
        }
    }

    protected final void testLog( final List<String> log, final String n )
            throws IOException
    {
        List<String> expectedLog = getLines( n + ".log" );

        // No .log file or it's empty then don't test the log
        if( expectedLog.isEmpty() ) {
            return;
        }

        int es = expectedLog.size();
        int l = 0;
        for( String line: log ) {
            if( l < es ) {
                assertEquals( "Line " + l, expectedLog.get( l ), line );
            }
            else {
                fail( "Extra line " + l + ": " + line );
            }
            l++;
        }

        if( es > log.size() ) {
            fail( "Missing last " + (es - log.size()) + " lines" );
        }
    }

    /**
     * Execute a job
     * <p>
     * @param job <p>
     * @throws Exception
     */
    protected final void execute( Job job )
            throws Exception
    {
        String n = job.getId();

        List<String> log = new ArrayList<>();

        Logger logger = LogHandler.getLogger( "test." + n,
                                              r -> String.format( "%-6.6s%-6.6s %s", n, r.getLevel(), r.getMessage() ),
                                              log::add );

        logger.setLevel( Level.FINE );

        final Scope scope = Scope.newInstance( logger );

        // Now run the job
        job.invoke( scope );

        testLog( log, n );
    }
}
