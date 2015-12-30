// Script to build various tests based on the test .job files
def mkdir = { File f -> if( f.mkdirs() ) println 'Created ' + f; f }

def destDir = new File( project.basedir, "target/generated-test-sources/groovy/onl/area51/gfs/grib2/job" );
mkdir(destDir);

generated = "@Generated(\""+new Date()+"\")";

def sourceDir = new File( project.basedir, "src/test/resources" );

// Work out the test classes based on job name, eg scope1.job becomes Scope
def tests = [] as Set;
sourceDir.eachDirRecurse {
    it.eachFileMatch( ~/.*\.job/ ) {
        file -> n = file.toString();
        // Limit to /job/*.job files
        if((m = n =~ /job\/([a-zA-Z][a-zA-Z_]*?)([0-9]*?).job$/))
            tests << m.group(1);
    }
}

// Uncomment to limit to a single test set
//tests = ['collection'];

// Now create each test class
tests.each {
    test ->
        className = test.replaceAll( "^([a-z])", { Object[] c->c[1].toUpperCase() } ) + "Test";

        jobs = [];
        sourceDir.eachDirRecurse {
            it.eachFileMatch( ~/${test}.*\.job/ ) {
                file -> jobs << file.name.replace(".job","");
            }
        };
        jobs=jobs.sort();

        new File( "$destDir/"+className+".java" ).withWriter { out ->
            out.println "package onl.area51.gfs.grib2.job;";
            out.println "";
            //out.println "import static org.junit.Assert.*;";
            out.println "import javax.annotation.Generated;";
            out.println "import org.junit.Test;";
            out.println "import uk.trainwatch.job.Job;";
            out.println "";
            out.println "/**";
            out.println " * Runs our test .job files through the Compiler ensuring that";
            out.println " * the compilation actually does work.";
            out.println " */";
            out.println generated;
            out.println "public class "+className;
            out.println "\textends AbstractCompilerTest";
            out.println "{";

            // Now each test case
            jobs.each {
                name ->
                out.println "";
                //out.println "\t@Test( timeout = 3000L )";
                out.println "\t@Test( timeout = 300000L )";
                out.println "\tpublic void " + name + "()";
                out.println "\t\tthrows Throwable";
                out.println "\t{";
                out.println "\t\trunTest( \"" + name + "\");";
                out.println "\t}";
            }

            out.println "";
            out.println "}";
    }
};
