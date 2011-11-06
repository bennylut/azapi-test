package ext.sim.agents;

import ext.sim.tools.Util;
import bgu.csp.az.api.agt.SimpleAgent;
import bgu.csp.az.api.ano.Algorithm;
import bgu.csp.az.api.ano.WhenReceived;
import bgu.csp.az.api.tools.Assignment;
import bgu.csp.az.impl.tools.DFSPsaudoTree;

@Algorithm(value="DPOP", useIdleDetector=true)
public class DPOPAgent extends SimpleAgent {

	private DFSPsaudoTree tree;
	private Util util;
	private Assignment cpa;
	
		
    @Override
    public void start() {
//    	log(this.getProblem().toString());
    	tree = new DFSPsaudoTree();
//    	log("tree is done");
    	util = new Util(this.getProblem(),this.getId(), this.getDomainSize());
//    	log("util is done");
    	cpa = new Assignment();
//    	log("cpa done");
    	tree.calculate(this);
//    	log("tree calculated");
    	if (tree.isLeaf()) {
//    		log("im a leaf");
//            log("processing");
    		process();
        }
    }

    private int ChooseOptimalValue() {
//        log("choosing optimalValue");
    	if (tree.isRoot()) {
            this.util.computeUtil(this);
//            log("ending search for optimal value");
            return this.util.getRootUtil().getValue();
        } else {
//        	log("ending search for optimal value");
            return this.util.getBestUtil(this.cpa).getValue();
        }
    	
    }
        
	@WhenReceived("ValueMessage")
	public void handleValueMessage(Assignment cpa){
//        log(" got value message from Agent " + getCurrentMessage().getSender());
        for (int i = 0; i < this.getProblem().getNumberOfVariables(); i++) {
            if (cpa.isAssigned(i)) {
                this.cpa.assign(i,cpa.getAssignment(i));
            }
        }
        final int optimalValue = ChooseOptimalValue();
        this.cpa.assign(this.getId(), optimalValue);
        send("ValueMessage", this.cpa).toAll(tree.getChildren());
//        log ("finishing with assignment " + optimalValue);
        submitCurrentAssignment(optimalValue);
//        log("ending handle valueMessage");
	}
    
    
    private boolean util_messages_from_all_children_arrived() {
        return util.getNoOfChildrenUtils() == tree.getChildren().size();
    }
    
        
    private void process() {
//    	log("computing util");
    	this.util.computeUtil(tree.getParent(), tree.getPsaudoParents(),this);    	
//    	log("sending message UtilMessage to parent");
    	send("UtilMessage",this.util).to(tree.getParent());
    }
    
    
	@WhenReceived("UtilMessage")
	public void handleUtilMessage(Util util){
//      log(" got util message from Agent " + getCurrentMessage().getSender());
		this.util.addChildUtil(util);
        if (util_messages_from_all_children_arrived()) {
            if (tree.isRoot()) {
                final int optimalValue = ChooseOptimalValue();
//                submitCurrentAssignment(optimalValue);
                this.cpa.assign(getId(), optimalValue); 
                send("ValueMessage", this.cpa).toAll(tree.getChildren());
//                log ("finishing with assignment " + optimalValue);
                submitCurrentAssignment(optimalValue);
            } else {
                this.util.computeUtil(tree.getParent(), tree.getPsaudoParents(),this);
                send("UtilMessage", this.util).to(tree.getParent());
            }
        }
//        log("ending handle utilMessage");
	}
	
	@Override
	public void onIdleDetected() {
		if (isFirstAgent()){
//			log("FINISHING");
			finishWithAccumulationOfSubmitedPartialAssignments();
		}
	}
	

 
}