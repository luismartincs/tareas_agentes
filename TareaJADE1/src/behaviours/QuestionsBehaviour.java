/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Luis
 */
public class QuestionsBehaviour extends TickerBehaviour {

    private static final String OPINION_TRADE = "opinion";

    private String tag;
    private String name;

    public QuestionsBehaviour(Agent a, long period) {
        super(a, period);
        name = a.getLocalName();
        tag = "(" + name + "): ";
    }

    @Override
    protected void onTick() {
        int nticks = getTickCount(); // obtenemos el numero de ticks desde el último reset
        if (nticks == 1) {
            sendRequest();
            reset();
        } else {

        }
    }

    private void sendRequest() {

        System.out.println(tag + " Preguntando opinion sobre mi");

        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);

        String dest1 = "";
        String dest2 = "";

        if (name.equals("agent1")) {
            dest1 = "agent2";
            dest2 = "agent3";
        } else if (name.equals("agent2")) {
            dest1 = "agent1";
            dest2 = "agent3";
        } else if (name.equals("agent3")) {
            dest1 = "agent1";
            dest2 = "agent2";
        }

        request.addReceiver(new AID(dest1, AID.ISLOCALNAME));
        request.addReceiver(new AID(dest2, AID.ISLOCALNAME));

        request.setContent("get-opinion");
        request.setConversationId(OPINION_TRADE);

        myAgent.send(request);
    }

    public void reset() {
        super.reset();
    }
}
