init(LIM):- L is LIM+1, generate_sieve(2, L).

last_ind(0).

% UTILS
inc(N, R) :- number(N), !, R is N + 1.
inc(N, R) :- number(R), !, N is R - 1.

is_divisor(X, Y):- 0 is Y mod X.

%---------------------------------------

concat([], B, B).
concat([H | T], B, [H | R]) :- concat(T, B, R).


add_non_primes(CUR, INIT, LIM):-
    CUR<LIM, !,
    assert(non_primes(CUR)),
    NCUR is CUR + INIT,
    add_non_primes(NCUR, INIT, LIM).

add_non_primes(CUR, INIT, LIM):- CUR >= LIM,!.

add_prime(X):-
    %assert(primes(X)),
    last_ind(I),
    assert(indexed_primes(I, X)),
    inc(I, I1),
    retract(last_ind(I)),
    assert(last_ind(I1)).

generate_sieve(2, LIM):-
    add_prime(2),
    add_non_primes(4, 2, LIM),
    generate_sieve(3, LIM).

generate_sieve(X, LIM):-
    X < LIM,
    \+ non_primes(X),
    add_prime(X),
    XX is X*X, XX < LIM, !, INIT is X*2,
    add_non_primes(XX, INIT, LIM),
    NX is X+2,
    generate_sieve(NX, LIM).


generate_sieve(X, LIM):-
    X < LIM,
    non_primes(X),!,
    NX is X+2,
    generate_sieve(NX, LIM).

generate_sieve(X, LIM):-
    X < LIM,
    \+ non_primes(X),
    add_prime(X),
    X*X >= LIM, !,
    add_rest_primes(X, LIM).

add_rest_primes(X, LIM):- X>=LIM,!.
add_rest_primes(X, LIM):- X<LIM, non_primes(X),!, inc(X, NX), add_rest_primes(NX, LIM).
add_rest_primes(X, LIM):- X<LIM, \+ non_primes(X), !, add_prime(X), inc(X, NX), add_rest_primes(NX, LIM).


composite(X):- \+ prime(X).
prime(X):- indexed_primes(P, X).

prime_divisors(X, LST):- factorizeNum(X, 0, LST, false).
square_divisors(X, LST):- factorizeNum(X, 0, LST, true).
cube_divisors(X, LST):-
    factorizeNum(X, 0, Res, false),
    tripleElements(Res, LST).

tripleElements([], []):- !.
tripleElements([X | XS], Res):-
    tripleElements(XS, R1),
    concat([X, X, X], R1, Res), !.

factorizeNum(1, PR_IND, [], F):- !.
factorizeNum(NUM, PR_IND, [NUM, NUM], true):- indexed_primes(PR_IND, DIV), NUM < DIV, !.
factorizeNum(NUM, PR_IND, [NUM], false):- indexed_primes(PR_IND, DIV), NUM < DIV, !.
factorizeNum(NUM, PR_IND, LST, F):-
    indexed_primes(PR_IND, DIV),
    is_divisor(DIV, NUM),!,
    divide_while_divides(NUM, DIV, X, R1, F),
    inc(PR_IND, NIND),
    factorizeNum(X, NIND, R2, F),
    append(R1, R2, LST).


factorizeNum(NUM, PR_IND, LST, F):-
    indexed_primes(PR_IND, DIV),
    NUM >= DIV,
    \+ is_divisor(DIV, NUM), !,
    inc(PR_IND, NIND),
    factorizeNum(NUM, NIND, LST, F).


divide_while_divides(NUM, DIV,NUM, [],F):- \+ is_divisor(DIV, NUM),!.
divide_while_divides(NUM, DIV, X, R, F):-
    is_divisor(DIV, NUM), !,
    NNUM is NUM/DIV,
    divide_while_divides(NNUM, DIV, NX, R1, F),
    X is NX,
    concatenate(DIV, R1, R, F).

concatenate(DIV, R1, R, true):-concat(R1, [DIV, DIV], R).
concatenate(DIV, R1, R, false):-concat(R1, [DIV], R).




