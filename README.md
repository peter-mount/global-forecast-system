# global-forecast-system
Code to handle Global Forecast System (GFS) forecasts in the GRIB2 format

Note: This is a work in progress, so it doesn't do much just yet.

Purpose: To read GRIB2 files from the NOAA http/ftp server which are generated 4 times a day and contain the GFS weather forecast.

NCEP NOAA: http://www.nco.ncep.noaa.gov/pmb/products/gfs/

For example, the 0.25 degree resolution data is available from above on the server ftp://ftp.ncep.noaa.gov/pub/data/nccf/com/gfs/prod

Look for a file like: gfs.t06z.pgrb2.0p25.f012 which is the gfs file generated at 0600Z and has the forecast hour of 012 from 0600. This will be about 250Mb in size.

So far the code will parse this file for the main structure. Next is to be able to extract data from it and then to produce weather forecast maps from that data.
