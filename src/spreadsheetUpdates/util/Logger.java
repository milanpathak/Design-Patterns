package spreadsheetUpdates.util;

public class Logger{


    public static enum DebugLevel { CONSTRUCTOR,
    								CYCLE,
    								VALUE_UPDATE,
    								TOTAL
                                   };

    private static DebugLevel debugLevel;


    public static void setDebugValue (int levelIn) {
	switch (levelIn) {
	  case 4: debugLevel = DebugLevel.CONSTRUCTOR; break;
	  case 3: debugLevel = DebugLevel.CYCLE; break;
	  case 2: debugLevel = DebugLevel.VALUE_UPDATE; break;
	  case 1: debugLevel = DebugLevel.TOTAL; break;
	}
    }

    public static void setDebugValue (DebugLevel levelIn) {
	debugLevel = levelIn;
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
