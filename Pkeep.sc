// keep only certain values from a flow
PkeepVal : Pattern{
	var <>values;
	*new{arg ... v; ^this.newCopyArgs(v)}
	embedInStream{
		arg in;
		loop{
			in=in.keeping(*values).yield
		}
	}
}
PdropVal : Pattern{
	var <>values;
	*new{arg ... v; ^this.newCopyArgs(v)}
	embedInStream{
		arg in;
		loop{
			in=in.removing(*values).yield
		}
	}
}

/*
	a=PdropVal(\bob, \joe) <> Pbind(\henri, 8, \bob, 6)
	a.trace.play
*/
