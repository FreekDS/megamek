package megamek.common;

import megamek.server.Server;
import java.io.Serializable;

/**
 * Date: 5/24/21
 * Time: 10:05 AM
 */

public interface IElo extends Serializable {
    
    void updateElo(boolean win);
    
    double getElo();
    
    void setElo(double elo);
    
}
