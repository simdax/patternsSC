MyPsync : Psync{

	embedInStream { arg event;
		var item, stream, delta, elapsed = 0.0, nextElapsed, clock, inevent;
		var	localquant = quant.value(event), localmaxdur = maxdur.value(event);
		var cleanup = EventStreamCleanup.new;

		stream = pattern.asStream;

		loop {
			inevent = stream.next(event).asEvent;
			if(inevent.isNil) {
				if(localquant.notNil) {
					delta = elapsed.roundUp(localquant) - elapsed;
					if(delta > 0)
					{
						(
							type:\midi,
							midiout:MIDIOut(0),
							midicmd:\noteOff,
							lag:delta
						).yield};
					//					{ Event.silent(delta, event).yield };
					^cleanup.exit(event);
				};
			};
			cleanup.update(inevent);
			inevent.postln;
			delta = inevent.delta.postln;
			nextElapsed = elapsed + delta;

			if (localmaxdur.notNil and: { nextElapsed.round(tolerance) >= localmaxdur }) {
				inevent = inevent.copy;
				inevent
				.put(\delta, localmaxdur - elapsed)
				.put(\hasGate, false);
				event = inevent.yield;
				//		^cleanup.exit(event);
			} {
				elapsed = nextElapsed;
				event = inevent.yield;
			};
		};
	}
}
