/**
 * 
 */


import java.util.HashMap;
import java.util.Map;

/**
 * @author Wang Yuanpei U14043099 wangyp@bu.edu
 * @since Fall 2018
 */
public class TestCases extends CyclicIterator<Map<String, Angled>> {

  Map<String, Angled> stop() {
    return this.stop;
  }

  private final Map<String, Angled> stop;

  @SuppressWarnings("unchecked")
  TestCases() {
    this.stop = new HashMap<String, Angled>();
    final Map<String, Angled> flat = new HashMap<String, Angled>();
    final Map<String, Angled> dead = new HashMap<String, Angled>();
    final Map<String, Angled> attack = new HashMap<String, Angled>();
    final Map<String, Angled> funny = new HashMap<String, Angled>();

    super.add(stop, flat, dead, attack, funny);

   
     // the stop pose
    stop.put(PA2.LEG1_DISTAL_NAME, new BaseAngled(-68, 0, 0));
    stop.put(PA2.LEG1_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.LEG1_BODY_NAME, new BaseAngled(12, 130, -28));
    stop.put(PA2.LEG1_FEET_NAME, new BaseAngled(40, 0, 0));
    
    stop.put(PA2.LEG2_DISTAL_NAME, new BaseAngled(-64, 0, 0));
    stop.put(PA2.LEG2_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.LEG2_BODY_NAME, new BaseAngled(20, -142, 24));
    stop.put(PA2.LEG2_FEET_NAME, new BaseAngled(40, 0, 0));

    
    stop.put(PA2.LEG3_DISTAL_NAME, new BaseAngled(-66, 0, 0));
    stop.put(PA2.LEG3_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.LEG3_BODY_NAME, new BaseAngled(38, 105, -28));
    stop.put(PA2.LEG3_FEET_NAME, new BaseAngled(40, 0, 0));

    
    
    stop.put(PA2.LEG4_DISTAL_NAME, new BaseAngled(-68, 0, 0));
    stop.put(PA2.LEG4_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.LEG4_BODY_NAME, new BaseAngled(42, -105, 28));
    stop.put(PA2.LEG4_FEET_NAME, new BaseAngled(40, 0, 0));

    
    stop.put(PA2.LEG5_DISTAL_NAME, new BaseAngled(-60, 0, 0));
    stop.put(PA2.LEG5_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.LEG5_BODY_NAME, new BaseAngled(39, 59, -32));
    stop.put(PA2.LEG5_FEET_NAME, new BaseAngled(40, 0, 0));

    
    stop.put(PA2.LEG6_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    stop.put(PA2.LEG6_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.LEG6_BODY_NAME, new BaseAngled(28, -39, 28));
    stop.put(PA2.LEG6_FEET_NAME, new BaseAngled(40, 0, 0));

    
    stop.put(PA2.TENTACLE1_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    stop.put(PA2.TENTACLE1_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.TENTACLE1_BODY_NAME, new BaseAngled(84, 8, 0));
    
    stop.put(PA2.TENTACLE2_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    stop.put(PA2.TENTACLE2_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.TENTACLE2_BODY_NAME, new BaseAngled(84, -20, 0));
    
    stop.put(PA2.Tail_NAME, new BaseAngled(154, 0, 0));
    stop.put(PA2.HEAD_NAME, new BaseAngled(-45, 0, 0));
    stop.put(PA2.BODY_NAME, new BaseAngled(-20, 0, 0));
    stop.put(PA2.Body_To_Tail, new BaseAngled(0,0,0));



    // the flat pose
    flat.put(PA2.LEG1_DISTAL_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG1_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG1_BODY_NAME, new BaseAngled(0, 130, 0));
    flat.put(PA2.LEG1_FEET_NAME, new BaseAngled(0, 0, 0));

    
    flat.put(PA2.LEG2_DISTAL_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG2_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG2_BODY_NAME, new BaseAngled(0, -130, 0));
    flat.put(PA2.LEG2_FEET_NAME, new BaseAngled(0, 0, 0));

    
    flat.put(PA2.LEG3_DISTAL_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG3_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG3_BODY_NAME, new BaseAngled(0, 105, 0));
    flat.put(PA2.LEG3_FEET_NAME, new BaseAngled(0, 0, 0));

    
    flat.put(PA2.LEG4_DISTAL_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG4_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG4_BODY_NAME, new BaseAngled(0, -105, 0));
    flat.put(PA2.LEG4_FEET_NAME, new BaseAngled(0, 0, 0));

    
    flat.put(PA2.LEG5_DISTAL_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG5_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG5_BODY_NAME, new BaseAngled(0, 85, 0));
    flat.put(PA2.LEG5_FEET_NAME, new BaseAngled(0, 0, 0));

    
    flat.put(PA2.LEG6_DISTAL_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG6_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG6_BODY_NAME, new BaseAngled(0, -85, 0));
    flat.put(PA2.LEG6_FEET_NAME, new BaseAngled(0, 0, 0));

   
    flat.put(PA2.TENTACLE1_DISTAL_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.TENTACLE1_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.TENTACLE1_BODY_NAME, new BaseAngled(26, 8, 0));
    
    flat.put(PA2.TENTACLE2_DISTAL_NAME, new BaseAngled(0,0 , 0));
    flat.put(PA2.TENTACLE2_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.TENTACLE2_BODY_NAME, new BaseAngled(26, -20, 0));
    
    flat.put(PA2.Tail_NAME, new BaseAngled(170, 0, 0));
    flat.put(PA2.HEAD_NAME, new BaseAngled(-13, 0, 188));
    flat.put(PA2.BODY_NAME, new BaseAngled(-20, 0, 0));
    flat.put(PA2.Body_To_Tail, new BaseAngled(0,0,0));

    // the dead pose
    dead.put(PA2.LEG1_DISTAL_NAME, new BaseAngled(-114, 0, -2));
    dead.put(PA2.LEG1_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.LEG1_BODY_NAME, new BaseAngled(12, 130, -28));
    dead.put(PA2.LEG1_FEET_NAME, new BaseAngled(-50,0,0));
    
    dead.put(PA2.LEG2_DISTAL_NAME, new BaseAngled(-110, 0, 0));
    dead.put(PA2.LEG2_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.LEG2_BODY_NAME, new BaseAngled(20, -142, 24));
    dead.put(PA2.LEG2_FEET_NAME, new BaseAngled(-50,0,0));

    
    dead.put(PA2.LEG3_DISTAL_NAME, new BaseAngled(-112, 0, 0));
    dead.put(PA2.LEG3_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.LEG3_BODY_NAME, new BaseAngled(38, 105, -28));
    dead.put(PA2.LEG3_FEET_NAME, new BaseAngled(-50,0,0));

    
    dead.put(PA2.LEG4_DISTAL_NAME, new BaseAngled(-114, 0, 0));
    dead.put(PA2.LEG4_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.LEG4_BODY_NAME, new BaseAngled(42, -105, 28));
    dead.put(PA2.LEG4_FEET_NAME, new BaseAngled(-50,0,0));

    
    dead.put(PA2.LEG5_DISTAL_NAME, new BaseAngled(-106, 0, 0));
    dead.put(PA2.LEG5_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.LEG5_BODY_NAME, new BaseAngled(39, 59, -32));
    dead.put(PA2.LEG5_FEET_NAME, new BaseAngled(-50,0,0));

    
    dead.put(PA2.LEG6_DISTAL_NAME, new BaseAngled(-136, 0, 0));
    dead.put(PA2.LEG6_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.LEG6_BODY_NAME, new BaseAngled(28, -39, 28));
    dead.put(PA2.LEG6_FEET_NAME, new BaseAngled(-50,0,0));

    
    dead.put(PA2.TENTACLE1_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    dead.put(PA2.TENTACLE1_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.TENTACLE1_BODY_NAME, new BaseAngled(84, 8, 0));
    
    dead.put(PA2.TENTACLE2_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    dead.put(PA2.TENTACLE2_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.TENTACLE2_BODY_NAME, new BaseAngled(84, -20, 0));
    
    dead.put(PA2.Tail_NAME, new BaseAngled(240, 0, 0));
    dead.put(PA2.HEAD_NAME, new BaseAngled(-70, 0, 28));
    dead.put(PA2.BODY_NAME, new BaseAngled(-72, 0, 0));
    dead.put(PA2.Body_To_Tail, new BaseAngled(0,0,188));
    
    // the attack pose
    attack.put(PA2.LEG1_DISTAL_NAME, new BaseAngled(-52, 0, -2));
    attack.put(PA2.LEG1_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    attack.put(PA2.LEG1_BODY_NAME, new BaseAngled(20, 130, -28));
    attack.put(PA2.LEG1_FEET_NAME, new BaseAngled(40, 0, -0));
    
    
    
    attack.put(PA2.LEG2_DISTAL_NAME, new BaseAngled(-48, 0, -2));
    attack.put(PA2.LEG2_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    attack.put(PA2.LEG2_BODY_NAME, new BaseAngled(28, -142, 24));
    attack.put(PA2.LEG2_FEET_NAME, new BaseAngled(40, 0, -0));

    
    attack.put(PA2.LEG3_DISTAL_NAME, new BaseAngled(-52, -0, -4));
    attack.put(PA2.LEG3_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    attack.put(PA2.LEG3_BODY_NAME, new BaseAngled(40, 85, -26));
    attack.put(PA2.LEG3_FEET_NAME, new BaseAngled(40, 0, -0));

    
    attack.put(PA2.LEG4_DISTAL_NAME, new BaseAngled(-68, 0, 0));
    attack.put(PA2.LEG4_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    attack.put(PA2.LEG4_BODY_NAME, new BaseAngled(42, -105, 28));
    attack.put(PA2.LEG4_FEET_NAME, new BaseAngled(40, 0, -0));

    
    attack.put(PA2.LEG5_DISTAL_NAME, new BaseAngled(-70, 0, 0));
    attack.put(PA2.LEG5_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    attack.put(PA2.LEG5_BODY_NAME, new BaseAngled(41, 25, -22));
    attack.put(PA2.LEG5_FEET_NAME, new BaseAngled(40, 0, -0));

    
    attack.put(PA2.LEG6_DISTAL_NAME, new BaseAngled(-38,0, 0));
    attack.put(PA2.LEG6_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    attack.put(PA2.LEG6_BODY_NAME, new BaseAngled(28, -27, 16));
    attack.put(PA2.LEG6_FEET_NAME, new BaseAngled(40, 0, -0));

   
    attack.put(PA2.TENTACLE1_DISTAL_NAME, new BaseAngled(-12, 0, 0));
    attack.put(PA2.TENTACLE1_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    attack.put(PA2.TENTACLE1_BODY_NAME, new BaseAngled(84, 26, 0));

    attack.put(PA2.TENTACLE2_DISTAL_NAME, new BaseAngled(-12.5, 0, 0));
    attack.put(PA2.TENTACLE2_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    attack.put(PA2.TENTACLE2_BODY_NAME, new BaseAngled(84, -20, 0));
    
    attack.put(PA2.Tail_NAME, new BaseAngled(128, 0, 0));
    attack.put(PA2.HEAD_NAME, new BaseAngled(27, 0, 0));
    attack.put(PA2.BODY_NAME, new BaseAngled(14, 0, 0));
    attack.put(PA2.Body_To_Tail, new BaseAngled(-26, 0, 0));

    funny.put(PA2.LEG1_DISTAL_NAME, new BaseAngled(-68, 0, 0));
    funny.put(PA2.LEG1_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    funny.put(PA2.LEG1_BODY_NAME, new BaseAngled(86, 136, -28));
    funny.put(PA2.LEG1_FEET_NAME, new BaseAngled(40, 0, -0));

    

    funny.put(PA2.LEG2_DISTAL_NAME, new BaseAngled(-68, 0, 0));
    funny.put(PA2.LEG2_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    funny.put(PA2.LEG2_BODY_NAME, new BaseAngled(94, -136, 24));
    funny.put(PA2.LEG2_FEET_NAME, new BaseAngled(40, 0, -0));


    funny.put(PA2.LEG3_DISTAL_NAME, new BaseAngled(-150, 0, -20));
    funny.put(PA2.LEG3_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    funny.put(PA2.LEG3_BODY_NAME, new BaseAngled(100, 93, -30));
    funny.put(PA2.LEG3_FEET_NAME, new BaseAngled(40, 0, -0));


    funny.put(PA2.LEG4_DISTAL_NAME, new BaseAngled(-148, 0, -20));
    funny.put(PA2.LEG4_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    funny.put(PA2.LEG4_BODY_NAME, new BaseAngled(104, -117, 26));
    funny.put(PA2.LEG4_FEET_NAME, new BaseAngled(40, 0, -0));


    funny.put(PA2.LEG5_DISTAL_NAME, new BaseAngled(-112, 0, 14));
    funny.put(PA2.LEG5_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    funny.put(PA2.LEG5_BODY_NAME, new BaseAngled(-21, 59, 118));
    funny.put(PA2.LEG5_FEET_NAME, new BaseAngled(40, 0, -0));


    funny.put(PA2.LEG6_DISTAL_NAME, new BaseAngled(-86, 0, 0));
    funny.put(PA2.LEG6_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    funny.put(PA2.LEG6_BODY_NAME, new BaseAngled(-8, -39, -64));
    funny.put(PA2.LEG6_FEET_NAME, new BaseAngled(40, 0, -0));


    funny.put(PA2.TENTACLE1_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    funny.put(PA2.TENTACLE1_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    funny.put(PA2.TENTACLE1_BODY_NAME, new BaseAngled(84, 4, 32));

    funny.put(PA2.TENTACLE2_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    funny.put(PA2.TENTACLE2_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    funny.put(PA2.TENTACLE2_BODY_NAME, new BaseAngled(84, -20, -14));
    
    funny.put(PA2.Tail_NAME, new BaseAngled(240, -70, 0));
    funny.put(PA2.HEAD_NAME, new BaseAngled(1, -48, 36));
    funny.put(PA2.BODY_NAME, new BaseAngled(-20, -25, 20));
    funny.put(PA2.Body_To_Tail, new BaseAngled(0, 0, 0));
  }
}
