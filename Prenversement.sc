
PchordsList : ListPattern {

	embedInStream{

		//		var list=Basse[[5,1],[2,3,4]], repeats=1;
		var seed=500.rand;
		var res=[0,2,4], rot=0;
		var l=(if(list.class==Array){
			Pseq(
				list.first.asArray.wrap(-3,3) ++
				list.pseq.differentiate.wrap(-3,3)
			)
		}{
			Pseq(
				[
					Pseed(Pn(seed,1), list.first),
					Pseed(Pn(seed, list.size-1), list).differentiate
				]				
			).wrap(-3,3)
		}).asStream;
		//					a=r{
		loop{
			var n=l.next;
			if(n.isNil){^nil};
			res=(res+(
				n.switch(
					0, [0,0,0], 1, [1,1,1], -1, [-1,-1,-1],
					2, [-1,0,0], -3, [-1,-1,0],
					-2, [0,0,1], 3, [0,1,1]
				)
				.rotate(rot)
			));
			rot=rot+(
				n.switch(
					0,0,1,0,2,1,3,1,-3,-1,-2,-1,-1,0
				)
			);
			res.asRef.yield
		}
		// };
		// a.iter.nextN(5)
		
	}
}

//stay in range
Pchords2 : FilterPattern{

	embedInStream{ arg inval;
		var rot=0, res=[0,2,4];
		var l=pattern.asStream;
		res=(l.next(inval)+[0,2,4]);
		inval=res.copy.wrap(-1,5).sort.embedInStream(inval);
		loop{
			var n=l.next(inval);
			if(n.isNil){^nil}{n=n.wrap(-3,3)};
			res=(res+(
				n.switch(
					0, [0,0,0], 1, [1,1,1], -1, [-1,-1,-1],
					2, [-1,0,0], -3, [-1,-1,0],
					-2, [0,0,1], 3, [0,1,1]
				)
				.rotate(rot)
			));
			rot=rot+(
				n.switch(
					0,0,1,0,2,1,3,1,-3,-1,-2,-1,-1,0
				)
			);
			if(res.maxItem > 5, {
				res[res.maxIndex]=res.maxItem-7
			});
			if(res.minItem < -1, {
				res[res.minIndex]=res.minItem+7
			});
			inval=(res.copy.sort).embedInStream(inval)
		}
		^inval
	}
	
}


Pchords : FilterPattern{

	embedInStream{ arg inval;
		var rot=0, res=[0,2,4];
		var l=pattern.asStream;
		res=(l.next(inval)+[0,2,4]);
		inval=res.embedInStream(inval);
		loop{
			var n=l.next(inval);
			if(n.isNil){^nil}{n=n.wrap(-3,3)};
			res=(res+(
				n.switch(
					0, [0,0,0], 1, [1,1,1], -1, [-1,-1,-1],
					2, [-1,0,0], -3, [-1,-1,0],
					-2, [0,0,1], 3, [0,1,1]
				)
				.rotate(rot)
			));
			rot=rot+(
				n.switch(
					0,0,1,0,2,1,3,1,-3,-1,-2,-1,-1,0
				)
			);
			inval=(res.copy.sort).embedInStream(inval)
		}
		^inval
	}
	
}
/*

Pchords([2].pseq(inf)).iter.nextN(10)
Pchords2([2].pseq(inf)).iter.nextN(10)

	PchordsList([0,1,2,3,4].scramble.postln.differentiate.postln).iter.nextN(7)

	PchordsList([0,1,2]).iter.nextN(5)
	PchordsList(Basse[5, [1,2],[4,5,3]]).iter.nextN(2)
 
Pbind(
	\basse, Prand((..6), inf),
	\degree, Pchords(Pdiff(Pkey(\basse))),
\dur, 0.25
	).midi(5).trace.play

*/

Pclosest : Pattern{
	var <threshold;
	*new{ arg threshold=1;
		^super.newCopyArgs(threshold)
	}
	embedInStream{
		arg ev;
		var nv=0;
		var t=threshold.asStream, tval;
		while{tval=t.next/2.pow(nv); tval.notNil}
		{
			if(ev.dur > tval)
			{ nv=nv+1 ; ev=ev.degree.closestHarmo (ev.couleur).yield }
			{ nv=0 ; ev=ev.degree.yield }
		}
		^ev
}
}



/*

Pbind(
	\degree, Pseries(0,1, inf),
	\dur, Prand([0.5,1,2], inf)/4,
	\couleur, [0,2,4],
	\degree, Pclosest(1)
	).trace.play

*/