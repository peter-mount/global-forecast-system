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

import java.io.File;
import static onl.area51.gfs.grib2.job.MiscOps.*;

import org.kohsuke.MetaInfServices;
import uk.trainwatch.job.ext.Extension;
import uk.trainwatch.job.lang.expr.ExpressionOperation;
import uk.trainwatch.job.lang.expr.Logic;

/**
 *
 * @author peter
 */
@MetaInfServices(Extension.class)
public class GFSExtension
        implements Extension
{

    @Override
    public String getName()
    {
        return "GlobalForecastSystem";
    }

    @Override
    public String getVersion()
    {
        return "1.0";
    }

    @Override
    public ExpressionOperation getExpression( String name, ExpressionOperation... args )
    {
        switch( args == null ? 0 : args.length ) {
            case 1:
                switch( name ) {

                    // file = retrieveGrib( hourOffset );
                    case "retrieveGrib":
                        return ( s, a ) -> {
                            int offset = getInt( args[0], s );
                            try( GribRetriever r = new GribRetriever() ) {
                                r.selectLatestRun();
                                return r.retrieveOffset( offset );
                            }
                        };

                    // file = retrieveGribForced( hourOffset );
                    case "retrieveGribForced":
                        return ( s, a ) -> {
                            int offset = getInt( args[0], s );
                            try( GribRetriever r = new GribRetriever() ) {
                                r.selectLatestRun();
                                return r.retrieveOffset( offset, true );
                            }
                        };

                    default:
                        break;
                }
                break;

            case 2:
                switch( name ) {

                    // file = retrieveGrib( file, hourOffset );
                    case "retrieveGrib":
                        return ( s, a ) -> {
                            File file = getFile( args[0], s );
                            int offset = getInt( args[1], s );
                            try( GribRetriever r = new GribRetriever() ) {
                                r.selectLatestRun();
                                return r.retrieveOffset( file, offset );
                            }
                        };

                    // Force download
                    // file = retrieveGribForced( file, hourOffset );
                    case "retrieveGribForced":
                        return ( s, a ) -> {
                            File file = getFile( args[0], s );
                            int offset = getInt( args[1], s );
                            try( GribRetriever r = new GribRetriever() ) {
                                r.selectLatestRun();
                                return r.retrieveOffset( file, offset, true );
                            }
                        };

                    default:
                        break;
                }
                break;

            case 3:
                switch( name ) {

                    // file = retrieveGrib( dir, hourOffset, force );
                    case "retrieveGrib":
                        return ( s, a ) -> {
                            File file = getFile( args[0], s );
                            int offset = getInt( args[1], s );
                            boolean force = Logic.isTrue( args[2].invoke( s, a ) );
                            try( GribRetriever r = new GribRetriever() ) {
                                r.selectLatestRun();
                                r.retrieveOffset( file, offset, force );
                            }
                            return file;
                        };

                    default:
                        break;
                }
                break;

            default:
                break;
        }
        return null;
    }

}
