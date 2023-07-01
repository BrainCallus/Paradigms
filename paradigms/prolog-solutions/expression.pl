:- consult('grammar.pl').

lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], R) :- lookup(K, T, R).


operation(op_add, Term1, Term2, Res) :- Res is Term1 + Term2, !.
operation(op_subtract, Term1, Term2, Res) :- Res is Term1 - Term2, !.
operation(op_multiply, Term1, Term2, Res) :- Res is Term1 * Term2, !.
operation(op_divide, Term1, Term2, Res) :- Res is Term1 / Term2, !.
operation(op_negate, Term, Res) :- Res is -1 * Term, !.
operation(op_sin, Term, Res):- Res is sin(Term), !.
operation(op_cos, Term, Res):- Res is cos(Term), !.
operation(op_sinh, Term, Res):- Res is / ((exp(Term)-exp(-Term)), 2), !.
operation(op_cosh, Term, Res):- Res is / ((exp(Term)+exp(-Term)), 2), !.

evaluate(const(Number), _, Number):- !.

evaluate(variable(Name), Variables, Res) :-
    atom_chars(Name, [H | _]),
    lookup(H, Variables, Res), !.

evaluate(operation(BinOper, Term1, Term2), Variables, Res) :-
   evaluate(Term1, Variables, NewTerm1),
   evaluate(Term2, Variables, NewTerm2),
   operation(BinOper, NewTerm1, NewTerm2, Res), !.

evaluate(operation(UnaryOper, Term), Variables, Res) :-
   evaluate(Term, Variables, NewTerm),
   operation(UnaryOper, NewTerm, Res), !.



polish_str(Expression, Atom) :- polish_strImpl(Expression, Atom), !.

polish_strImpl(Expression, Atom) :-
    ground(Expression),
    phrase(parse_polish(Expression), Chars),
    atom_chars(Atom, Chars).

polish_strImpl(Expression, Atom) :-
    atom(Atom),
    atom_chars(Atom, Chars),
    phrase(parse_polish(Expression), Chars).

