package org.eclipse.sirius.diagram.ui.edit.internal.part;

import org.eclipse.draw2d.Graphics;
import org.eclipse.sirius.diagram.LineStyle;

public class LineStyleHelper {

    private LineStyleHelper()
    {
        
    }
    
    
    public static int getLineStyle(LineStyle linestyle)
    {
        int result = Graphics.LINE_SOLID;
        switch (linestyle) {
        case SOLID_LITERAL:
            result=Graphics.LINE_SOLID;
            break;
        case DOT_LITERAL:
            result=Graphics.LINE_DOT;
            break;
        case DASH_LITERAL:
            result = Graphics.LINE_DASH;
            break;
        case DASH_DOT_LITERAL:
            result = Graphics.LINE_DASHDOT;
            break;
        default:
            break;
        }
        
        return result;
    }
}
