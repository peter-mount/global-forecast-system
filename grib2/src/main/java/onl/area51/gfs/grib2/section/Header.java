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
package onl.area51.gfs.grib2.section;

import java.io.IOException;
import onl.area51.gfs.grib2.io.GribInputStream;

/**
 * The header (Indicator) section of a GRIB2 file. This is the first part of that file.
 * <p>
 * This section serves to identify the start of the
 * record in a
 * human readable form, indicate the total length of the message, and
 * indicate the Edition number of GRIB used to construct or encode the
 * message. For GRIB2, this section is always 16 octets long.
 * <p>
 * <table border="1" cellspacing="0" cellpadding="5" width="80%">
 * <tbody>
 * <tr>
 * <th style="text-align: center; background-color: lightgrey;">Octet Number</th>
 * <th style="text-align: center; background-color: lightgrey;">Content</th>
 * </tr>
 * <tr>
 * <td>
 * <center>1-4</center>
 * </td>
 * <td>'GRIB' (Coded according to the International Alphabet Number
 * 5)<br>
 * </td>
 * </tr>
 * <tr>
 * <td style="vertical-align: top; text-align: center;">5-6<br>
 * </td>
 * <td style="vertical-align: top;">Reserved<br>
 * </td>
 * </tr>
 * <tr>
 * <td style="vertical-align: top; text-align: center;">7<br>
 * </td>
 * <td style="vertical-align: top;">Discipline (From <a href="grib2_table0-0.shtml">Table 0.0</a>)<br>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <center>8</center>
 * </td>
 * <td>Edition number - 2 for GRIB2<br>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <center>9-16<br>
 * </center>
 * </td>
 * <td>Total length of GRIB message in octets (All sections);</td>
 * </tr>
 * </tbody>
 * </table>
 * <p>
 * Ref: <a href="http://www.nco.ncep.noaa.gov/pmb/docs/grib2/grib2_sect0.shtml">GRIB2 Section 0</a>
 * <p>
 * @author peter
 */
public class Header
        extends Block
{

    private final Discipline discipline;
    private final int edition;
    private final long totalLength;

    public Header( GribInputStream gis )
            throws IOException
    {
        super( gis );

        if( !"GRIB".equals( gis.readByteString( 4 ) ) ) {
            throw new IOException( "Not a GRIB file" );
        }

        gis.skipBytes( 2 );
        discipline = Discipline.lookup( gis.readUnsignedByte() );
        this.edition = gis.readUnsignedByte();
        this.totalLength = gis.readLong();

        if( edition != 2 ) {
            throw new IOException( "Not a Grib2 file, got Grib" + edition );
        }

        if( discipline == Discipline.RESERVED ) {
            throw new IOException( "Unsupported discipline" );
        }
    }

    public Discipline getDiscipline()
    {
        return discipline;
    }

    /**
     * Edition number - 2 for GRIB2
     * <p>
     * @return
     */
    public int getEdition()
    {
        return edition;
    }

    /**
     * Total length of GRIB message in octets (All sections);
     * <p>
     * @return
     */
    public long getTotalLength()
    {
        return totalLength;
    }

    @Override
    public final int getLength()
    {
        return (int) totalLength;
    }

    @Override
    public String toString()
    {
        return "Header{" + "discipline=" + discipline + ", edition=" + edition + ", totalLength=" + totalLength + '}';
    }

}
