list('1', [a,b,c]).
list('2', [1,2,3]).


 dosomething([]).
 dosomething([H|T]) :- process(H), dosomething(T).
 
 process(H):-
	write(H).