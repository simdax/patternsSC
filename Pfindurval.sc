Pfindurval : FilterPattern{

	var times;
	*new{ arg times, pattern;
		^super.newCopyArgs(pattern, times)
	}
	embedInStream{ arg ev;
		var timeStream=times.asStream;
		var patstream=pattern.asStream;
		var timeval, now;
		while{
			timeval=timeStream.next;
			now=thisThread.beats;
			timeval.notNil
		}{
			while{ thisThread.beats<(now+timeval)}{
				pattern.embedInStream(ev)
			}
		}
		{nil.yield; ^ev}
	}
}

