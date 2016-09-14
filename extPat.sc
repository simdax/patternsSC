+ Pattern{

	==> { arg  slave, dur=inf, last=0;
		^Pspawn(Pbind(\method, \seq, \delta, last, \pattern, Pfunc{arg in;
			var copy; copy=in.copy; copy[\delta]=nil; 
			slave.finDur(dur) <> copy
		}) <> this)
	}
	<== { arg  master, dur=inf, last=0;
		^Pspawn(Pbind(\method, \seq, \delta, last, \pattern, Pfunc{arg in;
			var copy; copy=in.copy; copy[\delta]=nil; 
			this.finDur(dur) <> copy
		}) <> master)
	}

}

//
// z=Pbind(\a, [3, 2, 4].pseq);
// x=Pbind(\b, [9,8].pseq);
// d=Pbind(\c, [5,10].pseq) 
// a=d==>z==>x;
// a.trace.play
