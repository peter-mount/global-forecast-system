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

import static java.time.temporal.ChronoField.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.apache.commons.net.ftp.FTPFile;
import uk.trainwatch.io.ftp.FTPClient;
import uk.trainwatch.io.ftp.FTPClientBuilder;

/**
 * Handles the retrieval of GFS GRIB files from NOAA
 * <p>
 * @author peter
 */
public class GribRetriever
        implements AutoCloseable
{

    private static final Logger LOG = Logger.getLogger( GribRetriever.class.getName() );

    /**
     * Remote server details
     */
    private static final String SERVER = "ftp.ncep.noaa.gov";
    private static final String DIR = "/pub/data/nccf/com/gfs/prod";

    /**
     * The GRIB product.
     * <p>
     * 0p25 is the 0.25 degree resolution. Could also use 0p50 1p00 or 2p50 for 0.5,1.0 and 2.5 degree resolutions but we'll use the highest one.
     * <p>
     * First arg is the cycle runtime, 00, 06, 12 or 18.
     * <p>
     * Second arg is the forecast hour of product from 000-384
     */
    private static final String PRODUCT = "gfs.t%02dz.pgrb2.0p25.f%03f";

    public static final DateTimeFormatter DIR_FORMATTER;

    static {
        DIR_FORMATTER = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendValue( YEAR, 4 )
                .appendValue( MONTH_OF_YEAR, 2 )
                .appendValue( DAY_OF_MONTH, 2 )
                .appendValue( HOUR_OF_DAY )
                .toFormatter();
    }

    private final FTPClient client;

    /**
     * Connect to NOAA
     * <p>
     * @throws IOException
     */
    public GribRetriever()
            throws IOException
    {
        // Note: Don't put this in try-resources as it lives longer than the try block
        client = new FTPClientBuilder()
                .logger( LOG::info )
                .build();

        try {
            LOG.log( Level.INFO, "Connecting to NOAA..." );
            client.connect( SERVER );
            client.login( "anonymous", "gribuser@" );
        }
        catch( IOException ex ) {
            // Make certain the client is closed correctly
            client.close();
            throw ex;
        }
    }

    /**
     * Select the latest GFS run
     * <p>
     * @throws IOException
     */
    public void selectLatestRun()
            throws IOException
    {
        selectRun( null );
    }

    /**
     * Convert a {@link LocalDateTime} to a GFS run time. Specifically this is the date and hour of the day restricted to 0, 7, 12 or 18 hours.
     * <p>
     * @param date date
     * <p>
     * @return date modified to the nearest GFS run (earlier than date) or null if date was null
     */
    public static LocalDateTime toGFSRunTime( LocalDateTime date )
    {
        if( date == null ) {
            return null;
        }

        LocalDateTime dateTime = date.truncatedTo( ChronoUnit.HOURS );

        // Only allow hours 0, 6, 12 & 18
        int h = dateTime.get( ChronoField.HOUR_OF_DAY );
        if( h % 6 == 0 ) {
            return dateTime;
        }

        return dateTime.withHour( (h / 6) * 6 );
    }

    /**
     * Select the specified run.
     * <p>
     * @param date The date and hour of the required run
     * <p>
     * @throws java.io.IOException
     */
    public void selectRun( LocalDateTime date )
            throws IOException
    {
        LOG.log( Level.INFO, "Retrieving directory listing" );
        client.changeWorkingDirectory( DIR );
        Stream<FTPFile> dirs = client.directories()
                .filter( f -> f.getName().startsWith( "gfs." ) );

        if( date == null ) {
            // Look for the most recent date
            dirs = dirs.sorted( ( a, b ) -> b.getName().compareToIgnoreCase( a.getName() ) );
        }
        else {
            // Look for a specific date
            String filter = "gfs." + toGFSRunTime( date ).format( DateTimeFormatter.BASIC_ISO_DATE );
            dirs = dirs.filter( f -> filter.equals( f.getName() ) );
        }

        @SuppressWarnings("ThrowableInstanceNotThrown")
        FTPFile dir = dirs.findFirst()
                .orElseThrow( () -> new FileNotFoundException( "Failed to find remote gfs directory " ) );

        client.changeWorkingDirectory( dir.getName() );
        String pwd = client.printWorkingDirectory();
        LOG.log( Level.INFO, () -> "Now in directory " + pwd );
    }

    public File retrieveOffset( int offset )
            throws IOException
    {
        return retrieveOffset( null, offset, false );
    }

    public File retrieveOffset( File dir, int offset )
            throws IOException
    {
        return retrieveOffset( dir, offset, false );
    }

    public File retrieveOffset( int offset, boolean forceRetrive )
            throws IOException
    {
        return retrieveOffset( null, offset, forceRetrive );
    }

    public File retrieveOffset( File dir, int offset, boolean forceRetrive )
            throws IOException
    {
        LOG.log( Level.INFO, () -> "Looking for GFS file for offset " + offset );
        String ending = String.format( "z.pgrb2.0p25.f%03d", offset );
        
        @SuppressWarnings("ThrowableInstanceNotThrown")
        FTPFile remote = client.files()
                .filter( f -> f.getName().startsWith( "gfs.t" ) )
                .filter( f -> f.getName().endsWith( ending ) )
                .peek( f -> LOG.warning( f.getName() ) )
                .findAny()
                .orElseThrow( () -> new FileNotFoundException( "Unable to find GFS file for " + offset ) );

        File file = new File( dir, remote.getName() );

        if( forceRetrive || client.isFileRetrievable( file, remote ) ) {
            LOG.log( Level.INFO, () -> "Retrieving " + remote.getSize() + " bytes to " + file );

            try( InputStream is = client.retrieveFileStream( remote ) ) {
                Files.copy( is, file.toPath(), StandardCopyOption.REPLACE_EXISTING );
            }

            LOG.log( Level.INFO, () -> "Retrieved " + remote.getSize() + " bytes" );
        }
        else {
            LOG.log( Level.INFO, () -> "Skipping retrieval as local file is newer" );
        }

        return file;
    }

    @Override
    public void close()
            throws IOException
    {
        client.close();
    }
}
