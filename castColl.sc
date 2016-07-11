// + Object{
// 	// "primitif"
// 	asPoints{
// 		if(this.class==Points){^this};
// 		^this.as(Points)
// 	}
// 	asIntervalles{
// 		^this.as(Intervalles)
// 	}
// 	//"intermediaires"
// 	asIndices{
// 		^this.asSet.as(Array).sort.as(Indices)
// 	}
// 	asVecteurs{
// 		if(this.class==Vecteurs){^this};
// 		^this.as(Vecteurs)
// 	}
// 	asNotes{
// 		^this.asPoints.as(Notes)
// 	}
// 	// encore plus composites
// 	asMelodie{
// 		^switch(this.class,
// 			Melodie, {	this	},
// 			MelSilence, {this},
// 			{this.asIntervalles.as(Melodie)}
// 		)
// 	}
// 	asMotif{
// 		^this.class.switch(
// 			Motif, {^this},
// 			Intervalles, {^Motif(nil, this)},
// 			Melodie, {^Motif(nil, this)},
// 			Rythme, {^Motif(this, nil)},
// 		)
// 	}
// 	//
// 	asRythme{
// 		var tmp;
// 		// petit cast intelligent ;)
// 		if([Rythme, RythmeFloat].includes(this.class), {
// 			^this
// 		});
// 		tmp=this.as(Array);
// 		if(tmp.collect(_.class).includes(Float), {
// 			^tmp.as(RythmeFloat)
// 		}, {^tmp.as(Rythme)});
// 	}
// 	analyse{
// 		^Analyse(this)
// 	}
// 	asChords{
// 		if(this.class==Chords, {
// 			^this
// 		});
// 		^this.as(Vecteurs).asChords
// 	}
// }

+Collection{
	// ça c'est trop ze truc de BOGOSS
	pseq{ arg rep=1, random=false;
		^random.if
		{Prand(this, this.size)}
		{Pseq(this, rep)}
	}
	ptuple{ arg rep=1;
		^Ptuple(this, rep)
	}
	pstutter{ arg rep;
		^Pstutter(rep, this.pseq)
	}
}

+ Integer {
	asNotes{
		^this.asArray.asNotes
	}
}
+ String{
	asNotes{
		^(this.ascii-1).asNotes
	}
}