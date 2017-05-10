/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.HashMap;

/**
 *
 * @author Luis
 */
public class SimpleBehaviour extends CyclicBehaviour {

    private String tag;
    private String name;
    private static final String OPINION_TRADE = "opinion";

    private HashMap<String, String> beliefs;

    @Override
    public void onStart() {

        name = myAgent.getLocalName();
        beliefs = new HashMap<>();

        tag = "(" + name + "):";

        if (name.equals("agent1")) {
            beliefs.put("agent2", "You suck!");
            beliefs.put("agent3", "You are the best!");
        } else if (name.equals("agent2")) {
            beliefs.put("agent1", "You are great!");
            beliefs.put("agent3", "Wtf are you?");
        } else {
            beliefs.put("agent1", "You are cool!");
            beliefs.put("agent2", "You are...!");
        }

    }

    @Override
    public void action() {

        ACLMessage message = myAgent.receive();

        if (message != null) {
            if (message.getPerformative() == ACLMessage.REQUEST) {

                if (message.getConversationId().equals("debug")) {

                    sendRequest();

                } else if (message.getConversationId().equals(OPINION_TRADE)) {
                    System.out.println(tag + " El agente " + message.getSender().getLocalName() + " quiere saber mi opinion sobre el");
                    String opinion = beliefs.get(message.getSender().getLocalName());
                    reply(message,opinion);
                }

            }else if(message.getPerformative() == ACLMessage.INFORM){
                String opinion = message.getContent();
                System.out.println(tag+" Lo que "+message.getSender().getLocalName()+" piensa de mi es: "+opinion);
            }
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

    private void reply(ACLMessage message,String opinion) {
        ACLMessage reply = message.createReply();
        reply.setPerformative(ACLMessage.INFORM);

        reply.setContent(opinion);
        reply.setConversationId(OPINION_TRADE);
        myAgent.send(reply);
    }

}
