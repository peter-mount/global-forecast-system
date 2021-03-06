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
package onl.area51.gfs.mapviewer.action;

import java.util.Enumeration;
import javax.swing.DefaultListModel;

/**
 *
 * @author peter
 */
public class DataSetModel<T>
        extends DefaultListModel<T>
{

    public DataSetModel()
    {
    }

    public DataSetModel add( T ds )
    {
        addElement( ds );
        return this;
    }

    public static <T> DataSetModel<T> combine( DataSetModel<T> a, DataSetModel<T> b )
    {
        Enumeration<T> en = b.elements();
        while( en.hasMoreElements() ) {
            a.addElement( en.nextElement() );
        }
        return a;
    }
}
