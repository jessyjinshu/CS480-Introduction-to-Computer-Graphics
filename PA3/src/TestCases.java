/**
 * @author Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>
 * @author Zezhou Sun <micou@bu.edu>
 * @since Spring 2011
 * 
 *  /**
 * @author Jinshu Yang U19470900 jinshuy@bu.edu
 * @since Fall 2019
 */
/**
 * TestCases.java - includes five poses of Ant
 */

import java.util.HashMap;
import java.util.Map;

public class TestCases extends CyclicIterator<Map<String, Configuration>> {

	public static final String HEAD_NAME = "head" ;
	public static final String BODY_NAME = "body" ;
	public static final String MIDBODY_NAME = "midbody" ;

	public static final String TAIL_NAME = "tail" ;
	
	public static final String ANTENNA1_TOP_NAME = "antena1 top";
	public static final String ANTENNA2_TOP_NAME = "antenna2 top";
	
	public static final String ANTENNA1_END_NAME = "antena1 end";
	public static final String ANTENNA2_END_NAME = "antenna2 end";
	
	public static final String CAMERA_NAME = "camera";
	
  	public static final String LEFT_LEG1_TOP_NAME = "leftleg1 top";
  	public static final String LEFT_LEG1_MIDDLE_NAME = "leftleg1 middle";
  	public static final String LEFT_LEG1_END_NAME = "leftleg1 end";
  	
  	public static final String LEFT_LEG2_TOP_NAME = "leftleg2 top";
  	public static final String LEFT_LEG2_MIDDLE_NAME = "leftleg2 middle";
  	public static final String LEFT_LEG2_END_NAME = "leftleg2 end";
  	
  	public static final String LEFT_LEG3_TOP_NAME = "leftleg3 top";
  	public static final String LEFT_LEG3_MIDDLE_NAME = "leftleg3 middle";
  	public static final String LEFT_LEG3_END_NAME = "leftleg3 end";
  	
  	public static final String RIGHT_LEG1_TOP_NAME = "rightleg1 top";
  	public static final String RIGHT_LEG1_MIDDLE_NAME = "rightleg1 middle";
  	public static final String RIGHT_LEG1_END_NAME = "rightleg1 end";
  	
  	public static final String RIGHT_LEG2_TOP_NAME = "rightleg2 top";
  	public static final String RIGHT_LEG2_MIDDLE_NAME = "rightleg2 middle";
  	public static final String RIGHT_LEG2_END_NAME = "rightleg2 end";
  	
  	public static final String RIGHT_LEG3_TOP_NAME = "rightleg3 top";
  	public static final String RIGHT_LEG3_MIDDLE_NAME = "rightleg3 middle";
  	public static final String RIGHT_LEG3_END_NAME = "rightleg3 end";

	Map<String, Configuration> stop() {
		return this.stop;
	}

	private final Map<String, Configuration> stop;

	@SuppressWarnings("unchecked")
	TestCases() {
		this.stop = new HashMap<String, Configuration>();
		final Map<String, Configuration> up = new HashMap<String, Configuration>();
		final Map<String, Configuration> die = new HashMap<String, Configuration>();
		final Map<String, Configuration> left = new HashMap<String, Configuration>();
		final Map<String, Configuration> right = new HashMap<String, Configuration>();
		final Map<String, Configuration> random = new HashMap<String, Configuration>();

		super.add(random,stop,die,left,right);

		// test cases
		
		// the stop test case
		stop.put(HEAD_NAME, new BaseConfiguration(30, 0, 0));
		stop.put(BODY_NAME, new BaseConfiguration(20, 0, 0));
		stop.put(MIDBODY_NAME, new BaseConfiguration(0, 0, 20));
		stop.put(TAIL_NAME, new BaseConfiguration(10, 0, 0));


		stop.put(ANTENNA1_TOP_NAME, new BaseConfiguration(270, 0, 0));
		stop.put(ANTENNA1_END_NAME, new BaseConfiguration(60, 30, 0));
		stop.put(ANTENNA2_TOP_NAME, new BaseConfiguration(270, 0, 0));
		stop.put(ANTENNA2_END_NAME, new BaseConfiguration(60, -30, 0));
		stop.put(LEFT_LEG1_TOP_NAME, new BaseConfiguration(10, 60, 0));
		stop.put(LEFT_LEG1_MIDDLE_NAME, new BaseConfiguration(60, 0, 0));
		stop.put(LEFT_LEG1_END_NAME, new BaseConfiguration(-45, 0, 0));
		stop.put(LEFT_LEG2_TOP_NAME, new BaseConfiguration(40, 100, -30));
		stop.put(LEFT_LEG2_MIDDLE_NAME, new BaseConfiguration(60, 0, 0));
		stop.put(LEFT_LEG2_END_NAME, new BaseConfiguration(-45, 0, 0));
		stop.put(LEFT_LEG3_TOP_NAME, new BaseConfiguration(10, 130, -30));
		stop.put(LEFT_LEG3_MIDDLE_NAME, new BaseConfiguration(60, 0, 0));
		stop.put(LEFT_LEG3_END_NAME, new BaseConfiguration(-45, 0, 0));
		stop.put(RIGHT_LEG1_TOP_NAME, new BaseConfiguration(-10, -60, 0));
		stop.put(RIGHT_LEG1_MIDDLE_NAME, new BaseConfiguration(60, 0, 0));
		stop.put(RIGHT_LEG1_END_NAME, new BaseConfiguration(-45, 0, 0));
		stop.put(RIGHT_LEG2_TOP_NAME, new BaseConfiguration(40,-100,30));
		stop.put(RIGHT_LEG2_MIDDLE_NAME, new BaseConfiguration(60, 0, 0));
		stop.put(RIGHT_LEG2_END_NAME, new BaseConfiguration(-45, 0, 0));
		stop.put(RIGHT_LEG3_TOP_NAME, new BaseConfiguration(10,-130, 30));
		stop.put(RIGHT_LEG3_MIDDLE_NAME, new BaseConfiguration(60, 0, 0));
		stop.put(RIGHT_LEG3_END_NAME, new BaseConfiguration(-45, 0, 0));
		
		//UP POSE
		up.put(HEAD_NAME, new BaseConfiguration(30, 0, 0));
		up.put(BODY_NAME, new BaseConfiguration(20, 0, 0));
		up.put(MIDBODY_NAME, new BaseConfiguration(0, 0, 20));
		up.put(TAIL_NAME, new BaseConfiguration(10, 0, 0));


		up.put(ANTENNA1_TOP_NAME, new BaseConfiguration(270, 0, 0));
		up.put(ANTENNA1_END_NAME, new BaseConfiguration(60, 30, 0));
		up.put(ANTENNA2_TOP_NAME, new BaseConfiguration(270, 0, 0));
		up.put(ANTENNA2_END_NAME, new BaseConfiguration(60, -30, 0));
		up.put(LEFT_LEG1_TOP_NAME, new BaseConfiguration(10, 30, 0));
		up.put(LEFT_LEG1_MIDDLE_NAME, new BaseConfiguration(60, 10, 0));
		up.put(LEFT_LEG1_END_NAME, new BaseConfiguration(-20, 0, 0));
		up.put(LEFT_LEG2_TOP_NAME, new BaseConfiguration(40, 80, -50));
		up.put(LEFT_LEG2_MIDDLE_NAME, new BaseConfiguration(60, 0, 0));
		up.put(LEFT_LEG2_END_NAME, new BaseConfiguration(-45, 0, 0));
		up.put(LEFT_LEG3_TOP_NAME, new BaseConfiguration(10, 130, -30));
		up.put(LEFT_LEG3_MIDDLE_NAME, new BaseConfiguration(60, 0, 0));
		up.put(LEFT_LEG3_END_NAME, new BaseConfiguration(-45, 0, 0));
		up.put(RIGHT_LEG1_TOP_NAME, new BaseConfiguration(-10, -30, 0));
		up.put(RIGHT_LEG1_MIDDLE_NAME, new BaseConfiguration(60, 0, 0));
		up.put(RIGHT_LEG1_END_NAME, new BaseConfiguration(-20, 0, 0));
		up.put(RIGHT_LEG2_TOP_NAME, new BaseConfiguration(40,-80,50));
		up.put(RIGHT_LEG2_MIDDLE_NAME, new BaseConfiguration(60, 0, 0));
		up.put(RIGHT_LEG2_END_NAME, new BaseConfiguration(-45, 0, 0));
		up.put(RIGHT_LEG3_TOP_NAME, new BaseConfiguration(10,-130, 30));
		up.put(RIGHT_LEG3_MIDDLE_NAME, new BaseConfiguration(60, 0, 0));
		up.put(RIGHT_LEG3_END_NAME, new BaseConfiguration(-45, 0, 0));
		
		//die POSE
		die.put(HEAD_NAME, new BaseConfiguration(30, 0, 0));
		die.put(BODY_NAME, new BaseConfiguration(20, 0, 0));
		die.put(MIDBODY_NAME, new BaseConfiguration(0, 0, 20));
		die.put(TAIL_NAME, new BaseConfiguration(10, 0, 0));


		die.put(ANTENNA1_TOP_NAME, new BaseConfiguration(270, 0, 0));
		die.put(ANTENNA1_END_NAME, new BaseConfiguration(60, 30, 0));
		die.put(ANTENNA2_TOP_NAME, new BaseConfiguration(270, 0, 0));
		die.put(ANTENNA2_END_NAME, new BaseConfiguration(60, -30, 0));
		die.put(LEFT_LEG1_TOP_NAME, new BaseConfiguration(10, 40, 0));
		die.put(LEFT_LEG1_MIDDLE_NAME, new BaseConfiguration(60, -10, 0));
		die.put(LEFT_LEG1_END_NAME, new BaseConfiguration(-45, -10, 0));
		die.put(LEFT_LEG2_TOP_NAME, new BaseConfiguration(40, 80, -30));
		die.put(LEFT_LEG2_MIDDLE_NAME, new BaseConfiguration(60, -10, 0));
		die.put(LEFT_LEG2_END_NAME, new BaseConfiguration(-45, -10, 0));
		die.put(LEFT_LEG3_TOP_NAME, new BaseConfiguration(10, 110, -30));
		die.put(LEFT_LEG3_MIDDLE_NAME, new BaseConfiguration(60, -10, 0));
		die.put(LEFT_LEG3_END_NAME, new BaseConfiguration(-45, -10, 0));
		die.put(RIGHT_LEG1_TOP_NAME, new BaseConfiguration(-10, -40, 0));
		die.put(RIGHT_LEG1_MIDDLE_NAME, new BaseConfiguration(60, 10, 0));
		die.put(RIGHT_LEG1_END_NAME, new BaseConfiguration(-45, 10, 0));
		die.put(RIGHT_LEG2_TOP_NAME, new BaseConfiguration(40,-80,30));
		die.put(RIGHT_LEG2_MIDDLE_NAME, new BaseConfiguration(60, 10, 0));
		die.put(RIGHT_LEG2_END_NAME, new BaseConfiguration(-45, 10, 0));
		die.put(RIGHT_LEG3_TOP_NAME, new BaseConfiguration(10,-110, 30));
		die.put(RIGHT_LEG3_MIDDLE_NAME, new BaseConfiguration(60, 10, 0));
		die.put(RIGHT_LEG3_END_NAME, new BaseConfiguration(-45, 10, 0));
		
		
		//left position
		left.put(HEAD_NAME, new BaseConfiguration(30, 0, 0));
		left.put(BODY_NAME, new BaseConfiguration(20, 0, 0));
		left.put(MIDBODY_NAME, new BaseConfiguration(0, 0, 20));
		left.put(TAIL_NAME, new BaseConfiguration(10, 0, 0));


		left.put(ANTENNA1_TOP_NAME, new BaseConfiguration(270, 0, 0));
		left.put(ANTENNA1_END_NAME, new BaseConfiguration(60, 30, 0));
		left.put(ANTENNA2_TOP_NAME, new BaseConfiguration(270, 0, 0));
		left.put(ANTENNA2_END_NAME, new BaseConfiguration(60, -30, 0));
		
		left.put(LEFT_LEG1_TOP_NAME, new BaseConfiguration(10, 80, 0));
		left.put(LEFT_LEG1_MIDDLE_NAME, new BaseConfiguration(60,20, 0));
		left.put(LEFT_LEG1_END_NAME, new BaseConfiguration(-45, 20, 0));
		left.put(LEFT_LEG2_TOP_NAME, new BaseConfiguration(40, 120, -30));
		left.put(LEFT_LEG2_MIDDLE_NAME, new BaseConfiguration(60, 20, 0));
		left.put(LEFT_LEG2_END_NAME, new BaseConfiguration(-45, 20, 0));
		left.put(LEFT_LEG3_TOP_NAME, new BaseConfiguration(10, 150, -30));
		left.put(LEFT_LEG3_MIDDLE_NAME, new BaseConfiguration(60, 20, 0));
		left.put(LEFT_LEG3_END_NAME, new BaseConfiguration(-45, 20, 0));
		left.put(RIGHT_LEG1_TOP_NAME, new BaseConfiguration(-10, -40, 0));
		left.put(RIGHT_LEG1_MIDDLE_NAME, new BaseConfiguration(60, 20, 0));
		left.put(RIGHT_LEG1_END_NAME, new BaseConfiguration(-45, 20, 0));
		left.put(RIGHT_LEG2_TOP_NAME, new BaseConfiguration(40,-80,30));
		left.put(RIGHT_LEG2_MIDDLE_NAME, new BaseConfiguration(60, 20, 0));
		left.put(RIGHT_LEG2_END_NAME, new BaseConfiguration(-45, 20, 0));
		left.put(RIGHT_LEG3_TOP_NAME, new BaseConfiguration(10,-110, 30));
		left.put(RIGHT_LEG3_MIDDLE_NAME, new BaseConfiguration(60, 20, 0));
		left.put(RIGHT_LEG3_END_NAME, new BaseConfiguration(-45, 20, 0));

		//right
		right.put(HEAD_NAME, new BaseConfiguration(30, 0, 0));
		right.put(BODY_NAME, new BaseConfiguration(20, 0, 0));
		right.put(MIDBODY_NAME, new BaseConfiguration(0, 0, 20));
		right.put(TAIL_NAME, new BaseConfiguration(10, 0, 0));

		right.put(ANTENNA1_TOP_NAME, new BaseConfiguration(270, 0, 0));
		right.put(ANTENNA1_END_NAME, new BaseConfiguration(60, 30, 0));
		right.put(ANTENNA2_TOP_NAME, new BaseConfiguration(270, 0, 0));
		right.put(ANTENNA2_END_NAME, new BaseConfiguration(60, -30, 0));
		
		right.put(LEFT_LEG1_TOP_NAME, new BaseConfiguration(10, 40, 0));
		right.put(LEFT_LEG1_MIDDLE_NAME, new BaseConfiguration(60, -20, 0));
		right.put(LEFT_LEG1_END_NAME, new BaseConfiguration(-45, -20, 0));
		right.put(LEFT_LEG2_TOP_NAME, new BaseConfiguration(40, 80, -30));
		right.put(LEFT_LEG2_MIDDLE_NAME, new BaseConfiguration(60, -20, 0));
		right.put(LEFT_LEG2_END_NAME, new BaseConfiguration(-45, -20, 0));
		right.put(LEFT_LEG3_TOP_NAME, new BaseConfiguration(10, 110, -30));
		right.put(LEFT_LEG3_MIDDLE_NAME, new BaseConfiguration(60, -20, 0));
		right.put(LEFT_LEG3_END_NAME, new BaseConfiguration(-45, -20, 0));
		right.put(RIGHT_LEG1_TOP_NAME, new BaseConfiguration(-10, -80, 0));
		right.put(RIGHT_LEG1_MIDDLE_NAME, new BaseConfiguration(60, -20, 0));
		right.put(RIGHT_LEG1_END_NAME, new BaseConfiguration(-45, -20, 0));
		right.put(RIGHT_LEG2_TOP_NAME, new BaseConfiguration(40,-80,30));
		right.put(RIGHT_LEG2_MIDDLE_NAME, new BaseConfiguration(60, -20, 0));
		right.put(RIGHT_LEG2_END_NAME, new BaseConfiguration(-45, -20, 0));
		right.put(RIGHT_LEG3_TOP_NAME, new BaseConfiguration(10,-150, 30));
		right.put(RIGHT_LEG3_MIDDLE_NAME, new BaseConfiguration(60, -20, 0));
		right.put(RIGHT_LEG3_END_NAME, new BaseConfiguration(-45, -20, 0));

		//random
		random.put(HEAD_NAME, new BaseConfiguration(30, 0, 0));
		random.put(BODY_NAME, new BaseConfiguration(20, 0, 0));
		random.put(MIDBODY_NAME, new BaseConfiguration(0, 0, 20));
		random.put(TAIL_NAME, new BaseConfiguration(10, 0, 0));


		random.put(ANTENNA1_TOP_NAME, new BaseConfiguration(270, 0, 0));
		random.put(ANTENNA1_END_NAME, new BaseConfiguration(60, 30, 0));
		random.put(ANTENNA2_TOP_NAME, new BaseConfiguration(270, 0, 0));
		random.put(ANTENNA2_END_NAME, new BaseConfiguration(60, -30, 0));
		
		random.put(LEFT_LEG1_TOP_NAME, new BaseConfiguration(10, 40, 0));
		random.put(LEFT_LEG1_MIDDLE_NAME, new BaseConfiguration(60, -20, 0));
		random.put(LEFT_LEG1_END_NAME, new BaseConfiguration(-45, -20, 0));
		random.put(LEFT_LEG2_TOP_NAME, new BaseConfiguration(40, 80, -30));
		random.put(LEFT_LEG2_MIDDLE_NAME, new BaseConfiguration(60, -20, 0));
		random.put(LEFT_LEG2_END_NAME, new BaseConfiguration(-45, -20, 0));
		random.put(LEFT_LEG3_TOP_NAME, new BaseConfiguration(10, 110, -30));
		random.put(LEFT_LEG3_MIDDLE_NAME, new BaseConfiguration(60, -20, 0));
		random.put(LEFT_LEG3_END_NAME, new BaseConfiguration(-45, -20, 0));
		random.put(RIGHT_LEG1_TOP_NAME, new BaseConfiguration(-10, -40, 0));
		random.put(RIGHT_LEG1_MIDDLE_NAME, new BaseConfiguration(60, 20, 0));
		random.put(RIGHT_LEG1_END_NAME, new BaseConfiguration(-45, 20, 0));
		random.put(RIGHT_LEG2_TOP_NAME, new BaseConfiguration(40,-80,30));
		random.put(RIGHT_LEG2_MIDDLE_NAME, new BaseConfiguration(60, 20, 0));
		random.put(RIGHT_LEG2_END_NAME, new BaseConfiguration(-45, 20, 0));
		random.put(RIGHT_LEG3_TOP_NAME, new BaseConfiguration(10,-110, 30));
		random.put(RIGHT_LEG3_MIDDLE_NAME, new BaseConfiguration(60, 20, 0));
		random.put(RIGHT_LEG3_END_NAME, new BaseConfiguration(-45, 20, 0));
	}
}
