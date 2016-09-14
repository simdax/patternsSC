// goal is to extend Pdef manipulation
/*
add collect and trace softly
modifyng without restart pattern
*/

PdefCollect : Pattern{
	var <key, <actual, <esp;
	*new{ arg key;
		^super.newCopyArgs(key).init
	}
	init{
		Pdef(key).addDependant(this);
		this.initPat;
	}
	initPat{
		esp=Pbindf(Pdef(key).collect{arg x; actual=x},
			\type, \rest).asEventStreamPlayer
	}
	update{arg qui, que, quoi;// qui, quoi, comment;
		[qui, que, quoi].postln;
		if(qui.isKindOf(Pdef)){esp.perform(que)};
		if(que==\source){esp.stop;this.initPat;esp.play}
	}
	embedInStream{ arg in;
		loop{
			in=(in++actual).yield
		}
	}
}

+ Pdef{

	play{
		super.play;
		this.changed(\play)
	}
	stop{
		super.stop;
		this.changed(\stop)
	}
	

}



