package genericSerDeser.util;

public class Logger{


    public static enum DebugLevel { OUT,
    								NEWSERIALIZED,
    								NEWFOUND,
    								ERROR
                                   };

    private static DebugLevel debugLevel;


    public static void setDebugValue (int levelIn) {
	switch (levelIn) {
	  case 0: debugLevel = DebugLevel.OUT; break;
	  case 1: debugLevel = DebugLevel.NEWSERIALIZED; break;
	  case 2: debugLevel = DebugLevel.NEWFOUND; break;
	  case 3: debugLevel = DebugLevel.ERROR; break;
	}
    }

    public static void setDebugValue (DebugLevel levelIn) {
	debugLevel = levelIn;
    }

    public static DebugLevel getDebugValue () {
    	return debugLevel;
        }

    // @return None
    public static void writeMessage (String     message  ,
                                     DebugLevel levelIn ) {
	if (levelIn == debugLevel)
	    System.out.println(message);
    }

    public String toString() {
    	return "Debug Level is " + debugLevel;
    }
}
