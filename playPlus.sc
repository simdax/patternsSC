+Pdef{
	play{
		super.play;
		this.changed(\play)
	}
}
// + EventPatternProxy{
// 	play { arg argClock, protoEvent, quant, doReset=false;
// 		playQuant = quant ? this.quant;
// 		if(player.isNil) {
// 			player = EventStreamPlayer(this.asProtected.asStream, protoEvent);
// 			player.play(argClock, doReset, playQuant);
		
// 		} {
// 			// resets  when stream has ended or after pause/cmd-period:
// 			if(player.streamHasEnded or: { player.wasStopped }) { doReset = true };
// 			protoEvent !? { player.event = protoEvent };
// 			if(player.isPlaying.not) {
// 				player.play(argClock, doReset, playQuant);
// 			} {
// 				if(doReset) { player.reset };
// 			}
// 		}
// 	}
// }