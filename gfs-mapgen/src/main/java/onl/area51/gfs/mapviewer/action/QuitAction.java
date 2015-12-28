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

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

/**
 *
 * @author peter
 */
public class QuitAction
        extends AbstractAction
{

    public QuitAction()
    {
        super( "Exit" );
        setEnabled( enabled );
        putValue( SHORT_DESCRIPTION, "Exit application" );
        putValue( ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke( KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK ) );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    { 
        System.out.println("****EXIT****");
        System.exit( 0 );
    }

}
