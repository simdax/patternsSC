// goal is to extend Pdef manipulation
/*
add collect and trace softly
modifyng without restart pattern
*/

PdefCollect{
	var <key, <actual;
	*new{ arg key;
		^super.newCopyArgs(key).init
	}
	init{
		Pdef(key).addDependant(this);
	}
	update{arg qui, que, quoi;// qui, quoi, comment;
		//		[qui, que, quoi].postln;
		que.switch(\play, {
			Pbindf(Pdef(key).collect{arg x; actual=x}, \type, \rest).play
		})
	}
}

// a=Pdef(\bob)
// SimpleController(a).put(\play, {arg ...g; g.postln})
// Pdef(\bob, Pbind(\io, 8))
// Pdef(\bob).play; 
// a={Pdef(\bob).player}
// a.value

