:- load_library('alice.tuprolog.lib.DCGLibrary').

nonvar(V, _) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).

cut_space --> [].
cut_space --> [' '], cut_space.
trim(Str) --> cut_space, Str, cut_space.


bin_operation(op_add) --> ['+'].
bin_operation(op_subtract) --> ['-'].
bin_operation(op_multiply) --> ['*'].
bin_operation(op_divide) --> ['/'].
uno_operation(op_negate) --> ['n', 'e', 'g', 'a', 't', 'e'].
uno_operation(op_sin) -->['s','i','n'].
uno_operation(op_cos) --> ['c','o', 's'].
uno_operation(op_sinh) -->['s','i','n','h'].
uno_operation(op_cosh) --> ['c','o', 's', 'h'].


digits_p([]) --> [].
digits_p([H | T]) -->
  { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-']) },
  [H],
  digits_p(T).

expr_p(const(Value)) -->
    { nonvar(Value, number_chars(Value, Chars)) },
    trim((
        digits_p(Chars)
    )),
    { Chars = [_ | _], \= (Chars, ['-']), number_chars(Value, Chars) }.


variables_p([]) --> [].
variables_p([H | T]) -->
  { member(H, ['x', 'y', 'z']) },
  [H],
  variables_p(T).


expr_p(variable(Name)) -->
    { nonvar(Name, atom_chars(Name, Chars)) },
    trim((
        variables_p(Chars)
    )),
    { Chars = [_ | _], atom_chars(Name, Chars) }.



parse_polish(const(Value)) --> expr_p(const(Value)).
parse_polish(variable(Name)) --> expr_p(variable(Name)).

parse_polish(operation(BinOper, Term1, Term2)) -->
    trim((
        bin_operation(BinOper),
        [' '],
        parse_polish(Term1),
        [' '],
        parse_polish(Term2)
    )).

parse_polish(operation(UnaryOper, Term)) -->
    trim((
        uno_operation(UnaryOper),
        [' '],
        parse_polish(Term)
    )).