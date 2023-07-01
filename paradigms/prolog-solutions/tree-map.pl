node(Key, Prior, Data, Left, Right, node(Key, Prior, Data, Left, Right)).

inc(N, R) :- number(N), !, R is N + 1.
inc(N, R) :- number(R), !, N is R - 1.

concat([], B, B).
concat([H | T], B, [H | R]) :- concat(T, B, R).

getKey(node(Key, _, _, _, _), Key):- !.
getData(node(_, _, Data, _, _), Data):- !.
getLeft(node(_, _, _, Left, _), Left):- !.
getRight(node(_, _, _, _, Right), Right):- !.

newNode(Key, Data, Node):-
    rand_float(RND),
    node(Key, RND, Data, undefined, undefined, Node), !.

map_split(undefined, _, (undefined, undefined)):-!.
map_split(node(Key, Prior, Data, L, R), SKey, (TL, TR)):-
    SKey >= Key, !,
    map_split(R, SKey, (Res, TR)),
    node(Key, Prior, Data, L, Res, TL).

map_split(node(Key, Prior, Data, L, R), SKey, (TL, TR)):-
    map_split(L, SKey,(TL, Res)),
    node(Key, Prior, Data, Res, R, TR), !.


merge(undefined, T2, T2):- !.
merge(T1, undefined, T1):- !.
merge(node(K1, P1, D1, L1, R1), node(K2, P2, D2, L2, R2), node(K1, P1, D1, L1, NR)):-
    P1 >= P2, !,
    merge(R1, node(K2, P2, D2, L2, R2), NR).

merge(Node1, node(K2, P2, D2, L2, R2), node(K2, P2, D2, NL, R2)):-
    merge(Node1, L2, NL), !.

insert(undefined, Node, Node):- !.
insert(Node, undefined, Node):- !.
insert(Tree, Node, NTree):-
    getKey(Node, Key),
    map_split(Tree, Key, (TL, TR)),
    merge(TL, Node, Res),
    merge(Res, TR, NTree), !.

map_build(LST, Tree) :- map_build(LST, undefined, Tree).
map_build([], T, T) :- !.
map_build([(Key, Val) | XS], Root, Tree) :-
    newNode(Key, Val, NNode),
    insert(Root, NNode, NRoot),
    map_build(XS, NRoot, Tree).


map_get(node(Key, _, Val, _, _), Key, Val):- !.

map_get(Node, K, V):-
    getKey(Node, Key),
    K < Key, !,
    getLeft(Node, Left),
    map_get(Left, K, V).

map_get(Node, K, V):-
    getKey(Node, Key),
    K > Key, !,
    getRight(Node, Right),
    map_get(Right, K, V).

map_remove(undefined, _, undefined):-!.
map_remove(Tree, Key, Res):-
    inc(K,Key),
    map_split(Tree, K, (TL, TR)),
    map_split(TR, Key, (T1, T2)),
    merge(TL, T2, Res), !.


map_put(Tree, Key, Val, NRoot):-
    map_remove(Tree, Key, NTree),
    newNode(Key, Val, Node),
    insert(NTree, Node, NRoot), !.

% case getMin(undefined, _) special not considered to get false when tree is empty
getMin(node(Key, Prior, Data, undefined, R), Res):-
    node(Key, Prior, Data, undefined, R, Res), !.

getMin(node(Key, Prior, Data, L, R), Res):-
    getMin(L, Res), !.

ceilingNode(Node, K, Res):-
    inc(SKey, K),
    map_split(Node, SKey, (_, TR)),
    getMin(TR, Res), !.

map_getCeiling(node(Key,_,Val,_, _), Key, Val):- !.
map_getCeiling(Node, K, V):-
    ceilingNode(Node, K, CelNode),
    getData(CelNode, V), !.


map_putCeiling(Node, Key, Val, Res):-
    ceilingNode(Node, Key, CelNode),
    getKey(CelNode, CeilKey),
    map_put(Node, CeilKey, Val, Res), !.

map_putCeiling(Node, K, _, Node):-
    \+ ceilingNode(Node, K, _), !.

map_keys(undefined, []):- !.
map_keys(node(Key, _, _, Left, Right), Res):-
    map_keys(Left, LeftLst),
    concat(LeftLst,[Key], MList),
    map_keys(Right, RList),
    concat(MList, RList, Res), !.

map_values(undefined, []):- !.
map_values(node(_, _, Val, Left, Right), Res):-
    map_values(Left, LeftLst),
    concat(LeftLst, [Val], MList),
    map_values(Right, RList),
    concat(MList, RList, Res), !.