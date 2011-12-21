/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.impl.stat;

import bgu.dcr.az.api.Agent;
import bgu.dcr.az.api.Hooks.BeforeCallingFinishHook;
import bgu.dcr.az.api.ano.Register;
import bgu.dcr.az.api.infra.Execution;
import bgu.dcr.az.api.infra.Test;
import bgu.dcr.az.api.infra.stat.DBRecord;
import bgu.dcr.az.api.infra.stat.Database;
import bgu.dcr.az.api.infra.stat.VisualModel;
import bgu.dcr.az.api.infra.stat.vmod.LineVisualModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bennyl
 */
@Register(name="cc-sc", display="Avarage Number Of Constraint Checks")
public class CCStatisticCollector extends AbstractStatisticCollector<CCStatisticCollector.CCRecord> {

    @Override
    public VisualModel analyze(Database db, Test r) {
        String query = "select AVG(cc) as avg, rVar, algorithm from CC where TEST = '" + r.getName() + "' group by algorithm, rVar order by rVar";
        LineVisualModel line = new LineVisualModel(r.getRunningVarName(), "Avg(CC)", "CC");
        try {
            ResultSet rs = db.query(query);
            while (rs.next()) {
                line.setPoint(rs.getString("algorithm"), rs.getFloat("rVar"), rs.getFloat("avg"));
            }
            return line;
        } catch (SQLException ex) {
            Logger.getLogger(NCCCStatisticCollector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public void hookIn(final Agent[] agents, final Execution ex) {
        agents[0].hookIn(new BeforeCallingFinishHook() {

            @Override
            public void hook(Agent a) {
                int sum = 0;
                for (Agent ag : agents) {
                    sum += ag.getNumberOfConstraintChecks();
                }

                submit(new CCRecord(ex.getTest().getCurrentVarValue(), sum, a.getAlgorithmName()));
            }
        });
    }

    @Override
    public String getName() {
        return "Constraint Checks";
    }
    
    public static class CCRecord extends DBRecord {

        double rVar;
        int cc;
        String algorithm;

        public CCRecord(double rVal, int cc, String algorithm) {
            this.rVar = rVal;
            this.cc = cc;
            this.algorithm = algorithm;
        }

        @Override
        public String provideTableName() {
            return "CC";
        }
    }
}