PSoleil : ListPattern{
	embedInStream{
		arg in;
		var chrono=Ptime().asStream, val, t;
		list=list.iter.repeat;
		// begin with a yield
		t=list.next.yield;
		// then check
		loop{if(chrono.next>repeats){
			in=t.yield
		}{
			t=list.next;
			in=t.yield
		}}
	}
}

/*
Pbind(
	\do, PSoleil("acaba", 3),
	\dur, 0.25
).finDur(6).trace.play
*/