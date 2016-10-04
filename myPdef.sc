// goal is to extend Pdef manipulation
/*
add collect and trace softly
modifyng without restart pattern
*/

PdefCollect{
	classvar <all;
	var <key,
	<actual, <esp;
	*initClass{all=IdentityDictionary()}
	*new{ arg key;
		^Pfunc{
			if(all.at(key).isNil)
			{this.all.put(key,super.newCopyArgs(key).init)}
			{this.all}
			.at(key).actual ? ()
		}
	}
	doesNotUndertand{ arg msg;
		^this.collect(_.perform(msg))
	}
	init{
		Pdef(key).addDependant(this);
		this.initPat;
	}
	initPat{
		esp=Pbindf(Pdef(key).collect{arg x;
			actual=x},
			\type, \rest).asEventStreamPlayer
	}
	update{arg qui, que, quoi;// qui, quoi, comment;
		[qui, que, quoi].postln;
		if(qui.isKindOf(Pdef)){esp.perform(que)};
		if(que==\source){
			if(esp.isPlaying){
				esp.stop;this.initPat;esp.play}
			{this.initPat};
			
		}
	}
	// embedInStream{ arg in;
	// 	loop{
	// 		in=(in++actual).yield
	// 	}
	// }
}

// + Pdef{
// 	play{ //arg ... args;
// 		super.play();//*args);
// 		this.changed(\play)
// 	}
// 	stop{ //arg ... args;
// 		super.stop;
// 		this.changed(\stop)
// 	}
// 	midi{ arg port, chan;
// 		//"tchiop ?".postln;
// 		this.source_(this.source.midi(port, chan));
// 	}
//}



