PSoleil : ListPattern{
	embedInStream{
		arg in;
		var chrono=Ptime().asStream, val, t;
		list=list.iter.repeat;
		val;
		t=list.next.yield;
		loop{if(chrono.next>repeats){
			in=t.yield
		}{
			t=list.next;
			in=t.yield
		}}
	}
}

// Pbind(
// 	\do, PSoleil("acaba", 6),
// 	\dur, 0.5
// ).trace.play
