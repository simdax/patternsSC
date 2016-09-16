// PDEFS{
// 	classvar <names;
// 	classvar <dictCC;
// 	*check{ arg degree;
// 		^if(degree.isInteger){(degree+[0, 2, 4])}{degree};
// 	}
// 	*checkType{ arg obj;
// 		switch(obj.class,
// 			Ref, {^obj.value},
// 			Array, {^obj.bubble},
// 			{^obj.asArray}
// 		)
// 	}
// 	*checkPseq{arg array, op=\bubble;
// 		^Pseq(
// 			array.collect({|x|  if(x.isCollection, {
// 				x.perform(op)
// 			}, {x})
// 			}),
// 			inf
// 		)
// 	}
// 	*checkPat{ arg obj;
// 		switch(obj.class,
// 			Array, {^obj.pseq()},
// 			{^obj}
// 		);
// 	}
// 	// *getPbind{
// 	// 	arg type;
// 	// 	try{^patternpairs} // pbind
// 	// 	try{^pattern.patternpairs} // pbindf
// 	// 	try{^patterns.collect(_.patternpairs)}
// 	// }
// 	*calcLegato{
// 		arg partition;
// 		^partition.inject([], { arg a,b;
// 			switch(b,
// 				0, {
// 					if(a.isEmpty, {
// 						a=[1]
// 					},
// 						{a.last=a.last+1}
// 					);
// 				},
// 				{a=a.add(1)}
// 			)
// 		})
// 		.collect({|x| [x,(1!(x-1))] }).flat
// 	}
// 	*addCC{ arg name;
// 		if(dictCC[name].isNil)
// 		{
// 			dictCC.put(name, 0)
// 		}
// 		{dictCC.put(name, dictCC[name]+1)};
// 	}
// 	*reset{
// 		Pdef.removeAll;
// 		Pdefn.removeAll;
// 		this.initClass
// 	}
// 	//private
// 	*myPdef{ arg name, h=Points.rand(5,1,4), r=Rythme.rand(5,8), parent=MainWindow(1), nbItems=2;
// 		Pdef(name, Pbind(
// 			\type, \midi,
// 			\midiout, Pfunc{arg ev; MIDIOut(ev.out)},
// 			\degree,    Plazy{arg ev; ev.notes},
// 			\dur, Plazy{arg ev; ev.rythme},
// 		))
// 		.quant_(Quant.default)
// 		.set(*[\notes, h , \rythme, r]++Defaults.pbind)
// 		.gui(Defaults.size+nbItems, parent)
// 	}
// 	// go
// 	*initClass{
// 		names=Set[\arpeggiator, \ornement, \out, \basse];
// 		dictCC=IdentityDictionary();		
// 		Pdef(\out,
// 			Pbindf(
// 				Pn(Psym(
// 					Plazy
// 					{
// 						arg ev; var forme=ev[\forme];
// 						Pseq(
// 							forme.collectAs(_.asSymbol, Array), 1
// 						)
// 					}
// 				)),
// 				\root, Pn(Plazy{arg ev; Pstep(Pseq(ev[\roots]), 8) }),
// 				\scale, Pn(Plazy{arg ev; Pstep(Pseq(ev[\mode].collect(Scale.at(_))) ,8) }),
// 			)
// 		).set(*Defaults.forme);

// 		Pdef(\basse,
// 			{
// 				arg distance=7, dur=8, min=0, max=20, even=2, sortie,
// 				vecteurs=[1,3,5], st=(..5);
// 				var res;
// 				if([0,1].includes(distance)){distance=distance+7};
// 				if(distance==6){st=(..6)};
// 				res=Test({ arg distance, min, max, even, set,vecteurs;
// 					Partition(distance.asInt).go
// 					.select(_.every(vecteurs.includes(_)))
// 					.select({|x|
// 						(min < x.size) and: (x.size < max)
// 						and: (x.size % even == 0)
// 					});
// 				},  nb:200, verbeux:false,
// 					envir:(distance:distance, min:min, max:max, even:even, vecteurs:vecteurs, set:st),
// 					solution:
// 					(
// 						//		distance: _+7,
// 						vecteurs: { |x| x.add(((..6).asSet-x).choose).reject(_==nil) }
// 					)
// 				);
// 				//	var res=[ [ 2, 2, 2, 1 ], [ 3, 2, 1, 1 ], [ 2, 1, 1, 1, 1, 1 ], [ 5, 2 ] ],res2, set=(..5);
// 				res=Test({
// 					res[0].value.copy.scramble.collect{ arg x;
// 						([x]++x.allRotations).select{ arg x;
// 							var y=x.integrate % 7;
// 							(y.asSet - st).isEmpty
// 						};				
// 					};
// 				});
// 				res;
// 				//		this.envir ++ (res:res2.postln)
// 				Pbind(
// 					\type, sortie !? {\phrase} ?? {\midi}, 
// 					\instrument, sortie ?? {'nil'},
// 					\degree, res.flatten.choose.pseq.integrate %7,
// 					\dur, dur / res.size,
// 				)
// 			}
// 		);
		
// 		Pdef(\arpeggiator, {
// 			arg degree, dur, octave, //scale,
// 			operator='<',	partitions=1, legat=1,
// 			formuleOctave=[0], formule=[0], accents=[0],
// 			chan=0, amp=0.3;
// 			var size,  couleur=PDEFS.check(degree.value);
// 			var patternDegree=PdegreeToKey(
// 				Pseries2(formule.pseq(inf), 1+accents.pseq(inf)),
// 				couleur, 7);
// 			#operator, partitions, degree =
// 			[operator, partitions, degree].collect(PDEFS.checkType(_));
// 			size=[formule, formuleOctave, accents].collect(_.size).maxItem;
// 			Pbind(
// 				\legato, 1,
// 				\type, Pfunc{
// 					if(partitions.size==1)
// 					{
// 						partitions[0].switch(
// 							0, \rest, [0], \rest,
// 							1, \midi,
// 							\phrase
// 						)
// 					}{\phrase}
// 				},
// 				\partitions,partitions, 
// 				\operator, operator,
// 				\instrument, \ornement,
// 				\chan, chan,
// 				\amp, amp,
// 				\degree, patternDegree,
// 				\octave, octave+Pseq(formuleOctave, inf),
// 				\dur, (dur*legat) / size
// 			)
// 		});		
// 		Pdef(\ornement, {
// 			arg degree, dur, partitions, operator, next, round=true;
// 			var	ornement;
// 			if(partitions.isCollection, {
// 				partitions=partitions.normalizeSum
// 			}, {
// 				partitions=partitions.reciprocal ! partitions
// 			});
// 			ornement=operator.switch(
// 				'>', {((degree+partitions.size-1)..degree)},
// 				'<', {((degree-partitions.size+1)..degree)},
// 				'->', {
// 					var step= (next - degree) / partitions.size;
// 					var res;
// 					if(step==0){
// 						Array.rand(partitions.size, -1,1)
// 					}
// 					{res=(degree, degree+step .. next );
// 						degree=(if(round){res.round}{res}).pseq(inf);
// 						0}
// 				},
// 				{operator.asArray}
// 			).pseq(inf);
// 			Pbind(
// 				\type, \midi,
// 				\degree, degree + ornement,
// 				\dur,Prorate(partitions, dur)
// 			)
// 		});

		
// 	}

	
// }


/*

	Pbind(\type, \phrase, \instrument, \ornement, 
	\degree, Notes[0,1,2,3].bubble
	).trace.play


*/
// var val=2;
// Pbind(
// 	\degree, Notes.rand(10, 1,4) ,
// 	\dur, 0.5,
// 	\midiout, MIDIOut(1),
// 	\count, (Ptime()).trace,
// 	\type, Pif( Pkey(\time)>val, \midi, \rest).trace,
// 	\count, Pif( Pkey(\time)>val, 0, Ptime()).trace, 
// 	\legato, val,
// ).play


+ Pattern {
	//			Pbind(\degree, Notes[0,1,2,3], \dur, 0.5).atAll(1).trace.play
	atAll{ arg val;
		^Pbindf(this,
			\midiout, MIDIOut(1),
			\type, Pif( (Ptime()%val)<val, \midi, \rest), 
			\legato, val,
		)
	}
	arpeggie{ arg formule=[0, 1, 2, 1], formuleOctave=[0], densite=4;
		^Pbindf(this,
			\type, \phrase,
			\instrument, \arpeggiator,
			\legato, 1,
			\formule, formule.bubble,
			\formuleOctave, formuleOctave.bubble,
			\densite, densite,
		)
	}
	accords{ arg formuleR=[3, 3, 2];
		^Pbindf(this,
			\type, \phrase, \instrument, \accords,
			\formuleR, formuleR.bubble,
		)
	}	
}
+ Pdef{
	toggle{
		if(this.isPlaying)
		{this.stop}
		{this.play}
	}
	// TODO catch pattern
	// trace{
	// 	this.pattern=this.pattern.trace
	// }

}
+ Pattern {

	midi{
		arg out=5, chan;
		var m=MIDIOut(out);
		var type=
		if(
			try{this.patternpairs.includes(\phrase)}.asBoolean or:
			try{this.pattern.patternpairs.includes(\phrase)}.asBoolean or: // Pbindf
			try{this.patterns.postln.any { |x|
				try{x.patternpairs.postln.includes(\phrase)}
				{
					try{x.pattern.patternpairs.postln.includes(\phrase)}
				}
			}
			}.asBoolean // Pchain
		)
		{\phrase}{\midi};
		type.postln;
		
		^Pbindf(*[this, \midiout, m, \type, type]++(chan!?{[\chan, chan]})) 
	}
}


+Ppar{
	chan{ arg chans;
		this.list=
		(list+++chans).clump(2).collect{ arg x, y;
			x.midi(y)
		}
	}
}

