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
import java.time.LocalDateTime;
import onl.area51.gfs.grib2.io.GribInputStream;

/**
 * the Identification section
 * <p>
 * @author peter
 */
public class Identification
        extends Section
{

    private final int center;
    private final int subcenter;
    private final int masterTablesVersion;
    private final int localTablesVersion;
    private final ReferenceTimeSignificance referenceTimeSignificance;
    private final LocalDateTime dateTime;
    private final int productionStatus;
    private final TypeOfData typeOfData;

    public Identification( GribInputStream gis )
            throws IOException
    {
        super( gis );

        // 6-7
        center = gis.readShort();
        // 8-9
        subcenter = gis.readShort();

        // 10
        masterTablesVersion = gis.readUnsignedByte();

        // 11
        localTablesVersion = gis.readUnsignedByte();

        // 12
        referenceTimeSignificance = ReferenceTimeSignificance.lookup( gis.readUnsignedByte() );

        // 13-19 multiple fields
        dateTime = LocalDateTime.of( gis.readShort(), gis.readUnsignedByte(), gis.readUnsignedByte(),
                                     gis.readUnsignedByte(), gis.readUnsignedByte(), gis.readUnsignedByte() );

        // 20
        productionStatus = gis.readUnsignedByte();

        // 21
        typeOfData = TypeOfData.lookup( gis.readUnsignedByte() );

        // Reserved entries
        seekNextSection( gis );
    }

    public int getCenter()
    {
        return center;
    }

    public int getSubcenter()
    {
        return subcenter;
    }

    /**
     * <table>
     * <tr><th>Code</th><th>GRIB Master table</th></tr>
     * <tr><td>0</td><td>Experimental</td></tr>
     * <tr><td>1</td><td>Version Implemented on 7 November 2001</td></tr>
     * <tr><td>2</td><td>Version Implemented on 4 November 2003</td></tr>
     * <tr><td>3</td><td>Version Implemented on 2 November 2005</td></tr>
     * <tr><td>4</td><td>Version Implemented on 7 November 2007</td></tr>
     * <tr><td>5</td><td>Version Implemented on 4 November 2009</td></tr>
     * <tr><td>6</td><td>Version Implemented on 15 September 2010</td></tr>
     * <tr><td>7</td><td>Version Implemented on 4 May 2011</td></tr>
     * <tr><td>8</td><td>Version Implemented on 8 November 2011</td></tr>
     * <tr><td>9</td><td>Version Implemented on 2 May 2012</td></tr>
     * <tr><td>10</td><td>Version Implemented on 7 November 2012</td></tr>
     * <tr><td>11</td><td>Version Implemented on 8 May 2013</td></tr>
     * <tr><td>12</td><td>Version Implemented on 14 November 2013</td></tr>
     * <tr><td>13</td><td>Version Implemented on 7 May 2014</td></tr>
     * <tr><td>14</td><td>Version Implemented on 5 November 2014</td></tr>
     * <tr><td>15</td><td>Version Implemented on 6 May 2015</td></tr>
     * <tr><td>16</td><td>Pre-operational to be implemented by next amendment</td></tr>
     * <tr><td>17-254</td><td>Future Operational Version</td></tr>
     * <tr><td>255</td><td>Master tables not used. Local table entries and local templates may use the entire range of the table, not just those sections marked
     * "Reserved for local use".</td></tr>
     * </table>
     * <p>
     * @return
     */
    public int getMasterTablesVersion()
    {
        return masterTablesVersion;
    }

    /**
     * The local tables version. 0=not used, 1..254 the version, 255 missing
     * <p>
     * @return
     */
    public int getLocalTablesVersion()
    {
        return localTablesVersion;
    }

    public ReferenceTimeSignificance getReferenceTimeSignificance()
    {
        return referenceTimeSignificance;
    }

    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

    public int getProductionStatus()
    {
        return productionStatus;
    }

    public TypeOfData getTypeOfData()
    {
        return typeOfData;
    }

    @Override
    public String toString()
    {
        return "Identification{" + "center=" + center + ", subcenter=" + subcenter + ", masterTablesVersion=" + masterTablesVersion + ", localTablesVersion=" + localTablesVersion + ", referenceTimeSignificance=" + referenceTimeSignificance + ", dateTime=" + dateTime + ", productionStatus=" + productionStatus + ", typeOfData=" + typeOfData + '}';
    }

}
