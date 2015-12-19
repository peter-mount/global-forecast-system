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
package onl.area51.gfs.grib2.section.grid;

import java.io.IOException;
import onl.area51.gfs.grib2.io.GribInputStream;

/**
 * Implementation of {@link GridDefinition} for Latitude/Longitude (See Template 3.0) Also called Equidistant Cylindrical or Plate Caree
 * <p>
 * Ref: <a href="http://www.nco.ncep.noaa.gov/pmb/docs/grib2/grib2_table3-1.shtml">GRIB2 Table 3.1</a>
 * <p>
 * @author peter
 */
public class LatLongGrid
        extends GridDefinition
{

    private final int shapeOfEarth;
    private final int scaleFactorOfRadius;
    private final int scaleValueOfRadius;
    private final int scaleFactorOfMajorAxis;
    private final int scaleValueOfMajorAxis;
    private final int scaleFactorOfMinorAxis;
    private final int scaleValueOfMinorAxis;
    private final int noPointsAlongParallel;
    private final int noPointsAlingMeridian;
    private final int basicAngleOfInitialProductionDomain;
    private final int subdivisionsOfBasicAngle;
    private final int latFirstPoint;
    private final int longFirstPoint;

    //private final int resolutionComponentFlags;
    private final boolean iDirectionIncrementsGiven;
    private final boolean jDirectionIncrementsGiven;
    private final boolean resolvedUVrelative;

    private final int latLastPoint;
    private final int longLastPoint;
    private final int iDirectionIncrement;
    private final int jDirectorionIncrement;

    //private final int scanningModeFlags;
    private final boolean pointsFirstRowNegativeX;
    private final boolean pointsFirstRowNegativeY;
    private final boolean adjacentPointsConsecutiveXY;
    private final boolean adjacentRowsScanOppositeDirection;
    private final boolean pointsOddRowsNotOffsetX;
    private final boolean pointsEvenRowsNotOffsetX;
    private final boolean pointsNotOffsetY;
    private final boolean noPointsXY;

    public LatLongGrid( GribInputStream gis )
            throws IOException
    {
        super( gis );

        shapeOfEarth = gis.readUnsignedByte();

        scaleFactorOfRadius = gis.read();
        scaleValueOfRadius = gis.readInt();

        scaleFactorOfMajorAxis = gis.read();
        scaleValueOfMajorAxis = gis.readInt();

        scaleFactorOfMinorAxis = gis.read();
        scaleValueOfMinorAxis = gis.readInt();

        noPointsAlongParallel = gis.readInt();
        noPointsAlingMeridian = gis.readInt();

        basicAngleOfInitialProductionDomain = gis.readInt();
        subdivisionsOfBasicAngle = gis.readInt();

        latFirstPoint = gis.readInt();
        longFirstPoint = gis.readInt();

        final int resolutionComponentFlags = gis.readUnsignedByte();
        iDirectionIncrementsGiven = (resolutionComponentFlags & 0x4) == 0x4;
        jDirectionIncrementsGiven = (resolutionComponentFlags & 0x8) == 0x8;
        resolvedUVrelative = (resolutionComponentFlags & 0x10) == 0x10;

        latLastPoint = gis.readInt();
        longLastPoint = gis.readInt();

        iDirectionIncrement = gis.readInt();
        jDirectorionIncrement = gis.readInt();

        final int scanningModeFlags = gis.readUnsignedByte();
        pointsFirstRowNegativeX = (resolutionComponentFlags & 0x1) == 0x1;
        pointsFirstRowNegativeY = (resolutionComponentFlags & 0x2) == 0x2;
        adjacentPointsConsecutiveXY = (resolutionComponentFlags & 0x4) == 0x4;
        adjacentRowsScanOppositeDirection = (resolutionComponentFlags & 0x8) == 0x8;
        pointsOddRowsNotOffsetX = (resolutionComponentFlags & 0x10) == 0x10;
        pointsEvenRowsNotOffsetX = (resolutionComponentFlags & 0x20) == 0x20;
        pointsNotOffsetY = (resolutionComponentFlags & 0x40) == 0x40;
        noPointsXY = (resolutionComponentFlags & 0x80) == 0x80;

    }

    public int getShapeOfEarth()
    {
        return shapeOfEarth;
    }

    public int getScaleFactorOfRadius()
    {
        return scaleFactorOfRadius;
    }

    public int getScaleValueOfRadius()
    {
        return scaleValueOfRadius;
    }

    public int getScaleFactorOfMajorAxis()
    {
        return scaleFactorOfMajorAxis;
    }

    public int getScaleValueOfMajorAxis()
    {
        return scaleValueOfMajorAxis;
    }

    public int getScaleFactorOfMinorAxis()
    {
        return scaleFactorOfMinorAxis;
    }

    public int getScaleValueOfMinorAxis()
    {
        return scaleValueOfMinorAxis;
    }

    public int getNoPointsAlongParallel()
    {
        return noPointsAlongParallel;
    }

    public int getNoPointsAlingMeridian()
    {
        return noPointsAlingMeridian;
    }

    public int getBasicAngleOfInitialProductionDomain()
    {
        return basicAngleOfInitialProductionDomain;
    }

    public int getSubdivisionsOfBasicAngle()
    {
        return subdivisionsOfBasicAngle;
    }

    public int getLatFirstPoint()
    {
        return latFirstPoint;
    }

    public int getLongFirstPoint()
    {
        return longFirstPoint;
    }

    public boolean isiDirectionIncrementsGiven()
    {
        return iDirectionIncrementsGiven;
    }

    public boolean isjDirectionIncrementsGiven()
    {
        return jDirectionIncrementsGiven;
    }

    public boolean isResolvedUVrelative()
    {
        return resolvedUVrelative;
    }

    public int getLatLastPoint()
    {
        return latLastPoint;
    }

    public int getLongLastPoint()
    {
        return longLastPoint;
    }

    public int getiDirectionIncrement()
    {
        return iDirectionIncrement;
    }

    public int getjDirectorionIncrement()
    {
        return jDirectorionIncrement;
    }

    /**
     * false = Points in the first row or column scan in the +i (+x) direction
     *
     * true Points in the first row or column scan in the -i (-x) direction
     * <p>
     * @return
     */
    public boolean isPointsFirstRowNegativeX()
    {
        return pointsFirstRowNegativeX;
    }

    /**
     * false Points in the first row or column scan in the -j (-y) direction
     * <p>
     * true Points in the first row or column scan in the +j (+y) direction
     * <p>
     * @return
     */
    public boolean isPointsFirstRowNegativeY()
    {
        return pointsFirstRowNegativeY;
    }

    /**
     * false Adjacent points in the i (x) direction are consecutive
     * <p>
     * true Adjacent points in the j (y) direction are consecutive
     * <p>
     * @return
     */
    public boolean isAdjacentPointsConsecutiveXY()
    {
        return adjacentPointsConsecutiveXY;
    }

    /**
     * false All rows scan in the same direction
     * <p>
     * true Adjacent rows scan in the opposite direction
     * <p>
     * @return
     */
    public boolean isAdjacentRowsScanOppositeDirection()
    {
        return adjacentRowsScanOppositeDirection;
    }

    /**
     * false Points within odd rows are not offset in i(x) direction
     * <p>
     * true Points within odd rows are offset by Di/2 in i(x) direction
     * <p>
     * @return
     */
    public boolean isPointsOddRowsNotOffsetX()
    {
        return pointsOddRowsNotOffsetX;
    }

    /**
     * false Points within even rows are not offset in i(x) direction
     * <p>
     * true Points within even rows are offset by Di/2 in i(x) direction
     * <p>
     * @return
     */
    public boolean isPointsEvenRowsNotOffsetX()
    {
        return pointsEvenRowsNotOffsetX;
    }

    /**
     * false Points are not offset in j(y) direction
     * <p>
     * true Points are offset by Di/2 in j(y) direction
     * <p>
     * @return
     */
    public boolean isPointsNotOffsetY()
    {
        return pointsNotOffsetY;
    }

    /**
     * false Rows have Ni grid points and columns have Nj grid points
     * <p>
     * true then:
     * <p>
     * Rows have Ni grid points if points are not offset in i direction
     * Rows have Ni-1 grid points if points are offset by Di/2 in i direction
     * Columns have Nj grid points if points are not offset in j direction
     * Columns have Nj-1 grid points if points are offset by Dj/2 in j direction
     * <p>
     * @return
     */
    public boolean isNoPointsXY()
    {
        return noPointsXY;
    }

}
