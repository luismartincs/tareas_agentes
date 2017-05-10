/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import behaviours.QuestionsBehaviour;
import behaviours.SimpleBehaviour;
import jade.core.Agent;

/**
 *
 * @author Luis
 */
public class SimpleAgent extends Agent{
    
    private SimpleBehaviour cBehaviour;
    private QuestionsBehaviour qBehaviour;
    
    @Override
    protected void setup() {

        System.out.println("Hello World. I’m an agent!"); 
        System.out.println("My local-name is "+getAID().getLocalName()); 
        System.out.println("My GUID is "+getAID().getName());
        
        cBehaviour = new SimpleBehaviour();
        qBehaviour = new QuestionsBehaviour(this, 5000+(int)(Math.random()*5000));
        
        addBehaviour(cBehaviour);
        addBehaviour(qBehaviour);
    }
    
    
}
